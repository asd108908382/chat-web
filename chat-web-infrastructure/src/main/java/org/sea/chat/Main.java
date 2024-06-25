//package org.sea.chat;
//
//import com.alibaba.fastjson.JSON;
//import io.netty.bootstrap.Bootstrap;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.HttpServerCodec;
//import io.netty.handler.stream.ChunkedWriteHandler;
//import io.netty.handler.timeout.IdleStateHandler;
//import io.netty.util.concurrent.DefaultThreadFactory;
//import org.sea.chat.config.constant.protocol.ChatServerReqMsg;
//import org.sea.chat.handler.WebSocketHandler;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//
///**
// * @author guojiawei
// * @version 1.0
// * @date 2024/6/19 22:14
// */
//public class Main {
//
//    private static EventLoopGroup group = new NioEventLoopGroup(0, new DefaultThreadFactory("cim-work"));
//
//    public static void main(String[] args) throws InterruptedException, IOException {
//        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
//
//        try {
//            Bootstrap bootstrap = new Bootstrap();
//            bootstrap.group(group)
//                    .channel(NioSocketChannel.class)
//                    .handler(new WebSocketHandler())
//            ;
//            Channel channel = bootstrap.connect("127.0.0.1", 9000).sync().channel();
//            ChatServerReqMsg chatServerReqMsg = new ChatServerReqMsg();
//            chatServerReqMsg.setReqMsg("123");
//            chatServerReqMsg.setSenderId(1L);
//            chatServerReqMsg.setType(1);
//            chatServerReqMsg.setRequestId(1L);
//            ByteBuf byteBuf = Unpooled.wrappedBuffer(JSON.toJSONString(chatServerReqMsg).getBytes(StandardCharsets.UTF_8));
//            ChannelFuture future = channel.writeAndFlush(byteBuf);
//            future.addListener((ChannelFutureListener) channelFuture ->
//                    System.out.println("Registry cim server success!")
//            );
//            for (; ; ) {
//
//            }
//        } finally {
//            eventLoopGroup.shutdownGracefully();
//        }
//    }
//}
