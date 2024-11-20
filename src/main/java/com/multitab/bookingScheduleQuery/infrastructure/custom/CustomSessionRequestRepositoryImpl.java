package com.multitab.bookingScheduleQuery.infrastructure.custom;

import com.multitab.bookingScheduleQuery.messagequeue.messageIn.AfterSessionUserOutDto;
import com.multitab.bookingScheduleQuery.messagequeue.messageIn.CancelSessionUserMessage;
import com.multitab.bookingScheduleQuery.messagequeue.messageIn.ReRegisterSessionUserMessage;
import com.multitab.bookingScheduleQuery.entity.SessionRequest;
import com.multitab.bookingScheduleQuery.entity.vo.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

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

    @Override
    public void cancelSessionUser(CancelSessionUserMessage dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringSessionUuid")
                .is(dto.getSessionUuid())
                .and("sessionRequestLists.menteeUuid").is(dto.getMenteeUuid())
        );
        Update update = new Update();
        update.inc("nowHeadCount", -1); // nowHeadCount 감소
        update.set("sessionRequestLists.$.status", Status.CANCELLED_BY_USER); // "유저 취소상태로 업데이트"
        update.set("sessionRequestLists.$.updatedAt", LocalDateTime.now());
        if(dto.getShouldOpenSession()){
            update.set("isClosed",false); // 세션 닫힘 여부 업데이트 (다시 열림)
        }
        mongoTemplate.updateFirst(query, update, SessionRequest.class);

    }

    @Override
    public void reRegisterSessionUser(ReRegisterSessionUserMessage dto) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringSessionUuid")
                .is(dto.getSessionUuid())
                .and("sessionRequestLists.menteeUuid").is(dto.getMenteeUuid())
        );
        Update update = new Update();
        update.inc("nowHeadCount", 1); // nowHeadCount 증가
        update.set("sessionRequestLists.$.status", Status.PENDING); // "유저 대기 상태로 업데이트"
        update.set("sessionRequestLists.$.updatedAt", LocalDateTime.now());
        if(dto.getShouldCloseSession()){
            update.set("isClosed",true); // 세션 닫힘 여부 업데이트 (다시 닫힘)
        }
        mongoTemplate.updateFirst(query, update, SessionRequest.class);

    }

    @Override
    public void updateSessionRequestStatus(String sessionUuid, String userUuid, Status status) {
        Query query = new Query();
        query.addCriteria(Criteria.where("mentoringSessionUuid")
                .is(sessionUuid)
                .and("sessionRequestLists.menteeUuid").is(userUuid)
        );
        Update update = new Update();
        update.set("sessionRequestLists.$.status", status); //
        update.set("sessionRequestLists.$.updatedAt", LocalDateTime.now());

        mongoTemplate.updateFirst(query, update, SessionRequest.class);
    }

}
