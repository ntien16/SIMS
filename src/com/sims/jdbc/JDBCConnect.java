/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.jdbc;

import com.sims.entitynew.Customer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class JDBCConnect {

    private static Properties property;
    static Statement statement;
    static ResultSet resultSet;
    static PreparedStatement prepareStatement;
    private Connection connection;

    public Connection jdbcConnectDB() throws ClassNotFoundException, SQLException {
        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
//        String loadDriverString = property.getProperty("loadDriverString");
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
        return connection;
    }

    public void disConnection() throws SQLException {
        connection.close();
    }

    public ResultSet executeQuery(String sql) throws ClassNotFoundException, SQLException {
        Connection connnection = jdbcConnectDB();
        Statement statement = connnection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        return result;
    }

    public PreparedStatement createPrepareStatement(String sql) throws ClassNotFoundException, SQLException {
        Connection connnection = jdbcConnectDB();
        PreparedStatement preStatement = connnection.prepareStatement(sql);
        return preStatement;
    }

    

}
