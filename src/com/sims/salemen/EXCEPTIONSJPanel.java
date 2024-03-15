/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.salemen;

import com.formdev.flatlaf.ui.FlatTableUI;
import com.formdev.flatlaf.ui.FlatTextFieldUI;
import com.sims.controller.EmployeeController;
import com.sims.controller.ExceptionController;
import com.sims.controller.ExceptionsDetailController;
import com.sims.controller.ImportDetailController;
import com.sims.controller.InventoryController;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Exceptions;
import com.sims.entitynew.Exceptionsdetail;
import com.sims.entitynew.Inventory;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.HEIGHT;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class EXCEPTIONSJPanel extends javax.swing.JPanel {

    private EmployeeController employeeController;
    private Employees employees;
    private List<Employees> listEmployees;
    private String usernameEmployee;
    private String passwordEmployee;
    private List<Inventory> listInventory;
    private InventoryController inventoryController;
    private List<Exceptions> listexExceptions;
    private List<Exceptionsdetail> listExceptionsdetails;
    private ExceptionController exceptionController;
    private ExceptionsDetailController exceptionsDetailController;
//
//    public EXCEPTIONSJPanel() {
//        initComponents();
////        jDialogExcept.getContentPane().setBackground(new Color(0,0,0,0));
//
//    }

    public EXCEPTIONSJPanel(String username, String password) throws SQLException, ClassNotFoundException {
        initComponents();
        employeeController = new EmployeeController();
        inventoryController = new InventoryController();
        exceptionController = new ExceptionController();
        exceptionsDetailController = new ExceptionsDetailController();
        listexExceptions = exceptionController.getAllException();
        listExceptionsdetails = exceptionsDetailController.getAllExceptionsDetail();
        this.usernameEmployee = username;
        this.passwordEmployee = password;
        showExceptions();
        showExceptionsDetail();
        Employees loggedInEmployee = employeeController.getEmployeeByEmployeeNameand(this.usernameEmployee, this.passwordEmployee);
        if (loggedInEmployee != null) {

            loadEmployeeProfile(loggedInEmployee.getEmployeename(), loggedInEmployee.getPassword());
            System.out.println("loggedInEmployee.getEmployeename() " + loggedInEmployee.getEmployeename());
        }

        DefaultTableModel productTableModel = (DefaultTableModel) jTableProduct.getModel();
        DefaultTableModel exceptionsTable = (DefaultTableModel) jTableExceptions.getModel();
        jTableProduct.addMouseListener(new MouseAdapter() {

            Object[] rowData = new Object[10];

            @Override
            public void mouseClicked(MouseEvent e) {
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
                if (e.getClickCount() == 2) {
                    int rowIndex = jTableProduct.getSelectedRow();
                    if (rowIndex >= 0 && rowIndex < productTableModel.getRowCount()) {
                        String abcString = "";
                        rowData[0] = productTableModel.getValueAt(rowIndex, 0);
                        rowData[1] = productTableModel.getValueAt(rowIndex, 1);
                        rowData[2] = productTableModel.getValueAt(rowIndex, 2);
                        rowData[3] = productTableModel.getValueAt(rowIndex, 3);
                        rowData[4] = productTableModel.getValueAt(rowIndex, 4);
                        rowData[5] = productTableModel.getValueAt(rowIndex, 5);
                        rowData[6] = abcString;

                        exceptionsTable.addRow(rowData);
                    }
                }

                boolean productExists = false;
                int rowCount = exceptionsTable.getRowCount();
                for (int j = 0; j < rowCount; j++) {
                    Object importValue = exceptionsTable.getValueAt(j, 0);
                    if (importValue != null && importValue.equals(rowData[0])) {
                        productExists = true;
                        break;
                    }
                }

                if (!productExists) {
                    int row = exceptionsTable.getRowCount();
                    exceptionsTable.addRow(rowData);
                }
            }
        });

    }

    public void showExceptions() throws SQLException, ClassNotFoundException {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        listexExceptions = exceptionController.getAllException();
        tableModel.setRowCount(0);
        try {
            for (Exceptions exceptions : listexExceptions) {

                tableModel.addRow(new Object[]{
                    exceptions.getExceptionId(),
                    exceptions.getExceptionDate(),
                    exceptions.getEmployeeid().getEmployeename()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showExceptionsDetail() throws SQLException, ClassNotFoundException {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        listExceptionsdetails = exceptionsDetailController.getAllExceptionsDetail();
        tableModel.setRowCount(0);
        try {
            for (Exceptionsdetail exceptionsDetail : listExceptionsdetails) {

                tableModel.addRow(new Object[]{
                    exceptionsDetail.getExceptions().getExceptionId(),
                    exceptionsDetail.getInventory().getItemname(),
                    exceptionsDetail.getExceptionType()
                });
            }
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

    public void loadEmployeeProfile(String employeename, String password) {
        try {
            Employees employee = employeeController.getEmployeeByEmployeeNameand(employeename, password);
            if (employee != null) {
                jTextField3.setText(employee.getEmployeename());
                // Disable the text field after setting the text
                System.out.println("jTextField3.setText(employee.getEmployeename())+ " + (employee.getEmployeename()));
                jTextField3.setEnabled(false);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
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

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    component.setBackground(Color.LIGHT_GRAY);
                } else {
                    component.setBackground(Color.WHITE);
                }
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

    public int getEmployeeIdByName(String employeeName) throws SQLException, ClassNotFoundException {
        EmployeeController employeeController = new EmployeeController();
        return employeeController.getEmployeeByEmployeeName(employeeName);
    }

    public int insertInToExceptions(int employeeID) throws SQLException, ClassNotFoundException {
        ExceptionController exceptionController = new ExceptionController();
        return exceptionController.addNewExceptionsByID(employeeID);
    }

    public boolean insertExceptionsDetail(int exceptionID, int itemID, String content) throws SQLException, ClassNotFoundException {
        ExceptionsDetailController exceptionsDetailController = new ExceptionsDetailController();
        boolean insertExceptionsDetail = exceptionsDetailController.addNewIMPORTITEMS3Parameter(exceptionID, itemID, content);
        return insertExceptionsDetail;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogExceptions = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableProduct = new javax.swing.JTable();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableExceptions = new javax.swing.JTable();
        jDialogUpDate = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 255, 0));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-new-product-20.png"))); // NOI18N
        jButton4.setText("EXCEPTIONS");
        jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(0, 255, 102), new java.awt.Color(255, 255, 255)));
        jButton4.setMaximumSize(new java.awt.Dimension(100, 40));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 0, 51));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-defective-product-20.png"))); // NOI18N
        jButton5.setText("CANCEL");
        jButton5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, new java.awt.Color(255, 0, 0), java.awt.Color.white));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

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
        jScrollPane3.setViewportView(jTableProduct);

        jTabbedPane2.addTab("Product", jScrollPane3);

        jTableExceptions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Category", "Unit", "SellPrice", "Quantity", "Exceptions Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTableExceptions);

        jTabbedPane3.addTab("Exceptions", jScrollPane4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 987, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogExceptionsLayout = new javax.swing.GroupLayout(jDialogExceptions.getContentPane());
        jDialogExceptions.getContentPane().setLayout(jDialogExceptionsLayout);
        jDialogExceptionsLayout.setHorizontalGroup(
            jDialogExceptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogExceptionsLayout.setVerticalGroup(
            jDialogExceptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("EXCEPTIONS TPYE:");

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 255, 0));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-check-all-20.png"))); // NOI18N
        jButton8.setText("Save");
        jButton8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(0, 255, 102), new java.awt.Color(255, 255, 255)));
        jButton8.setMaximumSize(new java.awt.Dimension(100, 40));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 0, 51));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-defective-product-20.png"))); // NOI18N
        jButton9.setText("CANCEL");
        jButton9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, new java.awt.Color(255, 0, 0), java.awt.Color.white));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jTextField5.setForeground(new java.awt.Color(0, 0, 0));
        jTextField5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Item:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane5.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton9))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(18, 18, 18)
                            .addComponent(jScrollPane5))))
                .addGap(0, 28, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout jDialogUpDateLayout = new javax.swing.GroupLayout(jDialogUpDate.getContentPane());
        jDialogUpDate.getContentPane().setLayout(jDialogUpDateLayout);
        jDialogUpDateLayout.setHorizontalGroup(
            jDialogUpDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogUpDateLayout.setVerticalGroup(
            jDialogUpDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(0, 255, 0));
        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/outline_new_label_white_24dp.png"))); // NOI18N
        jButton1.setText("CREATE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(255, 0, 0));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ExceptionId", "DATE", "EMPLOYEE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);

        jTabbedPane1.addTab("LIST OF EXCEPTIONS", jScrollPane1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ExceptionId", "ITEMID", "ExceptionType"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jTabbedPane1.addTab("LIST OF EXCEPTIONS DETAIL", jScrollPane2);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Employee:");

        jTextField3.setForeground(new java.awt.Color(0, 0, 0));
        jTextField3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        jButton7.setBackground(new java.awt.Color(255, 0, 0));
        jButton7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/outline_delete_forever_white_24dp.png"))); // NOI18N
        jButton7.setText("DELETE");
        jButton7.setBorder(null);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(294, 294, 294)
                .addComponent(jLabel4)
                .addGap(52, 52, 52)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 970, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(572, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            jDialogExceptions.setVisible(true);
            jDialogExceptions.setSize(1200, 800);
            showProduct();
        } catch (SQLException ex) {
            Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            DefaultTableModel exceptionsTable = (DefaultTableModel) jTableExceptions.getModel();

            String employeeName = jTextField3.getText();
            int employeeID = getEmployeeIdByName(employeeName);
            ExceptionController exceptionController = new ExceptionController();
            int exceptionsID = insertInToExceptions(employeeID);
            for (int i = 0; i < jTableExceptions.getRowCount(); i++) {
                Object itemIDobjObject = jTableExceptions.getValueAt(i, 0);
                Object contentObjObject = jTableExceptions.getValueAt(i, 6);
                if (itemIDobjObject != null && contentObjObject != null) {
                    int itemid = Integer.parseInt(itemIDobjObject.toString());
                    String content = (String) contentObjObject;
                    boolean result = insertExceptionsDetail(exceptionsID, itemid, content);
                }
            }
            JOptionPane.showMessageDialog(jButton4, "Insert Into Exception table is sucessfull");
            jDialogExceptions.setVisible(false);
            exceptionsTable.setRowCount(0);
            showExceptions();
            showExceptionsDetail();
        } catch (SQLException ex) {
            Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        DefaultTableModel purchaseTableModel = (DefaultTableModel) jTableExceptions.getModel();
        int rowIndex = jTableExceptions.getSelectedRow();
        if (rowIndex >= 0) {
            purchaseTableModel.removeRow(rowIndex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        int indexRow = jTable1.getSelectedRow();
        if (indexRow >= 0 && indexRow < listExceptionsdetails.size()) {
            Exceptionsdetail selectedExceptionstDetail = listExceptionsdetails.get(indexRow);

            int importDetailID = selectedExceptionstDetail.getExceptions().getExceptionId();

            String exceptionDetailName = selectedExceptionstDetail.getInventory().getItemname();

            Exceptionsdetail selectedExceptionstDetail1 = listExceptionsdetails.get(indexRow);

            int importid = selectedExceptionstDetail1.getExceptions().getExceptionId();

            String content = selectedExceptionstDetail1.getExceptionType();

            String contenType = content;

            jTextArea1.setText(contenType);
            jTextField5.setText(exceptionDetailName);
            jTextField5.disable();
            jDialogUpDate.setVisible(true);
            jDialogUpDate.setSize(460, 250);

        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        DefaultTableModel tableModelImport = (DefaultTableModel) table.getModel();
        DefaultTableModel tableModelImportDetail = (DefaultTableModel) jTable1.getModel();

        int[] selectedRows = table.getSelectedRows();

        if (selectedRows.length > 0) {
            int choice = JOptionPane.showConfirmDialog(jButton5, "Are you sure you want to delete selected exceptions document?", "Delete exceptions document", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    int rowIndex = selectedRows[i];
                    int exceptionid = (int) tableModelImport.getValueAt(rowIndex, 0);

                    try {

                        boolean deleteSuccess = exceptionController.deleteExceptionsByID(exceptionid);

                        if (deleteSuccess) {
                            JOptionPane.showMessageDialog(jButton5, "Delete is successful");
                            tableModelImport.removeRow(rowIndex);

                            tableModelImportDetail.setRowCount(0);
                        } else {
                            JOptionPane.showMessageDialog(jButton5, "Delete failed");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            try {
                showExceptions();
                showExceptionsDetail();
            } catch (SQLException ex) {
                Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(jButton5, "No exceptions document selected");
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    public int getItemIdByName(String itemsName) throws SQLException, ClassNotFoundException {
        InventoryController inventoryController = new InventoryController();
        return inventoryController.getItemNameByName(itemsName);
    }
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        int rowIndex = jTable1.getSelectedRow();
        jTextArea1.disable();
        String itemname;
        int exceptiondID;

        if (rowIndex >= 0 && rowIndex < listExceptionsdetails.size()) {
            String itemString = jTextField5.getText();
            String contentType = jTextArea1.getText();

            try {

                int selectedRow = jTable1.getSelectedRow();
                int itemID = getItemIdByName(itemString);

                exceptiondID = (int) jTable1.getValueAt(selectedRow, 0);

                if (exceptiondID != -1) {
                    boolean update = exceptionsDetailController.updateImportDetailByIDAndQuantity(exceptiondID, contentType, itemID);
                    if (update) {
                        jTextArea1.setText("");
                        jTextField5.setText("");
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
                Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            showExceptions();
        } catch (SQLException ex) {
            Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            showExceptionsDetail();
        } catch (SQLException ex) {
            Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EXCEPTIONSJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        jDialogUpDate.setVisible(false);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jDialogUpDate.setVisible(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    public String getUsernameEmployee() {
        return usernameEmployee;
    }

    public String getPasswordEmployee() {
        return passwordEmployee;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialogExceptions;
    private javax.swing.JDialog jDialogUpDate;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableExceptions;
    private javax.swing.JTable jTableProduct;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
