package com.multitab.bookingScheduleQuery.serviceCall;

import com.multitab.bookingScheduleQuery.serviceCall.dto.in.SessionTimeResponseOutDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="mentoring-service" , url = "${mentoring-service.base-url}")
public interface MentoringServiceFeignClient {

    // mentoring-service 의 세션 조회 api 호출
    @GetMapping("/api/v1/mentoring-service/session-time/{uuid}")
    SessionTimeResponseOutDto getSessionTime(@PathVariable(name = "uuid") String uuid);

}
