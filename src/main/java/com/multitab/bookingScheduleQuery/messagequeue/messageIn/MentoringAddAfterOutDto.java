package com.multitab.bookingScheduleQuery.messagequeue.messageIn;

import com.multitab.bookingScheduleQuery.entity.SessionRequest;
import com.multitab.bookingScheduleQuery.entity.SessionUserHistory;
import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"mentoringSessionAddAfterOutDtoList", "mentoringCategoryAfterOutDtoList", "mentoringHashTagAfterOutDto"})

public class MentoringAddAfterOutDto {
    private String mentoringId;

    private String mentoringUuid;

    private String name;

    private String description;
    private String detail;

    private String mentorUuid;

    private String thumbnailUrl;

    private Boolean isReusable;

    private Boolean isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<MentoringSessionAddAfterOutDto> mentoringSessionAddAfterOutDtoList;

    private List<MentoringCategoryAfterOutDto> mentoringCategoryAfterOutDtoList;

    private MentoringHashTagAfterOutDto mentoringHashTagAfterOutDto;


    // 멘토링 저장 dto -> SessionRequest 엔티티
    public List<SessionRequest> toSessionRequestEntities() {
        return this.getMentoringSessionAddAfterOutDtoList()
                .stream()
                .map(session -> SessionRequest.builder()
                        .mentoringUuid(session.getMentoringUuid())
                        .mentoringSessionUuid(session.getSessionUuid())
                        .minHeadCount(session.getMinHeadCount())
                        .maxHeadCount(session.getMaxHeadCount())
                        .nowHeadCount(0)
                        .deadLineDate(session.getDeadlineDate())
                        .isClosed(session.getIsClosed())
                        .isDeleted(session.getIsDeleted())
                        .createdAt(session.getCreatedAt())
                        .updatedAt(session.getUpdatedAt())
                        .build())
                .toList();
    }
    // 멘토링 저장 dto -> List ScheduleList 값 타입
    public List<ScheduleList> toScheduleListEntities() {
        return this.getMentoringSessionAddAfterOutDtoList()
                .stream()
                .map(session -> ScheduleList.builder()
                        .mentoringSessionUuid(session.getSessionUuid())
                        .mentoringName(this.getName())
                        .startDate(session.getStartDate())
                        .endDate(session.getEndDate())
                        .startTime(session.getStartTime())
                        .endTime(session.getEndTime())
                        .createdAt(session.getCreatedAt())
                        .updatedAt(session.getUpdatedAt())
                        .build())
                .toList();
    }


    public List<SessionUserHistory> toSessionUserHistories() {
        return this.getMentoringSessionAddAfterOutDtoList()
                .stream()
                .map(session -> SessionUserHistory.builder()
                        .userUuid(this.mentorUuid) // 멘토 uuid
                        .mentoringSessionUuid(session.getSessionUuid())
                        .mentoringName(this.getName())
                        .price(session.getPrice())
                        .startDate(session.getStartDate())
                        .endDate(session.getEndDate())
                        .startTime(session.getStartTime())
                        .endTime(session.getEndTime())
                        .createdAt(session.getCreatedAt())
                        .updatedAt(session.getUpdatedAt())
                        .build())
                .toList();
    }
}
