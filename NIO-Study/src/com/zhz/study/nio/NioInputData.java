package com.zhz.study.nio;


import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * 通过Nio从文件中读取数据
 * @ClassName NioInputData
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年2月26日10:37:03 10:37
 * Version 1.0
 **/
public class NioInputData {
    public static void main(String[] args) throws Exception{
        Charset charset = Charset.forName("utf-8");
        CharsetDecoder decoder = charset.newDecoder();
        FileInputStream fis = new FileInputStream("D:\\zhanghongzhi.txt");
        FileChannel channel = fis.getChannel();
        ByteBuffer allocate = ByteBuffer.allocate(512);
        CharBuffer charBuffer = CharBuffer.allocate(512);
        StringBuffer sb = new StringBuffer();
        while(channel.read(allocate) != -1){
            //循环读取时也需要重置位置和清空缓冲区
            allocate.flip();
            decoder.decode(allocate,charBuffer,false);
            charBuffer.flip();
            sb.append(new String(charBuffer.array()));
            allocate.clear();
            charBuffer.clear();
        }
        System.out.println(sb.toString());

        fis.close();

    }
}
