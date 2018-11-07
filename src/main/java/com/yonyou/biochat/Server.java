package com.yonyou.biochat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器端
 * @author 小唐
 * @date 2018/11/7
 */
@Slf4j
public class Server {
    // 端口
    private static final int DEFAULT_SERVER_PORT = 7777;
    // 单例的ServerSocket
    private static ServerSocket serverSocket;

    // 根据传入参数设置监听端口，如果没有参数调用以下方法并使用默认值
    public static void start() throws IOException{
        start(DEFAULT_SERVER_PORT);
    }

    // 启动服务器端
    private synchronized static void start(int port) {
        if(serverSocket != null)
            return;
        try {
            // 创建ServerSock，监听port端口
            serverSocket = new ServerSocket(port);
            log.info("服务端已启动，端口号:" + port);
            // 通过无线循环监听客户端连接，acceptor代码实现
            while (true){
                Socket clientSocket = serverSocket.accept();
                new Thread(new ServerHandler(clientSocket)).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverSocket = null;
                log.info("服务端已关闭。。。");
            }
        }
    }
}
