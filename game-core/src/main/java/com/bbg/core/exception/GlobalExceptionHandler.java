package com.bbg.core.exception;

import com.bbg.core.entity.ApiRet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 参数校验
    @ExceptionHandler(BindException.class)
    public ApiRet<Object> bindExceptionHandler(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(item -> {
            errors.add(item.getDefaultMessage());
        });
        String errorMessage = StringUtils.join(errors, " , ");
        return ApiRet.buildNo(null, errorMessage);
    }
}
