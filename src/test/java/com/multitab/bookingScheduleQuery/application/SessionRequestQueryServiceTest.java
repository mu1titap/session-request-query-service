package com.multitab.bookingScheduleQuery.application;

import com.multitab.bookingScheduleQuery.dto.messageIn.MentoringAddAfterOutDto;
import com.multitab.bookingScheduleQuery.dto.messageIn.MentoringSessionAddAfterOutDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@Log4j2
class SessionRequestQueryServiceTest {
    @Autowired
    SessionRequestService sessionRequestQueryService;

    @Test
    void updateMentorSchedule(){
        log.info("test start");
        // given
        MentoringAddAfterOutDto testDto = MentoringAddAfterOutDto.builder()
                .mentoringId("100")
                .mentorUuid("test code mentorUuid")
                .name("test code name")
                .detail("test code detail")
                .isReusable(true)
                .mentoringSessionAddAfterOutDtoList(
                        List.of(
                                MentoringSessionAddAfterOutDto.builder()
                                        .sessionUuid("test code sessionUuid 1")
                                        .startDate(LocalDate.of(2024, 11, 29))
                                        .startTime(LocalTime.of(3, 0, 0))
                                        .endDate(LocalDate.of(2024, 11, 29))
                                        .startTime(LocalTime.of(4, 0, 0))
                                        .build(),
                                MentoringSessionAddAfterOutDto.builder()
                                        .sessionUuid("test code sessionUuid 2")
                                        .startDate(LocalDate.of(2024, 11, 30))
                                        .startTime(LocalTime.of(3, 0, 0))
                                        .endDate(LocalDate.of(2024, 11, 30))
                                        .startTime(LocalTime.of(4, 0, 0))
                                        .build(),
                                MentoringSessionAddAfterOutDto.builder()
                                        .sessionUuid("test code sessionUuid 3")
                                        .startDate(LocalDate.of(2024, 12, 01))
                                        .startTime(LocalTime.of(3, 0, 0))
                                        .endDate(LocalDate.of(2024, 12, 01))
                                        .startTime(LocalTime.of(4, 0, 0))
                                        .build()

                        )
                )
                .build();
        // when
        sessionRequestQueryService.updateMentorSchedule(testDto);
        // then
    }

}