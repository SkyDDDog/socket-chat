package com.lear.server;

import com.lear.router.Router;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器基类
 * @author 天狗
 */
public class BaseServer extends Thread {

    private ServerSocket serverSocket;
    public BaseServer() throws IOException {
    }

    @Override
    public void run() {//开启一个线程
        while (true) {
            try {
                //侦听到要连接到此端口的套接字，并且接收它
                Socket socket = serverSocket.accept();
                //将字节输入流读入到字符输入流，并传入字符输入流
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String str = bufferedReader.readLine();
                OutputStream os = socket.getOutputStream();
                Router.invoke(str, os);
                os.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
