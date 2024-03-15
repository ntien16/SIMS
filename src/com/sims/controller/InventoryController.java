/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.controller;

import static com.sims.controller.EmployeeController.prepareStatement;
import static com.sims.controller.EmployeeController.resultSet;
import com.sims.entitynew.Bill;
import com.sims.entitynew.Category;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Inventory;
import com.sims.entitynew.Statuz;
import com.sims.entitynew.Unit;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class InventoryController {

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

    public List<Inventory> getAllInventory() throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        List<Inventory> listInventory = new ArrayList<>();
        resultSet = statement.executeQuery("SELECT*FROM INVENTORY");
        CategoryController categoryController = new CategoryController();
        UnitController unitController = new UnitController();
        while (resultSet.next()) {
            Inventory inventory = new Inventory();
            inventory.setItemid(resultSet.getInt("Itemid"));
            inventory.setItemname(resultSet.getString("Itemname"));
            Category category = categoryController.getCategoryByID(resultSet.getInt("CATEGORYID"));
            inventory.setCategoryid(category);
            Unit unit = unitController.getUnitByID(resultSet.getInt("UNITID"));
            inventory.setUnitid(unit);;
            inventory.setCostPrice(resultSet.getBigDecimal("CostPrice"));
            inventory.setSellPrice(resultSet.getBigDecimal("Sellprice"));
            inventory.setInventoryquantity(resultSet.getInt("INVENTORYQUANTITY"));
            StatuzController statuzController=new StatuzController();
            Statuz statuz =statuzController.getStatuzByID(resultSet.getInt("STATUSID"));
            inventory.setStatusid(statuz);
            listInventory.add(inventory);
        }
//         disConnection();
        return listInventory;
    }

    public Inventory getInventoryByID(int id) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "Select*from INVENTORY where Itemid=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        resultSet = prepareStatement.executeQuery();
        CategoryController categoryController = new CategoryController();
        UnitController unitController = new UnitController();
        if (resultSet.next()) {
            Inventory inventory = new Inventory();
            inventory.setItemid(resultSet.getInt("Itemid"));
            inventory.setItemname(resultSet.getString("Itemname"));
            Category category = categoryController.getCategoryByID(resultSet.getInt("CATEGORYID"));
            inventory.setCategoryid(category);
            Unit unit = unitController.getUnitByID(resultSet.getInt("UNITID"));
            inventory.setUnitid(unit);;
            inventory.setCostPrice(resultSet.getBigDecimal("CostPrice"));
            inventory.setSellPrice(resultSet.getBigDecimal("Sellprice"));
            inventory.setInventoryquantity(resultSet.getInt("INVENTORYQUANTITY"));
            StatuzController statuzController=new StatuzController();
            Statuz statuz =statuzController.getStatuzByID(resultSet.getInt("STATUSID"));
            inventory.setStatusid(statuz);
            return inventory;
        } else {
//            connection.close();
            return null;
        }
    }

    public BigDecimal getInventoryByName(String name) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT * FROM INVENTORY WHERE itemname=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, name);
        resultSet = prepareStatement.executeQuery();

        if (resultSet.next()) {

            BigDecimal sellPrice = resultSet.getBigDecimal("Sellprice");

//            resultSet.close();
//            prepareStatement.close();
//            connection.close();
            return sellPrice;
        } else {
            // Đóng kết nối và trả về null nếu không tìm thấy dữ liệu
//            resultSet.close();
//            prepareStatement.close();
//            connection.close();

            return null;
        }
    }

    public boolean  addNewInventory(Inventory inventory) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "INSERT INTO INVENTORY(Itemname, CATEGORYID,UNITID,CostPrice,SellPrice,INVENTORYQUANTITY,STATUSID) VALUES (?,?,?,?,?,?,?)";
        prepareStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setString(1, inventory.getItemname());
        prepareStatement.setInt(2, inventory.getCategoryid().getCategoryid()); 
        prepareStatement.setInt(3, inventory.getUnitid().getUnitid()); 
        prepareStatement.setBigDecimal(4, inventory.getCostPrice());
        prepareStatement.setBigDecimal(5, inventory.getSellPrice());
        prepareStatement.setInt(6, inventory.getInventoryquantity());
        prepareStatement.setInt(7, inventory.getStatusid().getStatusid());

        int result = prepareStatement.executeUpdate();

        if (result == 1) {
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int itemID = generatedKeys.getInt(1);
                inventory.setItemid(itemID);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean addNewInventoryOne(Inventory newInventory) throws SQLException, ClassNotFoundException {

        String url = "jdbc:sqlserver://localhost;databaseName=SIMS;encrypt=true;trustServerCertificate=true";
        String username = "sa";
        String password = "sa";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection = DriverManager.getConnection(url, username, password);
        String sql = "INSERT INTO Inventory (Itemname,CATEGORYID,UNITID,CostPrice,SellPrice,INVENTORYQUANTITY,STATUZ)"
                + " VALUES(?,?,?,?,?,?,?,?)";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, newInventory.getItemname());
        prepareStatement.setInt(2, newInventory.getCategoryid().getCategoryid());
        prepareStatement.setInt(3, newInventory.getUnitid().getUnitid());
        prepareStatement.setBigDecimal(4, newInventory.getCostPrice());
        prepareStatement.setBigDecimal(5, newInventory.getSellPrice());
        prepareStatement.setInt(6, newInventory.getInventoryquantity());
       prepareStatement.setInt(7, newInventory.getStatusid().getStatusid());

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

   public boolean deleteInventoryByID(int itemId) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
       
        Inventory inventory = getInventoryByID(itemId);
        if (inventory == null) {
            return false;
        } else {
           String sql = " Delete from Inventory where ITEMID=?";
            prepareStatement = connection.prepareCall(sql);
            prepareStatement.setInt(1, itemId);
            
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

    public int getAvailableQuantityForProduct(int itemId) throws ClassNotFoundException {
        int availableQuantity = 0;
        try {
            jdbcConnectDB();

            String sql = "SELECT INVENTORYQUANTITY FROM Inventory WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, itemId);

            // Thực hiện truy vấn
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                availableQuantity = resultSet.getInt("quantity");
            }

            // Đóng kết nối
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableQuantity;
    }

    public int getItemNameByName(String itemsName) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sql = "SELECT Itemid  FROM INVENTORY WHERE Itemname=?";
        prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, itemsName);
        resultSet = prepareStatement.executeQuery();
        RolezController rolezController = new RolezController();

        if (resultSet.next()) {
            int employees = resultSet.getInt("Itemid");

            return employees;
        } else {
            return 0;
        }
    }
    
    public boolean updateInven(Inventory itemid) throws SQLException, ClassNotFoundException {
        jdbcConnectDB();
        String sqlStatement = "UPDATE INVENTORY SET ITEMNAME=?, STATUZ=? WHERE itemid=?";
        prepareStatement = connection.prepareStatement(sqlStatement);
        prepareStatement.setString(1, itemid.getItemname());
        prepareStatement.setInt(2, itemid.getStatusid().getStatusid());
        prepareStatement.setInt(3,itemid.getItemid());
        int result = prepareStatement.executeUpdate();

        if (result == 1) {
//            connection.close();
            return true;

        } else {
//            connection.close();
            return false;
        }
    }
    
    
    
   
}
