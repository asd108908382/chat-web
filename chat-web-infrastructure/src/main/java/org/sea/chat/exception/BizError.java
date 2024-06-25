package org.sea.chat.exception;

/**
 * @author jiaweiguo
 */
public enum BizError {
    /*/

     */
    B_MESSAGE_UNDEFINED(4000, "消息格式错误"),
    ;

    private final Integer errCode;
    private final String errDesc;

    BizError(Integer errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }
}
