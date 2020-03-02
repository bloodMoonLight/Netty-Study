package com.zhz.study.nettytest.basic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty服务端
 * @ClassName NettyServer
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年2月28日14:43:05 14:43
 * Version 1.0
 **/
public class NettyServer {
    public static void main(String[] args) throws Exception{
        //1、创建两个线程池，一个用来处理客户端连接操作，一个用来处理客户点read/write操作
        EventLoopGroup boss =  new NioEventLoopGroup();
        EventLoopGroup worker =  new NioEventLoopGroup();
        //2、创建服务端助手ServerBootStrap
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //3、开始配置服务器端
        serverBootstrap.group(boss,worker)      //4、配置两个线程池
                .channel(NioServerSocketChannel.class) //5、配置服务端通道的实现
                .option(ChannelOption.SO_BACKLOG,136)//6、设置服务器端线程的队列数(最多有多少个线程可以排队)
                .childOption(ChannelOption.SO_KEEPALIVE,true) //7、设置服务器是否保持连接存活
                .childHandler(new ChannelInitializer<SocketChannel>() { //8、创建一个通道初始化对象
                    @Override
                    public void initChannel(SocketChannel sc){
                            //9、通过该方法向PipeLine链中放入自己写的Handler
                        sc.pipeline().addLast(new NettyServerHandler());
                    }
                });
        System.out.println("......Server 基本初始化完毕，接下来需要配置端口号......");
        // 10、异步的配置端口
        ChannelFuture future = serverBootstrap.bind(9999).sync();
        System.out.println("......服务端初始化完毕......");
        //11、异步的关闭通道
        future.channel().closeFuture().sync();

        //12、关闭线程池
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }
}
