package com.zhz.study.chat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;

/**
 * 聊天程序客户端
 * @ClassName ChatClient
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年2月27日15:23:17 15:23
 * Version 1.0
 **/
public class ChatClient {
    /** 服务器端IP地址 */
    private final String HOST = "127.0.0.1";
    /** 服务器端端口号 */
    private final int PORT = 9999;
    /** 网络IO通道 */
    private SocketChannel socketChannel;
    /** 聊天用户名 */
    private String userName;

    public ChatClient(){
        try{
            socketChannel = SocketChannel.open();
            //设置阻塞方式
            socketChannel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress(HOST, PORT);
            //连接服务器端
            if(!socketChannel.connect(address)){
                while(!socketChannel.finishConnect()){
                    System.out.println("正在连接，请稍后。。。。。");
                }
            }
            //得到客户端的ip地址和端口信息，作为聊天用户名使用
            userName = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println("---------------------------Client (" + userName + ")is ready----------------------------");


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //向服务器发送消息
    public void sendMsg(String msg) throws Exception{
        if(msg.equals("bye")){
            socketChannel.close();
            return;
        }
        msg = userName + "说："+ msg;
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(buffer);
    }
    //接受从服务端返回的数据
    public void readMsg()throws Exception{
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int count =  socketChannel.read(buffer);
        if(count > 0){
            String msg = new String(buffer.array());
            System.out.println(msg.trim());
        }
    }


}
