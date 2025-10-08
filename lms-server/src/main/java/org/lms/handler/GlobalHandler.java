package org.lms.handler;

import org.lms.response.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(Exception.class)
    public Result globalHandlerAdvice(Exception e){
        return Result.error("异常"+e.getCause());
    }

}
