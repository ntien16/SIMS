package com.sims.login;

import com.sims.administrator.ADMINISTRATORFRAME;
import com.sims.administrator.ProfileJPanelAdmin;
import com.sims.entitynew.Admin;
import com.sims.entitynew.Employees;

import com.sims.inventorymanager.INVENTORYMANAGER;
import com.sims.jdbc.JDBCConnect;
import com.sims.salemanager.ProfileJPanelSaleManager;
import com.sims.salemen.ProfileJPanelSalemen;
import com.sims.inventorymanager.ProfileJPanelInventory;
import com.sims.salemanager.SALEMANAGERFRAME;
import com.sims.salemen.SALESMENFRAME;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ComboBoxEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.Renderer;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.eclipse.persistence.sessions.factories.SessionManager;

public class PanelLogin extends javax.swing.JLayeredPane {

    private JDBCConnect jdbc;
    private Admin admin;
    private Employees employees;
    private ProfileJPanelSaleManager profilePanel;
    private ProfileJPanelSalemen profileJPanelSalemen;
    private ProfileJPanelInventory profileJPanelInventory;
    private String usernameEmployee;
    private String passwordEmployee;
    private ProfileJPanelAdmin profileJPanelAdmin;

    public PanelLogin(ActionListener adminLogin, ActionListener employeesLogin) throws SQLException, ClassNotFoundException {
        initComponents();
        initAdmin(adminLogin);
        initEmployees(employeesLogin);
        Employees.setVisible(false);
        Admin.setVisible(true);
        jdbc = new JDBCConnect();
//        profilePanel = new ProfileJPanelSaleManager();
        setProfilePanel(profilePanel);
        profileJPanelSalemen = new ProfileJPanelSalemen();
        profileJPanelInventory = new ProfileJPanelInventory();
    }

    public PanelLogin() {

    }

    public PanelLogin(String usernameEmployee, String passwordEmployee) {
        this.usernameEmployee = usernameEmployee;
        this.passwordEmployee = passwordEmployee;
    }

