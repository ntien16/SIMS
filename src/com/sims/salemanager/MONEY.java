/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.salemanager;

import com.formdev.flatlaf.ui.FlatTableUI;
import com.formdev.flatlaf.ui.FlatTextFieldUI;
import com.sims.controller.BillController;
import com.sims.controller.BillDetailController;
import com.sims.entitynew.BILLDetail;
import com.sims.entitynew.Bill;
import com.sims.entitynew.Customer;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Itemstakeback;
import com.sims.jdbc.JDBCConnect;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class MONEY extends javax.swing.JPanel {

    private DefaultTableModel tableModel;
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
    private BigDecimal totalAmount = BigDecimal.ZERO;

    public MONEY() throws SQLException, ClassNotFoundException {
        customer = new Customer();
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
        createExceptions.getContentPane().setBackground(new Color(0, 0, 0, 0));
        showTransaction();
        showCombobox1();
        jComboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedValue = jComboBox1.getSelectedItem().toString();
                    filterIdTransactionBill(selectedValue);
                    if (selectedValue.equals("All")) {
                        jComboBox1.setSelectedIndex(0);
                        jDateChooserFilter.setDate(null);
                        filterIdTransactionBill("All");
                        filterIdTransactionBill(selectedValue);
                    } else {
                        filterIdTransactionBill(selectedValue);
                    }
                }

            }
        });
        showMoney();
        tableRender();
    }

    public void showTransaction() throws SQLException, ClassNotFoundException {

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        tableModel.setRowCount(0);
        listbill = billController.getAllBill();
        try {
            for (Bill bill : listbill) {
                // Assuming you have a method to retrieve bill details associated with a bill
                List<BILLDetail> billDetails = billDetailController.getBillDetailByIDList(bill.getBillID());

                for (BILLDetail billdetail : billDetails) {
                    String itemname = billdetail.getInventory().getItemname();
                    BigDecimal sellPrice = billdetail.getInventory().getSellPrice();
                    BigDecimal amount = sellPrice.multiply(new BigDecimal(billdetail.getQuantity()));
                    int quantity = billdetail.getQuantity();
                    tableModel.addRow(new Object[]{
                        bill.getBillID(),
                        bill.getCustomerId().getCustomerName(),
                        itemname,
                        bill.getCreatedDate(),
                        quantity,
                        decimalFormat.format(sellPrice),
                        decimalFormat.format(amount)
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tableRender() {
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer());
        table.setOpaque(true);
        table.setBackground(Color.white);
        table.setBorder(new EmptyBorder(0, 0, 0, 0));
        table.setShowHorizontalLines(true);
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                DefaultTableCellRenderer centerRenderer1 = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                if (row % 2 == 0) {
                    component.setBackground(Color.LIGHT_GRAY);
                } else {
                    component.setBackground(Color.WHITE);
                }
//                for (int i = 0; i < table.getColumnCount(); i++) {
//                    if (i == 0) {
//
//                        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
//                        System.out.println(i);
//                    } else if (i == 4 || i == 5 || i == 6) {
//
//                        table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
//                    }
//                }
                return component;

            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        JFrame frame = new JFrame("Remove Table Border Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JScrollPane scrollTable = new JScrollPane();
        scrollTable.setBorder(null);
        scrollTable.setViewport(null);
        renderer.setHorizontalAlignment(HEIGHT);
        table.repaint();
    }

    public void showCombobox1() {

        jComboBox1.removeAllItems();
        jComboBox1.addItem("All");

        for (Bill bill : listbill) {
            jComboBox1.addItem(String.valueOf(bill.getBillID()));
        }

    }

    public void filterIdTransactionBill(String evt) {
        DefaultTableModel table1 = (DefaultTableModel) table.getModel();

        TableRowSorter<DefaultTableModel> tableSort = new TableRowSorter<>(table1);
        table.setRowSorter(tableSort);

        if (evt != null) {
            if (!evt.equals("All")) {
                RowFilter<DefaultTableModel, Object> filter = RowFilter.numberFilter(
                        RowFilter.ComparisonType.EQUAL, Integer.parseInt(evt), 0);
                tableSort.setRowFilter(filter);

            } else {
                tableSort.setRowFilter(null);

            }
        }

        updateTotalAmount(evt);

    }

    private void updateTotalAmount(String selectedBillid) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        totalAmount = BigDecimal.ZERO;

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String amountStr = tableModel.getValueAt(i, 6).toString();
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


    private void showMoney() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        totalAmount = BigDecimal.ZERO;

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.######");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String amountStr = tableModel.getValueAt(i, 6).toString();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        createExceptions = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jTotalAmount = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jDateChooserFilter = new com.toedter.calendar.JDateChooser();

        createExceptions.setModal(true);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("NAME:");

        jTextField1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255)));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("CONTENT:");

        jTextField2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), java.awt.Color.white, new java.awt.Color(255, 255, 255)));

        jButton2.setBackground(new java.awt.Color(51, 255, 0));
        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("SAVE");
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jButton3.setBackground(new java.awt.Color(255, 51, 51));
        jButton3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("CANCEL");
        jButton3.setAutoscrolls(true);
        jButton3.setBorder(null);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addGap(45, 45, 45)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout createExceptionsLayout = new javax.swing.GroupLayout(createExceptions.getContentPane());
        createExceptions.getContentPane().setLayout(createExceptionsLayout);
        createExceptionsLayout.setHorizontalGroup(
            createExceptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        createExceptionsLayout.setVerticalGroup(
            createExceptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("ITEM:");

        jTabbedPane1.setBackground(new java.awt.Color(102, 255, 0));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        table.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BILL ID", "CUSTOMER NAME", "ITEM", "DATE", "QUANTITY", "SELL PRICE", "AMOUNT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(75);
            table.getColumnModel().getColumn(0).setPreferredWidth(75);
            table.getColumnModel().getColumn(0).setMaxWidth(75);
            table.getColumnModel().getColumn(1).setMinWidth(200);
            table.getColumnModel().getColumn(1).setPreferredWidth(200);
            table.getColumnModel().getColumn(1).setMaxWidth(200);
            table.getColumnModel().getColumn(2).setMinWidth(200);
            table.getColumnModel().getColumn(2).setPreferredWidth(200);
            table.getColumnModel().getColumn(2).setMaxWidth(200);
            table.getColumnModel().getColumn(3).setMinWidth(100);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
            table.getColumnModel().getColumn(3).setMaxWidth(100);
            table.getColumnModel().getColumn(4).setMinWidth(100);
            table.getColumnModel().getColumn(4).setPreferredWidth(100);
            table.getColumnModel().getColumn(4).setMaxWidth(100);
            table.getColumnModel().getColumn(5).setMinWidth(150);
            table.getColumnModel().getColumn(5).setPreferredWidth(150);
            table.getColumnModel().getColumn(5).setMaxWidth(150);
            table.getColumnModel().getColumn(6).setMinWidth(200);
            table.getColumnModel().getColumn(6).setPreferredWidth(200);
            table.getColumnModel().getColumn(6).setMaxWidth(200);
        }

        jTabbedPane1.addTab("LIST OF TRANSACTIONS", jScrollPane1);

        jPanel4.setBackground(new java.awt.Color(102, 255, 0));

        jTotalAmount.setBackground(new java.awt.Color(255, 255, 255));
        jTotalAmount.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTotalAmount.setForeground(new java.awt.Color(0, 0, 0));
        jTotalAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTotalAmount.setBorder(null);
        jTotalAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTotalAmountActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TOTAL AMOUNT:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-pay-date-25.png"))); // NOI18N
        jButton1.setText("SEARCH");
        jButton1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(148, 148, 148)
                            .addComponent(jDateChooserFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooserFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(336, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel tableModelBill = (DefaultTableModel) table.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
        Date selectedDate = jDateChooserFilter.getDate();
        String dateString = sDate.format(selectedDate);

        System.out.println(selectedDate);
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(tableModelBill);
        table.setRowSorter(trs);

        Object selectedValueObject = jComboBox1.getSelectedItem();
        if (selectedValueObject != null) {
            String selectedValue = selectedValueObject.toString();
            for (int i = 0; i < listbill.size(); i++) {
                if ("All".equals(selectedValue) || Integer.toString(i).equals(selectedValue)) {
                    jDateChooserFilter.setDate(null);
                } else {
                    jDateChooserFilter.setEnabled(true);
                }
            }
        }
        if (selectedDate != null) {
            RowFilter<DefaultTableModel, Object> dateFilter = RowFilter.regexFilter(dateString.trim(), 3);
            trs.setRowFilter(dateFilter);

            List<Integer> filteredBillIDs = new ArrayList<>();
            for (int i = 0; i < table.getRowCount(); i++) {
                filteredBillIDs.add((Integer) table.getValueAt(i, 0));
            }

            BigDecimal totalAmount = BigDecimal.ZERO;

            for (int i = 0; i < tableModelBill.getRowCount(); i++) {
                String currentBillDateString = tableModelBill.getValueAt(i, 3).toString();
                if (dateString.equals(currentBillDateString)) {
                    String amountStr = tableModelBill.getValueAt(i, 6).toString();
                    try {
                        BigDecimal amount = new BigDecimal(decimalFormat.parse(amountStr).doubleValue());
                        totalAmount = totalAmount.add(amount);
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    } catch (ParseException ex) {
                        Logger.getLogger(MONEY.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            jTotalAmount.setText(decimalFormat.format(totalAmount));
            System.out.println("jTotalAmount+ " + jTotalAmount);
        }

        jComboBox1.setSelectedIndex(-1);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTotalAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTotalAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTotalAmountActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog createExceptions;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooserFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTotalAmount;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
