/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import com.sims.entitynew.BILLDetail;
import com.sims.entitynew.BILLDetailPK;
import com.sims.entitynew.Bill;
import com.sims.entitynew.Customer;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Inventory;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class BillDetailController {

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

    public List<BILLDetail> getAllBillDetail() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<BILLDetail> listBillDetail = new ArrayList<>();
     
        resultSet = statement.executeQuery("SELECT*FROM BILLdetail");
        BillController billController = new BillController();
        InventoryController itemController = new InventoryController();
        while (resultSet.next()) {
            BILLDetail billdetail = new BILLDetail();
            int billID = resultSet.getInt("BillID");
            int itemID = resultSet.getInt("ItemID");
            Bill bill = billController.getBillByID(resultSet.getInt("BillId"));
            billdetail.setBill(bill);
            Inventory item = itemController.getInventoryByID(resultSet.getInt("ItemId"));
            billdetail.setInventory(item);
            billdetail.setBILLDetailPK(new BILLDetailPK(billID, itemID));
            billdetail.setQuantity(resultSet.getInt("QUANTITY"));
            billdetail.setDiscount(resultSet.getInt("DISCOUNT"));
            billdetail.setStatuz(resultSet.getString("STATUZ"));
            listBillDetail.add(billdetail);
        }
//        disConnection();
        return listBillDetail;
    }
    
    
     public List<BILLDetail> getAllBillDetailByID(int BillID) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<BILLDetail> listBillDetail = new ArrayList<>();
        String sql="SELECT*FROM BILLdetail where BillID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, BillID);
        resultSet = prepareStatement.executeQuery();
        
        BillController billController = new BillController();
        InventoryController itemController = new InventoryController();
        while (resultSet.next()) {
            BILLDetail billdetail = new BILLDetail();
            int billID = resultSet.getInt("BillID");
            int itemID = resultSet.getInt("ItemID");
            Bill bill = billController.getBillByID(resultSet.getInt("BillId"));
            billdetail.setBill(bill);
            Inventory item = itemController.getInventoryByID(resultSet.getInt("ItemId"));
            billdetail.setInventory(item);
            billdetail.setBILLDetailPK(new BILLDetailPK(billID, itemID));
            billdetail.setQuantity(resultSet.getInt("QUANTITY"));
            billdetail.setDiscount(resultSet.getInt("DISCOUNT"));
            billdetail.setStatuz(resultSet.getString("STATUZ"));
            listBillDetail.add(billdetail);
        }
