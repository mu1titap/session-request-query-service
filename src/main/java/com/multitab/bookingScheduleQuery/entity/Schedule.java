package com.multitab.bookingScheduleQuery.entity;

import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor
@Document(collection = "user_schedule")
@CompoundIndexes({
        // 스케줄 시작 날짜와 시간으로 인덱스 오름차순 생성
        @CompoundIndex(
                name = "start_date_time_idx",
                def = "{'scheduleLists.startDate': 1, 'scheduleLists.startTime': 1}"
        ),
        @CompoundIndex(
                name = "year_month_idx",
                def = "{'yearMonth': 1}"
        )
})
public class Schedule {
    @Id
    private String id;
    private String userUuid;
    private String yearMonth; // 연도와 월로 관리(스케줄 무한정 업데이트 방지 ex) "2024-09"

    @Setter
    private List<ScheduleList> scheduleLists;

    @Builder
    public Schedule(String userUuid, String yearMonth, List<ScheduleList> scheduleLists) {
        this.userUuid = userUuid;
        this.yearMonth = yearMonth;
        this.scheduleLists = scheduleLists;
    }
}
