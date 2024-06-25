package org.sea.chat.init;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.sea.chat.handler.WebSocketHandler;

/**
 * @author guojiawei
 * @version 1.0
 * @date 2023/3/24 16:48
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {

    private final WebSocketHandler webSocketHandler = new WebSocketHandler();

//    private final ChatServerHandler chatServerHandler = new ChatServerHandler();

    private static final String PATH = "/chat";

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //对http协议的支持.
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new IdleStateHandler(20, 0, 0));
        // 对大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //post请求分三部分. request line / request header / message body
        // HttpObjectAggregator将多个信息转化成单一的request或者response对象
        pipeline.addLast(new HttpObjectAggregator(8000));
        // 将http协议升级为ws协议. websocket的支持
        pipeline.addLast(new WebSocketServerProtocolHandler(PATH));
        // 自定义处理handler
        pipeline.addLast(webSocketHandler);
    }
}