package com.multitab.bookingScheduleQuery.messagequeue.messageIn;

import com.multitab.bookingScheduleQuery.entity.vo.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SessionUserUpdateMessage {
    private String userUuid;
    private LocalDate startDate;
    private String sessionUuid;
    private Status status;
}
