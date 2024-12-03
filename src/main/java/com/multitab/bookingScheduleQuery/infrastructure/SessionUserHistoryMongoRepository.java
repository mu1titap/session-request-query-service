package com.multitab.bookingScheduleQuery.infrastructure;

import com.multitab.bookingScheduleQuery.application.SessionUserHistoryService;
import com.multitab.bookingScheduleQuery.entity.SessionUserHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionUserHistoryMongoRepository extends MongoRepository<SessionUserHistory, String> {


}
