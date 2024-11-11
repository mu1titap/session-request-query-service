package com.multitab.bookingScheduleQuery.messagequeue;

import com.multitab.bookingScheduleQuery.application.BookingScheduleQueryService;
import com.multitab.bookingScheduleQuery.dto.messageIn.MentoringAddAfterOutDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class KafkaConsumer {
    private final BookingScheduleQueryService bookingScheduleQueryService;

    @KafkaListener(topics = "create-mentoring", groupId = "kafka-bookingSchedule-query-service",
            containerFactory = "mentoringAddAfterDtoListener")
    public void createMentoring(MentoringAddAfterOutDto dto) {
        // 멘토링 세션 참가 리스트 Read Data 생성
        bookingScheduleQueryService.createSessionRequestList(dto);
        // 멘토 스케줄 insert or push update
        bookingScheduleQueryService.updateMentorSchedule(dto);
    }


}

