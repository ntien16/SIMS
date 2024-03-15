/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.administrator;

import com.formdev.flatlaf.ui.FlatTextFieldUI;
import com.sims.controller.CategoryController;
import com.sims.controller.InventoryController;
import com.sims.controller.StatuzController;
import com.sims.controller.UnitController;
import com.sims.entitynew.Bill;
import com.sims.entitynew.Category;
import com.sims.entitynew.Inventory;
import com.sims.entitynew.Statuz;
import com.sims.entitynew.Unit;
import com.sims.jdbc.JDBCConnect;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ProductJPanel extends javax.swing.JPanel {

    private Category category;
    private List<Category> listcategory;
    private CategoryController categoryController;
    private JDBCConnect jDBCConnect;

    private Unit unit;
    private List<Unit> listUnit;
    private UnitController unitController;

    private List<Inventory> listInventorys;

    private InventoryController inventoryController;
    private List<Statuz> listStatuz;
    private StatuzController statuzController;

    public ProductJPanel() throws SQLException, ClassNotFoundException {
        category = new Category();
        listcategory = new ArrayList<>();
        categoryController = new CategoryController();
        jDBCConnect = new JDBCConnect();
        categoryController.jdbcConnectDB();
        listcategory = categoryController.getAllCategory();
        statuzController = new StatuzController();
        listStatuz = statuzController.getAllStatuz();
        unit = new Unit();
        listUnit = new ArrayList<>();
        unitController = new UnitController();
        jDBCConnect = new JDBCConnect();
        unitController.jdbcConnectDB();
        listUnit = unitController.getAllUnit();

        inventoryController = new InventoryController();
        listInventorys = inventoryController.getAllInventory();
        initComponents();
        jComboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedValue = jComboBox1.getSelectedItem().toString();
                    filter(selectedValue);
                }
            }
        });
        jFind.setOpaque(true);
//        tableRender();
        showProduct();
        showcategory();
        showUnit();
        showlistBillCombobox();
        showlistBillCombobox2();
        showlistBillCombobox3();
        showCombobox4and5();
    }

    public void showProduct() {
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        tableModel.setRowCount(0);
        for (Inventory inventory : listInventorys) {
            String formattedCostPrice = decimalFormat.format(inventory.getCostPrice());
            String formattedSellPrice = decimalFormat.format(inventory.getSellPrice());
            tableModel.addRow(new Object[]{
                inventory.getItemid(),
                inventory.getItemname(),
                inventory.getCategoryid().getCategoryname(),
                inventory.getUnitid().getUnitname(),
                inventory.getInventoryquantity(),
                formattedCostPrice,
                formattedSellPrice,
                inventory.getStatusid().getStatusname()
            });
        }
    }

//show category
    public void showcategory() throws SQLException, ClassNotFoundException {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        for (Category category : listcategory) {
            tableModel.addRow(new Object[]{
                category.getCategoryid(),
                category.getCategoryname()
            });
        }
    }
