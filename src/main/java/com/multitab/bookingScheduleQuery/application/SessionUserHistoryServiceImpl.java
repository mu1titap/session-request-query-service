package com.multitab.bookingScheduleQuery.application;

import com.multitab.bookingScheduleQuery.dto.out.SessionUserHistoryResponseDto;
import com.multitab.bookingScheduleQuery.entity.SessionUserHistory;
import com.multitab.bookingScheduleQuery.entity.vo.Status;
import com.multitab.bookingScheduleQuery.infrastructure.SessionUserHistoryMongoRepository;
import com.multitab.bookingScheduleQuery.infrastructure.custom.CustomSessionUserHistoryRepository;
import com.multitab.bookingScheduleQuery.messagequeue.messageIn.*;
import com.multitab.bookingScheduleQuery.serviceCall.MentoringServiceFeignClient;
import com.multitab.bookingScheduleQuery.serviceCall.dto.in.SessionTimeResponseOutDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class SessionUserHistoryServiceImpl implements SessionUserHistoryService {
    private final SessionUserHistoryMongoRepository sessionUserHistoryMongoRepository;
    private final MentoringServiceFeignClient mentoringServiceFeignClient;
    private final CustomSessionUserHistoryRepository customSessionUserHistoryRepository;
    @Override
    public void createSessionHistoryForMento(MentoringAddAfterOutDto dto) {
        List<SessionUserHistory> sessionUserHistories = dto.toSessionUserHistories();
        sessionUserHistories.forEach(SessionUserHistory::initStatus);
        sessionUserHistoryMongoRepository.saveAll(sessionUserHistories);
    }

    @Override
    public void createSessionHistoryForMento(SessionCreatedAfterOutDto dto) {
        List<SessionUserHistory> sessionUserHistories = dto.toSessionUserHistories();
        sessionUserHistories.forEach(SessionUserHistory::initStatus);
        sessionUserHistoryMongoRepository.saveAll(sessionUserHistories);
    }

    @Override
    public void cancelSessionUserHistory(CancelSessionUserMessage dto) {
        customSessionUserHistoryRepository.cancelSessionUserHistory(dto.getMenteeUuid(), dto.getSessionUuid());
    }

    @Override
    public void createSessionHistoryForMentee(AfterSessionUserOutDto dto) {
        SessionTimeResponseOutDto sessionTime = mentoringServiceFeignClient.getSessionTime(dto.getSessionUuid());
        SessionUserHistory sessionUserHistory = dto.toSessionUserHistory(sessionTime);
        sessionUserHistory.initStatus();
        sessionUserHistoryMongoRepository.save(sessionUserHistory);
    }

    @Override
    public void reRegisterSessionUserHistory(ReRegisterSessionUserMessage dto) {
        customSessionUserHistoryRepository.updatePendingStatus(dto.getMenteeUuid(), dto.getSessionUuid());
    }

    @Override
    public void updateSessionUserHistoryStatus(SessionUserUpdateMessage dto) {
        customSessionUserHistoryRepository.updateSessionUserHistoryStatus(dto.getUserUuid(), dto.getSessionUuid(), dto.getStatus());
    }

    @Override
    public void updateEndSession(String sessionUuid) {
        customSessionUserHistoryRepository.updateEndSession(sessionUuid);
    }

    @Override
    public void updateMentoSessionUserHistoryStatus(SessionConfirmedMessage dto) {
        customSessionUserHistoryRepository.updateSessionUserHistoryStatus(dto.getMentorUuid(), dto.getSessionUuid(),
                dto.getSessionIsConfirmed() ? Status.CONFIRMED : Status.CANCELLED_BY_SYSTEM,
                dto.getMinHeadCount(), dto.getMaxHeadCount(), dto.getNowHeadCount());
    }

    @Override
    public Page<SessionUserHistoryResponseDto> searchSessionUserHistory(String userUuid, Pageable pageable, Boolean includeCancelled) {
        return customSessionUserHistoryRepository.searchSessionUserHistory(userUuid, pageable, includeCancelled);
    }

}
