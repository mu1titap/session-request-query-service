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
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_schedule_list")
@Builder
public class ScheduleUser {
    @Id
    private String id;
    private String userUuid;
    private ScheduleList scheduleLists;
}
