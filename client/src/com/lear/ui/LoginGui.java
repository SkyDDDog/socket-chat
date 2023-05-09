package com.lear.ui;

import com.lear.entity.api.UserRequestModel;
import com.lear.request.UserRequest;
import com.lear.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

/**
 * 登录界面
 * @author 天狗
 */
public class LoginGui extends JFrame {

    JTextField component1;
    JLabel component2;
    JTextField component3;
    JLabel component4;
    JButton component5;

    public LoginGui() throws HeadlessException {
        super("login");
        this.setSize(450, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // 账号输入框
        component1 = new JTextField();
        // 账号输入提示
        component2 = new JLabel("用户账号");
        // 密码输入框
        component3 = new JPasswordField();
        // 密码输入提示
        component4 = new JLabel("用户密码");
        // 安全登录按钮
        component5 = new JButton("      登录      ");
        component5.addMouseListener(new LoginListener());

        // 实体化布局对象
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        // 实例化对象管理组件
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=1;
        gridBagConstraints.gridwidth=3;
        gridBagConstraints.gridheight=1;
        gridBagLayout.setConstraints(component1, gridBagConstraints);

        gridBagConstraints.gridx=4;
        gridBagConstraints.gridy=1;
        gridBagConstraints.gridwidth=1;
        gridBagConstraints.gridheight=1;
        gridBagLayout.setConstraints(component2, gridBagConstraints);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=2;
        gridBagConstraints.gridwidth=3;
        gridBagConstraints.gridheight=1;
        gridBagLayout.setConstraints(component3, gridBagConstraints);

        gridBagConstraints.gridx=4;
        gridBagConstraints.gridy=2;
        gridBagConstraints.gridwidth=1;
        gridBagConstraints.gridheight=1;
        gridBagLayout.setConstraints(component4, gridBagConstraints);


        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=3;
        gridBagConstraints.gridwidth=3;
        gridBagConstraints.gridheight=1;
        gridBagLayout.setConstraints(component5, gridBagConstraints);

        // 加入窗体
        this.add(component1);
        this.add(component2);
        this.add(component3);
        this.add(component4);
        this.add(component5);
        this.setVisible(true);
    }


    class LoginListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            String username = component1.getText();
            username = username.replaceAll(" ", "");
            String password = component3.getText();
            password = password.replaceAll(" ", "");
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                //TODO
                // 账号密码不能为空
                return;
            }
            String result = UserRequest.login(new UserRequestModel(username, password));
            if (!StringUtils.isEmpty(result) && result.contains("true")) {
                String userId = result.replaceAll("true,", "");
                if (!StringUtils.isEmpty(userId)) {
                    LoginGui.this.dispose();
                    // 登录成功
                    // 跳转到主界面
                    try {
                        new ChatGui(userId, username);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                // 关闭当前界面
            } else {
                //TODO
                // 登录失败
            }
            System.out.println(result);
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
    }


}


