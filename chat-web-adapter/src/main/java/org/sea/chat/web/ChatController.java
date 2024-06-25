package org.sea.chat.web;

import com.alibaba.cola.dto.SingleResponse;
import org.sea.chat.config.constant.protocol.SendMsgReq;
import org.sea.chat.server.ChatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guojiawei
 * @version 1.0
 * @date 2023/3/25 15:50
 */
@RestController
@RequestMapping("chat")
public class ChatController {

    @Autowired
    ChatServer chatServer;

    @PostMapping("/send")
    public SingleResponse<?> send(@RequestBody SendMsgReq sendMsgReq) {
        chatServer.sendMsg(sendMsgReq);
        return SingleResponse.buildSuccess();
    }

    @PostMapping("/sendAll")
    public SingleResponse<?> sendAll(@RequestBody SendMsgReq sendMsgReq) {
        chatServer.sendMsgAll(sendMsgReq.getMsg(), sendMsgReq.getUserId());
        return SingleResponse.buildSuccess();
    }
}
