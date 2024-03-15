/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.salemanager;

import com.sims.controller.BillController;
import com.sims.controller.BillDetailController;
import com.sims.controller.DeleteBillController;
import com.sims.entitynew.BILLDetail;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.sims.entitynew.Bill;
import com.sims.entitynew.Customer;
import com.sims.entitynew.Deletebill;
import com.sims.entitynew.Employees;
import com.sims.jdbc.JDBCConnect;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.List;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import javax.annotation.processing.Filer;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import org.eclipse.persistence.logging.LogCategory;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class BILLJPanel extends javax.swing.JPanel {

    private DefaultTableModel tableModel;
//    private JTable table;
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
    private List<Deletebill> listbillDelete;
    private DeleteBillController deleteBillController;

    public BILLJPanel() throws ClassNotFoundException, SQLException {
        customer = new Customer();
        deleteBillController = new DeleteBillController();
        employees = new Employees();
        listbill = new ArrayList<>();
        jdbcConnect = new JDBCConnect();
        billController = new BillController();
        billDetailController = new BillDetailController();
        try {
            jdbcConnect.jdbcConnectDB();
            billController.jdbcConnectDB();
            listbill = billController.getAllBill();
            listbILLDetail = billDetailController.getAllBillDetail();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        initComponents();
        jComboboxFilterBIll.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedValue = jComboboxFilterBIll.getSelectedItem().toString();
                    if (selectedValue.equals("All")) {
                        jComboboxFilterBIll.setSelectedIndex(0);
                        jDateChooserFilter.setDate(null);
                        filter("All");
                    } else {
                        filter(selectedValue);
                    }
                }
            }
        });
        showBill();
        showBillDetail();
        showlistBillCombobox();
        showDeleteBill();
        statisticBill();
        statisticBillDelete();
    }

    public void showBill() {

        DefaultTableModel tableModel = (DefaultTableModel) jTable3.getModel();
        try {
            for (Bill bill : listbill) {

                tableModel.addRow(new Object[]{
                    bill.getBillID(),
                    bill.getCustomerId().getCustomerName(),
                    bill.getCreatedDate(),
                    bill.getEmployeeid().getEmployeename()

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showBillDetail() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable4.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        try {
            for (BILLDetail billdetail : listbILLDetail) {
                BigDecimal sellPrice = billdetail.getInventory().getSellPrice();
                BigDecimal amount = sellPrice.multiply(new BigDecimal(billdetail.getQuantity()));

                tableModel.addRow(new Object[]{
                    billdetail.getBILLDetailPK().getBillID(),
                    billdetail.getInventory().getItemname(),
                    billdetail.getQuantity(),
                    decimalFormat.format(sellPrice),
                    billdetail.getDiscount(),
                    billdetail.getStatuz(),
                    decimalFormat.format(amount),});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showlistBillCombobox() throws ClassNotFoundException, SQLException {
        jComboboxFilterBIll.removeAllItems();
        jComboboxFilterBIll.addItem("All");
        for (Bill bill : listbill) {
            jComboboxFilterBIll.addItem(String.valueOf(bill.getBillID()));
        }

        String query = jComboboxFilterBIll.getSelectedItem().toString();
        filter(query);

    }

    public void statisticBill() {
        int count=0;

        for (int i = 0; i < listbill.size(); i++) {
            int numberRow = (int) jTable3.getValueAt(i, 0);
            count++;
            System.out.println("count+ "+ count);
            System.out.println("i+ "+ i);
        }

        

        jLabel3.setText(String.valueOf(count));
    }
    public void statisticBillDelete() {
        
        int count=0;

        for (int i = 0; i < listbillDelete.size(); i++) {
            int numberRow = (int) jTable3.getValueAt(i, 0);
            count++;
        }

        

        jLabel5.setText(String.valueOf(count));
    }

    public void filter(String query) {
        DefaultTableModel tableModelBill = (DefaultTableModel) jTable3.getModel();
        DefaultTableModel tableModelBillDetail = (DefaultTableModel) jTable4.getModel();

        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(tableModelBill);
        TableRowSorter<DefaultTableModel> trsBillDetail = new TableRowSorter<>(tableModelBillDetail);
        jTable3.setRowSorter(trs);
        jTable4.setRowSorter(trsBillDetail);

        if (query != null) {
            if (!query.equals("All")) {
                // Lọc theo ID
                RowFilter<DefaultTableModel, Object> filter = RowFilter.numberFilter(
                        RowFilter.ComparisonType.EQUAL, Integer.parseInt(query), 0);
                RowFilter<DefaultTableModel, Object> filterDetail = RowFilter.numberFilter(
                        RowFilter.ComparisonType.EQUAL, Integer.parseInt(query), 0);
                trs.setRowFilter(filter);
                trsBillDetail.setRowFilter(filterDetail);
            } else {

                trs.setRowFilter(null);
                trsBillDetail.setRowFilter(null);
            }
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
        jComboboxFilterBIll = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jDateChooserFilter = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jTable3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CUSTOMER", "DATE", "EMPLOYEE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setMinWidth(50);
            jTable3.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable3.getColumnModel().getColumn(0).setMaxWidth(200);
            jTable3.getColumnModel().getColumn(1).setMinWidth(300);
            jTable3.getColumnModel().getColumn(1).setMaxWidth(300);
            jTable3.getColumnModel().getColumn(2).setMinWidth(250);
            jTable3.getColumnModel().getColumn(2).setMaxWidth(250);
            jTable3.getColumnModel().getColumn(3).setMinWidth(300);
            jTable3.getColumnModel().getColumn(3).setMaxWidth(300);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1031, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("BILL", jPanel4);

        jTable4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "QUANTITY", "Sell Price", "DISCOUNT", "STATUS", "AMOUNT"
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
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1031, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("BILL DETAIL", jPanel5);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
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
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1031, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("DELETE BILL", jPanel6);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Filter ID Number");

        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-pay-date-25.png"))); // NOI18N
        jButton1.setText("SEARCH");
        jButton1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("THE NUMBER OF BILL:");

        jLabel4.setText("THE NUMBER OF BILLDELETE:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1031, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 137, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboboxFilterBIll, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jDateChooserFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboboxFilterBIll)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooserFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(211, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel tableModelBill = (DefaultTableModel) jTable3.getModel();
        DefaultTableModel tableModelBillDetail = (DefaultTableModel) jTable4.getModel();
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
        Date selectedDate = jDateChooserFilter.getDate();
        String dateString = sDate.format(selectedDate);

        System.out.println(selectedDate);
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(tableModelBill);
        TableRowSorter<DefaultTableModel> trsBillDetail = new TableRowSorter<>(tableModelBillDetail);
        jTable3.setRowSorter(trs);
        jTable4.setRowSorter(trsBillDetail);

        Object selectedValueObject = jComboboxFilterBIll.getSelectedItem();
        if (selectedValueObject != null) {
            String selectedValue = selectedValueObject.toString();
            for (int i = 0; i < listbill.size(); i++) {
                if ("All".equals(selectedValue) || Integer.toString(i).equals(selectedValue)) {
                    jDateChooserFilter.setDate(null);
                }
            }
        }
        if (selectedDate != null) {
            RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(dateString, 2);

            trs.setRowFilter(filter);

            List<Integer> billIDs = new ArrayList<>();
            for (int i = 0; i < jTable3.getRowCount(); i++) {
                billIDs.add((Integer) jTable3.getValueAt(i, 0));
            }
            RowFilter<DefaultTableModel, Object> filterDetail = RowFilter.orFilter(billIDs.stream()
                    .map(id -> RowFilter.regexFilter(String.valueOf(id), 0))
                    .collect(Collectors.toList()));
            trsBillDetail.setRowFilter(filterDetail);
            jComboboxFilterBIll.setSelectedIndex(-1);

        }

    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboboxFilterBIll;
    private com.toedter.calendar.JDateChooser jDateChooserFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
