package com.multitab.bookingScheduleQuery.entity.vo;

import lombok.Getter;

@Getter
public enum SessionRequestStatus {

    APPLY("신청"),
    CANCEL("취소");
    // 상태 값 반환
    private final String description;

    SessionRequestStatus(String description) {
        this.description = description;
    }

}
