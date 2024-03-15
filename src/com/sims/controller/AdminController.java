/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sims.controller;

import static com.sims.controller.ImportItemController.prepareStatement;
import static com.sims.controller.ImportItemController.resultSet;
import static com.sims.controller.ImportItemController.statement;
import com.sims.entitynew.Admin;
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
public class AdminController {

     public Statement statement;
    public ResultSet resultSet;
    public PreparedStatement prepareStatement;
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
    
    public List<Admin> getAllAdmin() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Admin> listAdmin = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT * FROM Admin");
        while (resultSet.next()) {
            Admin admin = new Admin();
            admin.setAdminid(resultSet.getInt("ADMINID"));
            admin.setAdminName(resultSet.getString("adminName"));
            admin.setAdminpassword(resultSet.getString("adminpassword"));
            listAdmin.add(admin);
        }
        return listAdmin;
    }

    public boolean updateAdmin(Admin admin) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "update [ADMIN] set  adminpassword=? where adminName=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, admin.getAdminpassword());
        prepareStatement.setString(2, admin.getAdminName());
        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            connection.close();
            return true;

        } else {
            connection.close();
            return false;
        }
    }

   public Admin getAdminByCredentials(String adminName, String adminPassword) throws SQLException, ClassNotFoundException {
    jdbcConnectDB();
    String sql = "SELECT * FROM [ADMIN] WHERE adminName=?";
    prepareStatement = connection.prepareStatement(sql);
    prepareStatement.setString(1, adminName);
//    prepareStatement.setString(2, adminPassword);

    resultSet = prepareStatement.executeQuery();
    if (resultSet.next()) {
        Admin admin = new Admin();
        admin.setAdminid(resultSet.getInt("ADMINID"));
        admin.setAdminName(resultSet.getString("adminName"));
        admin.setAdminpassword(resultSet.getString("adminPassword"));
        // Set other properties of Admin as needed

        connection.close();
        return admin;
    } else {
        connection.close();
        return null;
    }
}


//    public boolean addNewIMPORTITEMS(Importitems newImportitems) throws SQLException, ClassNotFoundException {
//        jdbcConnectDB();
//        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
//        String username = "sa";
//        String password = "sa";
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        connection = DriverManager.getConnection(url, username, password);
//        String sql = "INSERT INTO IMPORTITEMS (EMPLOYEEID,SUPPLIERID) VALUES(?,?)";
//        prepareStatement = connection.prepareStatement(sql);
//        EmployeeController employeeController = new EmployeeController();
//        SupplierController supplierController = new SupplierController();
//        Employees employees = employeeController.getEmployeeByID(resultSet.getInt("EMPLOYEEID"));
//        Supplier supplier = supplierController.getSupplierByID(resultSet.getInt("SUPPLIERID"));
//        prepareStatement.setInt(1, employees.getEmployeeid());
//        prepareStatement.setInt(2, supplier.getSupplierid());
//        prepareStatement.addBatch();
//
//        int result = prepareStatement.executeUpdate();
//        if (result == 1) {
//            connection.close();
//            return true;
//        } else {
//            connection.close();
//            return false;
//        }
//
//    }
//
//    public boolean deleteRolezByID(int importitemid) throws SQLException, ClassNotFoundException {
//        jdbcConnectDB();
//        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
//        String username = "sa";
//        String password = "sa";
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        connection = DriverManager.getConnection(url, username, password);
//        Importitems importitems = getIMPORTITEMSByID(importitemid);
//        if (importitems == null) {
//            return false;
//        } else {
//            String sql = " Delete from Rolez where ROLEID=?";
//            prepareStatement = connection.prepareCall(sql);
//            prepareStatement.setInt(1, importitemid);
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
