package com.multitab.bookingScheduleQuery.dto.out;

import com.multitab.bookingScheduleQuery.entity.SessionRequest;
import com.multitab.bookingScheduleQuery.entity.vo.SessionRequestMenteeList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SessionRequestResponseDto {
    private String mentoringSessionUuid; // 멘토링 세션 UUID

    private Integer minHeadCount; // 최소 신청인원수
    private Integer maxHeadCount; // 최대 신청인원수
    private Integer nowHeadCount; // 현재 신청인원
    private LocalDate deadLineDate; // 예약마감일

    public static SessionRequestResponseDto from(SessionRequest entity){
        return SessionRequestResponseDto.builder()
                .mentoringSessionUuid(entity.getMentoringSessionUuid())
                .minHeadCount(entity.getMinHeadCount())
                .maxHeadCount(entity.getMaxHeadCount())
                .nowHeadCount(entity.getNowHeadCount())
                .deadLineDate(entity.getDeadLineDate())
                .build();
    }
}
