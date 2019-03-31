package com.zhou.init.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import com.zhou.init.dto.HttpExceptionCode;
import com.zhou.init.dto.Result;
import com.zhou.init.enums.HttpExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 全局异常处理
 *
 * @author ZHOU
 * @create 2019-02-26 10:20
 */
//@Component
public class SpringHandlerExceptionResolver implements HandlerExceptionResolver, Ordered {

    /**
     * 由于我们是前后端分离的项目, 必然发送到前台不能处理
     *   所以我们在这设置最高的异常处理, 拦截Http请求异常.
     *   并返回到前台识别
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 设置优先级最高
     *      HIGHEST_PRECEDENCE
     *      最高_优先级
     */
    private int order = Ordered.HIGHEST_PRECEDENCE;

    @Override
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e) {
        logger.debug("异常" + e.getMessage(), e);
        Result result = doResolveException(request, response, obj, e);

        ModelAndView mv = new ModelAndView();

        FastJsonJsonView view = new FastJsonJsonView();

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("code", result.getCode());
        objectMap.put("data", result.getData());
        view.setAttributesMap(objectMap);
        mv.setView(view);
        logger.debug("异常" + e.getMessage(), e);
        return mv;
    }

    protected Result doResolveException(HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {
        try {
            if (ex instanceof HttpRequestMethodNotSupportedException) {
                return new Result(HttpExceptionCode.NOT_SUPPORTED_METHOD_EXCEPTION, HttpExceptionEnum.NOT_SUPPORTED_METHOD_EXCEPTION);
            }

            if (ex instanceof HttpMediaTypeNotSupportedException) {
                return new Result(HttpExceptionCode.NOT_SUPPORTED_MEDIA_TYPE_EXCEPTION, HttpExceptionEnum.NOT_SUPPORTED_MEDIA_TYPE_EXCEPTION);
            }

            if (ex instanceof HttpMediaTypeNotAcceptableException) {
                return new Result(HttpExceptionCode.NOT_ACCEPTABLE_MEDIA_TYPE_EXCEPTION, HttpExceptionEnum.NOT_ACCEPTABLE_MEDIA_TYPE_EXCEPTION);
            }

            if (ex instanceof MissingPathVariableException) {
                return new Result(HttpExceptionCode.MISSING_PATH_VARIABLE, HttpExceptionEnum.MISSING_PATH_VARIABLE);
            }

            if (ex instanceof MissingServletRequestParameterException) {
                return new Result(HttpExceptionCode.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION, HttpExceptionEnum.MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION);
            }

            if (ex instanceof ServletRequestBindingException) {
                return new Result(HttpExceptionCode.SERVLET_REQUEST_BINDING_EXCEPTION, HttpExceptionEnum.SERVLET_REQUEST_BINDING_EXCEPTION);
            }

            if (ex instanceof ConversionNotSupportedException) {
                return new Result(HttpExceptionCode.NOT_SUPPORT_CONVERSION_EXCEPTION, HttpExceptionEnum.NOT_SUPPORT_CONVERSION_EXCEPTION);
            }

            if (ex instanceof TypeMismatchException) {
                return new Result(HttpExceptionCode.TYPE_MISMATCH_EXCEPTION, HttpExceptionEnum.TYPE_MISMATCH_EXCEPTION);
            }

            if (ex instanceof HttpMessageNotReadableException) {
                return new Result(HttpExceptionCode.NOT_READABLE_HTTP_MESSAGE_EXCEPTION, HttpExceptionEnum.NOT_READABLE_HTTP_MESSAGE_EXCEPTION);
            }

            if (ex instanceof HttpMessageNotWritableException) {
                return new Result(HttpExceptionCode.NOT_WRITABLE_HTTP_MESSAGE_EXCEPTION, HttpExceptionEnum.NOT_WRITABLE_HTTP_MESSAGE_EXCEPTION);
            }

            if (ex instanceof MethodArgumentNotValidException) {
                return new Result(HttpExceptionCode.NOT_VALID_METHOD_ARGUMENT_EXCEPTION, HttpExceptionEnum.NOT_VALID_METHOD_ARGUMENT_EXCEPTION);
            }

            if (ex instanceof MissingServletRequestPartException) {
                return new Result(HttpExceptionCode.MISSING_SERVLET_REQUEST_PART_EXCEPTION, HttpExceptionEnum.MISSING_SERVLET_REQUEST_PART_EXCEPTION);
            }

            if (ex instanceof BindException) {
                return new Result(HttpExceptionCode.BIND_EXCEPTION, HttpExceptionEnum.BIND_EXCEPTION);
            }

            if (ex instanceof NoHandlerFoundException) {
                return new Result(HttpExceptionCode.NO_HANDLER_FOUND_EXCEPTION, HttpExceptionEnum.NO_HANDLER_FOUND_EXCEPTION);
            }

            if (ex instanceof AsyncRequestTimeoutException) {
                return new Result(HttpExceptionCode.ASYNC_REQUEST_TIMEOUT_EXCEPTION, HttpExceptionEnum.ASYNC_REQUEST_TIMEOUT_EXCEPTION);
            }
        } catch (Exception var6) {
            if (this.logger.isWarnEnabled()) {
                this.logger.warn("Failure while trying to resolve exception [" + ex.getClass().getName() + "]", var6);
            }
        }
        return null;
    }




}
