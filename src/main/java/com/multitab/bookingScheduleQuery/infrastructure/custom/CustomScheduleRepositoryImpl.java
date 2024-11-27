package com.multitab.bookingScheduleQuery.infrastructure.custom;

import com.multitab.bookingScheduleQuery.dto.out.MentoringSessionScheduleResponseDto;
import com.multitab.bookingScheduleQuery.messagequeue.messageIn.AfterSessionUserOutDto;
import com.multitab.bookingScheduleQuery.entity.Schedule;
import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import com.multitab.bookingScheduleQuery.entity.vo.Status;
import com.multitab.bookingScheduleQuery.serviceCall.dto.in.SessionTimeResponseOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.swing.text.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomScheduleRepositoryImpl implements CustomScheduleRepository {
    private final MongoTemplate mongoTemplate;


    @Override
    public void updateMentorSchedule(String userUuid, List<ScheduleList> scheduleLists, String yearMonth ) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userUuid").is(userUuid).and("yearMonth").is(yearMonth));
        Update update = new Update();
        update.push("scheduleLists").each(scheduleLists);

        mongoTemplate.updateFirst(query, update, Schedule.class);
    }

    @Override
    public void cancelMentorSchedule(String userUuid, String yearMonth, String sessionUuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userUuid").is(userUuid)
                .and("yearMonth").is(yearMonth)
                .and("scheduleLists.mentoringSessionUuid").is(sessionUuid)
        );
        Update update = new Update();
        update.set("scheduleLists.$.status", Status.CANCELLED_BY_USER); // "유저 취소상태로 업데이트"
        update.set("scheduleLists.$.updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, Schedule.class);
    }

    @Override
    public void updateMentorScheduleStatus(String mentorUuid, String sessionUuid, String yearMonth, Status status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userUuid").is(mentorUuid)
                .and("yearMonth").is(yearMonth)
                .and("scheduleLists.mentoringSessionUuid").is(sessionUuid)
        );
        Update update = new Update();
        update.set("scheduleLists.$.status", status);
        update.set("scheduleLists.$.updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, Schedule.class);
    }

    @Override
    public void reRegisterMentorSchedule(String userUuid, String yearMonth, String sessionUuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userUuid").is(userUuid)
                .and("yearMonth").is(yearMonth)
                .and("scheduleLists.mentoringSessionUuid").is(sessionUuid)
        );
        Update update = new Update();
        update.set("scheduleLists.$.status", Status.PENDING); // "유저 대기상태로 업데이트"
        update.set("scheduleLists.$.updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, Schedule.class);
    }

    @Override
    public Schedule findByUserScheduleOrderByStartDateAsc(String userUuid, String yearMonth) {
        // 1. userUuid와 yearMonth로 Schedule 문서 조회
        Query query = new Query();
        query.addCriteria(Criteria.where("userUuid").is(userUuid)
                .and("yearMonth").is(yearMonth)
                //.and("scheduleLists.status").in(Status.PENDING, Status.CONFIRMED, Status.END)
                .and("scheduleLists.status").in(Status.PENDING, Status.CONFIRMED, Status.END)
        );
        Schedule schedule = mongoTemplate.findOne(query, Schedule.class);
        // 2. startDate 와 startTime 기준으로 정렬
        if (schedule != null && schedule.getScheduleLists() != null) {
            schedule.getScheduleLists().sort(
                    Comparator.comparing(ScheduleList::getStartDate)
                            .thenComparing(ScheduleList::getStartTime) // startTime을 두 번째 정렬 기준으로 추가
            );
        }

        return schedule;
    }

    @Override
    public void updateMenteeSchedule(
            AfterSessionUserOutDto afterSessionDto,
            SessionTimeResponseOutDto sessionTimeDto,
            String yearMonth )
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("userUuid")
                .is(afterSessionDto.getMenteeUuid())
                .and("yearMonth").is(yearMonth) );
        Update update = new Update();
        ScheduleList newSchedule = ScheduleList.builder()
                .mentoringSessionUuid(afterSessionDto.getSessionUuid())
                .mentoringName(afterSessionDto.getMentoringName())
                .startDate(sessionTimeDto.getStartDate())
                .startTime(sessionTimeDto.getStartTime())
                .endDate(sessionTimeDto.getEndDate())
                .endTime(sessionTimeDto.getEndTime())
                .status(afterSessionDto.getStatus())
                .createdAt(afterSessionDto.getCreatedAt())
                .updatedAt(afterSessionDto.getUpdatedAt())
                .build();
        update.push("scheduleLists", newSchedule);
        mongoTemplate.updateFirst(query, update, Schedule.class);
    }

    @Override
    public void updateMenteeScheduleStatus(String userUuid, String yearMonth, String sessionUuid, Status status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userUuid").is(userUuid)
                .and("yearMonth").is(yearMonth)
                .and("scheduleLists.mentoringSessionUuid").is(sessionUuid)
        );
        Update update = new Update();
        update.set("scheduleLists.$.status", status);
        update.set("scheduleLists.$.updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, Schedule.class);
    }

    @Override
    public List<MentoringSessionScheduleResponseDto> findTodaySessionSchedule(String userUuid, String yearMonth, LocalDate startDate) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userUuid").is(userUuid)
                        .and("yearMonth").is(yearMonth)
                ),
                // 리스트 개별 문서처럼 분리
                Aggregation.unwind("scheduleLists"), // scheduleLists 배열의 각 요소를 개별 문서로 분리
                // 개별 문서 필터링
                Aggregation.match(Criteria.where("scheduleLists.startDate").is(startDate)
                        .and("scheduleLists.status").is(Status.CONFIRMED)
                ),
                Aggregation.sort(Sort.by(Sort.Order.desc("scheduleLists.startTime"))),
                Aggregation.project("scheduleLists.mentoringSessionUuid", "scheduleLists.mentoringName",
                                "scheduleLists.startDate", "scheduleLists.endDate",
                                "scheduleLists.startTime", "scheduleLists.endTime")
                        .and("scheduleLists.mentoringSessionUuid").as("sessionUuid")
                        .and("scheduleLists.mentoringName").as("mentoringName")
                        .and("scheduleLists.startDate").as("startDate")
                        .and("scheduleLists.endDate").as("endDate")
                        .and("scheduleLists.startTime").as("startTime")
                        .and("scheduleLists.endTime").as("endTime")
                        .and("scheduleLists.endTime").as("endTime")
        );

        return mongoTemplate.aggregate(aggregation, "user_schedule", MentoringSessionScheduleResponseDto.class)
                .getMappedResults();
    }


}
