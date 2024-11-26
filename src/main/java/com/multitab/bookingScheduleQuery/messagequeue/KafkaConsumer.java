package com.multitab.bookingScheduleQuery.messagequeue;

import com.multitab.bookingScheduleQuery.application.ScheduleService;
import com.multitab.bookingScheduleQuery.application.SessionRequestService;
import com.multitab.bookingScheduleQuery.messagequeue.messageIn.*;
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
     * 세션 추가 이벤트 컨슘
     */
    @KafkaListener(topics = "add-session", groupId = "kafka-sessionRequest-query-service",
            containerFactory = "addSessionUserOutDtoListener")
    public void addSession(SessionCreatedAfterOutDto dto) {
        sessionRequestService.createSessionRequestList(dto);
        // 멘토 스케줄 insert or push update
        scheduleService.updateMentorSchedule(dto);
    }

    /**
     * 멘토링 세션 참가 등록 이벤트 컨슘
     * 1. 멘토링 세션 참가 리스트 read data insert or update
     * 2. 멘티 스케줄 insert or update
     */
    @KafkaListener(topics = "register-session-user", groupId = "kafka-sessionRequest-query-service",
            containerFactory = "afterSessionUserOutDtoListener")
    public void registerSessionUser(AfterSessionUserOutDto dto) {
        // 세션 참가리스트 업데이트
        sessionRequestService.updateSessionRequestList(dto);
        // 유저(멘티)의 스케줄 insert or update
        scheduleService.updateMenteeSchedule(dto);
    }

    /**
     * 멘토링 세션 참가 등록 '취소' 이벤트 컨슘
     * 1. 멘토링 세션 참가 리스트 update
     * 2. 멘티 스케줄 update
     */
    @KafkaListener(topics = "cancel-session-user", groupId = "kafka-sessionRequest-query-service",
            containerFactory = "cancelSessionUserOutDtoListener")
    public void cancelSessionUser(CancelSessionUserMessage dto) {
        // 세션 참가리스트 업데이트 (취소 상태로)
        sessionRequestService.cancelSessionUser(dto);
        // 유저 스케줄 업데이트
        scheduleService.cancelSessionUser(dto);
    }


    /**
     * 멘토링 세션 '재'참가 등록  이벤트 컨슘
     */
    @KafkaListener(topics = "re-register-session-user", groupId = "kafka-sessionRequest-query-service",
            containerFactory = "reRegisterSessionUserOutDtoListener")
    public void reRegisterSessionUser(ReRegisterSessionUserMessage dto) {
        // 세션 참가리스트 업데이트 (취소 -> 대기 상태)
        sessionRequestService.reRegisterSessionUser(dto);
        // 유저 스케줄 업데이트
        scheduleService.reRegisterSessionUser(dto);
    }

    /**
     * 세션 유저 업데이트 이벤트 컨슘
     */

    @KafkaListener(topics = "update-session-user", groupId = "kafka-sessionRequest-query-service",
            containerFactory = "updateSessionUserListener")
    public void updateSessionUser(SessionUserUpdateMessage dto) {
        // 세션 참가리스트 업데이트
        sessionRequestService.updateSessionRequestStatus(dto);
        // 유저 스케줄 업데이트
        scheduleService.updateSessionUserStatus(dto);
        log.info("updateSessionUser 성공 : " + dto);
    }

    /**
     * 세션 확정 -> 확정 여부에 따라 멘토 스케줄 상태 업데이트
     */
    @KafkaListener(topics = "update-session-confirmed", groupId = "kafka-sessionRequest-query-service",
            containerFactory = "sessionConfirmedListener")
    public void updateSessionConfirmed(SessionConfirmedMessage dto) {
        scheduleService.updateMentorScheduleStatus(dto);

    }


}

