package com.lear.constant;

import java.io.IOException;
import java.util.Properties;

/**
 * 数据库常量类
 * @author 天狗
 */
public class Database {

//    private static final String ADDRESS = "127.0.0.1";
//    private static final String PORT = "3306";
//    private static final String DATABASE = "tcp-chat";

    private static String address = "";
    private static String port = "";
    private static String database = "";
    private static String username = "";
    private static String password = "";

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String toDsn() {
        return "jdbc:mysql://"+ address +":"+port+"/"+ database +"?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
    }

    static {
        try {
            Properties properties = new Properties();
            properties.load(Database.class.getResourceAsStream("db.properties"));
            address = properties.getProperty("address");
            port = properties.getProperty("port");
            database = properties.getProperty("database");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
