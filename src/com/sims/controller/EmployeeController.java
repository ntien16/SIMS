/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import com.sims.entitynew.Customer;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Rolez;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class EmployeeController {

    private static Properties property;
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

    public List<Employees> getAllEmployees() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Employees> listEmployees = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT * FROM EMPLOYEES");
        RolezController rolezController = new RolezController();
        while (resultSet.next()) {
            Employees employees1 = new Employees();
            employees1.setEmployeeid(resultSet.getInt("EMPLOYEEID"));
            employees1.setEmployeename(resultSet.getString("EMPLOYEENAME"));
            employees1.setPassword(resultSet.getString("password"));
            employees1.setPhone(resultSet.getString("PHONE"));
            employees1.setAddress(resultSet.getString("ADDRESS"));
            Rolez rolez = rolezController.getRolezByID(resultSet.getInt("ROLEID"));
            employees1.setRoleid(rolez);
            listEmployees.add(employees1);
        }
        return listEmployees;
    }

    public boolean updateEmployee(Employees employee, int roleid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE EMPLOYEES SET EMPLOYEENAME=?, password=?, ROLEID=?, PHONE=?, ADDRESS=? WHERE EMPLOYEEID=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, employee.getEmployeename());
        prepareStatement.setString(2, employee.getPassword());
        prepareStatement.setInt(3, roleid);
        prepareStatement.setString(4, employee.getPhone());
        prepareStatement.setString(5, employee.getAddress());
        prepareStatement.setInt(6, employee.getEmployeeid());

        int result = prepareStatement.executeUpdate();
        return result == 1;
    }

    public boolean updateEmployeeEMPLOYEENAMERole(Employees employee) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE EMPLOYEES SET password=?, phone=?, address=?, Roleid=?, employeename=? WHERE employeeid=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, employee.getPassword());
        prepareStatement.setString(2, employee.getPhone());
        prepareStatement.setString(3, employee.getAddress());
        prepareStatement.setString(4, employee.getRoleid().getRoleid().toString());
        prepareStatement.setString(5, employee.getEmployeename());
        prepareStatement.setInt(6, employee.getEmployeeid());

        int result = prepareStatement.executeUpdate();
        return result == 1;
    }

    public boolean updateEmployeeEMPLOYEENAME(Employees employee) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE EMPLOYEES SET password=?, phone=?, address=? WHERE employeename=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, employee.getPassword());
        prepareStatement.setString(2, employee.getPhone());
        prepareStatement.setString(3, employee.getAddress());
        prepareStatement.setString(4, employee.getEmployeename());

        int result = prepareStatement.executeUpdate();
        return result == 1;
    }

    public boolean addNewEmployee(Employees employee, int roleid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "INSERT INTO EMPLOYEES (EMPLOYEENAME, password, ROLEID, PHONE, ADDRESS) VALUES (?, ?, ?, ?, ?)";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, employee.getEmployeename());
        prepareStatement.setString(2, employee.getPassword());
        prepareStatement.setInt(3, roleid);
        prepareStatement.setString(4, employee.getPhone());
        prepareStatement.setString(5, employee.getAddress());

        int result = prepareStatement.executeUpdate();
        return result == 1;
    }
