package com.multitab.bookingScheduleQuery.viewObject.in;

import com.multitab.bookingScheduleQuery.dto.in.SessionUserScheduleSearchRequestDto;
import lombok.*;

import java.time.LocalDate;

@Data
public class SessionUserScheduleSearchRequestVo {
    private LocalDate startDate;
    private LocalDate endDate;


}
