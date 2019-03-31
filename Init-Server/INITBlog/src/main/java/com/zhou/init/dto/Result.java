package com.zhou.init.dto;

import com.zhou.init.enums.HttpExceptionEnum;
import com.zhou.init.enums.ResultEnums;
import lombok.Getter;
import lombok.Setter;

/**
 *
 *
 *
 * @author ZHOU
 * @create 2019-02-14 14:11
 */
@Getter
@Setter
public class Result {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 返回数据
     */
    private Object data;

    public Result(){}

    public Result(Integer code, Object data){
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, ResultEnums resultEnums){
        this.code = code;
        this.data = resultEnums.getInfo();
    }

    public Result(Integer code, HttpExceptionEnum httpExceptionEnum){
        this.code = code;
        this.data = httpExceptionEnum.getInfo();
    }

}
