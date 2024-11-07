package com.multitab.bookingScheduleQuery.entity;

import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor
@Document(collection = "user_schedule")
public class Schedule {
    @Id
    private String id;
    private String userUuid;
    private String yearMonth; // 연도와 월로 관리(스케줄 무한정 업데이트 방지 ex) "2024-09"

    private List<ScheduleList> scheduleLists;

    @Builder
    public Schedule(String userUuid, String yearMonth, List<ScheduleList> scheduleLists) {
        this.userUuid = userUuid;
        this.yearMonth = yearMonth;
        this.scheduleLists = scheduleLists;
    }
}
