package com.zhz.study.nettytest.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 聊天服务器端代码
 * @ClassName ChatServerNetty
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年3月1日14:27:18 14:27
 * Version 1.0
 **/
public class ChatServerNetty {

    private int port;

    public ChatServerNetty(int port) {
        this.port = port;
    }

    public void run() throws Exception{
        //创建两个线程组
        EventLoopGroup boss =  new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            //创建服务器端启动助手
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //配置服务器端
            serverBootstrap.group(boss, worker)
                    //设置服务端通道的实现类
                    .channel(NioServerSocketChannel.class)
                    //配置连接队列数
                    .option(ChannelOption.SO_BACKLOG, 136)
                    //设置连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //设置handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) {
                            //应该由链将它放入
                            ChannelPipeline pipeline = channel.pipeline();
                            //往pipeLine中添加一个解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            //往pipeLine中添加一个编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            //最后向pipeLine链中添加自定义handler（业务处理类）
                            pipeline.addLast(new ChatServerNettyHandler());
                        }
                    });
            //配置端口
            ChannelFuture sync = serverBootstrap.bind(this.port).sync();
            sync.channel().closeFuture().sync();
        }finally {
            boss.shutdownGracefully().sync();
            worker.shutdownGracefully().sync();
            System.out.println("Netty server 关闭");
        }
    }
}
