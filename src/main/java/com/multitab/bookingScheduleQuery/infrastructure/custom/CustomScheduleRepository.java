package com.multitab.bookingScheduleQuery.infrastructure.custom;

import com.multitab.bookingScheduleQuery.dto.in.UserScheduleSearchRequestDto;
import com.multitab.bookingScheduleQuery.dto.messageIn.AfterSessionUserOutDto;
import com.multitab.bookingScheduleQuery.entity.Schedule;
import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import com.multitab.bookingScheduleQuery.serviceCall.dto.in.SessionTimeResponseOutDto;

import java.util.List;

public interface CustomScheduleRepository {
    void updateMentorSchedule(String userUuid, List<ScheduleList> scheduleLists , String yearMonth);

    Schedule findByUserScheduleOrderByStartDateAsc(String userUuid, String yearMonth);

    void updateMenteeSchedule(AfterSessionUserOutDto afterSessionDto, SessionTimeResponseOutDto sessionTimeDto, String yearMonth);
}
