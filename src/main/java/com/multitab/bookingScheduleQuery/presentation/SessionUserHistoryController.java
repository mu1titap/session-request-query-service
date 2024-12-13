package com.multitab.bookingScheduleQuery.presentation;

import com.multitab.bookingScheduleQuery.application.SessionUserHistoryService;
import com.multitab.bookingScheduleQuery.common.entity.BaseResponse;
import com.multitab.bookingScheduleQuery.dto.out.SessionUserHistoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/session-user-history-read")
public class SessionUserHistoryController {
    private final SessionUserHistoryService sessionRequestService;
    @Operation(summary = "멘토링 세션 참여 내역 조회 (페이지네이션)", description = "includeCancelled == true => 대기,확정,완료 된 세션참여내역 조회. <br/>" +
                "includeCancelled == false => 취소(사용자취소, 최소인원미달로 인한 시스템 취소)된 세션참여내역 조회 ."
            ,tags = {"멘토링 세션 히스토리"})
    @GetMapping("/")
    public BaseResponse<Page<SessionUserHistoryResponseDto>> getSessionRequestInfo(
            @RequestHeader("userUuid") String  userUuid, @ParameterObject Pageable pageable ,
            @ParameterObject @RequestParam("includeCancelled") Boolean includeCancelled) {

        return new BaseResponse<>(
                sessionRequestService.searchSessionUserHistory(userUuid, pageable, includeCancelled)
        );
    }
}
