package com.multitab.bookingScheduleQuery.infrastructure.custom;

import com.multitab.bookingScheduleQuery.messagequeue.messageIn.AfterSessionUserOutDto;
import com.multitab.bookingScheduleQuery.messagequeue.messageIn.CancelSessionUserMessage;
import com.multitab.bookingScheduleQuery.messagequeue.messageIn.ReRegisterSessionUserMessage;
import com.multitab.bookingScheduleQuery.entity.SessionRequest;

public interface CustomSessionRequestRepository {

    SessionRequest findSessionRequestBySessionUuid(String sessionUuid);

    void updateSessionRequestList(AfterSessionUserOutDto dto);

    void cancelSessionUser(CancelSessionUserMessage dto);


    void reRegisterSessionUser(ReRegisterSessionUserMessage dto);
}

