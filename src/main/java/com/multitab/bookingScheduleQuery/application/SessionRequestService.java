package com.multitab.bookingScheduleQuery.application;

import com.multitab.bookingScheduleQuery.dto.in.UserScheduleSearchRequestDto;
import com.multitab.bookingScheduleQuery.dto.messageIn.AfterSessionUserOutDto;
import com.multitab.bookingScheduleQuery.dto.messageIn.MentoringAddAfterOutDto;
import com.multitab.bookingScheduleQuery.dto.out.ScheduleResponseDto;
import com.multitab.bookingScheduleQuery.dto.out.SessionRequestResponseDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface SessionRequestService {

    // 멘토링+세션 생성 시 세션의 참가 리스트 read Data 생성
    @Transactional
    void createSessionRequestList(MentoringAddAfterOutDto dto);


    // 멘티의  세션 참가 등록 시 세션의 참가 리스트 push update
    @Transactional
    void updateSessionRequestList(AfterSessionUserOutDto dto);


    /**
     * 조회
     */

    // 세션 uuid 로 세션 신청 상태 조회 (참가자 리스트 X)
    SessionRequestResponseDto findSessionRequestResponseDto(String sessionUuid);


}
