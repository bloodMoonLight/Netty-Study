package com.zhz.study.nettytest.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName ChatClientNettyHandler
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年3月1日14:28:18 14:28
 * Version 1.0
 **/
public class ChatClientNettyHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
    }
}