//        disConnection();
        return listBillDetail;
    }

    public BILLDetail getBillDetailByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from Billdetail where BillID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        BillController billController = new BillController();
        InventoryController itemController = new InventoryController();
        if (resultSet.next()) {
            BILLDetail billDetail = new BILLDetail();
            int billID = resultSet.getInt("BillID");
            int itemID = resultSet.getInt("ItemID");
            Bill bill = billController.getBillByID(resultSet.getInt("BillId"));
            billDetail.setBill(bill);
            Inventory item = itemController.getInventoryByID(resultSet.getInt("ItemId"));
            billDetail.setInventory(item);
            billDetail.setBILLDetailPK(new BILLDetailPK(billID, itemID));
            billDetail.setQuantity(resultSet.getInt("QUANTITY"));
            billDetail.setDiscount(resultSet.getInt("DISCOUNT"));
            billDetail.setStatuz(resultSet.getString("STATUZ"));
            return billDetail;
        } else {
//            connection.close();
            return null;
        }
    }

    public List<BILLDetail> getBillDetailByIDList(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT * FROM Billdetail WHERE BillID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        BillController billController = new BillController();
        InventoryController itemController = new InventoryController();
        List<BILLDetail> listBILLDetails = new ArrayList<>();

        while (resultSet.next()) {
            BILLDetail billDetail = new BILLDetail();
            int billID = resultSet.getInt("BillID");
            int itemID = resultSet.getInt("ItemID");
            Bill bill = billController.getBillByID(resultSet.getInt("BillId"));
            billDetail.setBill(bill);
            Inventory item = itemController.getInventoryByID(resultSet.getInt("ItemId"));
            billDetail.setInventory(item);
            billDetail.setBILLDetailPK(new BILLDetailPK(billID, itemID));
            billDetail.setQuantity(resultSet.getInt("QUANTITY"));
            billDetail.setDiscount(resultSet.getInt("DISCOUNT"));
            billDetail.setStatuz(resultSet.getString("STATUZ"));
            listBILLDetails.add(billDetail);
        }

        //        disConnection();
        return listBILLDetails;

    }

    public BILLDetail getBillDetailByIDItemID(int id, int itemid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from Billdetail where BillID=?and Itemid=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        prepareStatement.setInt(2, itemid);
        resultSet = prepareStatement.executeQuery();
        BillController billController = new BillController();
        InventoryController itemController = new InventoryController();
        if (resultSet.next()) {
            BILLDetail billDetail = new BILLDetail();
            int billID = resultSet.getInt("BillID");
            int itemID = resultSet.getInt("ItemID");
            Bill bill = billController.getBillByID(resultSet.getInt("BillId"));
            billDetail.setBill(bill);
            Inventory item = itemController.getInventoryByID(resultSet.getInt("ItemId"));
            billDetail.setInventory(item);
            billDetail.setBILLDetailPK(new BILLDetailPK(billID, itemID));
            billDetail.setQuantity(resultSet.getInt("QUANTITY"));
            billDetail.setDiscount(resultSet.getInt("DISCOUNT"));
            billDetail.setStatuz(resultSet.getString("STATUZ"));
            return billDetail;
        } else {
//            connection.close();
            return null;
        }
    }

    public boolean addNewBillDetail(BILLDetail newBillDetail, int billid, int itemid) throws SQLException, ClassNotFoundException {
        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        String sql = "INSERT INTO BILLDetail (BillID, ItemID, Quantity, Discount, Statuz) VALUES (?, ?, ?, ?, ?)";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, billid);
        prepareStatement.setInt(2, itemid);
        prepareStatement.setInt(3, newBillDetail.getQuantity());
        prepareStatement.setInt(4, newBillDetail.getDiscount());
        prepareStatement.setString(5, newBillDetail.getStatuz());

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

    public boolean addNewBillDetailFiveParameter(int billid, int itemid, int quantity, double discount, String statuz) throws SQLException, ClassNotFoundException {
//        String url = "jdbc:sqlserver://MSI\\MSSQLSERVER2022:1433;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
//        String username = "sa";
//        String password = "sa";
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        connection = DriverManager.getConnection(url, username, password);
        jdbcConnectDB();
        String sql = "INSERT INTO BILLDetail (BillID, ItemID, Quantity, Discount, Statuz) VALUES (?, ?, ?, ?, ?)";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, billid);
        prepareStatement.setInt(2, itemid);
        prepareStatement.setInt(3, quantity);
        prepareStatement.setDouble(4, discount);
        prepareStatement.setString(5, statuz);

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

    public boolean deleteBillByIDItemID(int billDetailId, int itemid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
//        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
//        String username = "sa";
//        String password = "sa";
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        connection = DriverManager.getConnection(url, username, password);
        BILLDetail billdetail = getBillDetailByIDItemID(billDetailId, itemid);
        if (billdetail == null) {
            return false;
        } else {
            String sql = " Delete from BillDetail where BillID=? and ItemId=?";
            prepareStatement = connection.prepareCall(sql);
            prepareStatement.setInt(1, billDetailId);
            prepareStatement.setInt(2, itemid);
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

    public boolean deleteBillByID(int billDetailId) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        BILLDetail billdetail = getBillDetailByID(billDetailId);
        if (billdetail == null) {
            return false;
        } else {
            String sql = " Delete from BillDetail where BillID=? ";
            prepareStatement = connection.prepareCall(sql);
            prepareStatement.setInt(1, billDetailId);
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

    public List<BILLDetail> getAllItemsSell() throws ClassNotFoundException, SQLException {
        jdbcConnectDB();
        List<BILLDetail> listBilldetailSell = new ArrayList<>();
        String sqlString = "SELECT ITEMID, SUM(QUANTITY) AS TOTAL_QUANTITY FROM BILLDETAIL GROUP BY ITEMID";
        resultSet = statement.executeQuery(sqlString);

        while (resultSet.next()) {
            int itemId = resultSet.getInt("ITEMID");
            int totalQuantity = resultSet.getInt("TOTAL_QUANTITY");

            // Create a new BILLDetailPK object with itemId and a placeholder billID
            BILLDetailPK pk = new BILLDetailPK(0, itemId); // Replace 0 with the actual billID

            // Create a new BILLDetail object using the retrieved values
            BILLDetail billDetail = new BILLDetail(pk, totalQuantity, "YOUR_STATUZ_VALUE"); // Replace with your statuz value

            listBilldetailSell.add(billDetail);
        }

        // Close the database resources
        resultSet.close();
        statement.close();
        connection.close();

        return listBilldetailSell;
    }

    public void setDataToChart(JPanel jpnItem) throws SQLException, ClassNotFoundException {
        List<BILLDetail> listItem = getAllBillDetail();
        
        if (listItem != null) {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
           
            for (BILLDetail billdetail : listItem) {
            BigDecimal sellPrice = billdetail.getInventory().getSellPrice();
            BigDecimal amount = sellPrice.multiply(new BigDecimal(billdetail.getQuantity()));
            dataset.addValue(amount.doubleValue(), "Value", billdetail.getInventory().getItemname());
            System.out.println("billdetail+ "+ amount.doubleValue());
            }
            JFreeChart chart = ChartFactory.createBarChart(
                    "Name of items are consumed in month",
                    "Item Name",
                    "VND",
                    dataset,
                            PlotOrientation.VERTICAL,
                            true,
                            true,
                            false
            );
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 420));
            jpnItem.removeAll();
            jpnItem.add(chartPanel, BorderLayout.CENTER);
            jpnItem.revalidate(); // Use revalidate() instead of validate()
            jpnItem.setLayout(new CardLayout());
//            jpnItem.validate();
            jpnItem.repaint();
            
        }
    }
}
