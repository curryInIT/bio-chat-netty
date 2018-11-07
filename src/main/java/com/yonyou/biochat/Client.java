package com.yonyou.biochat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 客户端
 * @author 小唐
 * @date 2018/11/7
 */
public class Client {
    // 服务器端口
    private static final int DEFAULT_SERVER_PORT = 7777;

    // 服务器IP
    private static final String DEFAULT_SERVER_IP = "127.0.0.1";

    // 默认的发送消息方法
    public static void send(String message) throws IOException{
        send(DEFAULT_SERVER_IP, DEFAULT_SERVER_PORT, message);
    }

    //  发送消息到指定地址
    private static void send(String ip, int port, String message) {
        Socket socket = null;
        BufferedReader in = null;
        BufferedReader input = null;
        PrintWriter out = null;

        String fromMsg = null;
        String toMsg = null;
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            input = new BufferedReader(new InputStreamReader(System.in));
            out =  new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
            String time = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (true){
                // 读取从服务器端传来的消息
                if((fromMsg = in.readLine()) == null)
                    break;
                time = sdf.format(new Date());
                System.out.println(time + " 客户端收到消息：" + fromMsg);
                System.out.print("客户端回复：");
                // 获取回复的消息
                toMsg = input.readLine();
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
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                input = null;
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
