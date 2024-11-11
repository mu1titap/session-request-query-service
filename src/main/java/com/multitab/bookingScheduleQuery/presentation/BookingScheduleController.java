package com.multitab.bookingScheduleQuery.presentation;

import com.multitab.bookingScheduleQuery.application.BookingScheduleQueryService;
import com.multitab.bookingScheduleQuery.common.entity.BaseResponse;
import com.multitab.bookingScheduleQuery.dto.in.UserScheduleSearchRequestDto;
import com.multitab.bookingScheduleQuery.dto.out.ScheduleResponseDto;
import com.multitab.bookingScheduleQuery.dto.out.SessionRequestResponseDto;
import com.multitab.bookingScheduleQuery.entity.Schedule;
import com.multitab.bookingScheduleQuery.viewObject.in.UserScheduleSearchRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking-schedule-read")
public class BookingScheduleController {
    private final BookingScheduleQueryService bookingScheduleQueryService;

    @Operation(summary = "유저 스케줄 조회" , description = "유저 uuid, 년월('2024-10')")
    @GetMapping("/schedule-list")
    public BaseResponse<ScheduleResponseDto> getScheduleListByUserUuidAndYearMonth(
            @ParameterObject UserScheduleSearchRequestVo requestVo
    ) {
        return new BaseResponse<>(
                bookingScheduleQueryService.findByUserUuidAndYearMonth(UserScheduleSearchRequestDto.from(requestVo))
        );
    }

    @Operation(summary = "멘토링 세션 참가요청 정보 조회(최대,최소,현재 신청인원 수, 예약마감일)")
    @GetMapping("/session-request-info/{mentoringSessionUuid}")
    public BaseResponse<SessionRequestResponseDto> getSessionRequestInfo(
            @ParameterObject @PathVariable(name = "mentoringSessionUuid") String mentoringSessionUuid
    ) {
        return new BaseResponse<>(
                bookingScheduleQueryService.findSessionRequestResponseDto(mentoringSessionUuid)
        );
    }
}
