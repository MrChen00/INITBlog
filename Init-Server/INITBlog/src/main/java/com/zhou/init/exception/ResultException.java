package com.zhou.init.exception;

import com.zhou.init.enums.ResultEnums;

/**
 * @author ZHOU
 * @create 2019-02-21 22:20
 */
public class ResultException extends RuntimeException {

    public ResultException(String message){
        super(message);
    }

    public ResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultException(ResultEnums resultEnums) {

    }

}
