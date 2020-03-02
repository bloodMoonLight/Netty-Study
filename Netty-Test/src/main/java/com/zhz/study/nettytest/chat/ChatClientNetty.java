package com.zhz.study.nettytest.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @ClassName ChatClientNetty
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年3月1日14:28:00 14:27
 * Version 1.0
 **/
public class ChatClientNetty {
    /** 服务器端ip地址 */
    private String host;
    /** 服务器端端口号 */
    private int port;

    public ChatClientNetty(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void run(){
        EventLoopGroup client = new NioEventLoopGroup();
        try{
            //创建客户端启动助手
            Bootstrap b = new Bootstrap();
            b.group(client)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel sc){
                            ChannelPipeline pipeline = sc.pipeline();
                            pipeline.addLast("decoder",new StringDecoder());
                            pipeline.addLast("encoder",new StringEncoder());
                            pipeline.addLast(new ChatClientNettyHandler());
                        }
                    });
            ChannelFuture sync = b.connect(host, port).sync();
            System.out.println("Netty client 启动完毕");
            Channel channel = sync.channel();
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNextLine()){
                String s = scanner.nextLine();
                channel.writeAndFlush(s+"\r\n");
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally{
            client.shutdownGracefully();
        }
    }
}
