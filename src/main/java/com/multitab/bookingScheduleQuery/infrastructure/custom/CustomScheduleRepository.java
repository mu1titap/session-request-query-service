package com.multitab.bookingScheduleQuery.infrastructure.custom;

import com.multitab.bookingScheduleQuery.dto.in.UserScheduleSearchRequestDto;
import com.multitab.bookingScheduleQuery.entity.Schedule;
import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;

import java.util.List;

public interface CustomScheduleRepository {
    void updateMentorSchedule(String userUuid, List<ScheduleList> scheduleListEntities);

    Schedule findByUserScheduleOrderByStartDateAsc(String userUuid, String yearMonth);
}
