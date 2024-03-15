/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.salemen;

import com.formdev.flatlaf.ui.FlatTextFieldUI;
import com.sims.controller.CategoryController;
import com.sims.controller.InventoryController;
import com.sims.controller.UnitController;
import com.sims.entitynew.Category;
import com.sims.entitynew.Inventory;
import com.sims.entitynew.Unit;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ProductJPanel extends javax.swing.JPanel {

    private Category category;
    private List<Category> listcategory;
//    private List<Unit> listUnit;
    private List<Inventory> listInventorys;
    private CategoryController categoryController;
    private UnitController unitController;
    private InventoryController inventoryController;

    public ProductJPanel() throws SQLException, ClassNotFoundException {
        initComponents();
       

        categoryController = new CategoryController();
        listcategory = categoryController.getAllCategory();
        unitController = new UnitController();
        inventoryController = new InventoryController();
        listInventorys = inventoryController.getAllInventory();
        showProduct();
        comboBoxRender();
        jComboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedValue = jComboBox1.getSelectedItem().toString();
                    filter(selectedValue);
                    jFind.setText("");
                }
            }

        });
        
    }

    public void comboBoxRender() {
        jComboBox1.removeAll();
        jComboBox1.addItem("All");
        for (Category category : listcategory) {
            jComboBox1.addItem(String.valueOf(category.getCategoryname()));
        }

    }

    public void filter(String query) {
        DefaultTableModel tableModelProduct = (DefaultTableModel) table1.getModel();
        if (query != null) {
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(tableModelProduct);
            table1.setRowSorter(trs);
            if (!query.equals("All")) {
                RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(query, 2);
                trs.setRowFilter(filter);
            } else {
                trs.setRowFilter(null); // return to  "All"
            }
        }
    }

    public void showProduct() {
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");

        for (Inventory inventory : listInventorys) {
            String formattedCostPrice = decimalFormat.format(inventory.getCostPrice());
            String formattedSellPrice = decimalFormat.format(inventory.getSellPrice());
            tableModel.addRow(new Object[]{
                inventory.getItemid(),
                inventory.getItemname(),
                inventory.getCategoryid().getCategoryname(),
                inventory.getUnitid().getUnitname(),
                inventory.getInventoryquantity(),
                formattedSellPrice,
                inventory.getStatusid().getStatusname()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jFind = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jFind.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFindKeyReleased(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jComboBox1.setBorder(null);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME ", "CATEGORY", "UNIT", "QUANTITY", "SELL PRICE", "STATUS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1043, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jFind, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(114, 114, 114)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFind, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jFindKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFindKeyReleased
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        String filter=jFind.getText();
        filter=filter.toUpperCase();
        TableRowSorter<DefaultTableModel> tableSorter = new TableRowSorter<>(tableModel);
        table1.setRowSorter(tableSorter);
        tableSorter.setRowFilter(RowFilter.regexFilter(filter));
        jFind.getText();
        jComboBox1.setSelectedItem("All");
    }//GEN-LAST:event_jFindKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JTextField jFind;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table1;
    // End of variables declaration//GEN-END:variables
}