//
    //show unit

    public void showUnit() throws SQLException, ClassNotFoundException {
        DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
        for (Unit unit : listUnit) {
            tableModel.addRow(new Object[]{
                unit.getUnitid(),
                unit.getUnitname()
            });
        }

    }
    //
    //filter product

    public void showlistBillCombobox() throws ClassNotFoundException, SQLException {
        jComboBox1.removeAllItems();
        jComboBox1.addItem("All");
        for (Category category : listcategory) {
            jComboBox1.addItem(String.valueOf(category.getCategoryname()));
        }

        String query = jComboBox1.getSelectedItem().toString();
        filter(query);

    }

    //
    public void showlistBillCombobox2() throws ClassNotFoundException, SQLException {
        jComboBox2.removeAllItems();
        jComboBox2.addItem("All");
        for (Category category : listcategory) {
            jComboBox2.addItem(String.valueOf(category.getCategoryname()));
        }

        String query = jComboBox2.getSelectedItem().toString();

    }

    public void showlistBillCombobox3() throws ClassNotFoundException, SQLException {
        jComboBox3.removeAllItems();
        jComboBox3.addItem("All");
        for (Unit unit : listUnit) {
            jComboBox3.addItem(String.valueOf(unit.getUnitname()));
        }

        String query = jComboBox3.getSelectedItem().toString();

    }

    //
    public void filter(String query) {
        DefaultTableModel tableModelBill = (DefaultTableModel) table1.getModel();

        if (query != null) {
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(tableModelBill);

            table1.setRowSorter(trs);

            if (!query.equals("All")) {
                RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(query, 2);

                trs.setRowFilter(filter);

            } else {
                trs.setRowFilter(null); // return to  "All"

            }
        }
    }

    private void formatTextField(JTextField textField) {
     String text = textField.getText().replaceAll(",", "");
    try {
        long number = Long.parseLong(text);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(number);
        textField.setText(formattedNumber);
    } catch (NumberFormatException ex) {
//        JOptionPane.showMessageDialog(null, "Please enter a valid number");
    }
}
    public void showCombobox4and5() {
        jComboBox4.removeAllItems();
        jComboBox4.addItem("All");
        jComboBox5.addItem("All");
        for (Statuz status : listStatuz) {
            jComboBox4.addItem(String.valueOf(status.getStatusname()));
            jComboBox5.addItem(String.valueOf(status.getStatusname()));
        }

    }

    //
