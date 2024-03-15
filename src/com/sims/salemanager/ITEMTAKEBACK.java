/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.salemanager;

import com.sims.controller.ItemTakeBackController;
import com.sims.controller.ItemTakeBackDetailController;
import com.sims.entitynew.Bill;
import com.sims.entitynew.Exceptions;
import com.sims.entitynew.Itemstakeback;
import com.sims.entitynew.Itemstakebackdetail;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ITEMTAKEBACK extends javax.swing.JPanel {

    private Itemstakeback itemTBK;
    private ItemTakeBackController itemTakeBackController;
    private List<Itemstakeback> listitemTBK;
    private JTable table;
    private List<Itemstakebackdetail> listItemstakebackdetails;
    private ItemTakeBackDetailController inBackDetailController;
    private BigDecimal totalAmount;

    public ITEMTAKEBACK() throws ClassNotFoundException, SQLException {
        itemTakeBackController = new ItemTakeBackController();
        inBackDetailController = new ItemTakeBackDetailController();
        itemTakeBackController.jdbcConnectDB();
        listitemTBK = itemTakeBackController.getAllItemTakeBack();
        listItemstakebackdetails = inBackDetailController.getAllItemTakeBackDetailsByItemTakeBackID();
        initComponents();
        jComboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedValue = jComboBox1.getSelectedItem().toString();
                    filter(selectedValue);
                    if (selectedValue.equals("All")) {
                        jComboBox1.setSelectedIndex(0);
                        jDateChooser1.setDate(null);
                        filter("All");
                    } else {
                        filter(selectedValue);
                    }
                }

            }
        });
        showCombobox1();
        showItem();
        statisticItemTakeBack();
        showItemDetail();
        showMoney();
    }

    public void showItem() {
        DefaultTableModel tableModel = (DefaultTableModel) jTableItemTBK.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        for (Itemstakeback itemstakeback : listitemTBK) {
//            BigDecimal sellPrice = itemstakeback.getItemid().getSellPrice();
//            BigDecimal amount = sellPrice.multiply(new BigDecimal(itemstakeback.getQuantity()));

            tableModel.addRow(new Object[]{
                itemstakeback.getItemtkid(),
                itemstakeback.getBillid().getBillID(),
                itemstakeback.getCustomerid().getCustomerName(),
                itemstakeback.getCanceldate(),});
        }

    }

    private void showMoney() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1Detail.getModel();
        totalAmount = BigDecimal.ZERO;

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.######");

        for (int i = 0; i < jTable1Detail.getRowCount(); i++) {
            String amountStr = jTable1Detail.getValueAt(i, 4).toString();
            try {
                BigDecimal amount = new BigDecimal(decimalFormat.parse(amountStr).doubleValue());
                totalAmount = totalAmount.add(amount);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        jTotalAmount.setText(decimalFormat.format(totalAmount));

        jTotalAmount.disable();
    }

    private void updateTotalAmount(String selectedBillid) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1Detail.getModel();
        totalAmount = BigDecimal.ZERO;

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String amountStr = tableModel.getValueAt(i, 4).toString();
            String billIdStr = tableModel.getValueAt(i, 0).toString();
            try {
                BigDecimal amount = new BigDecimal(decimalFormat.parse(amountStr).doubleValue());
                if (selectedBillid.equals("All") || selectedBillid.equals(billIdStr)) {
                    totalAmount = totalAmount.add(amount);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        jTotalAmount.setText(decimalFormat.format(totalAmount));
        jTotalAmount.disable();
    }

    public void updateTotalAmountBigDecimal( String billID) {
        DefaultTableModel tableModelDetail = (DefaultTableModel) jTable1Detail.getModel();
        DefaultTableModel tableModelItem = (DefaultTableModel) jTableItemTBK.getModel();
        totalAmount = BigDecimal.ZERO;
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");

        
        int rowCountItem = tableModelItem.getRowCount();
        int rowCountDetail = tableModelDetail.getRowCount();

        for (int i = 0; i < rowCountDetail; i++) {
            
            String billID1 = tableModelDetail.getValueAt(i, 0).toString();
            String amountStr = tableModelDetail.getValueAt(i, 4).toString();

            try {
                BigDecimal amount = new BigDecimal(decimalFormat.parse(amountStr).doubleValue());

                if (billID.equals(billID1)) {
                    totalAmount = totalAmount.add(amount);
                    System.out.println("billID1+ "+billID1);
                    System.out.println("totalAmount1111+ "+totalAmount);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        jTotalAmount.setText(decimalFormat.format(totalAmount));
        jTotalAmount.setEnabled(false);
    }

    public void showItemDetail() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1Detail.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        for (Itemstakebackdetail itemstakebackDetail : listItemstakebackdetails) {
            BigDecimal sellPrice = itemstakebackDetail.getInventory().getSellPrice();
            BigDecimal amount = sellPrice.multiply(new BigDecimal(itemstakebackDetail.getQuantity()));

            tableModel.addRow(new Object[]{
                itemstakebackDetail.getItemstakeback().getItemtkid(),
                itemstakebackDetail.getInventory().getItemname(),
                decimalFormat.format(sellPrice),
                itemstakebackDetail.getQuantity(),
                decimalFormat.format(amount),
                itemstakebackDetail.getStatuz()

            });
        }

    }

    public void showCombobox1() {

        jComboBox1.removeAllItems();
        jComboBox1.addItem("All");
        for (Itemstakeback itemstakeback : listitemTBK) {
            jComboBox1.addItem(String.valueOf(itemstakeback.getItemtkid()));
        }
        String query = jComboBox1.getSelectedItem().toString();
        filter(query);

    }

    public void filter(String evt) {
        DefaultTableModel table1 = (DefaultTableModel) jTableItemTBK.getModel();
        DefaultTableModel tableDetail = (DefaultTableModel) jTable1Detail.getModel();
        if (evt != null) {
            TableRowSorter<DefaultTableModel> tableSort = new TableRowSorter<>(table1);
            jTableItemTBK.setRowSorter(tableSort);

            TableRowSorter<DefaultTableModel> tableDetailSort = new TableRowSorter<>(tableDetail);
            jTable1Detail.setRowSorter(tableDetailSort);

            if (!evt.equals("All")) {
                RowFilter<DefaultTableModel, Object> filter = RowFilter.numberFilter(
                        RowFilter.ComparisonType.EQUAL, Integer.parseInt(evt), 0);
                tableSort.setRowFilter(filter);

                RowFilter<DefaultTableModel, Object> filterDetail = RowFilter.numberFilter(
                        RowFilter.ComparisonType.EQUAL, Integer.parseInt(evt), 0);
                tableDetailSort.setRowFilter(filterDetail);
            } else {
                tableSort.setRowFilter(null);
                tableDetailSort.setRowFilter(null);
            }

        }
        updateTotalAmount(evt);
    }

    public void statisticItemTakeBack() {
        int count = 0;

        for (int i = 0; i < listitemTBK.size(); i++) {
            int numberRow = (int) jTableItemTBK.getValueAt(i, 0);
            count++;
            System.out.println("count+ " + count);
            System.out.println("i+ " + i);
        }

        jLabel4.setText(String.valueOf(count));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogITTB = new javax.swing.JDialog();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableItemTBK = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1Detail = new javax.swing.JTable();
        canvas1 = new java.awt.Canvas();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTotalAmount = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        jTextField3.setBackground(new java.awt.Color(255, 255, 255));
        jTextField3.setForeground(new java.awt.Color(0, 0, 0));
        jTextField3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0), new java.awt.Color(255, 255, 255)));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("FILL ID BILL:");

        jButton4.setBackground(new java.awt.Color(51, 255, 51));
        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-check-all-20.png"))); // NOI18N
        jButton4.setText("ACCEPT");

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 0, 0));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-cancel-20.png"))); // NOI18N
        jButton5.setText("CANCEL");

        javax.swing.GroupLayout jDialogITTBLayout = new javax.swing.GroupLayout(jDialogITTB.getContentPane());
        jDialogITTB.getContentPane().setLayout(jDialogITTBLayout);
        jDialogITTBLayout.setHorizontalGroup(
            jDialogITTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogITTBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialogITTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jDialogITTBLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                    .addGroup(jDialogITTBLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );
        jDialogITTBLayout.setVerticalGroup(
            jDialogITTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogITTBLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jDialogITTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jDialogITTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTableItemTBK.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTableItemTBK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ITEMTKID", "BILLID", "CUSTOMER", "DATE"
            }
        ));
        jScrollPane2.setViewportView(jTableItemTBK);
        if (jTableItemTBK.getColumnModel().getColumnCount() > 0) {
            jTableItemTBK.getColumnModel().getColumn(0).setMinWidth(100);
            jTableItemTBK.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTableItemTBK.getColumnModel().getColumn(0).setMaxWidth(150);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1045, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("LIST ITEM RETURN", jPanel2);

        jTable1Detail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1Detail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ITEMTKID", "ITEM", "SELL PRICE", "QUANTITY", "AMOUNT", "STATUS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1Detail);

        jTabbedPane1.addTab("LIST ITEM RETURN DETAIL", jScrollPane1);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ID NUMBER FILTER");

        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Date Filter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("THE NUMBER OF ITEMTK:");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jPanel4.setBackground(new java.awt.Color(255, 51, 0));

        jTotalAmount.setBackground(new java.awt.Color(255, 255, 255));
        jTotalAmount.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTotalAmount.setForeground(new java.awt.Color(0, 0, 0));
        jTotalAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTotalAmount.setBorder(null);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("TOTAL REFUND:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(72, 72, 72)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1045, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 13, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(367, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(32, 32, 32))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel tableModelBill = (DefaultTableModel) jTableItemTBK.getModel();
        DefaultTableModel tableModelExcDetail = (DefaultTableModel) jTable1Detail.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
        Date selectedDate = jDateChooser1.getDate();
        String dateString = sDate.format(selectedDate);

        System.out.println(selectedDate);
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(tableModelBill);
        jTableItemTBK.setRowSorter(trs);
        TableRowSorter<DefaultTableModel> trsDetail = new TableRowSorter<>(tableModelExcDetail);
        jTable1Detail.setRowSorter(trsDetail);

        // Làm sạch totalAmount trước khi tính toán lại
        totalAmount = BigDecimal.ZERO;

        Object selectedValueObject = jComboBox1.getSelectedItem();
        if (selectedValueObject != null) {
            String selectedValue = selectedValueObject.toString();
            for (int i = 0; i < listitemTBK.size(); i++) {
                if ("All".equals(selectedValue) || Integer.toString(i).equals(selectedValue)) {
                    jDateChooser1.setDate(null);
                }
            }
        }

        if (selectedDate != null) {
            RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(dateString.trim(), 3);
            trs.setRowFilter(filter);

            List<Integer> itemList = new ArrayList<>();
            for (int i = 0; i < jTableItemTBK.getRowCount(); i++) {
                itemList.add((Integer) jTableItemTBK.getValueAt(i, 0));
            }

            RowFilter<DefaultTableModel, Object> filterDetail = RowFilter.orFilter(itemList.stream()
                    .map(id -> RowFilter.regexFilter(String.valueOf(id), 0))
                    .collect(Collectors.toList()));
            trsDetail.setRowFilter(filterDetail);

            for (int i = 0; i < tableModelBill.getRowCount(); i++) {
                String currentBillDateString = tableModelBill.getValueAt(i, 3).toString();
                if (dateString.equals(currentBillDateString)) {
                    String amountStr = tableModelBill.getValueAt(i, 0).toString();
                    updateTotalAmountBigDecimal( amountStr);
                    System.out.println("amountStr+ "+amountStr);
                }
            }

//        jTotalAmount.setText(decimalFormat.format(totalAmount));
            System.out.println("jTotalAmount+ " + totalAmount);
        }
        jComboBox1.setSelectedIndex(-1);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Canvas canvas1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JDialog jDialogITTB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1Detail;
    private javax.swing.JTable jTableItemTBK;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTotalAmount;
    // End of variables declaration//GEN-END:variables
}
