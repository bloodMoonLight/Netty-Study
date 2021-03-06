package com.zhz.study.nettytest.coder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Netty服务端业务处理类-使用Google的profobuf编辑器，设置pipeline链的编码和解码
 * @ClassName NettyServerHandler
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年2月28日14:43:58 14:43
 * Version 1.0
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据事件
     * @Description TODO
     * @params
     * @Author 张鸿志
     * @Date 2020/2/28 14:51
     * @Return
     **/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //将对象强转
        BookMessage.Book book = (BookMessage.Book)msg;
        System.out.println(book.getName());
    }

    /**
     * 读取数据完毕事件
     * @Description TODO
     * @params
     * @Author 张鸿志
     * @Date 2020/2/28 14:51
     * @Return
     **/
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //当读取数据完毕后，服务端给客户端发送一条消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务器端懒得搭理你！",CharsetUtil.UTF_8));
    }
    /**
     * 发生异常事件
     * @Description TODO
     * @params
     * @Author 张鸿志
     * @Date 2020/2/28 14:51
     * @Return
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
