/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import static com.sims.controller.ImportItemController.prepareStatement;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Exceptions;
import com.sims.entitynew.Inventory;
import com.sims.entitynew.Report;
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
public class ExceptionController {

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

    public List<Exceptions> getAllException() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Exceptions> listExceptions = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT * FROM Exceptions");
        InventoryController inventoryController = new InventoryController();
        EmployeeController employeeController = new EmployeeController();
        while (resultSet.next()) {
            Exceptions exceptions = new Exceptions();
            Employees employees = employeeController.getEmployeeByID(resultSet.getInt("EMPLOYEEID"));
            exceptions.setExceptionId(resultSet.getInt("ExceptionId"));
            exceptions.setExceptionDate(resultSet.getDate("ExceptionDate"));

            exceptions.setEmployeeid(employees);

            listExceptions.add(exceptions);
        }
        return listExceptions;
    }

    public boolean updateExceptions(Exceptions exceptionid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE Exceptions SET ExceptionType=?    WHERE ExceptionId=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setInt(2, exceptionid.getExceptionId());
        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            connection.close();
            return true;

        } else {
            connection.close();
            return false;
        }
    }

    public Exceptions getExceptionByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from Exceptions where ExceptionId=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        InventoryController inventoryController = new InventoryController();
        EmployeeController employeeController = new EmployeeController();
        if (resultSet.next()) {
            Exceptions exceptions = new Exceptions();
            exceptions.setExceptionId(resultSet.getInt("ExceptionId"));
            exceptions.setExceptionDate(resultSet.getDate("ExceptionDate"));
//            Inventory inventory = inventoryController.getInventoryByID(resultSet.getInt("ITEMID"));
            Employees employees = employeeController.getEmployeeByID(resultSet.getInt("EMPLOYEEID"));
            exceptions.setEmployeeid(employees);
            return exceptions;
        } else {
            connection.close();
            return null;
        }
    }

    public boolean addNewExpcetions(Exceptions newExceptions) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "INSERT INTO Exceptions (ExceptionType,ITEMID) VALUES(?,?,?)";
        InventoryController inventoryController = new InventoryController();
        Inventory inventory = inventoryController.getInventoryByID(resultSet.getInt("ITEMID"));
        EmployeeController employeeController = new EmployeeController();
        Employees employees = employeeController.getEmployeeByID(resultSet.getInt("EMPLOYEEID"));
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(2, inventory.getItemid());
        prepareStatement.setInt(3, employees.getEmployeeid());
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

    public int addNewExceptionsByID(int employeeID) throws ClassNotFoundException, SQLException {
        jdbcConnectDB();
        String sql = "INSERT INTO Exceptions (EmployeeID) VALUES(?)";
        int newIDExceptions = 0;

        try ( PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setInt(1, employeeID);
            int result = prepareStatement.executeUpdate();

            if (result > 0) {
                try ( ResultSet generateKeys = prepareStatement.getGeneratedKeys()) {
                    if (generateKeys.next()) {
                        newIDExceptions = generateKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException ex) {
            // Handle any exceptions here
            ex.printStackTrace();
        }

        return newIDExceptions;
    }

    public boolean deleteExceptionsByID(int exceptions) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        Exceptions exceptions1 = getExceptionByID(exceptions);
        if (exceptions1 == null) {
            return false;
        } else {
            String deleteImportDetailSQL = "DELETE FROM EXCEPTIONSDETAIL WHERE ExceptionId=?";
            prepareStatement = connection.prepareCall(deleteImportDetailSQL);
            prepareStatement.setInt(1, exceptions);
            
            prepareStatement.executeUpdate();
            String sql = " Delete from Exceptions where ExceptionId=?";
            prepareStatement = connection.prepareCall(sql);
            prepareStatement.setInt(1, exceptions);
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
}
