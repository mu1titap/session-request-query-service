package com.multitab.bookingScheduleQuery.presentation;

import com.multitab.bookingScheduleQuery.application.ScheduleService;
import com.multitab.bookingScheduleQuery.application.SessionRequestService;
import com.multitab.bookingScheduleQuery.common.entity.BaseResponse;
import com.multitab.bookingScheduleQuery.dto.in.UserScheduleSearchRequestDto;
import com.multitab.bookingScheduleQuery.dto.out.ScheduleResponseDto;
import com.multitab.bookingScheduleQuery.dto.out.SessionRequestResponseDto;
import com.multitab.bookingScheduleQuery.viewObject.in.UserScheduleSearchRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedule-read")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Operation(summary = "유저 스케줄 조회" , description = "유저 uuid, 년월('2024-10')")
    @GetMapping("/schedule-list")
    public BaseResponse<ScheduleResponseDto> getScheduleListByUserUuidAndYearMonth(
           UserScheduleSearchRequestVo requestVo
    ) {
        log.info("requestVo : "+requestVo);
        UserScheduleSearchRequestDto dto = UserScheduleSearchRequestDto.from(requestVo);

        return new BaseResponse<>(
                scheduleService.findByUserUuidAndYearMonth(dto)
        );
    }

}
