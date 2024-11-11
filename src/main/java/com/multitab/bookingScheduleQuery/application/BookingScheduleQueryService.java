package com.multitab.bookingScheduleQuery.application;

import com.multitab.bookingScheduleQuery.dto.in.UserScheduleSearchRequestDto;
import com.multitab.bookingScheduleQuery.dto.messageIn.MentoringAddAfterOutDto;
import com.multitab.bookingScheduleQuery.dto.out.ScheduleResponseDto;
import com.multitab.bookingScheduleQuery.dto.out.SessionRequestResponseDto;
import com.multitab.bookingScheduleQuery.entity.Schedule;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface BookingScheduleQueryService {

    // 멘토링+세션 생성 시 세션의 참가 리스트 read Data 생성
    @Transactional
    void createSessionRequestList(MentoringAddAfterOutDto dto);

    // 멘토링+세션 생성 시 멘토의 스케줄 업데이트
    @Transactional
    void updateMentorSchedule(MentoringAddAfterOutDto dto);

    // 유저 uuid, 년월 정보로 스케줄 조회
    ScheduleResponseDto findByUserUuidAndYearMonth(UserScheduleSearchRequestDto userScheduleSearchRequestDto);

    // 세션 uuid 로 세션 신청 상태 조회 (참가자 리스트 X)
    SessionRequestResponseDto findSessionRequestResponseDto(String sessionUuid);


}
