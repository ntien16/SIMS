/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import com.sims.entitynew.Bill;
import com.sims.entitynew.Deletebill;
import com.sims.entitynew.Rolez;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class DeleteBillController {

    static Statement statement;
    static ResultSet resultSet;
    static PreparedStatement prepareStatement;
    private Connection connection;

    public Connection jdbcConnectDB() throws ClassNotFoundException, SQLException {
       String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
        return connection;
    }

    public void disConnection() throws SQLException {
        connection.close();
    }
//

    public List<Deletebill> getAllDeleteBill() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Deletebill> listDeleteBill = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT * FROM DELETEBILL");
        BillController billController = new BillController();
        while (resultSet.next()) {
            Deletebill deletebill = new Deletebill();
            deletebill.setDeletebillid(resultSet.getInt("DELETEBILLID"));
            deletebill.setBillid(resultSet.getInt("BillID"));
            deletebill.setDeletedate(resultSet.getDate("DELETEDATE"));
            deletebill.setStatuz(resultSet.getString("statuz"));
            listDeleteBill.add(deletebill);
        }
        return listDeleteBill;
    }

//    public boolean updateDeleteBill(Deletebill deleteBillId) throws SQLException, ClassNotFoundException {
//        jdbcConnectDB();
//        String sqlStatement = "UPDATE DELETEBILL SET ROLENAME=?    WHERE ROLEID=?";
//        prepareStatement = connection.prepareStatement(sqlStatement);
//        prepareStatement.setString(1, roleid.getRolename());
//        prepareStatement.setInt(2, roleid.getRoleid());
//        int result = prepareStatement.executeUpdate();
//
//        if (result == 1) {
//            connection.close();
//            return true;
//
//        } else {
//            connection.close();
//            return false;
//        }
//    }
    public Deletebill getDeleteBillByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from DELETEBILL where DELETEBILLID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        BillController billController = new BillController();
        if (resultSet.next()) {
            Deletebill deletebill = new Deletebill();
            deletebill.setDeletebillid(resultSet.getInt("DELETEBILLID"));
            Bill bill = billController.getBillByID(resultSet.getInt("BILLID"));
            deletebill.setBillid(resultSet.getInt("BillID"));
            deletebill.setDeletedate(resultSet.getDate("DELETEDATE"));
            deletebill.setStatuz(resultSet.getString("statuz"));
            return deletebill;
        } else {
            connection.close();
            return null;
        }
    }

    public boolean addDeleteBill(Deletebill newDeleteBill) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        String sql = "INSERT INTO DELETEBILL VALUES(?)";
        BillController billController = new BillController();
        Bill bill = billController.getBillByID(resultSet.getInt("BILLID"));
        prepareStatement = connection.prepareStatement(sql);

        prepareStatement.setInt(1, bill.getBillID());
        prepareStatement.addBatch();

        int result = prepareStatement.executeUpdate();
        if (result == 1) {
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }

    }

    public boolean addDeleteBillByID(int billID) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "INSERT INTO DELETEBILL (BILLID) VALUES(?)";

        try {
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, billID);
            int result = prepareStatement.executeUpdate();

            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            connection.close();
        }
    }

//    public boolean deleteRolezByID(int roleid) throws SQLException, ClassNotFoundException {
//        jdbcConnectDB();
//        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
//        String username = "sa";
//        String password = "sa";
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        connection = DriverManager.getConnection(url, username, password);
//        Rolez roleId1 = getRolezByID(roleid);
//        if (roleId1 == null) {
//            return false;
//        } else {
//            String sql = " Delete from Rolez where ROLEID=?";
//            prepareStatement = connection.prepareCall(sql);
//            prepareStatement.setInt(1, roleid);
//            int result = prepareStatement.executeUpdate();
//            if (result == 1) {
//                connection.close();
//                return true;
//            } else {
//                connection.close();
//                return false;
//            }
//        }
//    }
}
