/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.inventorymanager;

import com.sims.controller.EmployeeController;
import com.sims.controller.RolezController;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Rolez;
import com.sims.salemanager.ProfileJPanelSaleManager;
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
public class ProfileJPanelInventory extends javax.swing.JPanel {

    private List<Employees> listemployees;
    private EmployeeController employeeController;
    private List<Rolez> listrolez;
    private RolezController rolezController;
    private Employees employees;
    private String username;
    private String password;

    public ProfileJPanelInventory() throws SQLException, ClassNotFoundException {
        initComponents();
        table1();
        employeeController = new EmployeeController();
        listemployees = employeeController.getAllEmployees();
        for (Employees employee : listemployees) {
            loadEmployeeProfile(employee.getEmployeename(), employee.getPassword());
        }

    }

    public ProfileJPanelInventory(String username, String password) throws SQLException, ClassNotFoundException {
        initComponents();
        table1();
        employeeController = new EmployeeController();
        this.username = username;
        this.password = password;
        Employees loggedInEmployee = employeeController.getEmployeeByEmployeeNameand(this.username, this.password);
        if (loggedInEmployee != null) {

            loadEmployeeProfile(loggedInEmployee.getEmployeename(), loggedInEmployee.getPassword());
        }

        System.out.println("password asdasd: " + this.password);
        System.out.println("username asdasd: " + this.username);

    }

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

//        for (int i = 0; i < table.getColumnCount(); i++) {
//            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
//        }
//        
//        JFrame frame = new JFrame("Remove Table Border Example");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JScrollPane scrollTable=new JScrollPane(); 
//        scrollTable.setBorder(null);
//        scrollTable.setViewport(null);
//        renderer.setHorizontalAlignment(HEIGHT);
//        table.repaint();
    }

    public void loadEmployeeProfile(String employeename, String password) {
        try {
            Employees employee = employeeController.getEmployeeByEmployeeNameand(employeename, password);
            if (employee != null) {
                jTextField2.setText(employee.getEmployeename());
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

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

        jPasswordField1.setText("jPasswordField1");

        jButton1.setBackground(new java.awt.Color(255, 153, 51));
        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-pencil-25.png"))); // NOI18N
        jButton1.setText("EDIT");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 0, 0));
        jButton3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-cancel-35.png"))); // NOI18N
        jButton3.setText("CANCEL");
        jButton3.setBorder(null);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))))
                .addContainerGap(25, Short.MAX_VALUE))
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
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(139, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jPasswordField1.setEnabled(true);
        jTextField5.setEnabled(true);
        jTextField6.setEnabled(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jPasswordField1.disable();
        jTextField5.disable();
        jTextField6.disable();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
