/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import com.sims.entitynew.Rolez;
import com.sims.entitynew.Unit;
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
public class UnitController {

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

    public List<Unit> getAllUnit() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Unit> listUnit = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT * FROM UNIT");
        while (resultSet.next()) {
            Unit unit = new Unit(resultSet.getInt("UNITID"), resultSet.getString("UNITNAME"));
            listUnit.add(unit);
        }
        return listUnit;
    }

    public boolean updateUnit(Unit unitId) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE UNIT SET UNITNAME=?    WHERE UNITID=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, unitId.getUnitname());
        prepareStatement.setInt(2, unitId.getUnitid());
        int result = prepareStatement.executeUpdate();

        if (result == 1) {
//            connection.close();
            return true;

        } else {
//            connection.close();
            return false;
        }
    }

    public Unit getUnitByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from Unit where UNITID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            Unit unit = new Unit(resultSet.getInt("UNITID"),
                    resultSet.getString("UNITNAME"));
            return unit;
        } else {
//            connection.close();
            return null;
        }
    }
    public String  getUnitIDByName(String  name) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from Unit where UNITNAME=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, name);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            String  unit = resultSet.getString("UNITNAME");
            return unit;
        } else {
//            connection.close();
            return null;
        }
    }

    public boolean addNewUnit(Unit unit) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "INSERT INTO UNIT (UNITNAME) VALUES (?)";
        prepareStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setString(1,unit.getUnitname());
       

        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int unitid = generatedKeys.getInt(1);
                unit.setUnitid(unitid);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteUnitByID(int unitId) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        Unit unitId1 = getUnitByID(unitId);
        if (unitId1 == null) {
            return false;
        } else {
            String sql = " Delete from Unit where UnitID=?";
            prepareStatement = connection.prepareCall(sql);
            prepareStatement.setInt(1, unitId);
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
    public Unit getUnitByname(String unitname)throws SQLException, ClassNotFoundException{
      jdbcConnectDB();
        String sql = "Select * from UNIT where UNITNAME=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1,unitname);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            Unit unit = new Unit();
            unit.setUnitid(resultSet.getInt("UNITID"));
            unit.setUnitname(resultSet.getString("UNITNAME"));
            return unit;
        } 
        return null;
    }
}
