package com.lear.server;

import com.lear.router.Router;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 用户请求服务端
 * @author 天狗
 */
public class UserPipe implements Runnable {

    private Socket client;
    private String path;

    public UserPipe(Socket client, String path) {
        this.client = client;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            OutputStream os = client.getOutputStream();
            Router.invoke(this.path, os);
            os.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
