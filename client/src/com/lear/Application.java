package com.lear;

import com.lear.entity.api.UserRequestModel;
import com.lear.request.*;
import com.lear.ui.ChatGui;
import com.lear.ui.LoginGui;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 客户端主启动类
 * @author 天狗
 */
public class Application {

    public static void main(String[] args) throws IOException {
        new LoginGui();
    }



}
