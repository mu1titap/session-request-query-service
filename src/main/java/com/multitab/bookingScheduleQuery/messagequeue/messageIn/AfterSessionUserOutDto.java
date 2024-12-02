package com.multitab.bookingScheduleQuery.messagequeue.messageIn;

import com.multitab.bookingScheduleQuery.entity.Schedule;
import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import com.multitab.bookingScheduleQuery.entity.vo.SessionRequestMenteeList;
import com.multitab.bookingScheduleQuery.entity.vo.Status;
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
    private String menteeImageUrl;

    private Status status;

    private String mentoringName;
    private Boolean isClosed;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SessionRequestMenteeList toMongoSessionRequestMenteeList() {
        return SessionRequestMenteeList.builder()
                .menteeUuid(this.menteeUuid)
                .menteeImageUrl(this.menteeImageUrl)
                .status(this.status)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }


}
