package org.sea.chat.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.sea.chat.config.AppConfiguration;
import org.sea.chat.config.constant.protocol.UserEntity;
import org.sea.chat.utils.SessionSocketHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sea.chat.utils.NettyAttrUtil;

/**
 * @author guojiawei
 * @version 1.0
 * @date 2023/3/24 18:52
 */
@Slf4j
@Service
public class ServerHeartBeatHandlerImpl implements HeartBeatHandler {

    @Autowired
    private RouteHandler routeHandler;

    @Autowired
    private AppConfiguration appConfiguration;

    @Override
    public void process(ChannelHandlerContext ctx) throws Exception {
        long heartBeatTime = appConfiguration.getHeartBeatTime() * 1000;

        Long lastReadTime = NettyAttrUtil.getReaderTime(ctx.channel());
        long now = System.currentTimeMillis();
        if (lastReadTime != null && now - lastReadTime > heartBeatTime) {
            UserEntity userEntity = SessionSocketHolder.getUserId((NioSocketChannel) ctx.channel());
            if (userEntity != null) {
                log.warn("客户端[{}]心跳超时[{}]ms，需要关闭连接!", userEntity.getUsername(), now - lastReadTime);
            }
            routeHandler.userOffLine(userEntity, (NioSocketChannel) ctx.channel());
            ctx.channel().close();
        }


    }
}
