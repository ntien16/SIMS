/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.administrator;

import com.sims.controller.BillController;
import com.sims.controller.EmployeeController;
import com.sims.controller.RolezController;
import com.sims.entitynew.Bill;
import com.sims.entitynew.Customer;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Rolez;
import com.sims.jdbc.JDBCConnect;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.relation.Role;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class USER extends javax.swing.JPanel {

    private DefaultTableModel tableModel;
    private List<Employees> listEmployees;
    private JDBCConnect jdbcConnect;
    private Statement statement;
    private BillController billController;
    private Employees employees;
    private Customer customer;
    private EmployeeController employeeController;
    private List<Rolez> listrolez;
    private RolezController rolezController;

    public USER() throws ClassNotFoundException, SQLException {
        initComponents();
        employeeController = new EmployeeController();
        rolezController = new RolezController();
        listrolez = rolezController.getAllRoles();
        employeeController.jdbcConnectDB();
        listEmployees = employeeController.getAllEmployees();
//        table1();
        showUser();
        showCombobox1();

    }

    public void showUser() throws SQLException, ClassNotFoundException {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        listEmployees = employeeController.getAllEmployees();
        model.setRowCount(0);
        try {
            for (Employees employees : listEmployees) {

                model.addRow(new Object[]{
                    employees.getEmployeeid(),
                    employees.getEmployeename(),
                    employees.getRoleid().getRolename(),
                    employees.getPhone(),
                    employees.getAddress()

                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void table1() {
//        table.getTableHeader().setReorderingAllowed(false);
//        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer());
//        table.setOpaque(true);
//        table.setBackground(Color.white);
//        table.setBorder(new EmptyBorder(0, 0, 0, 0));
//        table.setShowHorizontalLines(true);
//        table.setRowHeight(40);
//        table.setShowGrid(false);
//        table.setShowHorizontalLines(false);
//        table.setShowVerticalLines(false);
//
//        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                if (row % 2 == 0) {
//                    component.setBackground(Color.LIGHT_GRAY);
//                } else {
//                    component.setBackground(Color.WHITE);
//                }
//                return component;
//            }
//        };
//
//        for (int i = 0; i < table.getColumnCount(); i++) {
//            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
//        }
//
//        JFrame frame = new JFrame("Remove Table Border Example");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JScrollPane scrollTable = new JScrollPane();
//        scrollTable.setBorder(null);
//        scrollTable.setViewport(null);
//        renderer.setHorizontalAlignment(HEIGHT);
//        table.repaint();
//    }
    public Rolez getRoleID(String rolename) throws SQLException, ClassNotFoundException {
        return rolezController.getRolezByIDName(rolename);
    }

    public void showCombobox1() {
        jComboBox1.removeAll();
        jComboBox1.addItem("Select Role");
        for (Rolez role : listrolez) {
            jComboBox1.addItem(String.valueOf(role.getRolename()));
        }
    }

    public void resetForm() throws SQLException, ClassNotFoundException {
        jTextField2.setText("");
        jPasswordField1.setText("");
        jComboBox1.setSelectedItem("Select Role");
        jTextField3.setText("");
        jTextField4.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPasswordField1 = new javax.swing.JPasswordField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(51, 255, 51));
        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 102, 102));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/outline_new_label_white_24dp.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setLabel("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 0));
        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 102, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/outline_update_white_24dp.png"))); // NOI18N
        jButton2.setText("UPDATE");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 0, 0));
        jButton3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/outline_delete_forever_white_24dp.png"))); // NOI18N
        jButton3.setText("DELETE");
        jButton3.setBorder(null);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
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

        jLabel1.setText("EMPLOYEE NAME:");

        jLabel2.setText("PASSWORD:");

        jLabel3.setText("ROLE:");

        jScrollPane2.setBackground(new java.awt.Color(214, 217, 223));
        jScrollPane2.setBorder(null);
        jScrollPane2.setForeground(new java.awt.Color(255, 255, 255));

        table.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME", "ROLE", "PHONE NUMBER", "ADDRESS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(table);

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("SEARCH");

        jLabel5.setText("PHONE NUMBER:");

        jLabel6.setText("ADDRESS:");

        jButton4.setBackground(new java.awt.Color(255, 153, 51));
        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-pencil-25.png"))); // NOI18N
        jButton4.setText("EDIT");
        jButton4.setBorder(null);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPasswordField1)
                    .addComponent(jComboBox1, 0, 234, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(420, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(158, 158, 158)))
                .addGap(63, 63, 63))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(24, 24, 24)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel3))
                            .addGap(18, 18, 18)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 922, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(35, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(519, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(33, 33, 33)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGap(40, 40, 40)
                    .addComponent(jLabel2)
                    .addGap(44, 44, 44)
                    .addComponent(jLabel3)
                    .addGap(45, 45, 45)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(50, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String employeename = jTextField2.getText();
        String password = jPasswordField1.getText();
        String endcodingPassword = Base64.getEncoder().encodeToString(password.getBytes());
        String roleEmployee = jTextField2.getText();
        String selectRolename = jComboBox1.getSelectedItem().toString();

        Rolez role;
        try {
            role = rolezController.getRolezByIDName(selectRolename);

        } catch (SQLException ex) {
            Logger.getLogger(USER.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(USER.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        try {
            String employeeExist = employeeController.getEmployeeByName(employeename);
            if (employeeExist != null) {
                JOptionPane.showMessageDialog(this, "EmployeeName already exists. Cannot create a new employee.", "Error", JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(this, "Please create a new Employee again", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField1.setText("");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String phoneNumber = jTextField3.getText();
        if (!phoneNumber.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
//       
        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{6,}$")) {
            JOptionPane.showMessageDialog(this, "Please provide at least 6 characters, including one uppercase letter, one lowercase letter, one digit, and one special character in the password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String address = jTextField4.getText();
        if (employeename.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in the name field, the required field.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in Password field.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (phoneNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in the Phone field.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in the Address field.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Employees newEmployees = new Employees(employeename, endcodingPassword, phoneNumber, address, role);
        try {
            boolean newEmployee = employeeController.addNewEmployee(newEmployees);
            if (newEmployee) {
                tableModel = (DefaultTableModel) table.getModel();
                listEmployees.add(newEmployees);
                String rolename1 = role.getRolename();
//                String rolename = newEmployees.getRoleid().getRolename();
                tableModel.addRow(new Object[]{
                    newEmployees.getEmployeeid(),
                    newEmployees.getEmployeename(),
                    rolename1,
                    newEmployees.getPhone(),
                    newEmployees.getAddress()
                });
                tableModel.fireTableDataChanged();
                System.out.println("roleName+ " + role.getRolename());
                JOptionPane.showMessageDialog(this, "Employee added successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add Employee.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(USER.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error adding Employee to the database.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(USER.class.getName()).log(Level.SEVERE, null, ex);

        }
        try {
            resetForm();
        } catch (SQLException ex) {
            Logger.getLogger(USER.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(USER.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    public void deleteEmployee() throws SQLException, ClassNotFoundException {
        tableModel = (DefaultTableModel) table.getModel();
        int rowIndex = table.getSelectedRow();

        if (rowIndex >= 0 && rowIndex < listEmployees.size()) {
            Integer employeeIdObject = (Integer) tableModel.getValueAt(rowIndex, 0);
            if (employeeIdObject != null) {
                int employeeId = employeeIdObject.intValue();
                Employees employees = listEmployees.get(rowIndex);
                employees.setEmployeeid(employeeId);

                // Hiển thị hộp thoại xác nhận
                int confirm = JOptionPane.showConfirmDialog(jButton3, "Are you sure you want to delete this employee?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        boolean deleteEmployeeID = employeeController.deleteEmployeeByID(employees.getEmployeeid());
                        if (deleteEmployeeID) {
//                            JOptionPane.showMessageDialog(jButton3, "Delete is successful");
                            listEmployees.remove(rowIndex);
                            tableModel.removeRow(rowIndex);
                            tableModel.fireTableDataChanged();
                        } else {
                            JOptionPane.showMessageDialog(jButton3, "Delete is failed");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "It's not possible to delete the employee because this employee is related to invoices!");
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(USER.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(jButton3, "Selected employee ID is null");
            }
        } else {
            JOptionPane.showMessageDialog(jButton3, "No employee selected");
        }
    }
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        try {
            deleteEmployee();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "It's not possible to delete the employee because this employee is related to invoices!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(USER.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        tableModel = (DefaultTableModel) table.getModel();
        int rowIndex = table.getSelectedRow();
//        jComboBox1.removeAllItems();
        int employeeid;

        if (rowIndex >= 0 && rowIndex < listEmployees.size()) {
            String EmployeenameString = jTextField2.getText();
            try {
                employeeid = employeeController.getEmployeeByEmployeeName(EmployeenameString);
                System.out.println("employeeid + " + employeeid);
                String selectedRoleName = (String) jComboBox1.getSelectedItem();
                Rolez rolez = null;

                for (Rolez r : listrolez) {
                    if (r.getRolename().equals(selectedRoleName)) {
                        rolez = r;
                        break;
                    }
                }

                if (rolez != null) {
                    String phoneString = jTextField3.getText();
                    String passwordString = jPasswordField1.getText();
                    String addressString = jTextField4.getText();
                    String endcodingPassword = Base64.getEncoder().encodeToString(passwordString.getBytes());
                    if (!phoneString.matches("\\d{10}")) {
                        JOptionPane.showMessageDialog(this, "Please enter a valid phone number.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!passwordString.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])[A-Za-z\\d@#$%^&+=!]{6,}$")) {
                        JOptionPane.showMessageDialog(this, "Please provide at least 6 characters, including one uppercase letter, one lowercase letter, one digit, and one special character in the password.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Employees upDateEmployees = new Employees();

                    try {

                        if (passwordString.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please fill in password field.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else if (phoneString.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please fill in phone field.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else if (addressString.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please fill in address field.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        String originalPassword = employeeController.getEmployeePassword(EmployeenameString);
                        if (passwordString.compareTo(originalPassword) == 0) {
                            upDateEmployees.setPassword(originalPassword);
                        } else {
                            upDateEmployees.setPassword(endcodingPassword);
                        }
                        upDateEmployees.setEmployeeid(employeeid);
                        System.out.println("setEmployeeidelse " + upDateEmployees.getEmployeeid());
                        upDateEmployees.setEmployeename(EmployeenameString);
                        upDateEmployees.setPhone(phoneString);
                        upDateEmployees.setAddress(addressString);
                        upDateEmployees.setRoleid(rolez);

                        boolean updateEmployee = employeeController.updateEmployeeEMPLOYEENAMERole(upDateEmployees);

                        if (updateEmployee) {
                            JOptionPane.showMessageDialog(this, "Update is successful");
                            for (int i = 0; i < listEmployees.size(); i++) {
                                if (listEmployees.get(i).getEmployeeid() == employeeid) {
                                    tableModel.setValueAt(employeeid, rowIndex, 0);
                                    tableModel.setValueAt(EmployeenameString, rowIndex, 1);
                                    tableModel.setValueAt(rolez.getRolename(), rowIndex, 2);
                                    tableModel.setValueAt(phoneString, rowIndex, 3);
                                    tableModel.setValueAt(addressString, rowIndex, 4);
                                    break;
                                }
                            }
//                        tableModel.setValueAt(employeeid, rowIndex, 0);

                        } else {
                            JOptionPane.showMessageDialog(this, "Update is failed");
                        }

                        resetForm();
                        showUser();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a role from the dropdown.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(USER.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(USER.class.getName()).log(Level.SEVERE, null, ex);
            }
//            for (Rolez rolez : listrolez) {
//                jComboBox1.addItem(rolez.getRolename());
//            }

        }
//        else {
//            JOptionPane.showMessageDialog(this, "Please select a Employee from the table.");
//        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        tableModel = (DefaultTableModel) table.getModel();
        int indexRow = table.getSelectedRow();

        // Kiểm tra xem indexRow có hợp lệ không
        if (indexRow >= 0 && indexRow < listEmployees.size()) {
            Employees selectedEmployee = listEmployees.get(indexRow);
            int Employeeid = selectedEmployee.getEmployeeid();
            String EmployeeName = selectedEmployee.getEmployeename();
            String EmployeeRole = selectedEmployee.getRoleid().getRolename();
            String phoneNumber = selectedEmployee.getPhone();
            String phoneString = String.valueOf(phoneNumber);
            String address = selectedEmployee.getAddress();

            jTextField2.setText(EmployeeName);
            jTextField2.disable();
            jComboBox1.setSelectedItem(EmployeeRole);
            jTextField3.setText(phoneString);
            jPasswordField1.setText(selectedEmployee.getPassword());
            jTextField4.setText(address);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        String filterString = jTextField1.getText();
        filterString = filterString.toUpperCase();
        TableRowSorter<DefaultTableModel> tableSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(tableSorter);
        tableSorter.setRowFilter(RowFilter.regexFilter(filterString));
        jTextField1.getText();

    }//GEN-LAST:event_jTextField1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
