package org.sea.chat.config.constant.protocol;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guojiawei
 * @version 1.0
 * @date 2023/3/25 15:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMsgReq {
    @NotNull(message = "msg 不能为空")
    private String msg;

    @NotNull(message = "userId 不能为空")
    private Long userId;

    @NotNull(message = "senderId 不能为空")
    private Long senderId;
}
