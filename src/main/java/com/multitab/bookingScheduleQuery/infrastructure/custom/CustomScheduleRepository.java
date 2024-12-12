//package com.multitab.bookingScheduleQuery.infrastructure.custom;
//
//import com.multitab.bookingScheduleQuery.dto.out.MentoringSessionScheduleResponseDto;
//import com.multitab.bookingScheduleQuery.entity.vo.Status;
//import com.multitab.bookingScheduleQuery.messagequeue.messageIn.AfterSessionUserOutDto;
//import com.multitab.bookingScheduleQuery.entity.Schedule;
//import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
//import com.multitab.bookingScheduleQuery.serviceCall.dto.in.SessionTimeResponseOutDto;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public interface CustomScheduleRepository {
//    void updateMentorSchedule(String userUuid, List<ScheduleList> scheduleLists , String yearMonth);
//
//    void cancelMentorSchedule(String userUuid, String yearMonth, String sessionUuid);
//
//    void updateMentorScheduleStatus(String mentorUuid, String sessionUuid, String yearMonth, Status status);
//
//    void reRegisterMentorSchedule(String userUuid, String yearMonth, String sessionUuid);
//
//    Schedule findByUserScheduleOrderByStartDateAsc(String userUuid, String yearMonth);
//
//    void updateMenteeSchedule(AfterSessionUserOutDto afterSessionDto, SessionTimeResponseOutDto sessionTimeDto, String yearMonth);
//
//    void updateMenteeScheduleStatus(String userUuid, String yearMonth, String sessionUuid, Status status);
//
//    List<MentoringSessionScheduleResponseDto> findTodaySessionSchedule(String userUuid, String yearMonth, LocalDate startDate);
//}
//