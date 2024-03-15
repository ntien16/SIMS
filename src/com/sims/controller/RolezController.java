/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

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
public class RolezController {

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

    public List<Rolez> getAllRoles() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Rolez> listRoles = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT * FROM ROLEZ");
        while (resultSet.next()) {
            Rolez role = new Rolez(resultSet.getInt("ROLEID"), resultSet.getString("ROLENAME"));
            listRoles.add(role);
        }
        return listRoles;
    }

    public boolean updateRole(Rolez roleid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE ROLEZ SET ROLENAME=?    WHERE ROLEID=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, roleid.getRolename());
        prepareStatement.setInt(2, roleid.getRoleid());
        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            connection.close();
            return true;

        } else {
            connection.close();
            return false;
        }
    }

    public Rolez getRolezByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from Rolez where ROLEID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            Rolez role = new Rolez(resultSet.getInt("ROLEID"),
                    resultSet.getString("ROLENAME"));
            return role;
        } else {
            connection.close();
            return null;
        }
    }

    public Rolez getRolezByIDName(String roleName) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select * from Rolez where ROLENAME=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, roleName);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            Rolez rolez = new Rolez();
            rolez.setRoleid(resultSet.getInt("RoleID"));
            rolez.setRolename(resultSet.getString("ROLENAME"));
            return rolez;
        }
        return null;
    }

    public int getRolezByIDNameINT(String roleName) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT RoleID FROM Rolez WHERE ROLENAME=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, roleName);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            int role = resultSet.getInt("RoleID");
            resultSet.close(); // Đóng resultSet sau khi sử dụng xong
            return role;
        } else {
            // Không cần đóng kết nối ở đây
            return 0;
        }
    }

    public int getRolezByName(String roleName) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from Rolez where ROLENAME=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, roleName);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            int roleID = resultSet.getInt("ROLEID");
            return roleID;
        } else {
            connection.close();
            return -1;
        }
    }

    public boolean addNewrolez(Rolez newrolez) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        String sql = "INSERT INTO ROLEZ VALUES(?)";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, newrolez.getRolename());
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

    public boolean deleteRolezByID(int roleid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
       String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        Rolez roleId1 = getRolezByID(roleid);
        if (roleId1 == null) {
            return false;
        } else {
            String sql = " Delete from Rolez where ROLEID=?";
            prepareStatement = connection.prepareCall(sql);
            prepareStatement.setInt(1, roleid);
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
    
    
     public Rolez getRolezByID(String name) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from Rolez where ROLEID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, name);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            Rolez role = new Rolez(resultSet.getInt("ROLEID"),
                    resultSet.getString("ROLENAME"));
            return role;
        } else {
            connection.close();
            return null;
        }
    }
}
