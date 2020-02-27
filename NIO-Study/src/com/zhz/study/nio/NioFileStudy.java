package com.zhz.study.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 往本地文件中写数据，通过NIO实现文件IO
 * @ClassName NioFileStudy
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年2月26日10:09:25 10:09
 * Version 1.0
 **/
public class NioFileStudy {
    public static void main(String[] args) throws Exception{
        //1、创建文件流
        FileOutputStream fos = new FileOutputStream("D:\\zhanghongzhi.txt");
        //2、设置存入的数据
        String data = "这是测试Nio的数据！";
        //3、创建缓冲区(通过静态方法得到)
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //4、创建通道
        FileChannel channel = fos.getChannel();
        //5、将数据写入缓冲区
        buffer.put(data.getBytes());
        //6、在将缓冲区的数据写入通道之前，需要使用flip方法翻转缓冲区。否则会出现数据不会的情况
        buffer.flip();
        //7、将数据写入通道
        channel.write(buffer);
        //8、关闭流
        fos.close();


    }
}
