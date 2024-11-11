package com.multitab.bookingScheduleQuery.dto.in;

import com.multitab.bookingScheduleQuery.viewObject.in.UserScheduleSearchRequestVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserScheduleSearchRequestDto {
    private String userUuid;
    private String yearMonth;

    public static UserScheduleSearchRequestDto from(UserScheduleSearchRequestVo vo) {
        return UserScheduleSearchRequestDto.builder()
                .userUuid(vo.getUserUuid())
                .yearMonth(vo.getYearMonth())
                .build();
    }
}
