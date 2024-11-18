package com.multitab.bookingScheduleQuery.application;

import com.multitab.bookingScheduleQuery.entity.SessionRequest;
import com.multitab.bookingScheduleQuery.messagequeue.messageIn.*;
import com.multitab.bookingScheduleQuery.dto.out.SessionRequestResponseDto;
import com.multitab.bookingScheduleQuery.infrastructure.SessionRequestMongoRepository;
import com.multitab.bookingScheduleQuery.infrastructure.custom.CustomSessionRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class SessionRequestServiceImpl implements SessionRequestService {
    private final SessionRequestMongoRepository sessionRequestMongoRepository;
    private final CustomSessionRequestRepository customSessionRequestRepository;
    @Override
    public void createSessionRequestList(MentoringAddAfterOutDto dto) {
        if(dto.getMentoringSessionAddAfterOutDtoList() != null){
            sessionRequestMongoRepository.saveAll(dto.toSessionRequestEntities());
        }
    }

    @Override
    public void createSessionRequestList(SessionCreatedAfterOutDto dto) {
        List<SessionRequest> sessionRequestEntities = dto.toSessionRequestEntities();
        sessionRequestEntities.forEach(SessionRequest::initNowHeadCount);
        sessionRequestMongoRepository.saveAll(sessionRequestEntities);
    }


    @Override
    public void updateSessionRequestList(AfterSessionUserOutDto dto) {
        customSessionRequestRepository.updateSessionRequestList(dto);
    }

    @Override
    public void cancelSessionUser(CancelSessionUserMessage dto) {
        customSessionRequestRepository.cancelSessionUser(dto);
    }

    @Override
    public void reRegisterSessionUser(ReRegisterSessionUserMessage dto) {
        customSessionRequestRepository.reRegisterSessionUser(dto);
    }


    @Override
    public SessionRequestResponseDto findSessionRequestResponseDto(String sessionUuid) {
        return SessionRequestResponseDto
                .from(customSessionRequestRepository.findSessionRequestBySessionUuid(sessionUuid));
    }
}