//    public boolean addNewEmployee(Employees employee) throws SQLException, ClassNotFoundException {
//        jdbcConnectDB();
//        String sqlStatement = "INSERT INTO EMPLOYEES (EMPLOYEENAME, password,  PHONE, ADDRESS,ROLEID) VALUES (?, ?, ?, ?, ?)";
//        prepareStatement = connection.prepareStatement(sqlStatement);
//        prepareStatement.setString(1, employee.getEmployeename());
//        prepareStatement.setString(2, employee.getPassword());
//        prepareStatement.setInt(3, employee.getPhone());
//        prepareStatement.setString(4, employee.getAddress());
//        prepareStatement.setInt(5, employee.getRoleid().getRoleid());
//
//        int result = prepareStatement.executeUpdate();
//        return result == 1;
//    }

    public boolean addNewEmployee(Employees employee) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "INSERT INTO EMPLOYEES (EMPLOYEENAME, password, ROLEID, PHONE, ADDRESS) VALUES (?, ?, ?, ?, ?)";
        prepareStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setString(1, employee.getEmployeename());
        prepareStatement.setString(2, employee.getPassword());
        prepareStatement.setString(3, employee.getRoleid().getRoleid().toString());
        prepareStatement.setString(4, employee.getPhone());
        prepareStatement.setString(5, employee.getAddress());

        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int employeeID = generatedKeys.getInt(1);
                employee.setEmployeeid(employeeID);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean addNewEmployee(String nameString, String password, int roleid, int phone, String address) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "INSERT INTO EMPLOYEES (EMPLOYEENAME, password, ROLEID, PHONE, ADDRESS) VALUES (?, ?, ?, ?, ?)";
        prepareStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setString(1, nameString);
        prepareStatement.setString(2, password);
        prepareStatement.setInt(3, roleid);
        prepareStatement.setInt(4, phone);
        prepareStatement.setString(5, address);

        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int employeeID = generatedKeys.getInt(1);
                Employees employees = new Employees();
                employees.setEmployeeid(employeeID);
                return true;
            }
        }
        return false;
    }

    public boolean deleteEmployeeByID(int employeeID) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "DELETE FROM EMPLOYEES WHERE EMPLOYEEID=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setInt(1, employeeID);

        int result = prepareStatement.executeUpdate();
        if (result == 1) {
                connection.close();
                return true;
            } else {
                connection.close();
                return false;
            }
    }

    public Employees getEmployeeByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        int roleid;
        try {
            String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEEID=?";
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, id);
            resultSet = prepareStatement.executeQuery();
            RolezController rolezController = new RolezController();

            if (resultSet.next()) {
                Employees employees = new Employees();
                employees.setEmployeeid(resultSet.getInt(1));
                employees.setEmployeename(resultSet.getString(2));
                employees.setPassword(resultSet.getString(3));
                Rolez rolez = rolezController.getRolezByID(resultSet.getInt(4));
                employees.setRoleid(rolez);
                employees.setPhone(resultSet.getString(5));
                employees.setAddress(resultSet.getString(6));

                return employees;
            } else {
                return null;
            }
        } finally {
            // Đóng tất cả tài nguyên sau khi hoàn thành
            if (resultSet != null) {
                resultSet.close();
            }
            if (prepareStatement != null) {
                prepareStatement.close();
            }
            if (connection != null) {
//            connection.close();
            }
        }

    }

    public Employees getEmployeeByEmployeeNameand(String employeename, String password) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEENAME=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, employeename);
        resultSet = prepareStatement.executeQuery();
        RolezController rolezController = new RolezController();

        if (resultSet.next()) {
            Employees employees = new Employees();
            employees.setEmployeeid(resultSet.getInt(1));
            employees.setEmployeename(resultSet.getString(2));
            employees.setPassword(resultSet.getString(3));
            Rolez rolez = rolezController.getRolezByID(resultSet.getInt(4));
            employees.setRoleid(rolez);
            employees.setPhone(resultSet.getString(5));
            employees.setAddress(resultSet.getString(6));

            return employees;
        } else {
            return null;
        }
    }

    public int getEmployeeByEmployeeName(String employeename) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT EMPLOYEEID  FROM EMPLOYEES WHERE EMPLOYEENAME=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, employeename);
        resultSet = prepareStatement.executeQuery();
        RolezController rolezController = new RolezController();

        if (resultSet.next()) {
            int employees = resultSet.getInt("EMPLOYEEID");

            return employees;
        } else {
            return 0;
        }
    }

    public String getEmployeeByName(String employeename) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEENAME=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, employeename);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            String employeeName = resultSet.getString("EMPLOYEENAME");
            connection.close();
            return employeeName;
        } else {
            connection.close();
            return null;
        }
    }

    public String getEmployeePassword(String employeename) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT password  FROM EMPLOYEES WHERE EMPLOYEENAME=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, employeename);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            String password = resultSet.getString("password");
            connection.close();
            return password;
        } else {
            connection.close();
            return null;
        }
    }
    
    
    
     
}
