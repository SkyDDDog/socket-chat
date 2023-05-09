package com.lear.request;

import com.lear.util.StringUtils;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * 客户端接收信息线程
 * @author 天狗
 */
public class ReceivePipe implements Runnable {

    private DataInputStream dis;
    private Socket client;
    private boolean isRunning = true;
    private JTextArea msgText;

    public ReceivePipe(Socket client) {
        this.client = client;
        try {
            dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            this.release();
        }
    }

    public ReceivePipe(Socket client, JTextArea msgText) {
        this.client = client;
        this.msgText = msgText;
        try {
            dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            this.release();
        }
    }

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


    //释放资源
    public void release() {
        try {
            this.isRunning = false;
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
        while(isRunning) {
            //收到反馈结果
            String msg = receive();
            if(!StringUtils.isEmpty(msg)) {
//                msgText.append(msg);
                String[] split = msg.split("：");
                if (split.length==2) {
                    msgText.append(split[0]+"  "+new Date().toString()+"\n"+split[1]+"\n");
                }

                System.out.println("receive: "+msg);
            }
        }
    }
}
