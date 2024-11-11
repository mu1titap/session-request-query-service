package com.multitab.bookingScheduleQuery.util;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateConverter {
    // "yyyy-MM" 형식의 DateTimeFormatter 생성
    private static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    /**
     * LocalDateTime을 "yyyy-MM" 형식의 문자열로 변환
     *
     * @param dateTime 변환할 LocalDateTime 객체
     * @return "yyyy-MM" 형식의 yearMonth 문자열
     */
    public static String convertToYearMonth(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("날짜 없음");
        }
        return dateTime.format(YEAR_MONTH_FORMATTER);
    }
}
