package com.multitab.bookingScheduleQuery.messagequeue.messageIn;

import com.multitab.bookingScheduleQuery.entity.Schedule;
import com.multitab.bookingScheduleQuery.entity.SessionRequest;
import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "sessionAddAfterOutDtos")
public class SessionCreatedAfterOutDto {
    private String mentoringId;
    private String mentoringUuid;

    private String mentorUuid;
    private String mentoringName;

    List<SessionAddAfterOutDto> sessionAddAfterOutDtos;

    public List<SessionRequest> toSessionRequestEntities() {
        return this.getSessionAddAfterOutDtos()
                .stream()
                .map(session -> SessionRequest.builder()
                        .mentoringUuid(this.mentoringUuid)
                        .mentoringSessionUuid(session.getSessionUuid())
                        .minHeadCount(session.getMinHeadCount())
                        .maxHeadCount(session.getMaxHeadCount())
                        .deadLineDate(session.getDeadlineDate())
                        .isClosed(session.getIsClosed())
                        .isDeleted(session.getIsDeleted())
                        .createdAt(session.getCreatedAt())
                        .updatedAt(session.getUpdatedAt())
                        .build())
                .toList();
    }

    public List<ScheduleList> toScheduleEntities() {
        return this.getSessionAddAfterOutDtos()
                .stream()
                .map(session -> ScheduleList.builder()
                                    .mentoringSessionUuid(this.mentoringUuid)
                                    .mentoringName(this.mentoringName)
                                    .startDate(session.getStartDate())
                                    .endDate(session.getEndDate())
                                    .startTime(session.getStartTime())
                                    .endTime(session.getEndTime())
                                    .createdAt(session.getCreatedAt())
                                    .updatedAt(session.getUpdatedAt())
                                    .build()
                ).toList();
    }
}
