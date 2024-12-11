package com.multitab.bookingScheduleQuery.infrastructure.custom;

import com.multitab.bookingScheduleQuery.dto.in.SessionUserScheduleSearchRequestDto;
import com.multitab.bookingScheduleQuery.dto.out.MentoringSessionScheduleResponseDto;
import com.multitab.bookingScheduleQuery.dto.out.SessionUserHistoryResponseDto;
import com.multitab.bookingScheduleQuery.dto.out.SessionUserHistoryScheduleResponseDto;
import com.multitab.bookingScheduleQuery.entity.SessionUserHistory;
import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import com.multitab.bookingScheduleQuery.entity.vo.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Log4j2
public class CustomSessionUserHistoryRepositoryImpl implements  CustomSessionUserHistoryRepository{
    private final MongoTemplate mongoTemplate;
    @Override
    public void cancelSessionUserHistory(String userUuid, String sessionUuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userUuid").is(userUuid)
                .and("mentoringSessionUuid").is(sessionUuid)
                .and("status").is(Status.PENDING)
        );
        Update update = new Update();
        update.set("status", Status.CANCELLED_BY_USER); // "유저 취소상태로 업데이트"
        update.set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, SessionUserHistory.class);
    }

    @Override
    public void updatePendingStatus(String userUuid, String sessionUuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userUuid").is(userUuid)
                .and("mentoringSessionUuid").is(sessionUuid)
                .and("status").is(Status.CANCELLED_BY_USER)
        );
        Update update = new Update();
        update.set("status", Status.PENDING); // "유저 취소상태로 업데이트"
        update.set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, SessionUserHistory.class);
    }

    @Override
    public void updateSessionUserHistoryStatus(String userUuid, String sessionUuid, Status status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userUuid").is(userUuid)
                .and("mentoringSessionUuid").is(sessionUuid)
                .and("status").is(Status.PENDING)
        );
        Update update = new Update();
        update.set("status",status);
        update.set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, SessionUserHistory.class);
    }

    @Override
    public void updateSessionUserHistoryStatus(String userUuid, String sessionUuid , Status status, Integer minHeadCount, Integer maxHeadCount, Integer nowHeadCount) {
        log.info("sessionUuid : {}", sessionUuid);
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringSessionUuid").is(sessionUuid)
                .andOperator(
                        new Criteria().orOperator(
                                Criteria.where("status").is(Status.PENDING),
                                Criteria.where("status").is(Status.CONFIRMED)
                        )
                )
        );
        Update update = new Update();
        update.set("status",status);
        update.set("minHeadCount",minHeadCount);
        update.set("maxHeadCount",maxHeadCount);
        update.set("nowHeadCount",nowHeadCount);
        update.set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateMulti(query, update, SessionUserHistory.class);
    }

    @Override
    public void updateEndSession(String sessionUuid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringSessionUuid").is(sessionUuid)
                .and("status").is(Status.CONFIRMED)
        );
        Update update = new Update();
        update.set("status",Status.END);
        update.set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateMulti(query, update, SessionUserHistory.class);
    }

    @Override
    public Page<SessionUserHistoryResponseDto> searchSessionUserHistory(String userUuid, Pageable pageable, Boolean includeCancelled) {
        Criteria criteria = Criteria.where("userUuid").is(userUuid);
        if (includeCancelled) {
            criteria.and("status").in(Status.PENDING, Status.CONFIRMED, Status.END);
        } else {
            criteria.and("status").in(Status.CANCELLED_BY_USER, Status.CANCELLED_BY_SYSTEM);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.addFields().addField("prioritySort")
                        .withValue(
                                ConditionalOperators.when(Criteria.where("nowSessionCount").gte(1))
                                        .then(true)
                                        .otherwise(false)
                        ).build(),

                Aggregation.sort(Sort.by(Sort.Order.desc("updatedAt"))),

                Aggregation.project("mentoringName", "sessionUuid", "startDate", "endDate", "startTime", "endTime",
                                "price", "maxHeadCount", "minHeadCount", "nowHeadCount", "status", "updatedAt")
                        .and("mentoringName").as("mentoringName")
                        .and("mentoringSessionUuid").as("sessionUuid")
                        .and("startDate").as("startDate")
                        .and("endDate").as("endDate")
                        .and("startTime").as("startTime")
                        .and("endTime").as("endTime")
                        .and("price").as("price")
                        .and("maxHeadCount").as("maxHeadCount")
                        .and("minHeadCount").as("minHeadCount")
                        .and("nowHeadCount").as("nowHeadCount")
                        .and("status").as("status")
                        .and("updatedAt").as("updatedAt"),

                // 페이지네이션 처리
                Aggregation.skip(pageable.getOffset()),
                Aggregation.limit(pageable.getPageSize())
        );
        List<SessionUserHistoryResponseDto> content = mongoTemplate.aggregate(aggregation, "session_user_history", SessionUserHistoryResponseDto.class)
                .getMappedResults();


        // 토탈 카운트
        Aggregation countAggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.count().as("total")
        );
        // Total count 계산
        long total = Optional.ofNullable(mongoTemplate.aggregate(countAggregation, "session_user_history", Document.class)
                        .getUniqueMappedResult())
                .map(result -> result.get("total"))
                .map(totalCount -> totalCount instanceof Number ? ((Number) totalCount).longValue() : 0L)
                .orElse(0L);

        return PageableExecutionUtils.getPage(content, pageable, () -> total);
    }

    @Override
    public SessionUserHistoryScheduleResponseDto findHistoryByFromToDate(SessionUserScheduleSearchRequestDto dto) {
        log.info("dto : {}", dto);
        Criteria criteria = Criteria.where("userUuid").is(dto.getUserUuid())
                .and("startDate").gte(dto.getStartDate())
                .and("endDate").lte(dto.getEndDate());

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.by(Sort.Order.asc("startDate"), Sort.Order.asc("startTime"))),
                Aggregation.project("mentoringName", "mentoringSessionUuid", "startDate", "endDate", "startTime", "endTime",
                                "status", "createdAt", "updatedAt")
                        .and("mentoringName").as("mentoringName")
                        .and("mentoringSessionUuid").as("mentoringSessionUuid")
                        .and("startDate").as("startDate")
                        .and("endDate").as("endDate")
                        .and("startTime").as("startTime")
                        .and("endTime").as("endTime")
                        .and("status").as("status")
                        .and("createdAt").as("createdAt")
                        .and("updatedAt").as("updatedAt")
        );

        List<ScheduleList> scheduleLists = mongoTemplate.aggregate(aggregation, "session_user_history", ScheduleList.class)
                .getMappedResults();
        return SessionUserHistoryScheduleResponseDto.builder()
                .scheduleLists(scheduleLists)
                .build();
    }

    @Override
    public List<MentoringSessionScheduleResponseDto> findTodayHistorySchedule(String userUuid, LocalDate date) {
        Criteria criteria = Criteria.where("userUuid").is(userUuid)
                .and("startDate").is(date)
                .and("status").is(Status.CONFIRMED);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(Sort.by(Sort.Order.asc("startDate"), Sort.Order.asc("startTime"))),
                Aggregation.project("mentoringName", "mentoringSessionUuid", "startDate", "endDate", "startTime", "endTime")
                        .and("mentoringName").as("mentoringName")
                        .and("mentoringSessionUuid").as("sessionUuid")
                        .and("startDate").as("startDate")
                        .and("endDate").as("endDate")
                        .and("startTime").as("startTime")
                        .and("endTime").as("endTime")
        );

        return mongoTemplate.aggregate(aggregation, "session_user_history", MentoringSessionScheduleResponseDto.class)
                .getMappedResults();
    }
}
