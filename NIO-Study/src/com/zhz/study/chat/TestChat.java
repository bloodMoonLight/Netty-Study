package com.zhz.study.chat;

import java.util.Scanner;

/**
 * @ClassName TestChat
 * @Description TODO
 * @Author 张鸿志
 * @Date 2020年2月27日15:46:23 15:46
 * Version 1.0
 **/
public class TestChat {
    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        new Thread(){
            @Override
            public void run(){
                while(true){
                    try{
                        chatClient.readMsg();
                        Thread.sleep(2000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();


        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
           String msg =  scanner.nextLine();
           try{
               chatClient.sendMsg(msg);
           }catch (Exception e){
               e.printStackTrace();
           }

        }

    }
}
