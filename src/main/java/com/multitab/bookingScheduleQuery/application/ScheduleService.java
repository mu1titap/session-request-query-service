package com.multitab.bookingScheduleQuery.application;

import com.multitab.bookingScheduleQuery.dto.in.UserScheduleSearchRequestDto;
import com.multitab.bookingScheduleQuery.dto.out.MentoringSessionScheduleResponseDto;
import com.multitab.bookingScheduleQuery.messagequeue.messageIn.*;
import com.multitab.bookingScheduleQuery.dto.out.ScheduleResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface ScheduleService {

    // 멘토링+세션 생성 시 멘토의 스케줄 업데이트
    @Transactional
    void updateMentorSchedule(MentoringAddAfterOutDto dto);
    @Transactional
    void updateMentorSchedule(SessionCreatedAfterOutDto dto);
    @Transactional
    void updateMentorScheduleStatus(SessionConfirmedMessage dto);
    @Transactional
    void updateMenteeSchedule(AfterSessionUserOutDto dto);
    @Transactional
    void cancelSessionUser(CancelSessionUserMessage dto);

    @Transactional
    void reRegisterSessionUser(ReRegisterSessionUserMessage dto);

    @Transactional
    void updateSessionUserStatus(SessionUserUpdateMessage dto);
    /**
     * 조회
     */
    // 유저 uuid, 년월 정보로 스케줄 조회
    ScheduleResponseDto findByUserUuidAndYearMonth(UserScheduleSearchRequestDto userScheduleSearchRequestDto);

    List<MentoringSessionScheduleResponseDto> findTodaySessionSchedule(String userUuid, LocalDate date);
}
