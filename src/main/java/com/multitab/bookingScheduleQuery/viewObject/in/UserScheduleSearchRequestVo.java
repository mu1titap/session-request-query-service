package com.multitab.bookingScheduleQuery.viewObject.in;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

@Data
@ToString
@ParameterObject
public class UserScheduleSearchRequestVo {
    @Schema(description = "유저 UUID", name = "userUuid", nullable = false)
    private String userUuid;

    @Schema(description = "yyyy-MM 형식의 연도-월 입력", example = "2024-11",  name = "yearMonth", nullable = false)
    private String yearMonth;
}
