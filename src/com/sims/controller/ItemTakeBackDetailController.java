/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
import static com.sims.controller.ImportDetailController.prepareStatement;
import static com.sims.controller.ItemTakeBackController.resultSet;
import static com.sims.controller.ItemTakeBackController.statement;
import com.sims.entitynew.Inventory;
import com.sims.entitynew.Itemstakeback;
import com.sims.entitynew.Itemstakebackdetail;
import com.sims.entitynew.ItemstakebackdetailPK;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemTakeBackDetailController {

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

    public List<Itemstakebackdetail> getAllItemTakeBackDetailsByItemTakeBackID()
            throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Itemstakebackdetail> itemTakeBackDetails = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT*FROM ITEMSTAKEBACKDETAIL");

        InventoryController itemController = new InventoryController();
        ItemTakeBackController itemTakeBackController = new ItemTakeBackController();
        while (resultSet.next()) {
            Itemstakebackdetail itemTakeBackDetail = new Itemstakebackdetail();
            int itemTKID = resultSet.getInt("ITEMTKID");
            int itemID = resultSet.getInt("ITEMID");
            int quantity = resultSet.getInt("QUANTITY");
            String statuz = resultSet.getString("statuz");
            Itemstakeback itemstakeback = itemTakeBackController.getItemTakeBackByID(resultSet.getInt("ITEMTKID"));
            Inventory item = itemController.getInventoryByID(resultSet.getInt("ITEMID"));

            itemTakeBackDetail.setItemstakeback(itemstakeback);
            itemTakeBackDetail.setInventory(item);
            itemTakeBackDetail.setItemstakebackdetailPK(new ItemstakebackdetailPK(itemTKID, itemID));
            itemTakeBackDetail.setItemstakeback(itemstakeback);
            itemTakeBackDetail.setInventory(item);
            itemTakeBackDetail.setQuantity(quantity);
            itemTakeBackDetail.setStatuz(statuz);

            itemTakeBackDetails.add(itemTakeBackDetail);
        }

        resultSet.close();
        disConnection();

        return itemTakeBackDetails;
    }

    public boolean addItemTakeBackDetail(Itemstakebackdetail newItemTakeBackDetail, int itemTakeBackID)
            throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "INSERT INTO ITEMSTAKEBACKDETAIL (ITEMTKID, ITEMID, QUANTITY, statuz) VALUES (?, ?, ?, ?)";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, itemTakeBackID);
        prepareStatement.setInt(2, newItemTakeBackDetail.getInventory().getItemid());
        prepareStatement.setInt(3, newItemTakeBackDetail.getQuantity());
        prepareStatement.setString(4, newItemTakeBackDetail.getStatuz());

        prepareStatement.addBatch();

        int result = prepareStatement.executeUpdate();

        disConnection();

        return result == 1;
    }

    public boolean deleteItemTakeBackDetail(int itemTKID, int itemID) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "DELETE FROM ITEMSTAKEBACKDETAIL WHERE ITEMTKID=? AND ITEMID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, itemTKID);
        prepareStatement.setInt(2, itemID);

        int result = prepareStatement.executeUpdate();

        disConnection();

        return result == 1;
    }

    public boolean deleteItemTakeBackDetailByID(int itemTKID) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "DELETE FROM ITEMSTAKEBACKDETAIL WHERE ITEMTKID=? ";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, itemTKID);

        int result = prepareStatement.executeUpdate();

        disConnection();

        return result == 1;
    }

    public boolean addNewIMPORTITEMS3Parameter(int itemTKBID, int itemid, int quantity) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        // Kiểm tra xem hàng có tồn tại trong bảng chưa
        String checkSql = "SELECT * FROM ITEMSTAKEBACKDETAIL WHERE ITEMTKID = ? AND ITEMID = ?";
        prepareStatement = connection.prepareStatement(checkSql);
        prepareStatement.setInt(1, itemTKBID);
        prepareStatement.setInt(2, itemid);
        ResultSet resultSet = prepareStatement.executeQuery();

        if (resultSet.next()) {
           
            connection.close();
            return true;
        } else {
           
            String sql = "INSERT INTO ITEMSTAKEBACKDETAIL (ITEMTKID, ITEMID, Quantity) VALUES (?, ?, ?)";
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, itemTKBID);
            prepareStatement.setInt(2, itemid);
            prepareStatement.setInt(3, quantity);
             prepareStatement.addBatch();

            int result = prepareStatement.executeUpdate();
            connection.close();

            if (result == 1) {
                return true;
            } else {
                return false;
            }
        }

    }

    public boolean updateImportDetailByIDAndQuantity(int itemTKId, int newQuantity, int itemid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "update ITEMSTAKEBACKDETAIL SET quantity=? WHERE ITEMTKID=? AND ITEMID=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setInt(1, newQuantity);
        prepareStatement.setInt(2, itemTKId);
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

}