    private void initAdmin(ActionListener adminLogin) {
        Admin.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Admin");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(7, 164, 121));
        Admin.add(label);
        MyTextField txtUser = new MyTextField();
        txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/com/sims/icons/user.png")));
        txtUser.setHint("Name");
        Admin.add(txtUser, "w 60%");
        MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/com/sims/icons/pass.png")));
        txtPass.setHint("Password");
        Admin.add(txtPass, "w 60%");
        Button cmd = new Button();
        cmd.setBackground(new Color(7, 164, 121));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(adminLogin);
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String ADMINNAME = txtUser.getText().trim();
                String ADMINPASSWORD = String.valueOf(txtPass.getPassword());
                String endcoding = Base64.getEncoder().encodeToString(ADMINPASSWORD.getBytes());
                admin = new Admin(ADMINNAME, ADMINPASSWORD);
                try {
                    jdbc.jdbcConnectDB();
                    String queryString = "select*from Admin where ADMINNAME=? and ADMINPASSWORD=?";
                    PreparedStatement preparedStatement = jdbc.createPrepareStatement(queryString);
                    preparedStatement.setString(1, ADMINNAME);
                    preparedStatement.setString(2, endcoding);
                    ResultSet rs = preparedStatement.executeQuery();

                    if (rs.next()) {
                        ADMINISTRATORFRAME adminFrame = new ADMINISTRATORFRAME(ADMINNAME,endcoding);
//                        profileJPanelAdmin.loadEmployeeProfile(ADMINNAME, endcoding);
                        adminFrame.setVisible(true);
                        Window window = SwingUtilities.getWindowAncestor((Component) ae.getSource());
                        if (window != null) {
                            window.dispose();
                        }
//                        JOptionPane.showMessageDialog(cmd, "Login is successful");
                    }else
                    {
                        JOptionPane.showMessageDialog(cmd, "Login is failed");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        cmd.setText("SIGN UP");

        Admin.add(cmd, "w 40%, h 40");
    }

    private void initEmployees(ActionListener employeesLogin) {
        Employees.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Employee");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(7, 164, 121));
        Employees.add(label);
        MyTextField txtUser = new MyTextField();
        txtUser.setPrefixIcon(new ImageIcon(getClass().getResource("/com/sims/icons/user.png")));
        txtUser.setHint("Name");
        Employees.add(txtUser, "w 60%");
        MyPasswordField txtPass = new MyPasswordField();
        txtPass.setPrefixIcon(new ImageIcon(getClass().getResource("/com/sims/icons/pass.png")));
        txtPass.setHint("Password");
        Employees.add(txtPass, "w 60%");
        JButton cmdForget = new JButton("Forgot your password ?");
        cmdForget.setForeground(new Color(100, 100, 100));
        cmdForget.setFont(new Font("sansserif", 1, 12));
        cmdForget.setContentAreaFilled(false);
        cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Employees.add(cmdForget);
        String[] roles = {"SALEMANGER", "INVENTORYMANAGER", "SALESMAN"};
        JComboBox<String> comboBox = new JComboBox<>(roles);

        Employees.add(comboBox, "w 60%");
        Button cmd = new Button();
        cmd.setBackground(new Color(7, 164, 121));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(employeesLogin);
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                usernameEmployee = txtUser.getText().trim();
                passwordEmployee = String.valueOf(txtPass.getPassword());
                String encodedPassword = Base64.getEncoder().encodeToString(passwordEmployee.getBytes());

                String selectrole = String.valueOf(comboBox.getSelectedItem());
                try {
                    String querySQL = "SELECT * FROM employees AS EMP JOIN ROLEZ AS R ON EMP.ROLEID=R.ROLEID WHERE EMP.EMPLOYEENAME=? AND EMP.PASSWORD=? AND R.ROLENAME=?";
                    PreparedStatement prs = jdbc.createPrepareStatement(querySQL);
                    prs.setString(1, usernameEmployee);
                    prs.setString(2, encodedPassword);
                    prs.setString(3, selectrole);
                    ResultSet rs = prs.executeQuery();

//                    profilePanel.setUsernameEmployee(usernameEmployee);
//                    profilePanel.setEncodedPassword(encodedPassword);
                    if (rs.next()) {
                        int roleid = rs.getInt("ROLEID");

                        switch (roleid) {
                            case 1: // SALES MANAGER
                                SALEMANAGERFRAME salemanager = new SALEMANAGERFRAME(usernameEmployee, encodedPassword);
                                salemanager.setVisible(true);
                                com.sims.ultis.SessionManager.setCurrentEmployee(usernameEmployee, encodedPassword);
//                                profilePanel.loadEmployeeProfile(usernameEmployee, encodedPassword);
                                break;
                            case 2: // INVENTORY MANAGER
                                INVENTORYMANAGER inventorymanager = new INVENTORYMANAGER(usernameEmployee, encodedPassword);
                                inventorymanager.setVisible(true);
                                com.sims.ultis.SessionManager.setCurrentEmployee(usernameEmployee, encodedPassword);
                                profileJPanelInventory.loadEmployeeProfile(usernameEmployee, encodedPassword);
                                break;
                            case 3: // SALESMAN
                                SALESMENFRAME salemen = new SALESMENFRAME(usernameEmployee, encodedPassword);
                                salemen.setVisible(true);
                                com.sims.ultis.SessionManager.setCurrentEmployee(usernameEmployee, encodedPassword);
                                profileJPanelSalemen.loadEmployeeProfile(usernameEmployee, encodedPassword);
                                break;
                            default:
                                throw new AssertionError();
                        }

                        Window window = SwingUtilities.getWindowAncestor((Component) ae.getSource());
                        if (window != null) {
                            window.dispose();

                        }
                    } else {
                        System.out.println("Không có kết quả nào được trả về."); // In thông báo nếu không có kết quả
                        JOptionPane.showMessageDialog(cmd, "Login is failed");
                    }
                    setUsenameEmployee(usernameEmployee);
                    setPasswordEmployee(encodedPassword);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        cmd.setText("SIGN IN");
        Employees.add(cmd, "w 40%, h 40");
    }

    public void showRegister(boolean show) {
        if (show) {
            Admin.setVisible(true);
            Employees.setVisible(false);
        } else {
            Admin.setVisible(false);
            Employees.setVisible(true);
//            System.out.println(usernameEmployee);
//            System.out.println(passwordEmployee);
        }
    }

    public class Border extends EmptyBorder {

        private Color focusColor = new Color(128, 189, 255);
        private Color color = new Color(206, 212, 218);

        public Border(int border) {
            super(border, border, border, border);

        }

        public Border() {
            this(5);
        }

        @Override
        public void paintBorder(Component c, Graphics grphcs, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (c.isFocusOwner()) {
                g2.setColor(focusColor);

            } else {
                g2.setColor(color);
            }
            g2.drawRect(x, y, width - 1, height - 1);
        }

    }

    public String getUsenameEmployee() {
        return usernameEmployee;
    }

    public void setUsenameEmployee(String usernameEmployee) {
        this.usernameEmployee = usernameEmployee;
    }

    public String getPasswordEmployee() {
        return passwordEmployee;
    }

    public void setPasswordEmployee(String passwordEmployee) {
        this.passwordEmployee = passwordEmployee;
    }

    public ProfileJPanelSaleManager getProfilePanel() {
        return profilePanel;
    }

    public void setProfilePanel(ProfileJPanelSaleManager profilePanel) {
        this.profilePanel = profilePanel;
    }

    public ProfileJPanelSalemen getProfileJPanelSalemen() {
        return profileJPanelSalemen;
    }

    public void setProfileJPanelSalemen(ProfileJPanelSalemen profileJPanelSalemen) {
        this.profileJPanelSalemen = profileJPanelSalemen;
    }

    public ProfileJPanelInventory getProfileJPanelInventory() {
        return profileJPanelInventory;
    }

    public void setProfileJPanelInventory(ProfileJPanelInventory profileJPanelInventory) {
        this.profileJPanelInventory = profileJPanelInventory;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Employees = new javax.swing.JPanel();
        Admin = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        Employees.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout EmployeesLayout = new javax.swing.GroupLayout(Employees);
        Employees.setLayout(EmployeesLayout);
        EmployeesLayout.setHorizontalGroup(
            EmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        EmployeesLayout.setVerticalGroup(
            EmployeesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(Employees, "card3");

        Admin.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout AdminLayout = new javax.swing.GroupLayout(Admin);
        Admin.setLayout(AdminLayout);
        AdminLayout.setHorizontalGroup(
            AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        AdminLayout.setVerticalGroup(
            AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(Admin, "card2");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Admin;
    private javax.swing.JPanel Employees;
    // End of variables declaration//GEN-END:variables
}
