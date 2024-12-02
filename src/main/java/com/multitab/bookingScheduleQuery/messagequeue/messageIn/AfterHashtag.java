package com.multitab.bookingScheduleQuery.messagequeue.messageIn;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AfterHashtag {
    private String hashtagId;
    private String hashtagName;
}
