/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import static com.sims.controller.CustomerController.prepareStatement;
import static com.sims.controller.InventoryController.prepareStatement;
import static com.sims.controller.InventoryController.resultSet;
import com.sims.entitynew.Customer;
import com.sims.entitynew.Supplier;
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
public class SupplierController {

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

    public List<Supplier> getAllSupplier() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Supplier> listSupplier = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT*FROM SUPPLIER ");
        while (resultSet.next()) {
            Supplier supplier = new Supplier(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
            listSupplier.add(supplier);
        }
        return listSupplier;
    }

    public boolean updateCustomer(Supplier supplierId) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE SUPPLIER SET suppilerName=?, suppilerphone=?, suppilerAddress=? WHERE supplierid=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, supplierId.getSuppilerName());
        prepareStatement.setString(2, supplierId.getSuppilerphone());
        prepareStatement.setString(3, supplierId.getSuppilerAddress());
        prepareStatement.setInt(4, supplierId.getSupplierid());
        int result = prepareStatement.executeUpdate();

        if (result == 1) {
//           connection.close();
            return true;

        } else {
//             connection.close();
            return false;
        }
    }

    public Supplier getSupplierByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from SUPPLIER where supplierid=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            Supplier supplier = new Supplier(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4));

            return supplier;
        } else {
            //             connection.close();
            return null;
        }
    }

    public boolean addNewSuppiler(Supplier supplier) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "INSERT INTO SUPPLIER (suppilerName,suppilerphone,suppilerAddress) VALUES (?,?,?)";
        prepareStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setString(1, supplier.getSuppilerName());
        prepareStatement.setString(2, supplier.getSuppilerphone());
        prepareStatement.setString(3, supplier.getSuppilerAddress());

        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int supplierid = generatedKeys.getInt(1);
                supplier.setSupplierid(supplierid);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteSupplierByID(int supplierId) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
       String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        Supplier supplierID = getSupplierByID(supplierId);

        if (supplierID == null) {
            return false;
        } else {
            String sql = " Delete from SUPPLIER where supplierid=?";
            prepareStatement = connection.prepareCall(sql);
            prepareStatement.setInt(1, supplierId);

            int result = prepareStatement.executeUpdate();
            if (result == 1) {

                return true;
            } else {

                return false;
            }
        }
    }
    
     public int getSupplierByName(String suppilerName) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT supplierid  FROM SUPPLIER WHERE suppilerName=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, suppilerName);
        resultSet = prepareStatement.executeQuery();
        RolezController rolezController = new RolezController();

        if (resultSet.next()) {
            int employees = resultSet.getInt("supplierid");

            return employees;
        } else {
            return 0;
        }
    }
}
