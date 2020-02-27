package com.zhz.study.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Nio服务器端代码
 * @ClassName ServerSocket
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年2月27日13:00:43 13:00
 * Version 1.0
 **/
public class ServerSocket {
    public static void main(String[] args) throws Exception{
        //1、得到服务器端通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2、得到选择器对象
        Selector selector = Selector.open();
        //3、设定服务器端端口号
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //4、设置阻塞方式
        serverSocketChannel.configureBlocking(false);
        //5、将服务器端通道注册到选择器中,并选择监控事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //6、选择器监控客户端selector.select();返回连接到服务器端的客户端数量
        while(true){
            if(selector.select(2000) == 0){
                System.out.println("没有客户端连接我，我正在一直监控！");
                continue;
            }
            //如果有一个或以上的客户端连接我了，我就需要判断他们是什么事件
            //先得到所有的selectionKeys
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while(keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
                //客户端连接事件
                if(key.isAcceptable()){
                    System.out.println("OP_ACCEPT");
                    //服务端通道接收请求，并返回一个网络通道
                    SocketChannel socketAccpet = serverSocketChannel.accept();
                    //设置网络通道的阻塞方式
                    socketAccpet.configureBlocking(false);
                    //将的到的网络通道注册到选择器中,并设置该网络通道的监听方式，和缓冲区
                    socketAccpet.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                //读取客户端数据事件
                if(key.isReadable()){
                    //通过Key得到一个管道
                    SocketChannel  channel = (SocketChannel) key.channel();
                    //得到附件中的缓冲区
                    ByteBuffer buffer   = (ByteBuffer) key.attachment();
                    //读取管道中的数据到缓冲区中
                    channel.read(buffer);
                    System.out.println("客户端发送的数据为："+ new String(buffer.array()));

                }
                //每一个SelectionKey执行完毕后都要从当前集合中移除，避免重复处理
                keyIterator.remove();

        }
        }

    }
}