//    public void tableRender() {
//        table1.getTableHeader().setReorderingAllowed(false);
//        table1.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer());
//        table1.setOpaque(true);
//        table1.setBackground(Color.white);
//        table1.setBorder(new EmptyBorder(0, 0, 0, 0));
//        table1.setShowHorizontalLines(true);
//        table1.setRowHeight(40);
//        table1.setShowGrid(false);
//        table1.setShowHorizontalLines(false);
//        table1.setShowVerticalLines(false);
//
//        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                if (column == 7 && row % 2 == 0) {
//                    component.setForeground(new Color(0, 255, 51));
//                } else if (column == 7 && row % 2 != 0) {
//                    component.setForeground(new Color(51, 255, 51));
//                } else {
//                    component.setForeground(Color.BLACK);
//                }
//                if (row % 2 == 0) {
//                    component.setBackground(Color.LIGHT_GRAY);
//                } else {
//                    component.setBackground(Color.WHITE);
//                }
//                return component;
//            }
//        };
//
//        for (int i = 0; i < table1.getColumnCount(); i++) {
//            table1.getColumnModel().getColumn(i).setCellRenderer(renderer);
//        }
//        JFrame frame = new JFrame("Remove Table Border Example");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JScrollPane scrollTable = new JScrollPane();
//        scrollTable.setBorder(null);
//        scrollTable.setViewport(null);
//        renderer.setHorizontalAlignment(HEIGHT);
//        table1.repaint();
//    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogCategory = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jDialogUnit = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jDialogUpdate = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jDialogUpdate2 = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jDialog1 = new javax.swing.JDialog();
        jDialogproduct = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jComboBox4 = new javax.swing.JComboBox<>();
        jDialogupdateproduct = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jComboBox5 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jFind = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("CATEGORY NAME:");

        jTextField2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTextField2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));

        jButton3.setBackground(new java.awt.Color(0, 255, 0));
        jButton3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/outline_new_label_white_24dp.png"))); // NOI18N
        jButton3.setText("ADD");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 0, 0));
        jButton7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/outline_delete_forever_white_24dp.png"))); // NOI18N
        jButton7.setText("REFRESH");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7))
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogCategoryLayout = new javax.swing.GroupLayout(jDialogCategory.getContentPane());
        jDialogCategory.getContentPane().setLayout(jDialogCategoryLayout);
        jDialogCategoryLayout.setHorizontalGroup(
            jDialogCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogCategoryLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jDialogCategoryLayout.setVerticalGroup(
            jDialogCategoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("UNIT NAME:");

        jTextField4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTextField4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0)));

        jButton8.setBackground(new java.awt.Color(0, 255, 0));
        jButton8.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/outline_new_label_white_24dp.png"))); // NOI18N
        jButton8.setText("ADD");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(255, 0, 0));
        jButton9.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/outline_delete_forever_white_24dp.png"))); // NOI18N
        jButton9.setText("REFRESH");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialogUnitLayout = new javax.swing.GroupLayout(jDialogUnit.getContentPane());
        jDialogUnit.getContentPane().setLayout(jDialogUnitLayout);
        jDialogUnitLayout.setHorizontalGroup(
            jDialogUnitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogUnitLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jDialogUnitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jDialogUnitLayout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9))
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jDialogUnitLayout.setVerticalGroup(
            jDialogUnitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogUnitLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jDialogUnitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jDialogUnitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("CATEGORY NAME:");

        jButton2.setBackground(new java.awt.Color(255, 255, 51));
        jButton2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(153, 153, 153));
        jButton2.setText("UPDATE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(255, 51, 51));
        jButton18.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton18.setForeground(new java.awt.Color(153, 153, 153));
        jButton18.setText("REFRESH");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton18))
                .addContainerGap(245, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogUpdateLayout = new javax.swing.GroupLayout(jDialogUpdate.getContentPane());
        jDialogUpdate.getContentPane().setLayout(jDialogUpdateLayout);
        jDialogUpdateLayout.setHorizontalGroup(
            jDialogUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogUpdateLayout.setVerticalGroup(
            jDialogUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setText("UNIT NAME:");

        jButton15.setBackground(new java.awt.Color(255, 255, 51));
        jButton15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton15.setForeground(new java.awt.Color(153, 153, 153));
        jButton15.setText("UPDATE");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton19.setBackground(new java.awt.Color(255, 51, 51));
        jButton19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton19.setForeground(new java.awt.Color(153, 153, 153));
        jButton19.setText("REFRESH");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15)
                    .addComponent(jButton19))
                .addGap(0, 251, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogUpdate2Layout = new javax.swing.GroupLayout(jDialogUpdate2.getContentPane());
        jDialogUpdate2.getContentPane().setLayout(jDialogUpdate2Layout);
        jDialogUpdate2Layout.setHorizontalGroup(
            jDialogUpdate2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogUpdate2Layout.setVerticalGroup(
            jDialogUpdate2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("ITEM NAME");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setText("CATEGORY ID");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setText("UNIT ID");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setText("COST PRICE");

        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setText("SELL PRICE");

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("QUANTITY");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setText("SATATUZ");

        jButton16.setBackground(new java.awt.Color(51, 255, 51));
        jButton16.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton16.setForeground(new java.awt.Color(153, 153, 153));
        jButton16.setText("ADD");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton20.setBackground(new java.awt.Color(255, 0, 0));
        jButton20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton20.setForeground(new java.awt.Color(153, 153, 153));
        jButton20.setText("REFRESH");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))))
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField5)
                    .addComponent(jComboBox2, 0, 357, Short.MAX_VALUE)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField6)
                    .addComponent(jTextField7)
                    .addComponent(jTextField8)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton20))
                    .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 46, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)))
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton16)
                    .addComponent(jButton20))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogproductLayout = new javax.swing.GroupLayout(jDialogproduct.getContentPane());
        jDialogproduct.getContentPane().setLayout(jDialogproductLayout);
        jDialogproductLayout.setHorizontalGroup(
            jDialogproductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogproductLayout.setVerticalGroup(
            jDialogproductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setText("ITEM NAME");

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setText("STATUZ");

        jButton17.setBackground(new java.awt.Color(255, 255, 51));
        jButton17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton17.setForeground(new java.awt.Color(153, 153, 153));
        jButton17.setText("UPDATE");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton21.setBackground(new java.awt.Color(255, 51, 51));
        jButton21.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton21.setForeground(new java.awt.Color(153, 153, 153));
        jButton21.setText("REFRESH");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                        .addComponent(jButton21))
                    .addComponent(jTextField10)
                    .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton17)
                    .addComponent(jButton21))
                .addContainerGap(188, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogupdateproductLayout = new javax.swing.GroupLayout(jDialogupdateproduct.getContentPane());
        jDialogupdateproduct.getContentPane().setLayout(jDialogupdateproductLayout);
        jDialogupdateproductLayout.setHorizontalGroup(
            jDialogupdateproductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogupdateproductLayout.setVerticalGroup(
            jDialogupdateproductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jFind.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jFind.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFindKeyReleased(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jComboBox1.setBorder(null);
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        table1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME ", "CATEGORY", "UNIT", "QUANTITY", "COST PRICE", "SELL PRICE", "STATUS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table1);
        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(0).setMinWidth(50);
            table1.getColumnModel().getColumn(0).setPreferredWidth(50);
            table1.getColumnModel().getColumn(0).setMaxWidth(50);
            table1.getColumnModel().getColumn(1).setMinWidth(250);
            table1.getColumnModel().getColumn(1).setPreferredWidth(250);
            table1.getColumnModel().getColumn(1).setMaxWidth(250);
            table1.getColumnModel().getColumn(2).setMinWidth(150);
            table1.getColumnModel().getColumn(2).setPreferredWidth(150);
            table1.getColumnModel().getColumn(2).setMaxWidth(150);
            table1.getColumnModel().getColumn(3).setMinWidth(100);
            table1.getColumnModel().getColumn(3).setPreferredWidth(100);
            table1.getColumnModel().getColumn(3).setMaxWidth(100);
            table1.getColumnModel().getColumn(4).setMinWidth(100);
            table1.getColumnModel().getColumn(4).setPreferredWidth(100);
            table1.getColumnModel().getColumn(4).setMaxWidth(100);
            table1.getColumnModel().getColumn(5).setMinWidth(100);
            table1.getColumnModel().getColumn(5).setPreferredWidth(100);
            table1.getColumnModel().getColumn(5).setMaxWidth(100);
            table1.getColumnModel().getColumn(6).setMinWidth(100);
            table1.getColumnModel().getColumn(6).setPreferredWidth(100);
            table1.getColumnModel().getColumn(6).setMaxWidth(100);
            table1.getColumnModel().getColumn(7).setMinWidth(150);
            table1.getColumnModel().getColumn(7).setPreferredWidth(150);
            table1.getColumnModel().getColumn(7).setMaxWidth(150);
        }

        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jTable2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NAME"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable2);

        jButton4.setBackground(new java.awt.Color(51, 255, 51));
        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(102, 102, 102));
        jButton4.setBorder(null);
        jButton4.setLabel("ADD");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 0));
        jButton5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(102, 102, 102));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/outline_update_white_24dp.png"))); // NOI18N
        jButton5.setText("UPDATE");
        jButton5.setBorder(null);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 0, 0));
        jButton6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("DELETE");
        jButton6.setBorder(null);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(51, 255, 51));
        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(153, 153, 153));
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(255, 0, 0));
        jButton11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton11.setForeground(new java.awt.Color(153, 153, 153));
        jButton11.setText("DELETE");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(255, 255, 51));
        jButton12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton12.setForeground(new java.awt.Color(153, 153, 153));
        jButton12.setText("UPDATE");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 255, 255));
        jLabel1.setText("CATEGORY");

        jButton10.setBackground(new java.awt.Color(51, 255, 51));
        jButton10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton10.setForeground(new java.awt.Color(153, 153, 153));
        jButton10.setText("ADD");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(255, 51, 51));
        jButton13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton13.setForeground(new java.awt.Color(153, 153, 153));
        jButton13.setText("DELETE");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(255, 255, 51));
        jButton14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton14.setForeground(new java.awt.Color(153, 153, 153));
        jButton14.setText("UPDATE");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 255, 255));
        jLabel5.setText("UNIT");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 204, 255));
        jLabel7.setText("FIND");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29)
                                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31)
                                        .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(51, 51, 51)
                                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel5)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jFind, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addGap(65, 65, 65)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(38, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFind, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton12)
                    .addComponent(jButton11)
                    .addComponent(jButton10)
                    .addComponent(jButton13)
                    .addComponent(jButton14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(807, 807, 807))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:D
        jDialogproduct.setVisible(true);
        jDialogproduct.setSize(500, 500);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jDialogCategory.setVisible(true);
        jDialogCategory.setSize(400, 150);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        String categoryname = jTextField2.getText();
        Category category = new Category(categoryname);
        try {
            boolean newCategory = categoryController.addNewCategory(category);
            if (newCategory) {
                listcategory.add(category);
                tableModel = (DefaultTableModel) jTable1.getModel();
                tableModel.addRow(new Object[]{
                    category.getCategoryid(),
                    category.getCategoryname()
                });
                tableModel.fireTableDataChanged();

            }
            jTextField2.setText("");
            jDialogCategory.setVisible(false);
        } catch (SQLException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // TODO add your handling code here:
            update();

        } catch (SQLException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextField1.setText("");
        jDialogUpdate.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        jDialogUpdate.setVisible(true);
        jDialogUpdate.setSize(400, 150);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        try {
            // TODO add your handling code here:
            deleteButton();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Can not delete category!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        jDialogUnit.setVisible(true);
        jDialogUnit.setSize(400, 150);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        String unitname = jTextField4.getText();
        Unit unit = new Unit(unitname);
        try {
            String unitExist = unitController.getUnitIDByName(unitname);
            if (unitExist != null) {
                JOptionPane.showMessageDialog(this, "Unit already exists. Cannot create a new unit.", "Error", JOptionPane.ERROR_MESSAGE);
                JOptionPane.showMessageDialog(this, "Please create a new Unit again", "Error", JOptionPane.ERROR_MESSAGE);
                jTextField1.setText("");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean newUnit = unitController.addNewUnit(unit);
            if (newUnit) {
                listUnit.add(unit);
                tableModel = (DefaultTableModel) jTable2.getModel();
                tableModel.addRow(new Object[]{
                    unit.getUnitid(),
                    unit.getUnitname()
                });
                tableModel.fireTableDataChanged();

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextField4.setText("");
        jDialogUnit.setVisible(false);

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        try {
            // TODO add your handling code here:
            update3();
        } catch (SQLException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextField3.setText("");
        jDialogUpdate2.setVisible(false);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        jDialogUpdate2.setVisible(true);
        jDialogUpdate2.setSize(400, 150);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        try {
            // TODO add your handling code here:
            deleteButton2();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Can not delete unit!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jFindKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFindKeyReleased
        // TODO add your handling code here:
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        TableRowSorter<DefaultTableModel> tableSorter = new TableRowSorter<>(tableModel);
        table1.setRowSorter(tableSorter);
        tableSorter.setRowFilter(RowFilter.regexFilter(jFind.getText()));
    }//GEN-LAST:event_jFindKeyReleased

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        String itemname = jTextField5.getText();
        String categoryname = jComboBox2.getSelectedItem().toString();
        String unitname = jComboBox3.getSelectedItem().toString();
        String cost = jTextField6.getText();
        String sell = jTextField7.getText();
        String quantity = jTextField8.getText();
        String statuz = jComboBox4.getSelectedItem().toString();

        Category category;
        Unit unit;
        try {

            category = categoryController.getCategoryByname(categoryname);
            System.out.println("categoryid+ " + category.getCategoryid());
            unit = unitController.getUnitByname(unitname);
            System.out.println("unit+ " + unit.getUnitid());
        } catch (SQLException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        BigDecimal costprice;
        BigDecimal sellprice;
        int quantitynew = Integer.parseInt(quantity);
        try {
            Statuz statusid;
            statusid = statuzController.getStatuzByName(statuz);
            category = categoryController.getCategoryByname(categoryname);
            unit = unitController.getUnitByname(unitname);
            costprice = new BigDecimal(decimalFormat.parse(cost).doubleValue());
            sellprice = new BigDecimal(decimalFormat.parse(sell).doubleValue());
            Inventory inventory = new Inventory(itemname, costprice, sellprice, quantitynew, statusid, category, unit);

            //
            boolean newProduct = inventoryController.addNewInventory(inventory);
            if (newProduct) {
                listInventorys.add(inventory);
                tableModel = (DefaultTableModel) table1.getModel();
                tableModel.addRow(new Object[]{
                    inventory.getItemid(),
                    inventory.getItemname(),
                    inventory.getCategoryid().getCategoryname(),
                    inventory.getUnitid().getUnitname(),
                    inventory.getCostPrice(),
                    inventory.getSellPrice(),
                    inventory.getInventoryquantity(),
                    inventory.getStatusid().getStatusname()
                });
                tableModel.fireTableDataChanged();

            }
            showProduct();

            //
        } catch (SQLException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jComboBox4.addItem("All");
        jDialogproduct.setVisible(false);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        jDialogupdateproduct.setVisible(true);
        jDialogupdateproduct.setSize(400, 250);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        try {
            // TODO add your handling code here:
            update2();
        } catch (SQLException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextField10.setText("");
        jComboBox5.addItem("All");
        jDialogupdateproduct.setVisible(false);

    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            deleteButton3();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "can not delete product!");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        jTextField2.setText("");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        jTextField4.setText("");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        jTextField1.setText("");
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        jTextField3.setText("");
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jComboBox4.addItem("All");

    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
        jTextField10.setText("");
        jComboBox5.addItem("All");
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
//        formatTextField(jTextField6);

    }//GEN-LAST:event_jTextField6KeyReleased
//UPDATE UNIT

    public void update3() throws SQLException, ClassNotFoundException {
        DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        int rowIndex = jTable2.getSelectedRow();
        if (rowIndex >= 0 && rowIndex < listUnit.size()) {
            int unitid = (int) tableModel.getValueAt(rowIndex, 0);
            String unitname = jTextField3.getText();

            Unit unit = listUnit.get(rowIndex);
            if (unitname == null) {
                unit.setUnitid(unitid);
                unit.setUnitname(unitname);
            } else {
                unit.setUnitid(unitid);
                unit.setUnitname(unitname);
            }
            boolean updateSave = unitController.updateUnit(unit);
            if (updateSave) {
                JOptionPane.showMessageDialog(null, "Update is successful");
                tableModel.setValueAt(unitname, rowIndex, 1);
            } else {
                JOptionPane.showMessageDialog(null, "Update is failed");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a customer from the table.");
        }

    }
//  
    //

    public void update2() throws SQLException, ClassNotFoundException {
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        int rowIndex = table1.getSelectedRow();
        if (rowIndex >= 0 && rowIndex < listInventorys.size()) {
            int itemid = (int) tableModel.getValueAt(rowIndex, 0);
            String itemname = jTextField10.getText();
            String statuz = jComboBox5.getSelectedItem().toString();
            Statuz Statusid = statuzController.getStatuzByName(statuz);
            Inventory inventory = listInventorys.get(rowIndex);
            if (itemname == null) {
                inventory.setItemid(itemid);
                inventory.setItemname(itemname);
                inventory.setStatusid(Statusid);
            } else {
                inventory.setItemid(itemid);
                inventory.setItemname(itemname);
                inventory.setStatusid(Statusid);
            }
            boolean updateSave = inventoryController.updateInven(inventory);
            if (updateSave) {
                JOptionPane.showMessageDialog(null, "Update is successful");
                tableModel.setValueAt(itemname, rowIndex, 1);
                tableModel.setValueAt(statuz, rowIndex, 7);
            } else {
                JOptionPane.showMessageDialog(null, "Update is failed");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a customer from the table.");
        }

    }

    //
//UPDATE category
    public void update() throws SQLException, ClassNotFoundException {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        int rowIndex = jTable1.getSelectedRow();
        if (rowIndex >= 0 && rowIndex < listcategory.size()) {
            int categoryid = (int) tableModel.getValueAt(rowIndex, 0);
            String categoryname = jTextField1.getText();
            Category category = listcategory.get(rowIndex);
            if (categoryname == null) {
                category.setCategoryid(categoryid);
                category.setCategoryname(categoryname);
            } else {
                category.setCategoryid(categoryid);
                category.setCategoryname(categoryname);
            }
            boolean updateSave = categoryController.updateCategory(category);
            if (updateSave) {
                JOptionPane.showMessageDialog(null, "Update is successful");
                tableModel.setValueAt(categoryname, rowIndex, 1);
            } else {
                JOptionPane.showMessageDialog(null, "Update is failed");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a customer from the table.");
        }

    }
//
//DELETE CATEGORY

    public void deleteButton() throws SQLException, ClassNotFoundException {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        tableModel = (DefaultTableModel) jTable1.getModel();
        int rowIndex = jTable1.getSelectedRow();
        if (rowIndex >= 0 && rowIndex < listcategory.size()) {
            int categoryid = (int) tableModel.getValueAt(rowIndex, 0);
            Category category = listcategory.get(rowIndex);
            category.setCategoryid(categoryid);

            boolean deleteID = categoryController.deleteCategoryByID(category.getCategoryid());
            if (deleteID) {
                JOptionPane.showMessageDialog(null, "Delete is successful");
                listcategory.remove(rowIndex);
                tableModel.removeRow(rowIndex);
                tableModel.fireTableDataChanged();

            } else {
                JOptionPane.showMessageDialog(null, "Delete is failed");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No categogy selected");
        }
    }
//
//DELETE UNIT

    public void deleteButton2() throws SQLException, ClassNotFoundException {
        DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        tableModel = (DefaultTableModel) jTable2.getModel();
        int rowIndex = jTable2.getSelectedRow();
        if (rowIndex >= 0 && rowIndex < listUnit.size()) {
            int uitid = (int) tableModel.getValueAt(rowIndex, 0);
            Unit unit = listUnit.get(rowIndex);
            unit.setUnitid(uitid);

            boolean deleteID = unitController.deleteUnitByID(unit.getUnitid());
            if (deleteID) {
                JOptionPane.showMessageDialog(null, "Delete is successful");
                listUnit.remove(rowIndex);
                tableModel.removeRow(rowIndex);
                tableModel.fireTableDataChanged();
            } else {
                JOptionPane.showMessageDialog(null, "Delete is failed");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No unit selected");
        }
    }
//

    public void deleteButton3() throws SQLException, ClassNotFoundException {
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        tableModel = (DefaultTableModel) table1.getModel();
        int rowIndex = table1.getSelectedRow();
        if (rowIndex >= 0 && rowIndex < listInventorys.size()) {
            int itemid = (int) tableModel.getValueAt(rowIndex, 0);
            Inventory inventory = listInventorys.get(rowIndex);
            inventory.setItemid(itemid);
            System.out.println("itemid+ " + itemid);
            boolean deleteID = inventoryController.deleteInventoryByID(itemid);
            if (deleteID) {
                JOptionPane.showMessageDialog(null, "Delete is successful");
                listInventorys.remove(rowIndex);
                tableModel.removeRow(rowIndex);
                tableModel.fireTableDataChanged();
            } else {
                JOptionPane.showMessageDialog(null, "Delete is failed");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No unit selected");
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialogCategory;
    private javax.swing.JDialog jDialogUnit;
    private javax.swing.JDialog jDialogUpdate;
    private javax.swing.JDialog jDialogUpdate2;
    private javax.swing.JDialog jDialogproduct;
    private javax.swing.JDialog jDialogupdateproduct;
    private javax.swing.JTextField jFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTable table1;
    // End of variables declaration//GEN-END:variables
}
