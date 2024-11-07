package com.multitab.bookingScheduleQuery.infrastructure.custom;

import com.multitab.bookingScheduleQuery.entity.Schedule;
import com.multitab.bookingScheduleQuery.entity.SessionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Log4j2
public class CustomSessionRequestRepositoryImpl implements CustomSessionRequestRepository{
    private final MongoTemplate mongoTemplate;
    @Override
    public SessionRequest findSessionRequestBySessionUuid(String sessionUuid) {
        Query query = new Query();
//        query.addCriteria(Criteria.where("mentoringSessionUuid").is(sessionUuid)
//                .and("isDeleted").is(false));
        query.addCriteria(Criteria.where("mentoringSessionUuid").is(sessionUuid));
        SessionRequest one = mongoTemplate.findOne(query, SessionRequest.class);
        log.info("findSessionRequestBySessionUuid : "+one);
        return one;
    }
}
