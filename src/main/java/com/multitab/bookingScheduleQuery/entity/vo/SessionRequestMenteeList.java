package com.multitab.bookingScheduleQuery.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionRequestMenteeList {
    private String menteeUuid;
    private SessionRequestStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
