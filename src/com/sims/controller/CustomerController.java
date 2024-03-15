/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import static com.sims.controller.EmployeeController.prepareStatement;
import static com.sims.controller.EmployeeController.resultSet;
import com.sims.entitynew.Customer;
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
public class CustomerController {

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
//

    public List<Customer> getAllCustomer() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Customer> listCustomers = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT*FROM CUSTOMER ");
        while (resultSet.next()) {
            Customer customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            listCustomers.add(customer);
        }
        return listCustomers;
    }

    public boolean updateCustomer(Customer CustomerID) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE CUSTOMER SET phoneNumber=? , AddressCus=?   WHERE CustomerID=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, CustomerID.getPhoneNumber());
        prepareStatement.setString(2, CustomerID.getAddressCus());
        prepareStatement.setInt(3, CustomerID.getCustomerID());
        int result = prepareStatement.executeUpdate();

        if (result == 1) {

            return true;

        } else {

            return false;
        }
    }

    public Customer getCustomerByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from CUSTOMER where customerID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            Customer customer = new Customer(resultSet.getInt("CustomerID"),
                    resultSet.getString("CustomerName"), resultSet.getString("phoneNumber"),
                    resultSet.getString("AddressCus"));
            return customer;
        } else {

            return null;
        }
    }

    public int getCustomerIDByName(String name) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT CustomerID FROM CUSTOMER WHERE CustomerName = ?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, name);
        resultSet = prepareStatement.executeQuery();

        if (resultSet.next()) {
            int customerID = resultSet.getInt("CustomerID");
            return customerID;
        } else {
            return -1; 
        }
    }
    
    public String getCustomerByName(String customerName) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT * FROM CUSTOMER WHERE CustomerName=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, customerName);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            String employeeName = resultSet.getString("CustomerName");
            connection.close();
            return employeeName;
        } else {
            connection.close();
            return null;
        }
    }


    public boolean addNewCustomer(Customer newCustomer) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "INSERT INTO CUSTOMER VALUES(?,?,?)";
        prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//        prepareStatement.setInt(1, newCustomer.getCustomerID());
        prepareStatement.setString(1, newCustomer.getCustomerName());
        prepareStatement.setString(2, newCustomer.getPhoneNumber());
        prepareStatement.setString(3, newCustomer.getAddressCus());

        prepareStatement.addBatch();

        int result = prepareStatement.executeUpdate();
        if (result == 1) {

            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int customerID = generatedKeys.getInt(1);
            newCustomer.setCustomerID(customerID);
        }
            return true;
        } else {

            return false;
        }

    }
      
        
    

    public boolean deleteCustomerByID(int customerId) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        Customer customerID = getCustomerByID(customerId);
        if (customerID == null) {
            return false;
        } else {
            String sql = " Delete from CUSTOMER where customerID=?";
            prepareStatement = connection.prepareCall(sql);
            prepareStatement.setInt(1, customerId);
            int result = prepareStatement.executeUpdate();
            if (result == 1) {

                return true;
            } else {

                return false;
            }
        }
    }

}
