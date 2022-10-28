package dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import server.Person;

public class DataBase {

    // 数据库连接字符串
    private String conStr = "jdbc:mysql://localhost:3306/tongxun";//最新版本的mysql驱动
    // 数据库连接用户名
    private String dbUserName = "root";
    // 数据库连接密码
    private String dbPassword = "123456";
    // 数据库连接对象
    private static Connection con = null;

    public DataBase() {
        try {
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("com.mysql.cj.jdbc.Driver");//最新版本mysql驱动
            con = DriverManager.getConnection(conStr, dbUserName, dbPassword);
            System.out.println("[数据库连接成功]");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getCon() {
        return con;
    }
}
