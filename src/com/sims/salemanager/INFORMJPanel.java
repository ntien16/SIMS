/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.salemanager;

import com.sims.controller.BillDetailController;
import com.sims.controller.InventoryController;
import com.sims.entitynew.BILLDetail;
import com.sims.entitynew.Bill;
import com.sims.entitynew.Inventory;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class INFORMJPanel extends javax.swing.JPanel {

    private DefaultCategoryDataset dataset;
    private JFreeChart barchart;
    private CategoryPlot categoryPlot;
    private ChartPanel chartPanel;
    private List<BILLDetail> listbillDetail;
    private BillDetailController billDetailController;
    private Inventory inventory;
    private List<Inventory> listinventory;
    private InventoryController inventoryController;

    public INFORMJPanel() throws SQLException, ClassNotFoundException {
        initComponents();
        inventoryController = new InventoryController();
        listinventory = inventoryController.getAllInventory();
        billDetailController = new BillDetailController();
        listbillDetail = billDetailController.getAllBillDetail();
        billDetailController.setDataToChart(jPanel3);
        showlistBillCombobox();

    }

    public void showChart(JPanel jpnItem) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (BILLDetail billdetail : listbillDetail) {
            BigDecimal sellPrice = billdetail.getInventory().getSellPrice();
            BigDecimal amount = sellPrice.multiply(new BigDecimal(billdetail.getQuantity()));
            dataset.addValue(amount.doubleValue(), "Value", billdetail.getInventory().getItemname());
        }

         barchart = ChartFactory.createBarChart(
                "Name of items are consumed in month",
                "Item Name",
                "VND",
                dataset
//                PlotOrientation.VERTICAL,
//                false,
//                true,
//                false
        );  
        System.out.println("barchart+ "+ barchart);

        CategoryPlot categoryPlot = barchart.getCategoryPlot();
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer=(BarRenderer)categoryPlot.getRenderer();
        ChartPanel chartPanel = new ChartPanel(barchart);
        
        chartPanel.setPreferredSize(new Dimension(jpnItem.getWidth(), 420));
//        ChartFrame frame=new ChartFrame ("Name of items are consumed in month",barchart);
//        frame.setVisible(true);
//        frame.setSize(680,420);
        // Remove any existing components from jPanel2 and add the chartPanel
        jpnItem.removeAll();
        jpnItem.add(chartPanel, BorderLayout.CENTER);
        jpnItem.revalidate(); // Use revalidate() instead of validate()
        jpnItem.setLayout(new CardLayout());
        jpnItem.validate();
        jpnItem.repaint();
        
        
    }

    public void showlistBillCombobox() throws ClassNotFoundException, SQLException {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("All");
        for (Inventory inventory : listinventory) {
            model.addElement(String.valueOf(inventory.getItemname()));
        }

        jComboBox1.setModel(model);

        String query = jComboBox1.getSelectedItem().toString();
        filter(query);

    }

    public void filter(String query) {
//        DefaultTableModel tableModelBill = (DefaultTableModel) jTable3.getModel();
//        DefaultTableModel tableModelBillDetail = (DefaultTableModel) jTable4.getModel();

//        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(tableModelBill);
//        TableRowSorter<DefaultTableModel> trsBillDetail = new TableRowSorter<>(tableModelBillDetail);
//        jTable3.setRowSorter(trs);
//        jTable4.setRowSorter(trsBillDetail);
        if (query != null) {
            if (!query.equals("All")) {
                // Lọc theo ID
                RowFilter<DefaultTableModel, Object> filter = RowFilter.numberFilter(
                        RowFilter.ComparisonType.EQUAL, Integer.parseInt(query), 0);
                RowFilter<DefaultTableModel, Object> filterDetail = RowFilter.numberFilter(
                        RowFilter.ComparisonType.EQUAL, Integer.parseInt(query), 0);
//                trs.setRowFilter(filter);
//                trsBillDetail.setRowFilter(filterDetail);
            } else {

//                trs.setRowFilter(null);
//                trsBillDetail.setRowFilter(null);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ITEM:");

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1015, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 479, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 498, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(274, 274, 274))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addGap(26, 26, 26)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        showChart();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
