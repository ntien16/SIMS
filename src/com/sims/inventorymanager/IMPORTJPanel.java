/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.inventorymanager;

import com.sims.controller.BillController;
import com.sims.controller.BillDetailController;
import com.sims.controller.CategoryController;
import com.sims.controller.CustomerController;
import com.sims.controller.EmployeeController;
import com.sims.controller.ImportDetailController;
import com.sims.controller.ImportItemController;
import com.sims.controller.InventoryController;
import com.sims.controller.ItemTakeBackController;
import com.sims.controller.SupplierController;
import com.sims.entitynew.BILLDetail;
import com.sims.entitynew.Bill;
//import static com.sims.entitynew.Bill_.customerId;
import com.sims.entitynew.Category;
import com.sims.entitynew.Deletebill;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Importdetail;
import com.sims.entitynew.Importitems;
import com.sims.entitynew.Inventory;
import com.sims.entitynew.Supplier;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Table;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class IMPORTJPanel extends javax.swing.JPanel {

    private String usernameEmployee;
    private String passwordEmployee;
    private EmployeeController employeeController;
    private Employees employees;
    private List<Inventory> listInventory;
    private InventoryController inventoryController;
    private List<Supplier> listSupplier;
    private SupplierController supplierController;
    private List<Importitems> listImportItems;
    private List<Importdetail> listImportDetail;
    private ImportItemController importItemController;
    private ImportDetailController importDetailController;

    public IMPORTJPanel() {
        initComponents();
    }

    public IMPORTJPanel(String usernameEmployee, String passwordEmployee) throws SQLException, ClassNotFoundException {
        initComponents();
        importDetailController = new ImportDetailController();
        importItemController = new ImportItemController();
        this.usernameEmployee = usernameEmployee;
        this.passwordEmployee = passwordEmployee;
        System.out.println("this.usernameEmployee+ " + this.usernameEmployee);
        System.out.println("this.passwordEmployee+ " + this.passwordEmployee);
        inventoryController = new InventoryController();
        supplierController = new SupplierController();
        listInventory = inventoryController.getAllInventory();
        listSupplier = supplierController.getAllSupplier();
        employeeController = new EmployeeController();
        Employees loggedInEmployee = employeeController.getEmployeeByEmployeeNameand(this.usernameEmployee, this.passwordEmployee);
        if (loggedInEmployee != null) {

            loadEmployeeProfile(loggedInEmployee.getEmployeename(), loggedInEmployee.getPassword());
        }

        showProduct();
        showListComboboxSupplier();
        showListImport();
        showimportDetail();

        DefaultTableModel productTableModel = (DefaultTableModel) jTableProduct.getModel();
        DefaultTableModel importTable = (DefaultTableModel) jImport.getModel();
        jTableProduct.addMouseListener(new MouseAdapter() {

            Object[] rowData = new Object[6];

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int rowIndex = jTableProduct.getSelectedRow();
                    if (rowIndex >= 0 && rowIndex < productTableModel.getRowCount()) {
                        rowData[0] = productTableModel.getValueAt(rowIndex, 0);
                        rowData[1] = productTableModel.getValueAt(rowIndex, 1);
                        rowData[2] = productTableModel.getValueAt(rowIndex, 2);
                        rowData[3] = productTableModel.getValueAt(rowIndex, 3);
                        rowData[4] = productTableModel.getValueAt(rowIndex, 4);
                        rowData[5] = 0;

                        importTable.addRow(rowData);
                    }
                }

                boolean productExists = false;
                int rowCount = importTable.getRowCount();
                for (int j = 0; j < rowCount; j++) {
                    Object importValue = importTable.getValueAt(j, 0);
                    if (importValue != null && importValue.equals(rowData[0])) {
                        productExists = true;
                        break;
                    }
                }

                if (!productExists) {
                    int row = importTable.getRowCount();
                    importTable.addRow(rowData);
                }
            }
        });

    }

    public void showListImport() throws SQLException, ClassNotFoundException {
        listImportItems = importItemController.getAllImportItem();
        DefaultTableModel tableModel = (DefaultTableModel) jTableImport.getModel();
        tableModel.setRowCount(0);
        try {
            for (Importitems importitem : listImportItems) {

                tableModel.addRow(new Object[]{
                    importitem.getImportid(),
                    importitem.getEmployeeid().getEmployeename(),
                    importitem.getSupplierid().getSuppilerName(),
                    importitem.getImportdate(),});
            }
            tableModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showimportDetail() throws SQLException, ClassNotFoundException {
        listImportDetail = importDetailController.getAllImportItemDetail();
        DefaultTableModel tableModel = (DefaultTableModel) jTableImportDetail.getModel();
        tableModel.setRowCount(0);
        try {
            for (Importdetail importdetail : listImportDetail) {

                DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");

                tableModel.addRow(new Object[]{
                    importdetail.getImportdetailPK().getImportid(),
                    importdetail.getInventory().getItemname(),
                    importdetail.getQuantity(),});
            }
            tableModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    quantityformartString,});
            }

            tableModel.fireTableDataChanged();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    //    public void showListComboboxItems() {
    //        jComboBox1.removeAll();
    //        jComboBox1.addItem("Select Items");
    //        for (Inventory inventory : listInventory) {
    //            jComboBox1.addItem(String.valueOf(inventory.getItemname()));
    //        }
    //    }
    public void showListComboboxSupplier() {
        jComboBox2.removeAll();
        jComboBox2.addItem("Select Supplier");
        for (Supplier supplier : listSupplier) {
            jComboBox2.addItem(String.valueOf(supplier.getSuppilerName()));
        }
    }

    public void loadEmployeeProfile(String employeename, String password) {
        try {
            Employees employee = employeeController.getEmployeeByEmployeeNameand(employeename, password);
            if (employee != null) {
                jTextField2.setText(employee.getEmployeename());
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        jTextField2.disable();
    }

    public int getItemIdByName(String itemsName) throws SQLException, ClassNotFoundException {
        InventoryController inventoryController = new InventoryController();
        return inventoryController.getItemNameByName(itemsName);
    }

    private int getSupplierIDByName(String supplier) throws SQLException, ClassNotFoundException {
        supplierController = new SupplierController();
        return supplierController.getSupplierByName(supplier);
    }

    public int getEmployeeIdByName(String employeeName) throws SQLException, ClassNotFoundException {
        EmployeeController employeeController = new EmployeeController();
        return employeeController.getEmployeeByEmployeeName(employeeName);
    }

    private int insertImportItems(int employeeId, int supplierID) throws SQLException, ClassNotFoundException {
        ImportItemController importItemController = new ImportItemController();
        int insertbill = importItemController.addNewImport2parameter(employeeId, supplierID);
        return insertbill;
    }

    public boolean insertImportDetail(int billid, int itemID, int quantity) throws SQLException, ClassNotFoundException {
        ImportDetailController importDetailController = new ImportDetailController();
        boolean insertImportDetail = importDetailController.addNewIMPORTITEMS3Parameter(billid, itemID, quantity);
        return insertImportDetail;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogImp = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProduct = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jImport = new javax.swing.JTable();
        jDialogUpDate = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableImport = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableImportDetail = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("SUPPLIER:");

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 255, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-new-product-20.png"))); // NOI18N
        jButton2.setText("IMPORT");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(0, 255, 102), new java.awt.Color(255, 255, 255)));
        jButton2.setMaximumSize(new java.awt.Dimension(100, 40));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 0, 51));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-defective-product-20.png"))); // NOI18N
        jButton3.setText("CANCEL");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, new java.awt.Color(255, 0, 0), java.awt.Color.white));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField2.setForeground(new java.awt.Color(0, 0, 0));
        jTextField2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Employee:");

        jTableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Category", "Unit", "SellPrice", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableProduct);

        jTabbedPane1.addTab("Product", jScrollPane2);

        jImport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Category", "Unit", "SellPrice", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jImport);

        jTabbedPane2.addTab("Import", jScrollPane4);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel4)
                        .addGap(52, 52, 52)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 423, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(34, 34, 34)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogImpLayout = new javax.swing.GroupLayout(jDialogImp.getContentPane());
        jDialogImp.getContentPane().setLayout(jDialogImpLayout);
        jDialogImpLayout.setHorizontalGroup(
            jDialogImpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogImpLayout.setVerticalGroup(
            jDialogImpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jTextField3.setForeground(new java.awt.Color(0, 0, 0));
        jTextField3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("QUANTITY:");

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(0, 255, 0));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-check-all-20.png"))); // NOI18N
        jButton7.setText("Save");
        jButton7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(0, 255, 102), new java.awt.Color(255, 255, 255)));
        jButton7.setMaximumSize(new java.awt.Dimension(100, 40));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 0, 51));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-defective-product-20.png"))); // NOI18N
        jButton8.setText("CANCEL");
        jButton8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, new java.awt.Color(255, 0, 0), java.awt.Color.white));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jTextField4.setForeground(new java.awt.Color(0, 0, 0));
        jTextField4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Item:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton8))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(94, 94, 94))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jDialogUpDateLayout = new javax.swing.GroupLayout(jDialogUpDate.getContentPane());
        jDialogUpDate.getContentPane().setLayout(jDialogUpDateLayout);
        jDialogUpDateLayout.setHorizontalGroup(
            jDialogUpDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        jDialogUpDateLayout.setVerticalGroup(
            jDialogUpDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane3.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane3.setForeground(new java.awt.Color(0, 204, 153));
        jTabbedPane3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jTableImport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "EMPLOYEENAME", "SUPPLIER", "DATE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableImport);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 120, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("TABLE IMPORT", jPanel4);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTableImportDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "IMPORTID", "ITEM NAME", "QUANTITY"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableImportDetail);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 120, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("IMPORT DETAIL", jPanel3);

        jButton4.setBackground(new java.awt.Color(51, 255, 51));
        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-add-product-24.png"))); // NOI18N
        jButton4.setText("CREATE");
        jButton4.setBorder(null);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 0, 0));
        jButton5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/outline_delete_forever_white_24dp.png"))); // NOI18N
        jButton5.setText("DELETE");
        jButton5.setBorder(null);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 0));
        jButton6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 0, 0));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-refresh-25.png"))); // NOI18N
        jButton6.setText("UPDATE");
        jButton6.setBorder(null);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1003, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138))
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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jDialogImp.setVisible(true);
        jDialogImp.setSize(1200, 800);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        DefaultTableModel tableModelImport = (DefaultTableModel) jTableImport.getModel();
        DefaultTableModel tableModelImportDetail = (DefaultTableModel) jTableImportDetail.getModel();

        int[] selectedRows = jTableImport.getSelectedRows();

        if (selectedRows.length > 0) {
            int choice = JOptionPane.showConfirmDialog(jButton5, "Are you sure you want to delete selected import document?", "Delete import document", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int rowIndex = selectedRows[i];
                    int importID = (int) tableModelImport.getValueAt(rowIndex, 0);

                    try {
                        // Xóa các chi tiết nhập hàng trước
//                    importDetailController.deleteImportDetailByImportID(importID);

                        // Xóa hóa đơn nhập hàng
                        boolean deleteSuccess = importItemController.deleteImportByID(importID);

                        if (deleteSuccess) {
                            JOptionPane.showMessageDialog(jButton5, "Delete is successful");
                            tableModelImport.removeRow(rowIndex);
                            // Cập nhật bảng chi tiết nhập hàng
                            tableModelImportDetail.setRowCount(0); // Xóa toàn bộ dữ liệu trong bảng chi tiết
                        } else {
                            JOptionPane.showMessageDialog(jButton5, "Delete failed");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            try {
                showListImport();
            } catch (SQLException ex) {
                Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                showimportDetail();
            } catch (SQLException ex) {
                Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(jButton5, "No import document selected");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        DefaultTableModel tableModel = (DefaultTableModel) jTableImportDetail.getModel();
        int indexRow = jTableImportDetail.getSelectedRow();
        if (indexRow >= 0 && indexRow < listImportDetail.size()) {
            Importdetail selectedImportDetail = listImportDetail.get(indexRow);
            int importDetailID = selectedImportDetail.getImportitems().getImportid();
            String importDetailName = selectedImportDetail.getInventory().getItemname();
            Importdetail selectedimportDetail = listImportDetail.get(indexRow);
            int importid = selectedimportDetail.getImportitems().getImportid();
            int quantity = selectedimportDetail.getQuantity();
            String quantityString = String.valueOf(quantity);
            jTextField3.setText(quantityString);
            jTextField4.setText(importDetailName);
            jTextField4.disable();
            jDialogUpDate.setVisible(true);
            jDialogUpDate.setSize(400, 200);

        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        DefaultTableModel tableModel = (DefaultTableModel) jTableImportDetail.getModel();
        int rowIndex = jTableImportDetail.getSelectedRow();
        jTextField4.disable();
        String itemname;
        int importId;

        if (rowIndex >= 0 && rowIndex < listImportDetail.size()) {
            String quantityString = jTextField3.getText();
            String itemString = jTextField4.getText();

            try {
                int quantityChange = Integer.parseInt(quantityString); 
                System.out.println("quantityChange+ " + quantityChange);
                int selectedRow = jTableImportDetail.getSelectedRow();
                int itemID = getItemIdByName(itemString);
                System.out.println("itemID+ " + itemID);
                importId = (int) jTableImportDetail.getValueAt(selectedRow, 0);
                System.out.println("importId+" + importId);
                if (importId != -1) {
                    boolean update = importDetailController.updateImportDetailByIDAndQuantity(importId, quantityChange, itemID);
                    if (update) {
                        JOptionPane.showMessageDialog(this, "Update is successful");
                    } else {
                        JOptionPane.showMessageDialog(this, "Update is failed");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Import not found for given quantity and item");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity input. Please enter a valid number.");
            } catch (SQLException ex) {
                Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            showListImport();
        } catch (SQLException ex) {
            Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            showimportDetail();
        } catch (SQLException ex) {
            Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        jDialogUpDate.setVisible(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        jDialogUpDate.setVisible(false);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DefaultTableModel purchaseTableModel = (DefaultTableModel) jImport.getModel();
        int rowIndex = jImport.getSelectedRow();
        if (rowIndex >= 0) {
            purchaseTableModel.removeRow(rowIndex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        DefaultTableModel importTable = (DefaultTableModel) jImport.getModel();

        String selectedSupplier = jComboBox2.getSelectedItem().toString();
        String employeeName = jTextField2.getText();

        try {
            int supplier = getSupplierIDByName(selectedSupplier);
            int employeeId = getEmployeeIdByName(employeeName);

            if (supplier == -1) {
                JOptionPane.showMessageDialog(jButton2, "Please Choose Supplier");
                return;
            }

            int importitemid = insertImportItems(employeeId, supplier);

            for (int i = 0; i < importTable.getRowCount(); i++) {
                Object itemIdObj = importTable.getValueAt(i, 0);
                Object quantityObj = importTable.getValueAt(i, 5);

                if (itemIdObj != null && quantityObj != null) {
                    int itemId = Integer.parseInt(itemIdObj.toString());
                    System.out.println("itemId: " + itemId);

                    int quantity = Integer.parseInt(quantityObj.toString());
                    System.out.println("quantity: " + quantity);

                    boolean result = insertImportDetail(importitemid, itemId, quantity);
                }
            }

            JOptionPane.showMessageDialog(jButton2, "Purchase is successful");
            jDialogImp.setVisible(false);

            // Reset the supplier combo box
            jComboBox2.setSelectedIndex(0);

            // Optionally, you can also clear the table here if needed
            // importTable.setRowCount(0);
        } catch (SQLException ex) {
            Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            showListImport();
        } catch (SQLException ex) {
            Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            showimportDetail();
        } catch (SQLException ex) {
            Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IMPORTJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JDialog jDialogImp;
    private javax.swing.JDialog jDialogUpDate;
    private javax.swing.JTable jImport;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTableImport;
    private javax.swing.JTable jTableImportDetail;
    private javax.swing.JTable jTableProduct;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
