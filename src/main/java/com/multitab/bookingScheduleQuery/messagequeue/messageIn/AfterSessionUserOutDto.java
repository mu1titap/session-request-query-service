package com.multitab.bookingScheduleQuery.messagequeue.messageIn;

import com.multitab.bookingScheduleQuery.entity.SessionUserHistory;
import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import com.multitab.bookingScheduleQuery.entity.vo.SessionRequestMenteeList;
import com.multitab.bookingScheduleQuery.entity.vo.Status;
import com.multitab.bookingScheduleQuery.serviceCall.dto.in.SessionTimeResponseOutDto;
import com.multitab.bookingScheduleQuery.util.DateConverter;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AfterSessionUserOutDto {
    private String id; // 세션유저 Id
    private String sessionUuid;

    private String menteeUuid;
    private String nickName;
    private String menteeImageUrl;

    private Status status;

    private String mentoringName;
    private Boolean isClosed;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SessionRequestMenteeList toMongoSessionRequestMenteeList() {
        return SessionRequestMenteeList.builder()
                .menteeUuid(this.menteeUuid)
                .nickname(this.nickName)
                .menteeImageUrl(this.menteeImageUrl)
                .status(this.status)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public SessionUserHistory toSessionUserHistory(SessionTimeResponseOutDto sessionTimeDto){
        return SessionUserHistory.builder()
                .userUuid(this.menteeUuid)
                .mentoringSessionUuid(this.sessionUuid)
                .mentoringName(this.mentoringName)
                .startDate(sessionTimeDto.getStartDate())
                .startTime(sessionTimeDto.getStartTime())
                .endDate(sessionTimeDto.getEndDate())
                .endTime(sessionTimeDto.getEndTime())
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }


}
