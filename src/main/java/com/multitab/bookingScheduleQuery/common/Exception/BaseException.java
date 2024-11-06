package com.multitab.bookingScheduleQuery.common.Exception;

import com.multitab.bookingScheduleQuery.common.entity.BaseResponseStatus;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{

    private final BaseResponseStatus status;

    public BaseException(BaseResponseStatus status) {
        this.status = status;
    }
}