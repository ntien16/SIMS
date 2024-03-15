/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import com.sims.entitynew.BILLDetail;
import com.sims.entitynew.BILLDetailPK;
import com.sims.entitynew.Bill;
import com.sims.entitynew.Customer;
import com.sims.entitynew.Inventory;
import com.sims.entitynew.Itemstakeback;
import java.sql.Connection;
import java.sql.Date;
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
public class ItemTakeBackController {

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

    public List<Itemstakeback> getAllItemTakeBack() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Itemstakeback> listItemTakeBack = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT*FROM ITEMSTAKEBACK");
        BillController billController = new BillController();
        InventoryController itemController = new InventoryController();
        CustomerController customerController = new CustomerController();
        while (resultSet.next()) {
            Itemstakeback itemstakeback = new Itemstakeback();
            itemstakeback.setItemtkid(resultSet.getInt("ITEMTKID"));
            Bill bill = billController.getBillByID(resultSet.getInt("BillId"));
            Customer customer = customerController.getCustomerByID(resultSet.getInt("CustomerID"));
//            Inventory itemInventory = itemController.getInventoryByID(resultSet.getInt("Itemid"));
            itemstakeback.setBillid(bill);
            itemstakeback.setCustomerid(customer);
            itemstakeback.setCanceldate(resultSet.getDate("CANCELDATE"));
            listItemTakeBack.add(itemstakeback);
        }
//        disConnection();
        return listItemTakeBack;
    }

    public Itemstakeback getItemTakeBackByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from ITEMSTAKEBACK where ITEMTKID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        BillController billController = new BillController();
        InventoryController itemController = new InventoryController();
        CustomerController customerController = new CustomerController();
        if (resultSet.next()) {
            Itemstakeback itemstakeback = new Itemstakeback();
            itemstakeback.setItemtkid(resultSet.getInt("ITEMTKID"));
            Bill bill = billController.getBillByID(resultSet.getInt("BillId"));
            Customer customer = customerController.getCustomerByID(resultSet.getInt("CustomerID"));
//            Inventory itemInventory = itemController.getInventoryByID(resultSet.getInt("Itemid"));
            itemstakeback.setBillid(bill);
            itemstakeback.setCustomerid(customer);
            itemstakeback.setCanceldate(resultSet.getDate("CANCELDATE"));

            return itemstakeback;
        } else {
//            connection.close();
            return null;
        }
    }

    public int getItemTakeBackByBillID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT * FROM ITEMSTAKEBACK WHERE BillID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();

        int itemCount = 0;

        if (resultSet.next()) {
            itemCount = resultSet.getInt(1);
        }

        // Đóng các tài nguyên kết nối sau khi hoàn thành
        resultSet.close();
        prepareStatement.close();
        connection.close();

        return itemCount;
    }

    public boolean addNewItemTakeBack(Itemstakeback item, int billid, int customerid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "INSERT INTO ITEMSTAKEBACK (BILLID, CUSTOMERID,  CANCELDATE) VALUES (?, ?, ?)";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, billid);
        prepareStatement.setInt(2, customerid);
        prepareStatement.setDate(3, (Date) item.getCanceldate());

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

    
     public int addNewBill2Parameter(int billid, int customerid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "INSERT INTO ITEMSTAKEBACK (BILLID, CUSTOMERID) VALUES (?, ?); SELECT SCOPE_IDENTITY() AS BillId";

        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, billid);
            preparedStatement.setInt(2, customerid);

            int result = preparedStatement.executeUpdate();
            int newItemTKBId = 0;

            try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newItemTKBId = generatedKeys.getInt(1);
                }
            }

            return newItemTKBId;
        } finally {
            connection.close();
        }
    }

    
    
    
    
    public boolean deleteITEMTKByID(int itemtakebackId) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
//        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
//        String username = "sa";
//        String password = "sa";
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        connection = DriverManager.getConnection(url, username, password);
        Itemstakeback itemitakeback = getItemTakeBackByID(itemtakebackId);
        if (itemitakeback == null) {
            return false;
        } else {
            String sql = " Delete from ITEMSTAKEBACK where ITEMTKID=? ";
            prepareStatement = connection.prepareCall(sql);
            prepareStatement.setInt(1, itemtakebackId);
            int result = prepareStatement.executeUpdate();
            if (result == 1) {
                connection.close();
                return true;
            } else {
                connection.close();
                return false;
            }
        }
    }

    public boolean deleteITemTBByBillID(int billID) throws SQLException, ClassNotFoundException {
        try {
            jdbcConnectDB();
            Itemstakeback itemitakeback = getItemTakeBackByID(billID);
            if (itemitakeback == null) {
                return false;
            } else {
                String sql = "DELETE FROM ITEMSTAKEBACK WHERE BILLID=?";
                try ( PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, billID);
                    int result = preparedStatement.executeUpdate();
                    return result == 1;
                }
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public boolean deleteAllITemTBByBillID(int billid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();

        String deleteDetailSql = "DELETE FROM ITEMSTAKEBACKDETAIL WHERE ITEMTKID IN (SELECT ITEMTKID FROM ITEMSTAKEBACK WHERE BILLID=?)";
        try ( PreparedStatement deleteDetailStatement = connection.prepareStatement(deleteDetailSql)) {

            deleteDetailStatement.setInt(1, billid);

            deleteDetailStatement.executeUpdate();
            try {

                String deleteBillSql = "DELETE FROM ITEMSTAKEBACK WHERE BILLID=?";
                try ( PreparedStatement deleteBillStatement = connection.prepareStatement(deleteBillSql)) {

                    deleteBillStatement.setInt(1, billid);

                    int result = deleteBillStatement.executeUpdate();

                    return result >= 0;
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }

    }
}
