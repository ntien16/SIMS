/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import com.sims.entitynew.Bill;
import com.sims.entitynew.Customer;
import com.sims.entitynew.Employees;
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
public class BillController {

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
//

    public List<Bill> getAllBill() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Bill> listBill = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT*FROM BILL");
        CustomerController customerController = new CustomerController();
        EmployeeController employeeController = new EmployeeController();
        while (resultSet.next()) {
            Bill bill = new Bill();
            bill.setBillID(resultSet.getInt("BillID"));
            Customer customer = customerController.getCustomerByID(resultSet.getInt("CustomerId"));
            bill.setCustomerId(customer);
            bill.setCreatedDate(resultSet.getDate(3));
            Employees employees = employeeController.getEmployeeByID(resultSet.getInt("EMPLOYEEID"));
            bill.setEmployeeid(employees);
            listBill.add(bill);
        }
        disConnection();
        return listBill;
    }

    public Bill getBillByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from Bill where BillID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        CustomerController customerController = new CustomerController();
        EmployeeController employeeController = new EmployeeController();
        if (resultSet.next()) {
            Bill bill = new Bill();
            bill.setBillID(resultSet.getInt("BillID"));
            Customer customer = customerController.getCustomerByID(resultSet.getInt("CustomerID"));
            bill.setCustomerId(customer);
            bill.setCreatedDate(resultSet.getDate(3));
            Employees employees = employeeController.getEmployeeByID(resultSet.getInt("EMPLOYEEID"));
            bill.setEmployeeid(employees);
            return bill;
        } else {
//            connection.close();
            return null;
        }
    }

    public int getBillByIDInt(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT * FROM Bill WHERE BillID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            return id; // Trả về ID của hóa đơn nếu tìm thấy
        } else {
            return -1; // Trả về -1 nếu không tìm thấy hóa đơn với ID cụ thể
        }
    }
    
    public String getCustomerNameByBillID(int billID) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT C.CustomerName "
                + "FROM BILL B "
                + "INNER JOIN CUSTOMER C ON B.CustomerId = C.CustomerId "
                + "WHERE B.BillID = ?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, billID);
        resultSet = prepareStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("CustomerName");
        } else {
            return null;
        }
    }

      public int getBillByIDIntCustomer(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT CustomerID FROM Bill WHERE BillID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            return id; 
        } else {
            return -1; 
        }
    }

    public boolean addNewBill(Bill newBill, int customerid, int employeeid) throws SQLException, ClassNotFoundException {
        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        CustomerController customerController = new CustomerController();
        EmployeeController employeeController = new EmployeeController();
        String sql = "INSERT INTO Bill (Customerid,employeeid) VALUES(?,?)";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, customerid);
        prepareStatement.setInt(2, employeeid);

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

    public int addNewBill2Parameter(int customerid, int employeeid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        CustomerController customerController = new CustomerController();
        EmployeeController employeeController = new EmployeeController();
        String sql = "INSERT INTO Bill (Customerid, employeeid) VALUES (?, ?); SELECT SCOPE_IDENTITY() AS BillId";

        try ( PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, customerid);
            preparedStatement.setInt(2, employeeid);

            int result = preparedStatement.executeUpdate();
            int newBillId = 0;

            try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newBillId = generatedKeys.getInt(1);
                }
            }

            return newBillId;
        } finally {
            connection.close();
        }
    }

    public boolean deleteBillByID(int billId) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        try {

            BillDetailController billDetailController = new BillDetailController();
            billDetailController.deleteBillByID(billId);
            String deleteBillSQL = "DELETE FROM Bill WHERE BillID=?";
            prepareStatement = connection.prepareStatement(deleteBillSQL);
            prepareStatement.setInt(1, billId);

            int result = prepareStatement.executeUpdate();
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            connection.close();
        }
    }

}
