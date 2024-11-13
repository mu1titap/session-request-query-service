package com.multitab.bookingScheduleQuery.messagequeue;

import com.multitab.bookingScheduleQuery.application.ScheduleService;
import com.multitab.bookingScheduleQuery.application.SessionRequestService;
import com.multitab.bookingScheduleQuery.dto.messageIn.AfterSessionUserOutDto;
import com.multitab.bookingScheduleQuery.dto.messageIn.MentoringAddAfterOutDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class KafkaConsumer {
    private final SessionRequestService sessionRequestService;
    private final ScheduleService scheduleService;

    /**
     * 멘토링 생성 이벤트 컨슘
     * 1. 멘토링 세션 참가 리스트 Read Data 생성
     * 2. 멘토 스케줄 업데이트
     */
    @KafkaListener(topics = "create-mentoring", groupId = "kafka-sessionRequest-query-service",
            containerFactory = "mentoringAddAfterDtoListener")
    public void createMentoring(MentoringAddAfterOutDto dto) {
        // 멘토링 세션 참가 리스트 Read Data 생성
        sessionRequestService.createSessionRequestList(dto);
        // 멘토 스케줄 insert or push update
        scheduleService.updateMentorSchedule(dto);
    }
    /**
     * 멘토링 세션 참가 등록 이벤트 컨슘
     * 1. 멘토링 세션 참가 리스트 read data insert or update
     * 2. 멘티 스케줄 insert or update
     * 3. 세션 read data update (한번 더 생각)
     */
    @KafkaListener(topics = "register-session-user", groupId = "kafka-sessionRequest-query-service",
            containerFactory = "afterSessionUserOutDtoListener")
    public void registerSessionUser(AfterSessionUserOutDto dto) {
        log.info("AfterSessionUserOutDto: {}", dto);
        // 세션 참가리스트 업데이트
        sessionRequestService.updateSessionRequestList(dto);
        // 유저(멘티)의 스케줄 insert or update
        scheduleService.updateMenteeSchedule(dto);
    }


}

