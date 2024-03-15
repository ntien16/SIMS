/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import com.sims.controller.EmployeeController;
import com.sims.controller.SupplierController;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Importitems;
import com.sims.entitynew.Supplier;
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
public class ImportItemController {

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

    public List<Importitems> getAllImportItem() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Importitems> listImportItem = new ArrayList<>();
        EmployeeController employeeController = new EmployeeController();
        SupplierController supplierController = new SupplierController();
        resultSet = statement.executeQuery("SELECT * FROM IMPORTITEMS");
        while (resultSet.next()) {
            Importitems importitem = new Importitems();
            importitem.setImportid(resultSet.getInt("Importid"));
            Employees employees = employeeController.getEmployeeByID(resultSet.getInt("EMPLOYEEID"));
            Supplier supplier = supplierController.getSupplierByID(resultSet.getInt("SUPPLIERID"));
            importitem.setEmployeeid(employees);;
            importitem.setSupplierid(supplier);
            importitem.setImportdate(resultSet.getDate("importdate"));
            listImportItem.add(importitem);
        }
        return listImportItem;
    }

//    public boolean updateRole(Importitems importItemId) throws SQLException, ClassNotFoundException {
//        jdbcConnectDB();
//        String sqlStatement = "UPDATE ROLEZ SET ROLENAME=?    WHERE ROLEID=?";
//        prepareStatement = connection.prepareStatement(sqlStatement);
//        prepareStatement.setString(1, importItemId.get());
//        prepareStatement.setInt(2, importItemId.getRoleid());
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
    public Importitems getIMPORTITEMSByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from IMPORTITEMS where Importid=?";
        prepareStatement = connection.prepareStatement(sql);
        EmployeeController employeeController = new EmployeeController();
        SupplierController supplierController = new SupplierController();
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            Importitems importitem = new Importitems();
            importitem.setImportid(resultSet.getInt("Importid"));
            Employees employees = employeeController.getEmployeeByID(resultSet.getInt("EMPLOYEEID"));
            Supplier supplier = supplierController.getSupplierByID(resultSet.getInt("SUPPLIERID"));
            importitem.setEmployeeid(employees);;
            importitem.setSupplierid(supplier);
            importitem.setImportdate(resultSet.getDate("importdate"));
            return importitem;
        } else {
            connection.close();
            return null;
        }
    }

    public boolean addNewIMPORTITEMS(Importitems newImportitems) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
       String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        String sql = "INSERT INTO IMPORTITEMS (EMPLOYEEID,SUPPLIERID) VALUES(?,?)";
        prepareStatement = connection.prepareStatement(sql);
        EmployeeController employeeController = new EmployeeController();
        SupplierController supplierController = new SupplierController();
        Employees employees = employeeController.getEmployeeByID(resultSet.getInt("EMPLOYEEID"));
        Supplier supplier = supplierController.getSupplierByID(resultSet.getInt("SUPPLIERID"));
        prepareStatement.setInt(1, employees.getEmployeeid());
        prepareStatement.setInt(2, supplier.getSupplierid());
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

    public boolean deleteImportByID(int importitemid) throws SQLException, ClassNotFoundException {
    jdbcConnectDB();
    String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    connection = DriverManager.getConnection(url, username, password);
    
   
    String deleteImportDetailSQL = "DELETE FROM IMPORTDETAIL WHERE Importid=?";
    prepareStatement = connection.prepareCall(deleteImportDetailSQL);
    prepareStatement.setInt(1, importitemid);
    prepareStatement.executeUpdate();
    
   
    String deleteImportItemsSQL = "DELETE FROM IMPORTITEMS WHERE Importid=?";
    prepareStatement = connection.prepareCall(deleteImportItemsSQL);
    prepareStatement.setInt(1, importitemid);
    
    int result = prepareStatement.executeUpdate();
    
    if (result == 1) {
        connection.close();
        return true;
    } else {
        connection.close();
        return false;
    }
}

    public int addNewImport2parameter(int EMPLOYEEID, int SUPPLIERID) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        CustomerController customerController = new CustomerController();
        EmployeeController employeeController = new EmployeeController();
        String sql = "INSERT INTO IMPORTITEMS (EMPLOYEEID, SUPPLIERID) VALUES (?, ?); SELECT SCOPE_IDENTITY() AS BillId";

        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, EMPLOYEEID);
            preparedStatement.setInt(2, SUPPLIERID);

            int result = preparedStatement.executeUpdate();
            int newItemID = 0;

            try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newItemID = generatedKeys.getInt(1);
                }
            }

            return newItemID;
        } finally {
            connection.close();
        }
    }
}
