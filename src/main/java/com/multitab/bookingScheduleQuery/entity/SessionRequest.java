package com.multitab.bookingScheduleQuery.entity;

import com.multitab.bookingScheduleQuery.entity.vo.SessionRequestMenteeList;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "sessionRequest")
@ToString(exclude = "sessionRequestLists")
public class SessionRequest {
    @Id
    private String id;

    private String mentoringUuid;
    private String mentoringSessionUuid; // 멘토링 세션 UUID

    private Integer minHeadCount; // 최소 신청인원수
    private Integer maxHeadCount; // 최대 신청인원수
    private Integer nowHeadCount; // 현재 신청인원
    private LocalDate deadLineDate; // 예약마감일

    private Boolean isClosed; // 세션 마감 여부
    private Boolean isDeleted; // 삭제 여부
    private List<SessionRequestMenteeList> sessionRequestLists; // 세션 신청자 리스트

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
