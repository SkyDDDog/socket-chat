package com.lear;

import com.lear.server.ChatPipe;
import com.lear.server.UserPipe;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

/**
 * 主启动类
 * @author 天狗
 * @date 2023/04/29
 */
public class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    private static CopyOnWriteArrayList<ChatPipe> allChatter = new CopyOnWriteArrayList<ChatPipe>();


    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        while (true) {
            Socket client = server.accept();
            String str = new DataInputStream(client.getInputStream()).readUTF();
            System.out.println("path: "+ str);
            if (str.contains("chat")) {
                System.out.println("启用chat长连接");
                ChatPipe pipe = new ChatPipe(client, allChatter, str);
                allChatter.add(pipe);
                new Thread(pipe).start();
            } else {
                UserPipe pipe = new UserPipe(client, str);
                new Thread(pipe).start();
            }
        }
    }

}
