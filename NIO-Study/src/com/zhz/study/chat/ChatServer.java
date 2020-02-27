package com.zhz.study.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 网络聊天案例的服务器
 * @ClassName ChatServer
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年2月27日14:38:15 14:38
 * Version 1.0
 **/
public class ChatServer {
    /** 服务端网络监听通道 */
    private ServerSocketChannel listenerChannel;
    /** 服务器对象 */
    private Selector selector;
    /** 服务器端口 */
    private static final int PORT = 9999;

    public ChatServer() {
        // 在创建对象的过程中初始化服务器
        try{
            listenerChannel =   ServerSocketChannel.open();
            selector = Selector.open();
            //设置端口号
            listenerChannel.bind(new InetSocketAddress(PORT));
            //设置阻塞方式
            listenerChannel.configureBlocking(false);
            //注册到选择器中
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
            printo("Chat Server is ready");
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    // 执行的操作
    public void start(){
        try {
            while (true) {
                if (selector.select(2000) == 0) {
                    printo("此时没有客户端连接我！");
                    continue;
                }
                //如果此时有客户端连接我了，那么就拿到有所的key,进行遍历
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while(keyIterator.hasNext()){
                    //得到每一个key
                    SelectionKey key = keyIterator.next();
                    //监听该key是什么类型
                    if(key.isAcceptable()){
                        // 如果该Key是连接事件,服务端就执行连接操作，并返回一个网络通道
                        SocketChannel socketChannel =  listenerChannel.accept();
                        //将该通道注册到选择器中
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                        System.out.println("用户" +socketChannel.getLocalAddress().toString().substring(1) + "上线了" );

                    }
                    if(key.isReadable()){
                       /* //拿到对应的连接通道
                        SocketChannel channel = (SocketChannel)key.channel();
                        //从通道中读取数据到缓冲区中
                        ByteBuffer buffer = (ByteBuffer)key.attachment();
                        channel.read(buffer);
*/
                       readMsg(key);
                    }
                    keyIterator.remove();

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 拿到客户端消息，并广播出去
     * @Description TODO
     * @params
     * @Author 张鸿志
     * @Date 2020/2/27 15:07
     * @Return
     **/
    private void readMsg(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel)key.channel();
        //从通道中读取数据到缓冲区中
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int count =  channel.read(buffer);
        if(count > 0){
            String msg = new String(buffer.array());
            printo(msg);
            //广播出去,需要排除当前通道
            broadCast(msg,channel);
        }


    }
    /**
     * 给所有的客户端发广播
     * @Description TODO
     * @params
     * @Author 张鸿志
     * @Date 2020/2/27 15:14
     * @Return 
     **/
    public void broadCast(String msg ,SocketChannel channel) throws IOException{
        System.out.println("服务器发送了广播");
        //怎么拿到所有通道Selector
        // 拿到所有准备就绪通道
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key:keys) {
            //得到每一个准备就绪的通道
            Channel schannel = key.channel();
            //排除当前的通道
            if(schannel instanceof SocketChannel && channel != schannel){
                SocketChannel ssss = (SocketChannel)schannel;
                //任何数据都要通过缓冲区
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将缓冲区的数据写入通道
                ssss.write(buffer);
            }
        }
    }


    private void printo(String str){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("[" + simpleDateFormat.format(new Date()) + "] -> " + str);
    }

    public static void main(String[] args) {
        new ChatServer().start();
    }
}
