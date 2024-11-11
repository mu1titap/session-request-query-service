package com.multitab.bookingScheduleQuery.infrastructure.custom;

import com.multitab.bookingScheduleQuery.entity.SessionRequest;

public interface CustomSessionRequestRepository {

    SessionRequest findSessionRequestBySessionUuid(String sessionUuid);
}
