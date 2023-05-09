package com.lear.request;

import com.lear.util.StringUtils;

import java.io.*;
import java.net.Socket;

/**
 * 客户端发送信息线程
 * @author 天狗
 */
public class SendMsgPipe implements Runnable {

    private Socket client;
    private DataOutputStream dos;
    private DataInputStream dis;
    private BufferedReader br;
    private boolean isRunning = true;

    public SendMsgPipe(Socket client, String userId) {
        this.client = client;
        br = new BufferedReader(new InputStreamReader(System.in));
        try {
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());

            dos.writeUTF("/chat/"+userId);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            this.release();
        }
    }

    public SendMsgPipe(Socket client) {
        this.client = client;
        br = new BufferedReader(new InputStreamReader(System.in));
        try {
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());

            dos.writeUTF("/chat");
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            this.release();
        }
    }

    //释放资源
    public void release() {
        try {
            this.isRunning = false;
            if( dos != null ) {
                dos.close();
            }
            if( dis != null ) {
                dis.close();
            }
            if( client != null ) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCloseMsg() {
        this.send("CLOSE_THREAD");
    }


    //从控制台获取信息
    private String fromConsole() {
        try {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            this.release();
        }
        return "";
    }

    //向服务端发送信息
    public void send(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
            System.out.println("send: "+msg);
        } catch (IOException e) {
            e.printStackTrace();
            this.isRunning = false;
            this.release();
        }
    }

    @Override
    public void run() {
        while(isRunning) {
            String msg = fromConsole();
            if(!StringUtils.isEmpty(msg)) {
                this.send(msg);
            }
        }
    }




}
