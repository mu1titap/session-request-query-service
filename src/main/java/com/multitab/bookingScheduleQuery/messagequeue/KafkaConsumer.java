package com.multitab.bookingScheduleQuery.messagequeue;

import com.multitab.bookingScheduleQuery.dto.in.MentoringAddAfterOutDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class KafkaConsumer {
    // 멘토링 생성
    @KafkaListener(topics = "create-mentoring", groupId = "kafka-bookingSchedule-query-service",
            containerFactory = "mentoringAddAfterDtoListener")
    public void createMentoring(MentoringAddAfterOutDto dto) {
        // 멘토링 저장
        log.info("멘토링 생성 이벤트 dto 수신 완료");
        log.info(dto);
        log.info(dto.getMentoringSessionAddAfterOutDtoList());
    }




}

