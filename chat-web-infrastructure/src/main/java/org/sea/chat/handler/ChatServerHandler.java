//package org.sea.chat.handler;
//
//
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelHandler;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.timeout.IdleState;
//import io.netty.handler.timeout.IdleStateEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.sea.chat.config.constant.Constants;
//import org.sea.chat.config.constant.protocol.ChatServerReqMsg;
//import org.sea.chat.config.constant.protocol.UserEntity;
//import org.sea.chat.utils.NettyAttrUtil;
//import org.sea.chat.utils.SessionSocketHolder;
//import org.sea.chat.utils.SpringBeanFactory;
//
///**
// * @author guojiawei
// * @version 1.0
// * @date 2023/3/24 17:46
// */
//@ChannelHandler.Sharable
//@Slf4j
//public class ChatServerHandler extends SimpleChannelInboundHandler<ChatServerReqMsg> {
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ChatServerReqMsg chatServerReqMsg) throws Exception {
//        if (Constants.CommandType.LOGIN.equals(chatServerReqMsg.getType())) {
//            //保存客户端与 Channel 之间的关系
//            SessionSocketHolder.put(chatServerReqMsg.getRequestId(), (NioSocketChannel) channelHandlerContext.channel());
//            SessionSocketHolder.saveSession(chatServerReqMsg.getRequestId(), chatServerReqMsg.getReqMsg());
//            log.info("client [{}] online success!!", chatServerReqMsg.getReqMsg());
//        }
//
//        //心跳更新时间
//        if (Constants.CommandType.PING.equals(chatServerReqMsg.getType())) {
//            NettyAttrUtil.updateReaderTime(channelHandlerContext.channel(), System.currentTimeMillis());
//            //向客户端响应 pong 消息
//            ChatServerReqMsg heartBeat = SpringBeanFactory.getBean("heartBeat", ChatServerReqMsg.class);
//            channelHandlerContext.writeAndFlush(heartBeat).addListeners((ChannelFutureListener) future -> {
//                if (!future.isSuccess()) {
//                    log.error("IO error,close Channel");
//                    future.channel().close();
//                }
//            });
//        }
//
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        //可能出现业务判断离线后再次触发 channelInactive
//        UserEntity userEntity = SessionSocketHolder.getUserId((NioSocketChannel) ctx.channel());
//        if (userEntity != null) {
//            log.warn("[{}] trigger channelInactive offline!", userEntity.getUsername());
//
//            //Clear route info and offline.
//            RouteHandler routeHandler = SpringBeanFactory.getBean(RouteHandler.class);
//            routeHandler.userOffLine(userEntity, (NioSocketChannel) ctx.channel());
//
//            ctx.channel().close();
//        }
//    }
//
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
//            if (idleStateEvent.state() == IdleState.READER_IDLE) {
//                HeartBeatHandler heartBeatHandler = SpringBeanFactory.getBean(ServerHeartBeatHandlerImpl.class);
//                heartBeatHandler.process(ctx);
//            }
//        }
//        super.userEventTriggered(ctx, evt);
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        log.error(cause.getMessage(), cause);
//    }
//}
