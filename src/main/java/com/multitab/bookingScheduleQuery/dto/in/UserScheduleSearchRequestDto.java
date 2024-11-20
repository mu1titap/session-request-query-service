package com.multitab.bookingScheduleQuery.dto.in;

import com.multitab.bookingScheduleQuery.viewObject.in.UserScheduleSearchRequestVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class UserScheduleSearchRequestDto {
    private String userUuid;
    private String yearMonth;

    public static UserScheduleSearchRequestDto of (String userUuid, UserScheduleSearchRequestVo vo) {
        return UserScheduleSearchRequestDto.builder()
                .userUuid(userUuid)
                .yearMonth(vo.getYearMonth())
                .build();
    }
}
