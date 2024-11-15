package com.multitab.bookingScheduleQuery.application;

import com.multitab.bookingScheduleQuery.dto.in.UserScheduleSearchRequestDto;
import com.multitab.bookingScheduleQuery.dto.messageIn.AfterSessionUserOutDto;
import com.multitab.bookingScheduleQuery.dto.messageIn.MentoringAddAfterOutDto;
import com.multitab.bookingScheduleQuery.dto.out.ScheduleResponseDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ScheduleService {

    // 멘토링+세션 생성 시 멘토의 스케줄 업데이트
    @Transactional
    void updateMentorSchedule(MentoringAddAfterOutDto dto);


    @Transactional
    void updateMenteeSchedule(AfterSessionUserOutDto dto);

    /**
     * 조회
     */
    // 유저 uuid, 년월 정보로 스케줄 조회
    ScheduleResponseDto findByUserUuidAndYearMonth(UserScheduleSearchRequestDto userScheduleSearchRequestDto);
}
