package com.multitab.bookingScheduleQuery.dto.in;

import com.multitab.bookingScheduleQuery.viewObject.in.SessionUserScheduleSearchRequestVo;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class SessionUserScheduleSearchRequestDto {
    private String userUuid;
    private LocalDate startDate;
    private LocalDate endDate;


    public static SessionUserScheduleSearchRequestDto of (String userUuid, SessionUserScheduleSearchRequestVo vo){
        return SessionUserScheduleSearchRequestDto.builder()
                .userUuid(userUuid)
                .startDate(vo.getStartDate())
                .endDate(vo.getEndDate())
                .build();

    }
}
