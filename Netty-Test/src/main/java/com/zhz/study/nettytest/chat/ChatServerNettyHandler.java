package com.zhz.study.nettytest.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ChatServerNettyHandler
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年3月1日14:27:44 14:27
 * Version 1.0
 **/
public class ChatServerNettyHandler extends SimpleChannelInboundHandler<String> {

    private static List<Channel> channels = new ArrayList<>();
    /**
     * 读取
     * @Description TODO
     * @params
     * @Author 张鸿志
     * @Date 2020/3/1 14:30
     * @Return
     **/
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel inChannel = channelHandlerContext.channel();
        for(Channel channel :channels){
            if(channel != inChannel){
                channel.writeAndFlush("【"+channel.remoteAddress().toString().substring(1)+"】说:" + "xxxx");
            }
        }
    }
    /**
     * 通道已就绪
     * @Description TODO
     * @params
     * @Author 张鸿志
     * @Date 2020/3/1 14:30
     * @Return
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel inChannel = ctx.channel();
        channels.add(inChannel);
        System.out.println("【Server】:"+inChannel.remoteAddress().toString().substring(1)+"上线");
    }
    /**
     * 通道未就绪
     * @Description TODO
     * @params
     * @Author 张鸿志
     * @Date 2020/3/1 14:29
     * @Return
     **/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel inChannel = ctx.channel();
        channels.remove(inChannel);
        System.out.println("【Server】:"+inChannel.remoteAddress().toString().substring(1)+"下线");
    }
    /**
     * 发生异常时执行的方法
     * @Description TODO
     * @params
     * @Author 张鸿志
     * @Date 2020/3/1 14:30
     * @Return
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
