package com.zhz.study.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

/**
 * @ClassName NioFileCopy
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年2月26日13:18:56 13:18
 * Version 1.0
 **/
public class NioFileCopy {
    public static void main(String[] args) throws Exception{
        //原数据
        FileInputStream fis = new FileInputStream("D:\\安装包\\06-NIO与Netty编程\\03 NIO编程\\03 网络IO\\21.mp4");
        File file = new File("d:\\testV\\28.mp4");
        if(!file.exists()){
            file.getParentFile().mkdir();
        }
        file.createNewFile();
        //目标路径
        FileOutputStream fos = new FileOutputStream(file);

        //创建两个通道
        FileChannel sourceFc = fis.getChannel();
        FileChannel targetFc = fos.getChannel();
        //执行复制操作，transferFrom(),transferTo()方法都可以
        targetFc.transferFrom(sourceFc,0,sourceFc.size()); // 从目标通道复制数据到自己的通道
      //  sourceFc.transferTo(0,sourceFc.size(),targetFc);    //将自己通道中的数据复制给目标通道

        //关闭流
        fis.close();
        fos.close();

    }
}
