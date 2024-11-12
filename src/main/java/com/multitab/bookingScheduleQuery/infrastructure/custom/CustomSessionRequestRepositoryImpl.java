package com.multitab.bookingScheduleQuery.infrastructure.custom;

import com.multitab.bookingScheduleQuery.dto.messageIn.AfterSessionUserOutDto;
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
        query.addCriteria(Criteria.where("mentoringSessionUuid").is(sessionUuid));
        SessionRequest one = mongoTemplate.findOne(query, SessionRequest.class);
        log.info("findSessionRequestBySessionUuid : "+one);
        return one;
    }

    @Override
    public void updateSessionRequestList(AfterSessionUserOutDto dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringSessionUuid").is(dto.getSessionUuid()));
        Update update = new Update();
        update.push("sessionRequestLists",dto.toMongoSessionRequestMenteeList()); // 참가자 리스트에 참가자 추가
        update.inc("nowHeadCount", 1); // nowHeadCount 증가
        if(dto.getIsClosed()){ // 세션 닫힘 여부 업데이트
            update.set("isClosed",true);
        }
        mongoTemplate.updateFirst(query, update, SessionRequest.class);
    }
}
