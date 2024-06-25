package org.sea.chat.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author guojiawei
 * @version 1.0
 * @date 2023/2/11 20:43
 */
@Data
@Slf4j
public class BizException extends RuntimeException {

    private Integer errorCode;
    private String errorMsg;

    public BizException(BizError error) {
        super(error.getErrDesc());
        this.errorCode = error.getErrCode();
        this.errorMsg = error.getErrDesc();
    }
}
