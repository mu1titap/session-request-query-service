package com.multitab.bookingScheduleQuery.infrastructure;

import com.multitab.bookingScheduleQuery.entity.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ScheduleMongoRepository extends MongoRepository<Schedule, String> {


    boolean existsByUserUuidAndYearMonth(String userUuid, String yearMonth);

    Schedule findByUserUuidAndYearMonth(String userUuid, String yearMonth);
}
