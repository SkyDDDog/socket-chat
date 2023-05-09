package com.lear.util;


import com.lear.constant.Database;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Jdbc工具类
 * @author 天狗
 * @date 2023/04/29
 */
public class JdbcUtil {

    private static final Logger logger = Logger.getLogger(JdbcUtil.class.getName());


    /**
     * 获取连接
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Database.toDsn(), Database.getUsername(), Database.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    /**
     * 获取Statement对象
     * @param conn 数据库连接
     * @return
     */
    public static Statement getStatement(Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }


    /**
     * 获取预编译的命令发送
     * @param conn  数据库连接
     * @param sql   sql语句
     * @return
     */
    public static PreparedStatement getPreparedStatement(Connection conn,
                                                         String sql) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }


    /**
     * 获取预编译的命令发送
     * @param sql   sql语句
     * @return
     */
    public static PreparedStatement getPreparedStatement(String sql) {
        PreparedStatement pstmt = null;
        try {
            Connection conn = getConnection();
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }

    /**
     * 释放资源
     * @param rs    结果集
     * @param stmt  Statement对象
     * @param conn  数据库连接
     */
    public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 封装DML操作工具方法
     * @param sql   sql语句
     * @param params   填充?的参数
     * @return
     */
    public static boolean executeDML(String sql, Object... params) {

        // 声明连接
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            // 获取连接
            conn = getConnection();
            // 取消事务的自动提交
            conn.setAutoCommit(false);

            // 获取命令发送器
            pstmt = getPreparedStatement(conn, sql);
            // 给SQL参数赋值
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            // 发送SQL并执行SQL
            int count = pstmt.executeUpdate();

            // 判断
            if (count == 0) {
                // 手动回滚事务
                conn.rollback();
                return false;
            } else {
                // 手动提交事务
                conn.commit();
                return true;
            }

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();

        } finally {// 释放资源
            closeAll(null, pstmt, conn);
        }

        return false;

    }

    /**
     * 封装查询操作的工具方法
     * @param cls 结果类
     * @param sql sql语句
     * @param params 填充?的参数
     * @return
     */
    public static <T> List<T> executeDQL(Class<T> cls, String sql,
                                         Object... params) {
        // 创建返回值对象
        List<T> list = new ArrayList<T>();

        // 声明连接
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            // 获取连接
            conn = getConnection();

            // 获取命令发送器
            pstmt = getPreparedStatement(conn, sql);
            // 给sql参数赋值
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            // 发送SQL并执行SQL
            rs = pstmt.executeQuery();

            // 获取结果集的元数据信息（列数，字段名称，总列数）
            ResultSetMetaData metadata = rs.getMetaData();

            // 遍历结果集
            while (rs.next()) {
                // 创建T对象
                T obj = cls.newInstance();
                // 获取结果集中的数据并且赋值给T对象
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    BeanUtils.setProperty(obj, metadata.getColumnLabel(i)
                            .toLowerCase(), rs.getObject(i));
                }
                ConvertUtils.register(new DateConverter(null),
                        java.util.Date.class);
                // 将每条记录的对象存放至list集合中
                list.add(obj);
            }

        } catch (SQLException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        } finally {// 关闭资源
            closeAll(rs, pstmt, conn);
        }

        return list;
    }



}
