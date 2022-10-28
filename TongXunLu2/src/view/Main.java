package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.DataBase;
import dao.Person;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private JFrame frame;
    private DataBase dataBase = new DataBase();

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public Main() {
        initialize();
    }

    //数据标题
    private String headTitle[] = new String[]{"id","姓名", "地址", "手机号"};
    private DefaultTableModel dtm = null;
    private JTextField tx_id;
    private JTextField tx_name;
    private JTextField tx_address;
    private JTextField tx_phone;
    private final JTable table = new JTable();

    public Object[][] makeTable() {
        List<Person> list = DataBase.queryUserInfo();
        Object[][] data = new Object[list.size()+1][4];
        data[0][0] = "id";
        data[0][1] = "姓名";
        data[0][2] = "电话";
        data[0][3] = "家庭地址";
        for (int i = 1; i <= list.size(); i++) {
            data[i][0] = list.get(i-1).getId();
            data[i][1] = list.get(i-1).getName();
            data[i][2] = list.get(i-1).getPhone();
            data[i][3] = list.get(i-1).getAddress();
        }
        return data;
    }


    private void initialize() {
        frame = new JFrame();
        frame.setTitle("个人通讯录系统");
        frame.setBounds(100, 100, 734, 540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnNewButton = new JButton("添加联系人");
        btnNewButton.setBounds(14, 245, 152, 27);
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("删除联系人");
        btnNewButton_1.setBounds(14, 309, 152, 27);
        frame.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("修改联系人");
        btnNewButton_2.setBounds(14, 362, 152, 27);
        frame.getContentPane().add(btnNewButton_2);

        JButton btn_refresh = new JButton("刷新通讯录");
        btn_refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //刷新数据
                dtm = new DefaultTableModel(makeTable(), headTitle);
                table.removeAll();
                table.setModel(dtm);
            }
        });
        btn_refresh.setBounds(14, 415, 152, 27);
        frame.getContentPane().add(btn_refresh);


        tx_id = new JTextField();
        tx_id.setBounds(53, 23, 113, 24);
        frame.getContentPane().add(tx_id);
        tx_id.setColumns(10);
        tx_id.setEditable(false);

        tx_name = new JTextField();
        tx_name.setBounds(53, 71, 113, 24);
        frame.getContentPane().add(tx_name);
        tx_name.setColumns(10);

        tx_phone = new JTextField();
        tx_phone.setBounds(53, 125, 113, 24);
        frame.getContentPane().add(tx_phone);
        tx_phone.setColumns(10);

        tx_address = new JTextField();
        tx_address.setBounds(53, 186, 113, 24);
        frame.getContentPane().add(tx_address);
        tx_address.setColumns(10);

        JLabel lblNewLabel = new JLabel("id");
        lblNewLabel.setBounds(10, 26, 72, 18);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("姓名");
        lblNewLabel_1.setBounds(10, 74, 72, 18);
        frame.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("电话");
        lblNewLabel_2.setBounds(10, 128, 72, 18);
        frame.getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("家庭地址");
        lblNewLabel_3.setBounds(10, 189, 72, 18);
        frame.getContentPane().add(lblNewLabel_3);

        table.setBounds(196, 13, 558, 454);
        frame.getContentPane().add(table);

        dtm=new DefaultTableModel(makeTable(),headTitle);
        table.setModel(dtm);

        //选择表数据
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = table.getSelectedRow();
                if (index > 0) {
                    tx_id.setText(table.getValueAt(index, 0).toString());
                    tx_name.setText(table.getValueAt(index, 1).toString());
                    tx_phone.setText(table.getValueAt(index, 2).toString());
                    tx_address.setText(table.getValueAt(index, 3).toString());
                }
            }
        });

        //添加
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Person contactPerson = new Person(tx_name.getText(),tx_phone.getText(),tx_address.getText());
                boolean result =DataBase.addUserInfo(contactPerson);
                if (result) {
                    JOptionPane.showMessageDialog(null, "添加联系人成功!", "提示", JOptionPane.PLAIN_MESSAGE);
                    //刷新列表
                    btn_refresh.doClick();
                    //清空
                    tx_name.setText("");
                    tx_address.setText("");
                    tx_phone.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "添加联系人失败!", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //删除
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean result = DataBase.deleteUserInfo(Integer.parseInt(tx_id.getText()));
                if (result) {
                    JOptionPane.showMessageDialog(null, "删除联系人成功!", "提示", JOptionPane.PLAIN_MESSAGE);
                    //刷新列表
                    btn_refresh.doClick();
                    //清空
                    tx_id.setText("");
                    tx_name.setText("");
                    tx_address.setText("");
                    tx_phone.setText("");

                } else {
                    JOptionPane.showMessageDialog(null, "删除联系人失败!", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //更新
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Person contactPerson = new Person(Integer.valueOf(tx_id.getText()),tx_name.getText(),tx_phone.getText(),tx_address.getText());
                boolean result = DataBase.modifyUserInfo(contactPerson);
                if (result) {
                    JOptionPane.showMessageDialog(null, "修改联系人成功!", "提示", JOptionPane.PLAIN_MESSAGE);
                    //刷新列表
                    btn_refresh.doClick();
                } else {
                    JOptionPane.showMessageDialog(null, "修改联系人失败!", "提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}

