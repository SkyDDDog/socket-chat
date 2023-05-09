package com.lear.server;

import com.lear.service.UserService;
import com.lear.util.StringUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 在线聊天服务端
 * @author 天狗
 */
public class ChatPipe implements Runnable {

    private CopyOnWriteArrayList<ChatPipe> all;
    private Socket client;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean isRunning = true;
    private String userId;
    private String username;

    public ChatPipe(Socket client, CopyOnWriteArrayList<ChatPipe> all, String path) {
        this.all = all;
        this.client = client;
        userId = path.replaceAll("/chat/", "");
        username = UserService.getNameById(userId);
        try {
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());

//            this.name = receive();
//            this.send("欢迎你的到来");
//            sendOthers(this.name+"上线了",true);//系统消息
        } catch (IOException e) {
            e.printStackTrace();
            release();
        }
    }

    public ChatPipe(Socket client) {
        this.client = client;

        try {
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
//            this.name = receive();
//            this.send("欢迎你的到来");
//            sendOthers(this.name+"上线了",true);//系统消息
        } catch (IOException e) {
            e.printStackTrace();
            release();
        }
    }

    //收到
    private String receive() {
        String msg = "";
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            release();
        }
        return msg;
    }

    //发送
    private void send(String msg) {
        try {
            dos.writeUTF(msg);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            this.isRunning = false;
            release();
        }
    }

    //发送信息给其他人
    private void sendOthers(String msg) {
        for(ChatPipe others:all) {
            if(others == this) {
                continue;
            }
            others.send(this.username+"："+msg);
        }
    }


    //释放资源
    private void release() {
        try {
            if(dos != null ) {
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

    @Override
    public void run() {
        //Io流
        //输入流 接受客户端的请求
        while(isRunning) {
            //收到请求
            String msg = this.receive();
            System.out.println("receive: "+msg);
            //反馈结果
            if ("CLOSE_THREAD".equals(msg)) {
                isRunning = false;
                System.out.println("closed");
                all.remove(this);
                break;
            }
            if(!StringUtils.isEmpty(msg)) {
                sendOthers(msg);
            } else {
                isRunning = false;
            }
        }
        //释放资源
        release();
    }

}
