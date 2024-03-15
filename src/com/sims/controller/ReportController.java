/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import com.sims.entitynew.Employees;
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
public class ReportController {

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

    public List<Report> getAllReport() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Report> listReport = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT * FROM REPORT");
        EmployeeController employeeController = new EmployeeController();
        while (resultSet.next()) {
            Report report = new Report();
            report.setReportid(resultSet.getInt("REPORTID"));
            report.setContentreport(resultSet.getString("CONTENTREPORT"));
            Employees employees = employeeController.getEmployeeByID(resultSet.getInt("EMPLOYEEID"));
            report.setEmployeeid(employees);
            report.setReportdate(resultSet.getDate("REPORTDATE"));
            listReport.add(report);
        }
        return listReport;
    }

    public boolean updateREPORT(Report reportId) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE REPORT SET CONTENTREPORT=?    WHERE REPORTID=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, reportId.getContentreport());
        prepareStatement.setInt(2, reportId.getReportid());
        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            connection.close();
            return true;

        } else {
            connection.close();
            return false;
        }
    }

    public Report getReportByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from Report where REPORTID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        EmployeeController employeeController = new EmployeeController();
        if (resultSet.next()) {
            Report report = new Report();
            report.setReportid(resultSet.getInt("REPORTID"));
            report.setContentreport(resultSet.getString("CONTENTREPORT"));
            Employees employees = employeeController.getEmployeeByID(resultSet.getInt("EMPLOYEEID"));
            report.setEmployeeid(employees);
            report.setReportdate(resultSet.getDate("REPORTDATE"));
            return report;
        } else {
            connection.close();
            return null;
        }
    }

    public boolean addNewReport(Report newreport) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
//        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
//        String username = "sa";
//        String password = "sa";
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        connection = DriverManager.getConnection(url, username, password);
        String sql = "INSERT INTO Report (CONTENTREPORT,EMPLOYEEID) VALUES(?,?)";
        EmployeeController employeeController = new EmployeeController();
        Employees employees = employeeController.getEmployeeByID(resultSet.getInt("EMPLOYEEID"));
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, newreport.getContentreport());
        prepareStatement.setInt(2, employees.getEmployeeid());
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

    public boolean deleteReportzByID(int reportId) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
       String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        Report report = getReportByID(reportId);
        if (report == null) {
            return false;
        } else {
            String sql = " Delete from Report where REPORTID=?";
            prepareStatement = connection.prepareCall(sql);
            prepareStatement.setInt(1, reportId);
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
