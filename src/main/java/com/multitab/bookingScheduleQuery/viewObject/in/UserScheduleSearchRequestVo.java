package com.multitab.bookingScheduleQuery.viewObject.in;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserScheduleSearchRequestVo {
    @Schema(description = "유저 UUID")
    private String userUuid;

    @Schema(description = "yyyy-MM 형식의 연도-월 입력", example = "2024-11")
    private String yearMonth;
}
