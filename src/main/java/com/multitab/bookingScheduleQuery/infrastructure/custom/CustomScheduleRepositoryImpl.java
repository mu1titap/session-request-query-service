package com.multitab.bookingScheduleQuery.infrastructure.custom;

import com.multitab.bookingScheduleQuery.dto.messageIn.AfterSessionUserOutDto;
import com.multitab.bookingScheduleQuery.entity.Schedule;
import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import com.multitab.bookingScheduleQuery.serviceCall.dto.in.SessionTimeResponseOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

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
    public Schedule findByUserScheduleOrderByStartDateAsc(String userUuid, String yearMonth) {
        // 1. userUuid와 yearMonth로 Schedule 문서 조회
        Query query = new Query();
        query.addCriteria(Criteria.where("userUuid").is(userUuid).and("yearMonth").is(yearMonth));
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



}
