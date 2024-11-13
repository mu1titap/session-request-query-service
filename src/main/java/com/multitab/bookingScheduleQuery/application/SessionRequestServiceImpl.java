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
public class SessionRequestServiceImpl implements SessionRequestService {
    private final SessionRequestMongoRepository sessionRequestMongoRepository;
    private final ScheduleMongoRepository scheduleMongoRepository;
    private final CustomScheduleRepository customScheduleRepository;
    private final MentoringServiceFeignClient mentoringServiceFeignClient;
    private final CustomSessionRequestRepository customSessionRequestRepository;
    @Override
    public void createSessionRequestList(MentoringAddAfterOutDto dto) {
        sessionRequestMongoRepository.saveAll(dto.toSessionRequestEntities());
    }



    @Override
    public void updateSessionRequestList(AfterSessionUserOutDto dto) {
        customSessionRequestRepository.updateSessionRequestList(dto);
    }





    @Override
    public SessionRequestResponseDto findSessionRequestResponseDto(String sessionUuid) {
        return SessionRequestResponseDto
                .from(customSessionRequestRepository.findSessionRequestBySessionUuid(sessionUuid));
    }
}
