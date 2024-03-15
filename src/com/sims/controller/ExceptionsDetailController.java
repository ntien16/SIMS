/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import static com.sims.controller.BillDetailController.resultSet;
import static com.sims.controller.ImportDetailController.prepareStatement;
import com.sims.entitynew.Exceptions;
import com.sims.entitynew.Exceptionsdetail;
import com.sims.entitynew.ExceptionsdetailPK;
import com.sims.entitynew.Inventory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ExceptionsDetailController {

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

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
        if (connection != null) {
            connection.close();
        }
    }

    public List<Exceptionsdetail> getAllExceptionsDetail() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Exceptionsdetail> listExceptionsDetail = new ArrayList<>();

        resultSet = statement.executeQuery("SELECT * FROM EXCEPTIONSDETAIL");
        Exceptions exception = new Exceptions();
        Inventory inventory = new Inventory();
        InventoryController itemController = new InventoryController();
        ExceptionController exceptionController = new ExceptionController();
        while (resultSet.next()) {
            Exceptionsdetail exceptionsDetail = new Exceptionsdetail();
            Inventory item = itemController.getInventoryByID(resultSet.getInt("ItemId"));
            int exceptionID = resultSet.getInt("ExceptionId");
            int itemID = resultSet.getInt("ItemID");
            Exceptions exceptions = exceptionController.getExceptionByID(resultSet.getInt("ExceptionId"));
            //get ID exceptions
            exceptionsDetail.setExceptions(exceptions);
            //get ID Inventory Items
            exceptionsDetail.setInventory(item);
            //Primary key ExceptionID and ItemID
            exceptionsDetail.setExceptionsdetailPK(new ExceptionsdetailPK(exceptionID, itemID));
            exceptionsDetail.setExceptionType(resultSet.getString("ExceptionType"));
            //add to list
            listExceptionsDetail.add(exceptionsDetail);
        }

        resultSet.close();
        disConnection();

        return listExceptionsDetail;
    }

    public Exceptionsdetail getExceptionsDetailByExceptionID(int exceptionID) throws SQLServerException, ClassNotFoundException, SQLException {
        jdbcConnectDB();
        String sql = "SELECT * FROM EXCEPTIONSDETAIL WHERE ExceptionId=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, exceptionID);
        resultSet = preparedStatement.executeQuery();
        InventoryController itemController = new InventoryController();
        ExceptionController exceptionController = new ExceptionController();
        if (resultSet.next()) {

            Exceptionsdetail exceptionsDetail = new Exceptionsdetail();
            Inventory item = itemController.getInventoryByID(resultSet.getInt("ItemId"));
            int exceptionID1 = resultSet.getInt("ExceptionId");
            int itemID = resultSet.getInt("ItemID");
            Exceptions exceptions = exceptionController.getExceptionByID(resultSet.getInt("ExceptionId"));
             exceptionsDetail.setExceptions(exceptions);
            //get ID Inventory Items
            exceptionsDetail.setInventory(item);
            //Primary key ExceptionID and ItemID
            exceptionsDetail.setExceptionsdetailPK(new ExceptionsdetailPK(exceptionID1, itemID));
            exceptionsDetail.setExceptionType(resultSet.getString("ExceptionType"));

            resultSet.close();
            disConnection();

            return exceptionsDetail;
        } else {
            resultSet.close();
            disConnection();
            return null;
        }
    }

    public boolean addNewExceptionsDetail(Exceptionsdetail newExceptionsDetail, int exceptionID,int itemId) throws SQLServerException, ClassNotFoundException, SQLException {
        jdbcConnectDB();
        String sql = "INSERT INTO EXCEPTIONSDETAIL (ExceptionId, ExceptionType, ITEMID) VALUES (?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, exceptionID);
        preparedStatement.setString(2, newExceptionsDetail.getExceptionType());
        preparedStatement.setInt(3, itemId);

        int result = preparedStatement.executeUpdate();

        preparedStatement.close();
        disConnection();

        return result == 1;
    }

    public boolean deleteExceptionsDetailByExceptionID(int exceptionID) throws SQLServerException, ClassNotFoundException, SQLException {
        jdbcConnectDB();
        String sql = "DELETE FROM EXCEPTIONSDETAIL WHERE ExceptionId=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, exceptionID);

        int result = preparedStatement.executeUpdate();

        preparedStatement.close();
        disConnection();

        return result == 1;
    }
    public boolean addNewIMPORTITEMS3Parameter(int exceptionsID, int itemid, String  content) throws SQLException, ClassNotFoundException {
        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        String sql = "INSERT INTO EXCEPTIONSDETAIL (ExceptionId, ITEMID, ExceptionType) VALUES (?, ?, ?)";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, exceptionsID);
        prepareStatement.setInt(2, itemid);
        prepareStatement.setString(3, content);

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
    
    public boolean updateImportDetailByIDAndQuantity(int importId, String  exceptionsType, int itemid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE EXCEPTIONSDETAIL SET ExceptionType=? WHERE ExceptionId=? AND ITEMID=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, exceptionsType);
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

}
