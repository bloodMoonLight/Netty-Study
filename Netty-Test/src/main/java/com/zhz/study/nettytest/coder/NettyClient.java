package com.zhz.study.nettytest.coder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import java.net.InetSocketAddress;

/**
 * Netty客户端
 * @ClassName NettyClient
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年2月28日14:42:01 14:41
 * Version 1.0
 **/
public class NettyClient {

    public static void main(String[] args) throws Exception{
        //1、创建线程组
        EventLoopGroup eventExecutors = new  NioEventLoopGroup();
        //2、创建客户端助手对象
        Bootstrap bootstrap = new Bootstrap();
        //3、配置客户端
        bootstrap.group(eventExecutors) //4、设置线程组
            .channel(NioSocketChannel.class)    //5、设置客户端通道实现类
                .handler(new ChannelInitializer<SocketChannel>() {
                    //6、往pipeling链中添加handler
                    @Override
                    public void initChannel(SocketChannel sc){
                        ChannelPipeline pipeline = sc.pipeline();
                        //添加google的编码器
                        pipeline.addLast("encoder",new ProtobufEncoder());
                        pipeline.addLast(new NettyClientHandler());
                    }
                });
        System.out.println("......客户端已经就绪......");
        //7、开始连接服务器端,异步
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 9999)).sync();
        //8、关闭通道，异步
        future.channel().closeFuture().sync();
        eventExecutors.shutdownGracefully();
    }
}
