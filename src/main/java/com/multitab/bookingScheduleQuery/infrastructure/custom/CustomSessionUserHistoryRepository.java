package com.multitab.bookingScheduleQuery.infrastructure.custom;

import com.multitab.bookingScheduleQuery.dto.out.SessionUserHistoryResponseDto;
import com.multitab.bookingScheduleQuery.entity.vo.Status;
import com.multitab.bookingScheduleQuery.messagequeue.messageIn.CancelSessionUserMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomSessionUserHistoryRepository {
    void cancelSessionUserHistory(String userUuid, String sessionUuid);

    void updatePendingStatus(String userUuid, String sessionUuid);

    void updateSessionUserHistoryStatus(String userUuid, String sessionUuid , Status status);
    void updateSessionUserHistoryStatus(String userUuid, String sessionUuid , Status status, Integer minHeadCount, Integer maxHeadCount, Integer nowHeadCount);

    void updateEndSession(String sessionUuid);

    Page<SessionUserHistoryResponseDto> searchSessionUserHistory(String userUuid, Pageable pageable, Boolean includeCancelled);

}
