/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.salemanager;

import com.formdev.flatlaf.ui.FlatTableUI;
import com.formdev.flatlaf.ui.FlatTextFieldUI;
import com.sims.controller.ExceptionController;
import com.sims.controller.ExceptionsDetailController;
import com.sims.controller.InventoryController;
import com.sims.entitynew.Bill;
import com.sims.entitynew.Exceptions;
import com.sims.entitynew.Exceptionsdetail;
import com.sims.entitynew.Itemstakeback;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import static java.awt.image.ImageObserver.HEIGHT;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFrame;
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
public class EXCEPTIONSJPanel extends javax.swing.JPanel {

    private DefaultTableModel tableModel;
    private ExceptionController exceptionController;
    private List<Exceptions> listExceptions;
    private InventoryController inventoryController;
    private ExceptionsDetailController exceptionsDetailController;
    private List<Exceptionsdetail> listExceptionsDetail;

    public EXCEPTIONSJPanel() throws ClassNotFoundException, SQLException {
        exceptionController = new ExceptionController();
        inventoryController = new InventoryController();
        exceptionsDetailController=new ExceptionsDetailController();
        exceptionController.jdbcConnectDB();
        listExceptions = exceptionController.getAllException();
        listExceptionsDetail=exceptionsDetailController.getAllExceptionsDetail();
        initComponents();
        createExceptions.getContentPane().setBackground(new Color(0, 0, 0, 0));
        showExceptions();
        showCombobox1();
        jComboboxFilterBIll.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedValue = jComboboxFilterBIll.getSelectedItem().toString();
                    if (selectedValue.equals("All")) {
                        jComboboxFilterBIll.setSelectedIndex(0);
                        jDateChooserFilter.setDate(null);
                        filter("All");
                    } else {
                        filter(selectedValue);
                    }
                }
            }
        });
        statisticEXCEPTIONS();
        showExceptionsDetail();

    }

    public void showExceptions() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        try {
            for (Exceptions exceptions : listExceptions) {

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

    public void showExceptionsDetail() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        try {
            for (Exceptionsdetail exceptionsDetail : listExceptionsDetail) {

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

    public void filter(String evt) {
        DefaultTableModel table1 = (DefaultTableModel) table.getModel();
        DefaultTableModel exceptionsDetailTableModel = (DefaultTableModel) jTable1.getModel();
        if (evt != null) {
            TableRowSorter<DefaultTableModel> tableSort = new TableRowSorter<>(table1);
            TableRowSorter<DefaultTableModel> detailSort = new TableRowSorter<>(exceptionsDetailTableModel);
            table.setRowSorter(tableSort);
            jTable1.setRowSorter(detailSort);
            if (!evt.equals("All")) {
                RowFilter<DefaultTableModel, Object> filter = RowFilter.numberFilter(
                        RowFilter.ComparisonType.EQUAL, Integer.parseInt(evt), 0);
                tableSort.setRowFilter(filter);
                RowFilter<DefaultTableModel, Object> filterDetail = RowFilter.numberFilter(
                        RowFilter.ComparisonType.EQUAL, Integer.parseInt(evt), 0);
                detailSort.setRowFilter(filterDetail);
            } else {
                tableSort.setRowFilter(null);
                detailSort.setRowFilter(null);
            }

        }
    }

    public void showCombobox1() {

        jComboboxFilterBIll.removeAllItems();
        jComboboxFilterBIll.addItem("All");
        for (Exceptions exceptions : listExceptions) {
            jComboboxFilterBIll.addItem(String.valueOf(exceptions.getExceptionId()));
        }
        String query = jComboboxFilterBIll.getSelectedItem().toString();
        filter(query);

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

    public void statisticEXCEPTIONS() {
//        List<Integer> totalSum = new ArrayList<>();
        int[] statisticArray = new int[table.getRowCount()];
        int count = 0;

        for (int i = 0; i < listExceptions.size(); i++) {
            int numberRow = (int) table.getValueAt(i, 0);
            count++;
            System.out.println("count+ " + count);
            System.out.println("i+ " + i);
        }

        jLabel5.setText(String.valueOf(count));
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
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jDateChooserFilter = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jComboboxFilterBIll = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(255, 0, 0));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        table.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DATE", "EMPLOYEE"
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
                "ID", "ITEM", "TYPE"
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

        jTabbedPane1.addTab("EXCEPTIONS DETAIL", jScrollPane2);

        jButton1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-pay-date-25.png"))); // NOI18N
        jButton1.setText("SEARCH");
        jButton1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Filter ID Number");

        jLabel4.setText("THE NUMBER OF EXCEPTIONS:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jComboboxFilterBIll, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooserFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooserFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboboxFilterBIll)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(541, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel tableModelBill = (DefaultTableModel) table.getModel();
        DefaultTableModel tableModelExcDetail = (DefaultTableModel) jTable1.getModel();
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
        Date selectedDate = jDateChooserFilter.getDate();
        String dateString = sDate.format(selectedDate);

        System.out.println(selectedDate);
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(tableModelBill);
        table.setRowSorter(trs);
        TableRowSorter<DefaultTableModel> trsDetail = new TableRowSorter<>(tableModelExcDetail);
        jTable1.setRowSorter(trsDetail);

        Object selectedValueObject = jComboboxFilterBIll.getSelectedItem();
        if (selectedValueObject != null) {
            String selectedValue = selectedValueObject.toString();
            for (int i = 0; i < listExceptions.size(); i++) {
                if ("All".equals(selectedValue) || Integer.toString(i).equals(selectedValue)) {
                    jDateChooserFilter.setDate(null);
                }
            }
        }

        if (selectedDate != null) {
            RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(dateString.trim(), 1);
            trs.setRowFilter(filter);

            List<Integer> excpetionsList = new ArrayList<>();
            for (int i = 0; i < table.getRowCount(); i++) {
                excpetionsList.add((Integer) table.getValueAt(i, 0));
            }

            RowFilter<DefaultTableModel, Object> filterDetail = RowFilter.orFilter(excpetionsList.stream()
                    .map(id -> RowFilter.regexFilter(String.valueOf(id), 0))
                    .collect(Collectors.toList()));
                    trsDetail.setRowFilter(filterDetail);
        }
        jComboboxFilterBIll.setSelectedIndex(-1);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog createExceptions;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboboxFilterBIll;
    private com.toedter.calendar.JDateChooser jDateChooserFilter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
