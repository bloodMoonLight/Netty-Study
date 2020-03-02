package com.zhz.study.nettytest.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Netty客户端业务处理类
 * @ClassName NettyClientHandler
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年2月28日14:43:30 14:43
 * Version 1.0
 **/
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 通道就绪事件
     * @Description TODO
     * @params
     * @Author 张鸿志
     * @Date 2020/2/28 16:34
     * @Return
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端正在发送数据："+ctx );
        ctx.writeAndFlush(Unpooled.copiedBuffer("我给服务端发送数据！", CharsetUtil.UTF_8));
    }

    /**
     * 通道读取事件
     * @Description TODO
     * @params
     * @Author 张鸿志
     * @Date 2020/2/28 16:34
     * @Return
     **/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;
        System.out.println("服务端发来消息:"+byteBuf.toString(CharsetUtil.UTF_8));
    }
}
