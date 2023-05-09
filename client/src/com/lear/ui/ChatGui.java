package com.lear.ui;

import com.lear.request.FriendRequest;
import com.lear.request.ReceivePipe;
import com.lear.request.SendMsgPipe;
import com.lear.request.UserRequest;

import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.*;
import java.util.Date;

/**
 * 聊天界面
 * @author 天狗
 */
public class ChatGui {
    //变量
    private String userId;
    private String username;
    // 通信
    private Socket client;
    private SendMsgPipe sendMsgPipe;
    private ReceivePipe receivePipe;
    //文本域
    JTextArea textArea= new JTextArea();
    //好友列表GUI list
    List onList =new List();
    //好友总的列表 GUI
    List allFriendListGui =new List();
    //当前在线人数显示
    JLabel allCount=new JLabel("当前在线人数：");

    public ChatGui(String userId, String username) throws IOException {
        this.userId = userId;
        this.username = username;
        client = new Socket("127.0.0.1", 8888);
        sendMsgPipe = new SendMsgPipe(client, userId);
//        receivePipe = new ReceivePipe(client);
        receivePipe = new ReceivePipe(client, textArea);
        new Thread(this.sendMsgPipe).start();
        new Thread(this.receivePipe).start();
        this.content();
    }

    public ChatGui() throws IOException {
        client = new Socket("127.0.0.1", 8888);
        sendMsgPipe = new SendMsgPipe(client);
//        receivePipe = new ReceivePipe(client);
        receivePipe = new ReceivePipe(client, textArea);
        new Thread(this.sendMsgPipe).start();
        new Thread(this.receivePipe).start();
        this.content();
    }

    /**
     * 基本框架窗口
     * @return  返回一个基本框架窗口
     */
    public JFrame baseWindow() {
        JFrame window=new JFrame("聊天室");
        Container con=window.getContentPane();
        con.setBackground(Color.white);
        window.setBounds(600,200,720,540);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //关闭事件：发送下线信息
        window.addWindowListener(new WindowListener(){
            @Override
            public void windowActivated(WindowEvent e) {

            }
            @Override
            public void windowClosed(WindowEvent e) {
                //给服务器发送下线通知

                sendMsgPipe.sendCloseMsg();
                receivePipe.release();
                sendMsgPipe.release();
            }
            @Override
            public void windowClosing(WindowEvent e) {

            }
            @Override
            public void windowDeactivated(WindowEvent e) {

            }
            @Override
            public void windowDeiconified(WindowEvent e) {

            }
            @Override
            public void windowIconified(WindowEvent e) {

            }
            @Override
            public void windowOpened(WindowEvent e) {

            }
        });
        return window;
    }

