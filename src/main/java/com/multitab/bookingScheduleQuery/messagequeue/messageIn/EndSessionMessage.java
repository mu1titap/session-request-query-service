package com.multitab.bookingScheduleQuery.messagequeue.messageIn;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EndSessionMessage {
    private String sessionUuid;

}
