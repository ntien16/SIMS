/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sims.controller;

import com.sims.entitynew.Category;
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
public class CategoryController {
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

    public List<Category> getAllCategory() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Category> listCategory = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT * FROM Category");
        while (resultSet.next()) {
            Category category = new Category(resultSet.getInt("CATEGORYID"),
resultSet.getString("CATEGORYNAME"));
            listCategory.add(category);
        }
        return listCategory;
    }

    public boolean updateCategory(Category categoryId) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE Category SET CATEGORYNAME=?    WHERE CATEGORYID=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, categoryId.getCategoryname());
        prepareStatement.setInt(2, categoryId.getCategoryid());
        int result = prepareStatement.executeUpdate();

        if (result == 1) {
//            connection.close();
            return true;

        } else {
//            connection.close();
            return false;
        }
    }

    public Category getCategoryByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from Category where CATEGORYID=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            Category category = new Category(resultSet.getInt("CATEGORYID"),
                    resultSet.getString("CATEGORYNAME"));
            return category;
        } else {
//            connection.close();
            return null;
        }
    }

    public boolean addNewCategory(Category category) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "INSERT INTO CATEGORY (CATEGORYNAME) VALUES (?)";
        prepareStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setString(1, category.getCategoryname());
       

        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int categoryid = generatedKeys.getInt(1);
                category.setCategoryid(categoryid);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteCategoryByID(int categoryId) throws SQLException, ClassNotFoundException {
       jdbcConnectDB();
       String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        Category category = getCategoryByID(categoryId);
        if (category == null) {
            return false;
        } else {
            String sql = " Delete from CATEGORY where CATEGORYID=?";
            prepareStatement = connection.prepareCall(sql);
            prepareStatement.setInt(1, categoryId);
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
    public Category getCategoryByname(String categoryname)throws SQLException, ClassNotFoundException{
        jdbcConnectDB();
        String sql = "Select * from CATEGORY where CATEGORYNAME=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1,categoryname);
        resultSet = prepareStatement.executeQuery();
        if (resultSet.next()) {
            Category category = new Category();
            category.setCategoryid(resultSet.getInt("CATEGORYID"));
            category.setCategoryname(resultSet.getString("CATEGORYNAME"));
            return category;
        } 
        return null;
    }
}
