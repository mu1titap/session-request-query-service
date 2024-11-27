package com.multitab.bookingScheduleQuery.application;

import com.multitab.bookingScheduleQuery.messagequeue.messageIn.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ScheduleUserService {
//    // 멘토링+세션 생성 시 멘토의 스케줄 업데이트
//    @Transactional
//    void updateMentorSchedule(MentoringAddAfterOutDto dto);
//    @Transactional
//    void updateMentorSchedule(SessionCreatedAfterOutDto dto);
//    @Transactional
//    void updateMentorScheduleStatus(SessionConfirmedMessage dto);
//    @Transactional
//    void updateMenteeSchedule(AfterSessionUserOutDto dto);
//    @Transactional
//    void cancelSessionUser(CancelSessionUserMessage dto);
//
//    @Transactional
//    void reRegisterSessionUser(ReRegisterSessionUserMessage dto);
//
//    @Transactional
//    void updateSessionUserStatus(SessionUserUpdateMessage dto);
}
