/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sims.salemanager;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.data.category.DefaultCategoryDataset;
/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class SalesTrendChart  extends ApplicationFrame {
     static Statement statement;
    static ResultSet resultSet;
    static PreparedStatement prepareStatement;
    private Connection connection;
    
 public SalesTrendChart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Car Model",
                "Value",
                createDataset(),
                PlotOrientation.VERTICAL.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            // Connect to the database (update the connection details)
            String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
            
            // Execute a SQL query to retrieve the data
            String sql = "SELECT Bill.BillID, BillDetail.ITEMID, BillDetail.QUANTITY " +
                         "FROM Bill " +
                         "INNER JOIN BillDetail ON Bill.BillID = BillDetail.BillID";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int billID = rs.getInt("BillID");
                int itemID = rs.getInt("ITEMID");
                int quantity = rs.getInt("QUANTITY");

                // Add the data to the dataset
                dataset.addValue(quantity, "Quantity", "BillID " + billID + ", ItemID " + itemID);
            }

            // Close database resources
            rs.close();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataset;
    }

    public static void main(String[] args) {
        SalesTrendChart chart = new SalesTrendChart("Car Usage Statistics", "Item quantity");
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
