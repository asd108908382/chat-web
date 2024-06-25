package org.sea.chat.exception;

import com.alibaba.cola.dto.SingleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author guojiawei
 * @version 1.0
 * @date 2023/2/11 21:25
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public SingleResponse<?> exceptionHandler(BizException e) {
        log.error(e.getMessage());
        return SingleResponse.buildFailure(String.valueOf(e.getErrorCode()), e.getMessage());
    }


}
