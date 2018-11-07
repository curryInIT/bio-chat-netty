package com.yonyou.biochat;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 服务端消息处理类
 * @author 小唐
 * @date 2018/11/7
 */
@Slf4j
public class ServerHandler implements Runnable {
    private Socket clientSocket;

    public ServerHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        BufferedReader consoleInput = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            consoleInput = new BufferedReader(new InputStreamReader(System.in));
            out =  new PrintWriter(clientSocket.getOutputStream(), true);
            // 读取从客户端传来的消息并回复
            String fromMsg = null;
            String toMsg = null;
            String time = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (true){
                // 读取从客户端传来的消息
                if((fromMsg = in.readLine()) == null)
                    break;
                time = sdf.format(new Date());
                System.out.println((time + " 服务器端收到信息：" + fromMsg));
                System.out.print("服务器端回复：");
                // 获取回复的消息
                toMsg = consoleInput.readLine();
                // 返回客户端
                out.println(toMsg);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            // 清理资源
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (out != null) {
                out.close();
                out = null;
            }
            if (consoleInput != null) {
                try {
                    consoleInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                consoleInput = null;
            }
            if(clientSocket != null){
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clientSocket = null;
            }
        }

    }
}
