package org.sea.chat.server;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.sea.chat.config.constant.Constants;
import org.sea.chat.config.constant.protocol.ChatServerReqMsg;
import org.sea.chat.config.constant.protocol.SendMsgReq;
import org.sea.chat.init.ChatServerInitializer;
import org.sea.chat.utils.SessionSocketHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author guojiawei
 * @version 1.0
 * @date 2023/3/24 16:44
 */
@Component
@Slf4j
public class ChatServer {

    private final EventLoopGroup boss = new NioEventLoopGroup(1);
    private final EventLoopGroup work = new NioEventLoopGroup(8);


    @Value("${chat.server.port:9000}")
    private int nettyPort;

//    @Autowired
//    ChatMessageSender chatMessageSender;


    @PostConstruct
    public void start() throws InterruptedException {

        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, work)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(nettyPort))
                //保持长连接
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChatServerInitializer());

        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            log.info("Start chat server success! port is {}", nettyPort);
        }
    }

    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        boss.shutdownGracefully().syncUninterruptibly();
        work.shutdownGracefully().syncUninterruptibly();
        log.info("Close tim server success!!!");
    }


    /**
     * Push msg to client.
     *
     * @param sendMsgReq 消息
     */
    public void sendMsg(SendMsgReq sendMsgReq) {
        NioSocketChannel socketChannel = SessionSocketHolder.get(sendMsgReq.getUserId());
        if (null == socketChannel) {
            log.error("client {} offline!", sendMsgReq.getUserId());
            return;
        }
        ChatServerReqMsg protocol = new ChatServerReqMsg(sendMsgReq.getUserId(), sendMsgReq.getSenderId(), sendMsgReq.getMsg(), Constants.CommandType.MSG);

        WebSocketFrame frame = new TextWebSocketFrame(JSON.toJSONString(protocol));
        ChannelFuture future = socketChannel.writeAndFlush(frame);
        future.addListener((ChannelFutureListener) channelFuture ->
                log.info("server push msg:[{}]", sendMsgReq));
    }

    public void sendMsgAll(String msg, Long senderId) {
        List<NioSocketChannel> socketChannels = SessionSocketHolder.getAll();
        if (CollectionUtils.isEmpty(socketChannels)) {
            log.error("client offline!");
            return;
        }
        ChatServerReqMsg protocol = new ChatServerReqMsg(null, senderId, msg, Constants.CommandType.MSG);
        socketChannels.forEach(socketChannel -> {
            WebSocketFrame frame = new TextWebSocketFrame(JSON.toJSONString(protocol));
            ChannelFuture future = socketChannel.writeAndFlush(frame);
            future.addListener((ChannelFutureListener) channelFuture ->
                    log.info("server push msg:[{}]", msg));
        });
    }

}
