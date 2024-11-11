package com.multitab.bookingScheduleQuery.entity.vo;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ScheduleList {
    private String mentoringSessionUuid;
    private String mentoringName;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private ScheduleStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void initStatus(ScheduleStatus status) {
        this.status = status;
    }
}
