package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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



    /**
     * 查询user信息数据
     */
    public static List<Person> queryUserInfo() {
        String sql = "select * from person";
        List<Person> list = new ArrayList<Person>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Person contactPerson = new Person();
                contactPerson.setId(rs.getInt(1));
                contactPerson.setName(rs.getString(2));
                contactPerson.setPhone(rs.getString(3));
                contactPerson.setAddress(rs.getString(4));
                list.add(contactPerson);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 删除user信息
     */
    public static boolean deleteUserInfo(int id) {
        String sql = "delete from person where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int rs = ps.executeUpdate();
            ps.close();
            return rs != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 添加user信息
     */
    public static boolean addUserInfo(Person contactPerson) {
        String sql = "insert into person(name,phone,address) values (?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, contactPerson.getName());
            ps.setString(2, contactPerson.getPhone());
            ps.setString(3, contactPerson.getAddress());
            int rs = ps.executeUpdate();
            ps.close();
            return rs != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改user信息
     */
    public static boolean modifyUserInfo(Person contactPerson) {
        String sql = "update person set name=?,phone=?,address=? where id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, contactPerson.getName());
            ps.setString(2, contactPerson.getPhone());
            ps.setString(3, contactPerson.getAddress());
            ps.setInt(4, contactPerson.getId());
            int rs = ps.executeUpdate();
            ps.close();
            return rs != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
