/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.salemen;

import com.sims.controller.BillController;
import com.sims.controller.BillDetailController;
import com.sims.controller.CategoryController;
import com.sims.controller.CustomerController;
import com.sims.controller.EmployeeController;
import com.sims.controller.InventoryController;
import com.sims.entitynew.BILLDetail;
//import static com.sims.entitynew.BILLDetail_.discount;
import com.sims.entitynew.Category;
import com.sims.entitynew.Customer;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Inventory;

//import static com.sims.entitynew.Inventory_.sellPrice;
import static com.sun.java.accessibility.util.AWTEventMonitor.addActionListener;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class SELLING extends javax.swing.JPanel {

    private InventoryController inventoryController;
    private List<Inventory> listInventory;
    private JSpinner quantitySpinner;
    private List<JSpinner> quantitySpinners = new ArrayList<>();
    private BigDecimal totalAmount;
    private Category category;
    private List<Category> listCategory;
    private CategoryController categoryController;
    private String usernameEmployee;
    private String passwordEmployee;
    private EmployeeController employeeController;
    private Employees employees;
    private List<Employees> listEmployees;

    public SELLING() {
        initComponents();

    }

    public SELLING(String username, String password) throws SQLException, ClassNotFoundException {
        initComponents();
        this.usernameEmployee = username;
        this.passwordEmployee = password;
        employeeController = new EmployeeController();
        categoryController = new CategoryController();
        listCategory = categoryController.getAllCategory();
        inventoryController = new InventoryController();
        listInventory = inventoryController.getAllInventory();
        showProduct();
        showListCombobox1();
        jComboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedValue = jComboBox1.getSelectedItem().toString();
                    filter(selectedValue);
                    jTextField1.setText("");
                }
            }

        });
        Employees loggedInEmployee = employeeController.getEmployeeByEmployeeNameand(this.usernameEmployee, this.passwordEmployee);
        if (loggedInEmployee != null) {

            loadEmployeeProfile(loggedInEmployee.getEmployeename(), loggedInEmployee.getPassword());
        }
        System.out.println("password asdasd: " + this.passwordEmployee);
        System.out.println("username asdasd: " + this.usernameEmployee);
        showCustomer();

    }

    public void showProduct() throws SQLException, ClassNotFoundException {
        try {

            listInventory = inventoryController.getAllInventory();

            DefaultTableModel tableModel = (DefaultTableModel) jTableProduct.getModel();
            tableModel.setRowCount(0);
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");

            for (Inventory inventory : listInventory) {
                String sellPriceFormat = decimalFormat.format(inventory.getSellPrice());
                String quantityformartString = decimalFormat.format(inventory.getInventoryquantity());
                BigDecimal sellPrice = inventory.getSellPrice();
                BigDecimal amount = sellPrice.multiply(new BigDecimal(inventory.getInventoryquantity()));

                tableModel.addRow(new Object[]{
                    inventory.getItemid(),
                    inventory.getItemname(),
                    inventory.getCategoryid().getCategoryname(),
                    inventory.getUnitid().getUnitname(),
                    sellPriceFormat,
                    quantityformartString,
                    decimalFormat.format(amount)
                });
            }

            tableModel.fireTableDataChanged();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    private int getCustomerIdByName(String customerName) throws SQLException, ClassNotFoundException {
        CustomerController customerController = new CustomerController();
        return customerController.getCustomerIDByName(customerName);
    }

    public int getEmployeeIdByName(String employeeName) throws SQLException, ClassNotFoundException {
        EmployeeController employeeController = new EmployeeController();
        return employeeController.getEmployeeByEmployeeName(employeeName);
    }

    private int insertBill(int customerId, int employeeId) throws SQLException, ClassNotFoundException {
        BillController billController = new BillController();
        int insertbill = billController.addNewBill2Parameter(customerId, employeeId);
        return insertbill;
    }

    public boolean insertBillDetail(int billid, int itemID, int quantity, double discount, String complete) throws SQLException, ClassNotFoundException {
        BillDetailController billDetailController = new BillDetailController();
        boolean insertBillDetail = billDetailController.addNewBillDetailFiveParameter(billid, itemID, quantity, discount, complete);
        return insertBillDetail;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablePurchase = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldEmployee1 = new javax.swing.JTextField();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableProduct = new javax.swing.JTable();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 255), new java.awt.Color(255, 255, 255)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(0, 0, 0));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(0, 255, 255)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(51, 255, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 255, 51));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-bill-35.png"))); // NOI18N
        jLabel1.setText("TOTAL:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButton1.setBackground(new java.awt.Color(0, 255, 153));
        jButton1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-mobile-shop-coins-30.png"))); // NOI18N
        jButton1.setText("PURCHASE");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTabbedPane2.setBackground(new java.awt.Color(204, 204, 204));
        jTabbedPane2.setForeground(new java.awt.Color(0, 0, 0));
        jTabbedPane2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jTablePurchase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "CATEGORY", "UNIT", "SELL PRICE", "QUANTITY", "Discount", "AMOUNT"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTablePurchase);

        jTabbedPane2.addTab("LIST OF ORDER     ", jScrollPane2);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("SEARCH");

        jButton3.setBackground(new java.awt.Color(0, 255, 102));
        jButton3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-purchase-30.png"))); // NOI18N
        jButton3.setText("ADD");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 153, 0));
        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-cancel-35.png"))); // NOI18N
        jButton4.setText("CANCEL");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Category-Wise Filter");

        jTextFieldEmployee1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEmployee1ActionPerformed(evt);
            }
        });

        jTabbedPane3.setBackground(new java.awt.Color(204, 204, 204));
        jTabbedPane3.setForeground(new java.awt.Color(0, 0, 0));
        jTabbedPane3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jTableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "CATEGORY", "UNIT", "SELL PRICE", "QUANTITY", "AMOUNT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableProduct);

        jTabbedPane3.addTab("LIST OF PRODUCT", jScrollPane3);

        jComboBox2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("NAME OF SALE PERSON");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("CUSTOMER:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(689, 689, 689)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(19, 19, 19)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addGap(24, 24, 24)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(65, 65, 65)
                                    .addComponent(jTextFieldEmployee1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3)
                            .addGap(18, 18, 18)
                            .addComponent(jButton4)
                            .addGap(8, 8, 8)))
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1037, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 1480, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldEmployee1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(284, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        DefaultTableModel tableModel = (DefaultTableModel) jTableProduct.getModel();
        String filter=jTextField1.getText();
        filter=filter.toUpperCase();
        TableRowSorter<DefaultTableModel> tableSorter = new TableRowSorter<>(tableModel);
        jTableProduct.setRowSorter(tableSorter);
        tableSorter.setRowFilter(RowFilter.regexFilter(filter));
        jComboBox1.setSelectedItem("All");

    }//GEN-LAST:event_jTextField1KeyReleased

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        int[] selectedRows = jTableProduct.getSelectedRows();
        if (selectedRows.length > 0) {
            DefaultTableModel productTableModel = (DefaultTableModel) jTableProduct.getModel();
            DefaultTableModel purchaseTableModel = (DefaultTableModel) jTablePurchase.getModel();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
            quantitySpinners.add(quantitySpinner);
            for (int i = 0; i < selectedRows.length; i++) {
                int rowIndex = selectedRows[i];
                if (rowIndex >= 0 && rowIndex < productTableModel.getRowCount()) {
                    Object[] rowData = new Object[8];
                    rowData[0] = productTableModel.getValueAt(rowIndex, 0);
                    rowData[1] = productTableModel.getValueAt(rowIndex, 1);
                    rowData[2] = productTableModel.getValueAt(rowIndex, 2);
                    rowData[3] = productTableModel.getValueAt(rowIndex, 3);
                    rowData[4] = productTableModel.getValueAt(rowIndex, 4);

                    rowData[5] = 1;
                    String sellpriceString=productTableModel.getValueAt(rowIndex, 4).toString();
                    BigDecimal sellPrice;
                    try {
                        sellPrice = new BigDecimal(decimalFormat.parse(sellpriceString).toString());
                        rowData[6] = 0;

                    int quantity = (int) rowData[5];
                    int discount = (int) rowData[6];

                    BigDecimal amount;
                    if (discount > 0) {

                        BigDecimal discountPercentage = new BigDecimal(discount).divide(new BigDecimal(100));
                        amount = sellPrice.multiply(new BigDecimal(quantity)).multiply(BigDecimal.ONE.subtract(discountPercentage));

                    } else {

                        amount = sellPrice.multiply(new BigDecimal(quantity));

                    }

                    rowData[7] = amount;
                    } catch (ParseException ex) {
                        Logger.getLogger(SELLING.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    

                    boolean productExists = false;
                    int rowCount = purchaseTableModel.getRowCount();
                    for (int j = 0; j < rowCount; j++) {
                        if (rowData[0].equals(purchaseTableModel.getValueAt(j, 0))) {
                            productExists = true;
                            break;
                        }
                    }
                    if (!productExists) {
                        int row = purchaseTableModel.getRowCount();
                        purchaseTableModel.addRow(rowData);

                    }

                }
                jTablePurchase.getModel().addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
                        if (e.getType() == TableModelEvent.UPDATE) {
                            int row = e.getFirstRow();
                            int col = e.getColumn();

                            if (col == 5) {
                                DefaultTableModel productTableModel = (DefaultTableModel) jTableProduct.getModel();
                                DefaultTableModel purchaseTableModel = (DefaultTableModel) jTablePurchase.getModel();

                                int productId = (int) purchaseTableModel.getValueAt(row, 0);
                                String purchaseQuantityString = purchaseTableModel.getValueAt(row, 5).toString();
                                BigDecimal purchaseQuantity = BigDecimal.ZERO;

                                try {
                                    purchaseQuantity = new BigDecimal(decimalFormat.parse(purchaseQuantityString).doubleValue());
                                } catch (ParseException ex) {

                                    ex.printStackTrace();
                                    return;
                                }

                                BigDecimal productQuantity = BigDecimal.ZERO;

                                for (int i = 0; i < productTableModel.getRowCount(); i++) {
                                    int productIdInProductTable = (int) productTableModel.getValueAt(i, 0);
                                    if (productId == productIdInProductTable) {
                                        String productQuantityStr = productTableModel.getValueAt(i, 5).toString();
                                        try {
                                            productQuantity = new BigDecimal(decimalFormat.parse(productQuantityStr).doubleValue());
                                        } catch (ParseException ex) {

                                            ex.printStackTrace();
                                            return;
                                        }
                                        break;
                                    }
                                }

                                if (purchaseQuantity.compareTo(productQuantity) > 0) {
                                    JOptionPane.showMessageDialog(null, "Not enough product in inventory");
                                     purchaseQuantity = productQuantity;
                                }
                            }
                        }
                    }
                });
                jTablePurchase.getModel().addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        if (e.getColumn() == 7) {
                            showMoney();
                        }
                        try {
                            jTablePurchaseChange(e);
                        } catch (ParseException ex) {
                            Logger.getLogger(SELLING.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                );

            }

        }


    }//GEN-LAST:event_jButton3MouseClicked

    private void jTablePurchaseChange(TableModelEvent evt) throws ParseException {
        int row = evt.getFirstRow();
        int column = evt.getColumn();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");

        if (column == 5 || column == 6) {

            int quantity = (int) jTablePurchase.getValueAt(row, 5);
            int discount = (int) jTablePurchase.getValueAt(row, 6);

            BigDecimal sellPrice = new BigDecimal(decimalFormat.parse(jTablePurchase.getValueAt(row, 4).toString()).doubleValue());
            BigDecimal discountPercentage = (discount > 1) ? new BigDecimal(discount).divide(new BigDecimal(100)) : new BigDecimal(discount);
            BigDecimal amount = sellPrice.multiply(new BigDecimal(quantity)).multiply(BigDecimal.ONE.subtract(discountPercentage));

            jTablePurchase.setValueAt(decimalFormat.format(amount), row, 7);

            jTablePurchase.repaint();
        }
    }

    private int findProductRowIndex(DefaultTableModel productTableModel, int itemId) {
        for (int row = 0; row < productTableModel.getRowCount(); row++) {
            int productId = Integer.parseInt(productTableModel.getValueAt(row, 0).toString());
            if (itemId == productId) {
                return row;
            }
        }
        return -1; // T
    }

    public void showListCombobox1() {
        jComboBox1.removeAll();
        jComboBox1.addItem("All");
        for (Category category : listCategory) {
            jComboBox1.addItem(String.valueOf(category.getCategoryname()));
        }
    }

    public void filter(String query) {
        DefaultTableModel tableModelProduct = (DefaultTableModel) jTableProduct.getModel();
        if (query != null) {
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(tableModelProduct);
            jTableProduct.setRowSorter(trs);
            if (!query.equals("All")) {
                RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(query, 2);
                trs.setRowFilter(filter);
            } else {
                trs.setRowFilter(null); // return to  "All"
            }
        }
    }

    public void loadEmployeeProfile(String employeename, String password) {
        try {
            Employees employee = employeeController.getEmployeeByEmployeeNameand(employeename, password);
            if (employee != null) {
                jTextFieldEmployee1.setText(employee.getEmployeename());
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        jTextFieldEmployee1.disable();
    }

    public void showCustomer() throws SQLException, ClassNotFoundException {
        CustomerController customerController = new CustomerController();
        List<Customer> listCustomer = customerController.getAllCustomer();
        jComboBox2.removeAll();
        jComboBox2.addItem("All");
        for (Customer customer : listCustomer) {
            jComboBox2.addItem(String.valueOf(customer.getCustomerName()));
        }
    }

    public void showMoney() {
        DefaultTableModel tableModel = (DefaultTableModel) jTablePurchase.getModel();
        totalAmount = BigDecimal.ZERO;
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String amountStr = tableModel.getValueAt(i, 7).toString();

            try {
                BigDecimal amount = new BigDecimal(decimalFormat.parse(amountStr).doubleValue());
                totalAmount = totalAmount.add(amount);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        jTextField2.setText(decimalFormat.format(totalAmount));
        jTextField2.setEnabled(false);
    }

    public BigDecimal calculateAmount(int quantity, BigDecimal sellPrice, int discount) {
        BigDecimal discountPercentage = new BigDecimal(discount).divide(new BigDecimal(100));
        BigDecimal amount = sellPrice.multiply(new BigDecimal(quantity)).multiply(BigDecimal.ONE.subtract(discountPercentage));
        return amount;
    }

    public String formatAmount(BigDecimal amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        return decimalFormat.format(amount);
    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // Get CustomerId from jComboBox2 (Customername)
            String selectedCustomerName = jComboBox2.getSelectedItem().toString();
            int customerId = getCustomerIdByName(selectedCustomerName);
            if (customerId == -1) {
                JOptionPane.showMessageDialog(jButton1, "Please Choose customer");
                return;
            }

            // Get Employeeid from  jTextFieldEmployee1 (employeename)
            String employeeName = jTextFieldEmployee1.getText();
            int employeeId = getEmployeeIdByName(employeeName);

            // Insert new bill
            int billId = insertBill(customerId, employeeId);
            System.out.println("billid + " + billId);
            DefaultTableModel purchaseTableModel = (DefaultTableModel) jTablePurchase.getModel();

            // Iterator in jTablePurchase
            for (int i = 0; i < purchaseTableModel.getRowCount(); i++) {
                int itemId = Integer.parseInt(purchaseTableModel.getValueAt(i, 0).toString());
                int quantity = Integer.parseInt(purchaseTableModel.getValueAt(i, 5).toString());
                System.out.println("quantity+ " + quantity);
                double discount = Double.parseDouble(purchaseTableModel.getValueAt(i, 6).toString());

                // Insert Data into billdetail
                boolean result = insertBillDetail(billId, itemId, quantity, discount, "complete");
            }
            purchaseTableModel.setRowCount(0);
            JOptionPane.showMessageDialog(jButton1, "Purchase is successfull");
            showProduct();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot add new Bill");
            JOptionPane.showMessageDialog(jButton1, "Purchase is failed");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SELLING.class
                    .getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        DefaultTableModel purchaseTableModel = (DefaultTableModel) jTablePurchase.getModel();
        int rowIndex = jTablePurchase.getSelectedRow();
        if (rowIndex >= 0) {
            purchaseTableModel.removeRow(rowIndex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextFieldEmployee1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEmployee1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmployee1ActionPerformed

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
    }

    public String getPasswordEmployee() {
        return passwordEmployee;
    }

    public void setPasswordEmployee(String passwordEmployee) {
        this.passwordEmployee = passwordEmployee;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTableProduct;
    private javax.swing.JTable jTablePurchase;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextFieldEmployee1;
    // End of variables declaration//GEN-END:variables
}
