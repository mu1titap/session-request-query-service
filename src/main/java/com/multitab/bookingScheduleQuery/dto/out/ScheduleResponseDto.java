package com.multitab.bookingScheduleQuery.dto.out;

import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ScheduleResponseDto {
    private String userUuid;
    private String yearMonth;
    private List<ScheduleList> scheduleLists;

}
