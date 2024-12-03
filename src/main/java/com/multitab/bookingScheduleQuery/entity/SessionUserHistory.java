package com.multitab.bookingScheduleQuery.entity;

import com.multitab.bookingScheduleQuery.entity.vo.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "session_user_history")
@Builder
public class SessionUserHistory {
    @Id
    private String id;
    private String userUuid;
    private String mentoringSessionUuid;
    private String mentoringName;


    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private Integer price;
    private Integer minHeadCount;
    private Integer maxHeadCount;
    private Integer nowHeadCount;

    private Status status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void initStatus() {
        this.status = Status.PENDING;
    }
}
