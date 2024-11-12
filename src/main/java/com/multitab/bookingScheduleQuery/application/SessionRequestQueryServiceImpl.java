package com.multitab.bookingScheduleQuery.application;

import com.multitab.bookingScheduleQuery.dto.in.UserScheduleSearchRequestDto;
import com.multitab.bookingScheduleQuery.dto.messageIn.AfterSessionUserOutDto;
import com.multitab.bookingScheduleQuery.dto.messageIn.MentoringAddAfterOutDto;
import com.multitab.bookingScheduleQuery.dto.out.ScheduleResponseDto;
import com.multitab.bookingScheduleQuery.dto.out.SessionRequestResponseDto;
import com.multitab.bookingScheduleQuery.entity.Schedule;
import com.multitab.bookingScheduleQuery.entity.vo.ScheduleList;
import com.multitab.bookingScheduleQuery.entity.vo.Status;
import com.multitab.bookingScheduleQuery.infrastructure.ScheduleMongoRepository;
import com.multitab.bookingScheduleQuery.infrastructure.SessionRequestMongoRepository;
import com.multitab.bookingScheduleQuery.infrastructure.custom.CustomScheduleRepository;
import com.multitab.bookingScheduleQuery.infrastructure.custom.CustomSessionRequestRepository;
import com.multitab.bookingScheduleQuery.serviceCall.MentoringServiceFeignClient;
import com.multitab.bookingScheduleQuery.serviceCall.dto.in.SessionTimeResponseOutDto;
import com.multitab.bookingScheduleQuery.util.DateConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class SessionRequestQueryServiceImpl implements SessionRequestQueryService {
    private final SessionRequestMongoRepository sessionRequestMongoRepository;
    private final ScheduleMongoRepository scheduleMongoRepository;
    private final CustomScheduleRepository customScheduleRepository;
    private final CustomSessionRequestRepository customSessionRequestRepository;
    private final MentoringServiceFeignClient mentoringServiceFeignClient;
    @Override
    public void createSessionRequestList(MentoringAddAfterOutDto dto) {
        sessionRequestMongoRepository.saveAll(dto.toSessionRequestEntities());
    }


    @Override
    public void updateMentorSchedule(MentoringAddAfterOutDto dto) {
        /**
         *  예를 들어 멘토링에 세션을 3개 생성했다, 세션의 시작날짜가 "2024-10" 인게 2개, "2024-11" 인게 1개 이라면 각각
         *  "2024-10" : [세션1, 세션2] , "2024-11" : [세션3] 으로 매핑.
         *  이러면 이미 존재하는 스케줄인지 날짜별(map이라 중복 없음) + userUuid 로 2번의 DB 조회면 충분하다
         *
         * 이렇게 하지 않고 세션1, 세션2, 세션3 의 startDate와 userUuid로 조회하면 3번의 DB 조회가 필요하다.
         * 만든 멘토링 세션 수 만큼 read db를 찌르기 때문에 위의 방법으로 하는게 효율적으로 보인다.
         */
        String userUuid = dto.getMentorUuid();
        List<ScheduleList> scheduleListEntities = dto.toScheduleListEntities();
        // 멘토 스케줄 상태 [대기]로 초기화
        scheduleListEntities.forEach(schedule -> schedule.initStatus(Status.PENDING));

        // Map 생성
        Map<String, List<ScheduleList>> sessionYearMonthMap = new HashMap<>();
        scheduleListEntities.forEach(scheduleList -> {
            String yearMonth = DateConverter.convertToYearMonth(scheduleList.getStartDate());
            List<ScheduleList> existingScheduleList = sessionYearMonthMap.getOrDefault(yearMonth, new ArrayList<>());
            existingScheduleList.add(scheduleList); // 리스트에 스케줄을 추가
            sessionYearMonthMap.put(yearMonth, existingScheduleList);
        });
        // 생성한 Map 을 이용해 insert or update
        for (String yearMonth : sessionYearMonthMap.keySet() ){
            // 존재하면 push update
            if (scheduleMongoRepository.existsByUserUuidAndYearMonth(userUuid,yearMonth)){
                customScheduleRepository.updateMentorSchedule(userUuid,sessionYearMonthMap.get(yearMonth), yearMonth);
            }
            // 없으면 insert
            else{
                Schedule schedule = Schedule.builder()
                        .userUuid(userUuid)
                        .yearMonth(yearMonth)
                        .scheduleLists(sessionYearMonthMap.get(yearMonth))
                        .build();
                scheduleMongoRepository.save(schedule);
            }
        }
    }

    @Override
    public void updateSessionRequestList(AfterSessionUserOutDto dto) {
        customSessionRequestRepository.updateSessionRequestList(dto);
    }

    @Override
    public void updateMenteeSchedule(AfterSessionUserOutDto dto) {
        SessionTimeResponseOutDto sessionTime = mentoringServiceFeignClient.getSessionTime(dto.getSessionUuid());

        String yearMonth = DateConverter.convertToYearMonth(sessionTime.getStartDate());
        String userUuid = dto.getMenteeUuid();
        if (scheduleMongoRepository.existsByUserUuidAndYearMonth(userUuid,yearMonth)) {
            // update
            customScheduleRepository.updateMenteeSchedule(dto, sessionTime, yearMonth);
        }
        else{
            // insert
            scheduleMongoRepository.save(
                    Schedule.builder()
                            .userUuid(userUuid)
                            .yearMonth(yearMonth)
                            .scheduleLists(List.of(createNewMenteeSchedule(dto, sessionTime)))
                            .build()
            );
        }

    }

    private ScheduleList createNewMenteeSchedule(AfterSessionUserOutDto dto, SessionTimeResponseOutDto sessionTime) {
        return ScheduleList.builder()
                .mentoringSessionUuid(dto.getSessionUuid())
                .mentoringName(dto.getMentoringName())
                .startDate(sessionTime.getStartDate())
                .endDate(sessionTime.getEndDate())
                .startTime(sessionTime.getStartTime())
                .endTime(sessionTime.getEndTime())
                .status(dto.getStatus())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    @Override
    public ScheduleResponseDto findByUserUuidAndYearMonth(UserScheduleSearchRequestDto userScheduleSearchRequestDto) {
        log.info("userScheduleSearchRequestDto : "+userScheduleSearchRequestDto);
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
