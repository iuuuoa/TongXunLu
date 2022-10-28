package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.DataBase;

public class Function {
	
	static DataBase database = new DataBase();
	static Connection con = database.getCon();
	
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
