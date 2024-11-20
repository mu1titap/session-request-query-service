package com.multitab.bookingScheduleQuery.application;

import com.multitab.bookingScheduleQuery.messagequeue.messageIn.*;
import com.multitab.bookingScheduleQuery.dto.out.SessionRequestResponseDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface SessionRequestService {

    // 멘토링+세션 생성 시 세션의 참가 리스트 read Data 생성
    @Transactional
    void createSessionRequestList(MentoringAddAfterOutDto dto);

    @Transactional
    void createSessionRequestList(SessionCreatedAfterOutDto dto);

    // 멘티의  세션 참가 등록 시 세션의 참가 리스트 push update
    @Transactional
    void updateSessionRequestList(AfterSessionUserOutDto dto);
    @Transactional
    void cancelSessionUser(CancelSessionUserMessage dto);
    @Transactional
    void reRegisterSessionUser(ReRegisterSessionUserMessage dto);

    @Transactional
    void updateSessionRequestStatus(SessionUserUpdateMessage dto);
    /**
     * 조회
     */
    // 세션 uuid 로 세션 신청 상태 조회 (참가자 리스트 X)
    SessionRequestResponseDto findSessionRequestResponseDto(String sessionUuid);


}