    //聊天框
    public JPanel messagePanel() {
        JPanel pan=new JPanel();
        pan.setLayout(null);
        pan.setBounds(0,30,500,300);
        pan.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.gray));
        pan.setBackground(Color.white);
        JScrollPane ScrollPane = new JScrollPane(textArea);
        textArea.setBounds(0, 0, 499, 300);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        ScrollPane .setBounds(0, 0, 499, 300);
        textArea.setLineWrap(true);
        ScrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pan.add(ScrollPane);
        return pan;
    }


    //聊天输入框
    TextField tfshuru=new TextField();
    public JPanel messagesInput(){
        JPanel pan=new JPanel();
        pan.setLayout(null);
        pan.setBounds(0,365,500,175);
        pan.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.gray));
        pan.setBackground(Color.white);
        //文本框

        tfshuru.setBounds(0,0,498,100);
        tfshuru.setEditable(true);
        tfshuru.setBackground(Color.white);
        //自动换行(待补充)

        //发送按钮
        JButton jb=new JButton("发送");
        jb.setBounds(430,100,60,30);
        //设置发送的快捷键 ALT+ENTER
        jb.setMnemonic(KeyEvent.VK_ENTER);
        pan.add(tfshuru);
        pan.add(jb);

        //发送按钮绑定事件
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s=tfshuru.getText().toString();
//                String msg = username+":"+s;
                sendMsgPipe.send(s);
                //将发送的信息显示在主聊天框中
                textArea.append(username+"  "+new Date().toString()+"\n"+s+"\n");
            }
        });
        return pan;
    }

    //右边部分：在线好友、刷新按钮、所有好友
    //在线好友
    public JPanel onlineFriend() {
        JPanel pan=new JPanel();
        pan.setLayout(null);
        pan.setBounds(501,0,219,200);
        pan.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
        pan.setBackground(Color.white);
        onList.setBounds(0,0,200,200);
        onList.setMultipleMode(false);
        pan.add(onList);
        //双击元素事件
        onList.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                //TODO
                // 打开与某好友的聊天
                List theList = (List) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    int index = theList.getSelectedIndex();
                    if (index >= 0) {
                        //双击事件
                        String item = theList.getItem(index);
                        String userId = item.replaceAll(".*\\(", "").replace(")", "");
//                        sendMsgPipe.send();
//                        acceptUserName=acceptUserName.split(",")[0];
//                        //状态为4，请求消息记录
//                        //打包号数据类型,用于发送给服务器
//                        Message m=new Message();
//                        m.setSendName(userName);
//                        m.setAcceptName(acceptUserName);
//                        m.setTime(new Date().toString());
//                        m.setType("6");
//                        sendMessage=m;
//                        person(acceptUserName);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        return pan;
    }

    //右侧中间部分显示
    public JPanel buttons(){
        JPanel pan=new JPanel();
        pan.setLayout(null);
        pan.setBounds(501,201,219,100);
        pan.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
        pan.setBackground(Color.white);
        allCount.setBounds(0,0,130,20);
        //刷新按钮
        JButton jb=new JButton("刷新");
        jb.setBounds(0,20,76,30);
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allFriendListGui.removeAll();
                onList.removeAll();
                String friendList = FriendRequest.friendList(userId);
                String[] split = friendList.split(";");
                for (String s : split) {
                    allFriendListGui.add(s);
                    onList.add(s);
                }
            }
        });

        //下方标签
        JLabel jldown =new JLabel("所有好友");
        jldown.setBounds(0,79,100,20);

        pan.add(jldown);
        pan.add(allCount);
        pan.add(jb);
        return pan;
    }
    //好友列表
    public JPanel allFriend() {

        JPanel pan=new JPanel();
        pan.setLayout(null);
        pan.setBounds(501,302,219,150);
        pan.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
        pan.setBackground(Color.white);
        allFriendListGui.setBounds(0,0,200,150);
        allFriendListGui.setMultipleMode(false);//不可多选
        pan.add(allFriendListGui);
        //双击元素事件
//        allFriendListGUI.addMouseListener(new MouseListener(){
//
//            public void mouseClicked(MouseEvent mouseEvent) {
//                List theList = (List) mouseEvent.getSource();
//                if (mouseEvent.getClickCount() == 2) {
//                    int index = theList.getSelectedIndex();
//                    if (index >= 0) {
//                        //双击事件
//                        String s = theList.getItem(index);
//                        System.out.println(s);
//                    }
//                }
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//            }
//        });

        return pan;
    }

    //添加好友
    public JPanel addFriend() {
        JPanel pan=new JPanel();
        pan.setLayout(null);
        pan.setBounds(501,453,219,80);
        pan.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray));
        pan.setBackground(Color.white);
        //添加好友输入框
        TextField tf=new TextField("输入用户Id");
        tf.setBounds(0,5,100,22);
        tf.setEditable(true);
        tf.setBackground(Color.white);
        //查找用户按钮
        JButton jb=new JButton("添加用户");
        jb.setBounds(101,5,90,22);
        jb.setBorderPainted(false);
        jb.setBorderPainted(false);
        jb.setFocusPainted(false);
        final String[] tip = {""};
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = FriendRequest.addFriend(userId, tf.getText());
                if (result.contains("true")) {
                    tip[0] = "添加成功";
                } else {
                    tip[0] = "添加失败";
                }
            }
        });
        JLabel jl=new JLabel(tip[0]);
        jl.setBounds(0,28,200,20);
        pan.add(jl);
        pan.add(jb);
        pan.add(tf);
        return pan;
    }

    //窗口的内容整合
    public void content(){
        //左上角聊天对象显示
        JFrame win= baseWindow();
        JPanel get= messagePanel();
        JPanel send= messagesInput();
        JPanel onlineFriend= onlineFriend();
        JPanel allFriend= allFriend();
        JPanel buttons=buttons();
        JPanel addFriend= addFriend();

        //组件装入窗口win
        win.add(addFriend);
        win.add(buttons);
        win.add(allFriend);
        win.add(onlineFriend);
        win.add(get);
        win.add(send);
        win.setLayout(null);
        win.validate();
        win.setVisible(true);
    }
}
