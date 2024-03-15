/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import static com.sims.controller.BillDetailController.prepareStatement;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Importdetail;
import com.sims.entitynew.ImportdetailPK;
import com.sims.entitynew.Importitems;
import com.sims.entitynew.Inventory;
import com.sims.entitynew.Supplier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ImportDetailController {

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

    public List<Importdetail> getAllImportItemDetail() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Importdetail> listImportItemDetail = new ArrayList<>();
        ImportItemController importItemController = new ImportItemController();
        InventoryController itemInventory = new InventoryController();
        resultSet = statement.executeQuery("SELECT * FROM IMPORTDETAIL");
        while (resultSet.next()) {
            int importid = resultSet.getInt("Importid");
            int itemid = resultSet.getInt("ITEMID");
            Importdetail importitemDetail = new Importdetail();
            Importitems importitems = importItemController.getIMPORTITEMSByID(resultSet.getInt("Importid"));
            Inventory inventory = itemInventory.getInventoryByID(resultSet.getInt("ITEMID"));
            importitemDetail.setImportitems(importitems);;
            importitemDetail.setInventory(inventory);
            importitemDetail.setImportdetailPK(new ImportdetailPK(importid, itemid));
            importitemDetail.setQuantity(resultSet.getInt("QUANTITY"));
            listImportItemDetail.add(importitemDetail);
        }
        return listImportItemDetail;
    }

    public boolean updateImportDetail(Importdetail importDetail, int itemID) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE IMPORTDETAIL SET QUANTITY=? WHERE Importid=? AND itemID=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setInt(1, importDetail.getQuantity());
        prepareStatement.setInt(2, importDetail.getImportitems().getImportid());
        prepareStatement.setInt(3, itemID);
        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }
    }

    public boolean updateImportDetailByIDAndQuantity(int importId, int newQuantity, int itemid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE IMPORTDETAIL SET QUANTITY=? WHERE Importid=? AND itemID=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setInt(1, newQuantity);
        prepareStatement.setInt(2, importId);
        prepareStatement.setInt(3, itemid);

        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }
    }

    public Importdetail getIMPORTITEMSDetailByID(int Importid, int ITEMID) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from IMPORTDETAIL where Importid=? and ITEMID=?";
        prepareStatement = connection.prepareStatement(sql);
        ImportItemController importItemController = new ImportItemController();
        InventoryController itemInventory = new InventoryController();
        prepareStatement.setInt(1, Importid);
        prepareStatement.setInt(2, ITEMID);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            int importid = resultSet.getInt("Importid");
            int itemid = resultSet.getInt("ITEMID");
            Importdetail importitemDetail = new Importdetail();
            Importitems importitems = importItemController.getIMPORTITEMSByID(resultSet.getInt("Importid"));
            Inventory inventory = itemInventory.getInventoryByID(resultSet.getInt("ITEMID"));
            importitemDetail.setImportitems(importitems);;
            importitemDetail.setInventory(inventory);
            importitemDetail.setImportdetailPK(new ImportdetailPK(importid, itemid));
            importitemDetail.setQuantity(resultSet.getInt("QUANTITY"));
            return importitemDetail;
        } else {
            connection.close();
            return null;
        }
    }

    public boolean addNewIMPORTITEMS(Importdetail newImportDetail) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
       String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        String sql = "INSERT INTO IMPORTDETAIL (Importid,ITEMID,QUANTITY) VALUES(?,?,?)";
        prepareStatement = connection.prepareStatement(sql);
        ImportItemController importItemController = new ImportItemController();
        InventoryController itemInventory = new InventoryController();
        Importitems importitems = importItemController.getIMPORTITEMSByID(resultSet.getInt("Importid"));
        Inventory inventory = itemInventory.getInventoryByID(resultSet.getInt("ITEMID"));
        prepareStatement.setInt(1, importitems.getImportid());
        prepareStatement.setInt(2, inventory.getItemid());
        prepareStatement.setInt(3, newImportDetail.getQuantity());
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

    public boolean deleteImportDetailByID(int importDetail, int itemID) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        Importdetail importdetail = getIMPORTITEMSDetailByID(importDetail, itemID);
        if (importdetail == null) {
            return false;
        } else {
            String sql = " Delete from IMPORTDETAIL where Importid=? and itemid=?";
            prepareStatement = connection.prepareCall(sql);
            prepareStatement.setInt(1, importDetail);
            prepareStatement.setInt(2, itemID);
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

    public boolean addNewIMPORTITEMS3Parameter(int billid, int itemid, int quantity) throws SQLException, ClassNotFoundException {
        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        String sql = "INSERT INTO IMPORTDETAIL (Importid, ITEMID, Quantity) VALUES (?, ?, ?)";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, billid);
        prepareStatement.setInt(2, itemid);
        prepareStatement.setInt(3, quantity);

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

    public int getImportIdByQuantityAndItemName(int itemid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT Importid FROM IMPORTDETAIL WHERE ITEMID =? ";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, itemid);
       
        resultSet = prepareStatement.executeQuery();

        if (resultSet.next()) {
            int importId = resultSet.getInt("Importid");
            connection.close();
            return importId;
        } else {
            connection.close();
            return -1;
        }
    }

}
