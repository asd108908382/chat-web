package org.sea.chat.handler;

import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.sea.chat.config.constant.protocol.UserEntity;
import org.springframework.stereotype.Component;
import org.sea.chat.utils.SessionSocketHolder;

import java.io.IOException;

/**
 * @author guojiawei
 * @version 1.0
 * @date 2023/3/24 18:48
 */
@Component
@Slf4j
public class RouteHandler {
    public void userOffLine(UserEntity userEntity, NioSocketChannel channel) {

        if (userEntity != null) {
            log.info("Account [{}] offline", userEntity.getUsername());
            SessionSocketHolder.removeSession(userEntity.getId());
            //清除路由关系
            clearRouteInfo(userEntity);
        }
        SessionSocketHolder.remove(channel);
    }

    /**
     * 清除路由关系
     *
     * @param userEntity
     * @throws IOException
     */
    public void clearRouteInfo(UserEntity userEntity) {
        //todo 上层api清理用户与服务器关系

    }
}
