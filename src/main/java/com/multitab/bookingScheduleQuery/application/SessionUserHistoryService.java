package com.multitab.bookingScheduleQuery.application;

import com.multitab.bookingScheduleQuery.dto.in.SessionUserScheduleSearchRequestDto;
import com.multitab.bookingScheduleQuery.dto.in.UserScheduleSearchRequestDto;
import com.multitab.bookingScheduleQuery.dto.out.MentoringSessionScheduleResponseDto;
import com.multitab.bookingScheduleQuery.dto.out.ScheduleResponseDto;
import com.multitab.bookingScheduleQuery.dto.out.SessionUserHistoryResponseDto;
import com.multitab.bookingScheduleQuery.dto.out.SessionUserHistoryScheduleResponseDto;
import com.multitab.bookingScheduleQuery.entity.SessionUserHistory;
import com.multitab.bookingScheduleQuery.messagequeue.messageIn.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface SessionUserHistoryService {
    // 멘토링 생성 시 유저 이력 생성 (멘토링 생성 시)
    @Transactional
    void createSessionHistoryForMento(MentoringAddAfterOutDto dto);
    // 멘토링 생성 시 유저 이력 생성 (세션 추가 시)
    @Transactional
    void createSessionHistoryForMento(SessionCreatedAfterOutDto dto);

    @Transactional
    void cancelSessionUserHistory(CancelSessionUserMessage dto);

    @Transactional
    void createSessionHistoryForMentee(AfterSessionUserOutDto dto);

    @Transactional
    void reRegisterSessionUserHistory(ReRegisterSessionUserMessage dto);

    @Transactional
    void updateSessionUserHistoryStatus(SessionUserUpdateMessage dto);

    @Transactional
    void updateEndSession(String sessionUuid);

    // 예약마감일 지난 세션 처리 -> 확정 or 최소인원미달 취소
    @Transactional
    void updateMentoSessionUserHistoryStatus(SessionConfirmedMessage dto);

    // 멘토링 세션 내역
    Page<SessionUserHistoryResponseDto> searchSessionUserHistory(String userUuid, Pageable pageable, Boolean includeCancelled);


    SessionUserHistoryScheduleResponseDto findHistoryByFromToDate(SessionUserScheduleSearchRequestDto sessionUserScheduleSearchRequestDto);

    //List<MentoringSessionScheduleResponseDto> findTodaySessionSchedule(String userUuid, LocalDate date);
    List<MentoringSessionScheduleResponseDto> findTodayHistorySchedule(String userUuid, LocalDate date);
}
