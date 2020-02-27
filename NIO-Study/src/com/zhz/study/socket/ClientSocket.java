package com.zhz.study.socket;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * 客户端程序
 * @ClassName ClientSocket
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年2月26日14:54:47 14:54
 * Version 1.0
 **/
public class ClientSocket {
    public static void main(String[] args) throws Exception{
        // 1、获得SocketChannel
        SocketChannel channel = SocketChannel.open();
        //2、设置阻塞方式(设置为非阻塞)
        channel.configureBlocking(false);
        //3、创建一个InetSocketAdress对象
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",9999);
        // 4、连接服务器
        if(!channel.connect(socketAddress)){
            while(!channel.finishConnect()){
                System.out.println("正在连接服务器!");
            }
        }
        Scanner scanner = new Scanner(System.in);

        while(true){
            String s = scanner.nextLine();
            //5、创建一个缓冲区
            ByteBuffer wrap = ByteBuffer.wrap(s.getBytes());
            //6、将缓冲区的数据通过管道发给服务端
            channel.write(wrap);
        }

    }
}
