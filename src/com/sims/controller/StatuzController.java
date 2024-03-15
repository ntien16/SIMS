/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sims.controller;

import com.sims.entitynew.Category;
import com.sims.entitynew.Statuz;
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
public class StatuzController {
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

      public List<Statuz> getAllStatuz() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Statuz> listStatuz = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT * FROM Statuz");
        while (resultSet.next()) {
            Statuz statuz = new Statuz(resultSet.getInt("STATUSID"),
                resultSet.getString("STATUSNAME"));
            listStatuz.add(statuz);
        }
        return listStatuz;
    }

    public boolean updateStatuz(Statuz statuz) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE Statuz SET STATUSNAME=? WHERE STATUSID=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, statuz.getStatusname());
        prepareStatement.setInt(2, statuz.getStatusid());
        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public Statuz getStatuzByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT * FROM Statuz WHERE STATUSID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            Statuz statuz = new Statuz(resultSet.getInt("STATUSID"),
                resultSet.getString("STATUSNAME"));
            return statuz;
        } else {
            return null;
        }
    }

    public boolean addNewStatuz(Statuz statuz) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "INSERT INTO Statuz (STATUSNAME) VALUES (?)";
        prepareStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setString(1, statuz.getStatusname());

        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int statusid = generatedKeys.getInt(1);
                statuz.setStatusid(statusid);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteStatuzByID(int statusid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        Statuz statuz = getStatuzByID(statusid);
        if (statuz == null) {
            return false;
        } else {
            String sql = "DELETE FROM Statuz WHERE STATUSID=?";
            prepareStatement = connection.prepareCall(sql);
            prepareStatement.setInt(1, statusid);
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
    
    
      public Statuz getStatuzByName(String name) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT * FROM Statuz WHERE STATUSNAME=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, name);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            Statuz statuz = new Statuz(resultSet.getInt("STATUSID"),
                resultSet.getString("STATUSNAME"));
            return statuz;
        } else {
            return null;
        }
    }

}
