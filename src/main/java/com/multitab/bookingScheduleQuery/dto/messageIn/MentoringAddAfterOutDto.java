package com.multitab.bookingScheduleQuery.dto.messageIn;

import com.multitab.bookingScheduleQuery.entity.Schedule;
import com.multitab.bookingScheduleQuery.entity.SessionRequest;
import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import com.multitab.bookingScheduleQuery.util.DateConverter;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"mentoringSessionAddAfterOutDtoList","mentoringCategoryAfterOutDtoList"})
//@ToString
public class MentoringAddAfterOutDto {
    private String mentoringId;

    private String mentoringUuid;

    private String name;

    private String detail;

    private String mentorUuid;

    private String thumbnailUrl;

    private Boolean isReusable;

    private Boolean isDeleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<MentoringSessionAddAfterOutDto> mentoringSessionAddAfterOutDtoList;

    private List<MentoringCategoryAfterOutDto> mentoringCategoryAfterOutDtoList;

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
    // 멘토링 저장 dto -> 스케줄 엔티티
    public Schedule toScheduleEntity() {
        return Schedule.builder()
                .userUuid(this.getMentorUuid())
                .yearMonth(DateConverter.convertToYearMonth(this.getCreatedAt()))
                .scheduleLists(toScheduleListEntities())
                .build();
    }

}
