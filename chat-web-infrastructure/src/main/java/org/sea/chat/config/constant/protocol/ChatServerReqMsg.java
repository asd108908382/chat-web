package org.sea.chat.config.constant.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @since JDK 1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatServerReqMsg {

    private Long requestId;
    private Long senderId;
    private String reqMsg;
    private Integer type;
}
