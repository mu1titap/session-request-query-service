package com.multitab.bookingScheduleQuery.infrastructure;

import com.multitab.bookingScheduleQuery.entity.SessionRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRequestMongoRepository extends MongoRepository<SessionRequest, String> {
    //SessionRequest findByMentoringSessionUuidAndIsDeletedFalse(String mentoringSessionUuid);


}
