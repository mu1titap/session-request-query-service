package com.multitab.bookingScheduleQuery.dto.out;

import com.multitab.bookingScheduleQuery.entity.vo.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SessionUserHistoryResponseDto {
    private String mentoringName;
    private String sessionUuid;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private Integer price;
    private Integer maxHeadCount;
    private Integer minHeadCount;
    private Integer nowHeadCount;

    private Status status;

    private LocalDateTime updatedAt;

}
