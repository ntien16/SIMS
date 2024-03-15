/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.salemanager;

import com.sims.controller.EmployeeController;
import com.sims.controller.RolezController;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Rolez;
import com.sims.login.PanelLogin;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ProfileJPanelSaleManager extends javax.swing.JPanel {

    private List<Employees> listemployees;
    private EmployeeController employeeController;
    private List<Rolez> listrolez;
    private RolezController rolezController;
    private Employees employees;
    private String usernameEmployee;
    private String encodedPassword;
    private boolean passwordChanged = false;
    private String existingPassword = "";

    public ProfileJPanelSaleManager(String usernameEmployee, String encodedPassword) throws SQLException, ClassNotFoundException {
        initComponents();

        // Initialize employeeController
        employeeController = new EmployeeController();
        employees = new Employees();
        this.usernameEmployee = usernameEmployee;
        this.encodedPassword = encodedPassword;
        setUsernameEmployee(this.usernameEmployee);
        setEncodedPassword(this.encodedPassword);

        // Tạo hai biến userString và passwordPassString để lưu trữ các giá trị của this.username và this.password
        String userString = this.usernameEmployee;
        String passwordPassString = this.encodedPassword;

        Employees loggedInEmployee = employeeController.getEmployeeByEmployeeNameand(this.usernameEmployee, this.encodedPassword);
        if (loggedInEmployee != null) {

            loadEmployeeProfile(loggedInEmployee.getEmployeename(), loggedInEmployee.getPassword());
        }
        System.out.println("password asdasd: " + this.encodedPassword);
        System.out.println("username asdasd: " + this.usernameEmployee);
    }

//    public ProfileJPanelSaleManager() throws SQLException, ClassNotFoundException {
//        initComponents();
//        table1();
//        employeeController = new EmployeeController();
//        String abcString=getUsernameEmployee();
//        String bcas=getEncodedPassword();
//        System.out.println(abcString);
//        System.out.println(bcas);
//        Employees loggedInEmployee = employeeController.getEmployeeByEmployeeNameand(this.usernameEmployee, this.encodedPassword);
//        if (loggedInEmployee != null) {
//            // Hiển thị thông tin của người đăng nhập
//            loadEmployeeProfile(loggedInEmployee.getEmployeename(), loggedInEmployee.getPassword());
//        }
//
//    }
    public void table1() {
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

    }

    public void loadEmployeeProfile(String employeename, String password) {
        try {
            Employees employee = employeeController.getEmployeeByEmployeeNameand(employeename, password);
            if (employee != null) {
                jTextField2.setText(employee.getEmployeename());
                existingPassword = employee.getPassword();
                jPasswordField1.setText(employee.getPassword());
                jTextField4.setText(employee.getRoleid().getRolename());
                jTextField5.setText(String.valueOf(employee.getPhone()));
                jTextField6.setText(employee.getAddress());
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        jTextField2.disable();
        jPasswordField1.disable();
        jTextField4.disable();
        jTextField5.disable();
        jTextField6.disable();
    }

    public void setUsernameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public String getUsernameEmployee() {
        return this.usernameEmployee;
    }

    public String getEncodedPassword() {
        return this.encodedPassword;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton4 = new javax.swing.JButton();

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(255, 255, 0));
        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 102, 102));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-refresh-25.png"))); // NOI18N
        jButton2.setText("UPDATE");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("EMPLOYEE NAME:");

        jLabel2.setText("PASSWORD");

        jLabel3.setText("ROLE");

        jLabel4.setText("PHONE");

        jLabel5.setText("ADDRESS");

        jButton1.setBackground(new java.awt.Color(255, 153, 51));
        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-pencil-25.png"))); // NOI18N
        jButton1.setText("Edit");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPasswordField1.setText("jPasswordField1");
        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyReleased(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(204, 0, 0));
        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-cancel-35.png"))); // NOI18N
        jButton4.setText("CANCEL");
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
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPasswordField1)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                    .addComponent(jTextField2))
                                .addGap(107, 107, 107)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(605, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButton4))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(151, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jPasswordField1.setEnabled(true);
        jTextField5.setEnabled(true);
        jTextField6.setEnabled(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            String phone = jTextField5.getText();
            String address = jTextField6.getText();
            String employeename = jTextField2.getText();
            String role = jTextField4.getText();
            System.out.println("role+ " + role);

           
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid phone number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String originalPassword = employeeController.getEmployeePassword(employeename);

            String passwordManager = new String(jPasswordField1.getPassword()); // Lấy mật khẩu từ jPasswordField

            Employees employeesUpdate = new Employees();

            // Kiểm tra nếu có mật khẩu mới
            if (passwordManager.compareTo(originalPassword) == 0) {
                employeesUpdate.setPassword(originalPassword);
            } else {
                String encodedPassword = Base64.getEncoder().encodeToString(passwordManager.getBytes());
                employeesUpdate.setPassword(encodedPassword);
            }

            if (phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill out all the required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            employeesUpdate.setEmployeename(employeename);
            employeesUpdate.setPhone(phone);
            employeesUpdate.setAddress(address);

            try {
                boolean update = employeeController.updateEmployeeEMPLOYEENAME(employeesUpdate);
                if (update) {
                    JOptionPane.showMessageDialog(this, "Update is successful");
                } else {
                    JOptionPane.showMessageDialog(this, "Update is failed");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid phone number format");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "An error occurred during the update");
                e.printStackTrace();
            }

            jPasswordField1.setEnabled(false);
            jTextField5.setEnabled(false);
            jTextField6.setEnabled(false);
        } catch (SQLException ex) {
            Logger.getLogger(ProfileJPanelSaleManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProfileJPanelSaleManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jPasswordField1.disable();
        jTextField5.disable();
        jTextField6.disable();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jPasswordField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
