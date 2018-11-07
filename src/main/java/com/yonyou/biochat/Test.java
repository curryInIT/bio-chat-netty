package com.yonyou.biochat;

import java.io.IOException;

/**
 * 测试类
 * @author 小唐
 * @date 2018/11/7
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        // 启动服务器
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //防止客户端先于服务器启动前执行代码
        Thread.sleep(100);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Client.send("你好吗？");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
