package com.multitab.bookingScheduleQuery.entity.vo;

import lombok.*;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

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

    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void initStatus(Status status) {
        this.status = status;
    }
}
