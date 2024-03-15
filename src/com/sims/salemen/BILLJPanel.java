/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.salemen;

import com.sims.controller.BillController;
import com.sims.controller.BillDetailController;
import com.sims.controller.DeleteBillController;
import com.sims.controller.ItemTakeBackController;
import com.sims.entitynew.BILLDetail;
import com.sims.entitynew.Bill;
import com.sims.entitynew.Customer;
import com.sims.entitynew.Deletebill;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Itemstakeback;
import com.sims.jdbc.JDBCConnect;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class BILLJPanel extends javax.swing.JPanel {

    private List<Bill> listbill;
    private JDBCConnect jdbcConnect;
    private Statement statement;
    private BillController billController;
    private Employees employees;
    private Customer customer;
    private List<BILLDetail> listbILLDetail;
    private BillDetailController billDetailController;
    private Bill bill;
    private BILLDetail bILLDetail;
    private DeleteBillController deleteBillController;
    private List<Deletebill> listbillDelete;
    private DefaultTableModel tableModel;
    Map<Integer, BigDecimal> billAmountMap = new HashMap<>();

    public BILLJPanel() throws SQLException, ClassNotFoundException {
        billController = new BillController();
        listbill = billController.getAllBill();
        billDetailController = new BillDetailController();
        listbILLDetail = billDetailController.getAllBillDetail();
        deleteBillController = new DeleteBillController();
        listbillDelete = deleteBillController.getAllDeleteBill();
        initComponents();
        showBill();
        showBillDetail();
        showDeleteBill();
    }

    public void showBill() throws SQLException, ClassNotFoundException {
        listbill = billController.getAllBill();
        DefaultTableModel tableModel = (DefaultTableModel) jTable3.getModel();
        tableModel.setRowCount(0);
        try {
            for (Bill bill : listbill) {
                BigDecimal totalAmount = billAmountMap.getOrDefault(bill.getBillID(), BigDecimal.ZERO);
                tableModel.addRow(new Object[]{
                    bill.getBillID(),
                    bill.getCustomerId().getCustomerName(),
                    bill.getCreatedDate(),
                    bill.getEmployeeid().getEmployeename(),
                    totalAmount
                });
            }
            tableModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showBillDetail() throws SQLException, ClassNotFoundException {
        listbILLDetail = billDetailController.getAllBillDetail();
        DefaultTableModel tableModel = (DefaultTableModel) jTable4.getModel();
        tableModel.setRowCount(0);
        try {
            for (BILLDetail billdetail : listbILLDetail) {
                BigDecimal sellPrice = billdetail.getInventory().getSellPrice();
                int billID = billdetail.getBILLDetailPK().getBillID();
                int discount = billdetail.getDiscount();
                BigDecimal discountPercentage = new BigDecimal(discount).divide(new BigDecimal(100));

                BigDecimal amount;

                if (discount > 0) {
                    amount = sellPrice.multiply(new BigDecimal(billdetail.getQuantity())).multiply(BigDecimal.ONE.subtract(discountPercentage));
                } else {
                    amount = sellPrice.multiply(new BigDecimal(billdetail.getQuantity()));
                }

                DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
                if (billAmountMap.containsKey(billID)) {

                    BigDecimal currentAmount = billAmountMap.get(billID);
                    billAmountMap.put(billID, currentAmount.add(amount));
                } else {

                    billAmountMap.put(billID, amount);
                }
                tableModel.addRow(new Object[]{
                    billdetail.getBILLDetailPK().getBillID(),
                    billdetail.getInventory().getItemname(),
                    billdetail.getQuantity(),
                    decimalFormat.format(sellPrice),
                    billdetail.getDiscount(),
                    billdetail.getStatuz(),
                    decimalFormat.format(amount)
                });
            }
            tableModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDeleteBill() throws SQLException, ClassNotFoundException {
        listbillDelete = deleteBillController.getAllDeleteBill();
        DefaultTableModel tableModel = (DefaultTableModel) jTable5.getModel();
        tableModel.setRowCount(0);
        try {
            for (Deletebill deleteBill : listbillDelete) {

                tableModel.addRow(new Object[]{
                    deleteBill.getDeletebillid(),
                    deleteBill.getBillid(),
                    deleteBill.getDeletedate(),
                    deleteBill.getStatuz()

                });
            }
            tableModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CUSTOMER", "DATE", "EMPLOYEE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 997, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 120, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("BILL", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "QUANTITY", "Sellprice", "DISCOUNT", "STATUS", "AMOUNT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 997, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("BILL DETAIL", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "BILL ID", "DATE", "STATUS"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 997, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 120, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("DELETE BILL", jPanel6);

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/outline_delete_forever_white_24dp.png"))); // NOI18N
        jButton1.setText("DELETE BILL");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(872, 872, 872)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 997, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(145, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        DefaultTableModel tableModel = (DefaultTableModel) jTable3.getModel();
        DefaultTableModel tableModelDetail = (DefaultTableModel) jTable4.getModel();
        DefaultTableModel tableModelDelete = (DefaultTableModel) jTable5.getModel();

        int[] selectedRows = jTable3.getSelectedRows(); 

        if (selectedRows.length > 0) { 
            int choice = JOptionPane.showConfirmDialog(jButton1, "Are you sure you want to delete selected bills?", "Delete Bills", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int rowIndex = selectedRows[i];
                    int billID = (int) tableModel.getValueAt(rowIndex, 0);

                    try {
                        ItemTakeBackController itemTakeBackController = new ItemTakeBackController();

                        itemTakeBackController.deleteAllITemTBByBillID(billID);

                        boolean deleteSuccess = billController.deleteBillByID(billID);

                        if (deleteSuccess) {
                            JOptionPane.showMessageDialog(jButton1, "Delete is successful");
                            listbill.remove(rowIndex);
                            tableModel.removeRow(rowIndex);
                            for (Deletebill deleteBill : listbillDelete) {
                                tableModelDelete.addRow(new Object[]{
                                    deleteBill.getDeletebillid(),
                                    deleteBill.getBillid(),
                                    deleteBill.getDeletedate(),
                                    deleteBill.getStatuz()
                                });
                            }
                            showBill();
                            showBillDetail();
                            showDeleteBill();
                        } else {
                            JOptionPane.showMessageDialog(jButton1, "Delete is failed");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(BILLJPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(BILLJPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(jButton1, "No bills selected");
        }
    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    // End of variables declaration//GEN-END:variables
}
