package org.sea.chat.handler;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author jiaweiguo
 */
public interface HeartBeatHandler {

    /**
     * 处理心跳
     */
    void process(ChannelHandlerContext ctx) throws Exception;
}
