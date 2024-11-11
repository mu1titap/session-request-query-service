package com.multitab.bookingScheduleQuery.application;

import com.multitab.bookingScheduleQuery.dto.in.UserScheduleSearchRequestDto;
import com.multitab.bookingScheduleQuery.dto.messageIn.MentoringAddAfterOutDto;
import com.multitab.bookingScheduleQuery.dto.out.ScheduleResponseDto;
import com.multitab.bookingScheduleQuery.dto.out.SessionRequestResponseDto;
import com.multitab.bookingScheduleQuery.entity.Schedule;
import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import com.multitab.bookingScheduleQuery.entity.vo.ScheduleStatus;
import com.multitab.bookingScheduleQuery.infrastructure.ScheduleMongoRepository;
import com.multitab.bookingScheduleQuery.infrastructure.SessionRequestMongoRepository;
import com.multitab.bookingScheduleQuery.infrastructure.custom.CustomScheduleRepository;
import com.multitab.bookingScheduleQuery.infrastructure.custom.CustomSessionRequestRepository;
import com.multitab.bookingScheduleQuery.util.DateConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class BookingScheduleQueryServiceImpl implements BookingScheduleQueryService{
    private final SessionRequestMongoRepository sessionRequestMongoRepository;
    private final ScheduleMongoRepository scheduleMongoRepository;
    private final CustomScheduleRepository customScheduleRepository;
    private final CustomSessionRequestRepository customSessionRequestRepository;
    @Override
    public void createSessionRequestList(MentoringAddAfterOutDto dto) {
        sessionRequestMongoRepository.saveAll(dto.toSessionRequestEntities());
    }
    @Override
    public void updateMentorSchedule(MentoringAddAfterOutDto dto) {
        String userUuid = dto.getMentorUuid();
        List<ScheduleList> scheduleListEntities = dto.toScheduleListEntities();
        // 멘토 스케줄 상태 [대기]로 초기화
        scheduleListEntities.forEach(schedule -> schedule.initStatus(ScheduleStatus.PENDING));
        // 존재하면 push update
        String yearMonth = DateConverter.convertToYearMonth(dto.getCreatedAt());
        if (scheduleMongoRepository.existsByUserUuidAndYearMonth(userUuid,yearMonth)){
            customScheduleRepository.updateMentorSchedule(userUuid, scheduleListEntities);
        }
        // 없으면 insert
        else {
            Schedule schedule = dto.toScheduleEntity();
            schedule.getScheduleLists().forEach(scheduleList -> scheduleList.initStatus(ScheduleStatus.PENDING));
            scheduleMongoRepository.save(schedule);
        }
    }

    @Override
    public ScheduleResponseDto findByUserUuidAndYearMonth(UserScheduleSearchRequestDto userScheduleSearchRequestDto) {
        Schedule schedule = customScheduleRepository.findByUserScheduleOrderByStartDateAsc
                (userScheduleSearchRequestDto.getUserUuid(), userScheduleSearchRequestDto.getYearMonth());
        return ScheduleResponseDto.from(schedule);
    }

    @Override
    public SessionRequestResponseDto findSessionRequestResponseDto(String sessionUuid) {
        return SessionRequestResponseDto
                .from(customSessionRequestRepository.findSessionRequestBySessionUuid(sessionUuid));


    }
}
