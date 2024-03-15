/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.sims.salemen;

import com.sims.controller.BillController;
import com.sims.controller.BillDetailController;
import com.sims.controller.CustomerController;
import com.sims.controller.ImportDetailController;
import com.sims.controller.ImportItemController;
import com.sims.controller.InventoryController;
import com.sims.controller.ItemTakeBackController;
import com.sims.controller.ItemTakeBackDetailController;
import com.sims.entitynew.BILLDetail;
import com.sims.entitynew.Bill;
import com.sims.entitynew.Customer;
import com.sims.entitynew.Importdetail;
import com.sims.entitynew.Itemstakeback;
import com.sims.entitynew.Itemstakebackdetail;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ITEMTAKEBACK extends javax.swing.JPanel {

    private BillController billController;
    private List<Bill> listBill;
    private List<BILLDetail> listbillBillDetail;
    private BillDetailController billDetailController;
    Map<Integer, BigDecimal> billAmountMap = new HashMap<>();
    private List<Itemstakeback> listItemstakebacks;
    private ItemTakeBackController itemTakeBackController;
    private ItemTakeBackDetailController itemTakeBackDetailController;
    private List<Itemstakebackdetail> listItemTakItemstakebackdetails;
    private BigDecimal totalAmount;

    public ITEMTAKEBACK() throws SQLException, ClassNotFoundException {
        itemTakeBackController = new ItemTakeBackController();
        itemTakeBackDetailController = new ItemTakeBackDetailController();
        billController = new BillController();
        listBill = billController.getAllBill();
        billDetailController = new BillDetailController();

        initComponents();
        showItemTakeBack();
        showItemDetail();
        DefaultTableModel productTableModel = (DefaultTableModel) jTableBillDetail.getModel();
        DefaultTableModel returnTable = (DefaultTableModel) jTableReturn.getModel();
        jTableBillDetail.addMouseListener(new MouseAdapter() {

            Object[] rowData = new Object[10];

            @Override
            public void mouseClicked(MouseEvent e) {
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
                if (e.getClickCount() == 2) {
                    int rowIndex = jTableBillDetail.getSelectedRow();
                    if (rowIndex >= 0 && rowIndex < productTableModel.getRowCount()) {
                        rowData[0] = productTableModel.getValueAt(rowIndex, 0);
                        rowData[1] = productTableModel.getValueAt(rowIndex, 1);
                        rowData[2] = productTableModel.getValueAt(rowIndex, 2);
                        rowData[3] = productTableModel.getValueAt(rowIndex, 3);
                        rowData[4] = productTableModel.getValueAt(rowIndex, 4);
                        rowData[5] = 0;
                        rowData[6] = productTableModel.getValueAt(rowIndex, 6);
                        String sellpriceString = productTableModel.getValueAt(rowIndex, 4).toString();
                        BigDecimal sellPrice;
                        try {
                            sellPrice = new BigDecimal(decimalFormat.parse(sellpriceString).doubleValue());

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
                        returnTable.addRow(rowData);
                    }
                }

                boolean productExists = false;
                int rowCount = returnTable.getRowCount();
                for (int j = 0; j < rowCount; j++) {
                    Object importValue = returnTable.getValueAt(j, 0);
                    if (importValue != null && importValue.equals(rowData[0])) {
                        productExists = true;
                        break;
                    }
                }

                if (!productExists) {
                    int row = returnTable.getRowCount();
                    returnTable.addRow(rowData);
                }
            }
        });
        jTableReturn.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getColumn() == 7) {
//                            showMoney();
                }
                try {
                    jTablePurchaseChange(e);
                } catch (ParseException ex) {
                    Logger.getLogger(SELLING.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        jComboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedValue = jComboBox1.getSelectedItem().toString();
                    filter(selectedValue);
                    if (selectedValue.equals("All")) {
                        jComboBox1.setSelectedIndex(0);
                        jDateChooser1.setDate(null);
                        filter("All");
                    } else {
                        filter(selectedValue);
                    }
                }

            }
        });

        showCombobox1();
    }

    private void jTablePurchaseChange(TableModelEvent evt) throws ParseException {
        int row = evt.getFirstRow();
        int column = evt.getColumn();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");

        if (column == 5 || column == 6) {
            String quantity = jTableReturn.getValueAt(row, 5).toString();
            String discount = jTableReturn.getValueAt(row, 6).toString();

            BigDecimal quantityDecimal = new BigDecimal(decimalFormat.parse(quantity).doubleValue());
            BigDecimal discountDecimal = new BigDecimal(decimalFormat.parse(discount).doubleValue());

            BigDecimal sellPrice = new BigDecimal(decimalFormat.parse(jTableReturn.getValueAt(row, 4).toString()).doubleValue());
            BigDecimal discountPercentage = (discountDecimal.compareTo(BigDecimal.ONE) > 0)
                    ? discountDecimal.divide(new BigDecimal(100))
                    : discountDecimal;

            BigDecimal amount = sellPrice.multiply(quantityDecimal).multiply(BigDecimal.ONE.subtract(discountPercentage));

            jTableReturn.setValueAt(decimalFormat.format(amount), row, 7);

            jTableReturn.repaint();
        }
    }

    public void showBillDetail() throws SQLException, ClassNotFoundException {
        DefaultTableModel tableModel = (DefaultTableModel) jTableBillDetail.getModel();
        tableModel.setRowCount(0);
        String idBill = jTextField4.getText().trim();
        int billID = Integer.parseInt(idBill);
        listbillBillDetail = billDetailController.getAllBillDetailByID(billID);
        for (BILLDetail listBILLDetail : listbillBillDetail) {
            BigDecimal sellPrice = listBILLDetail.getInventory().getSellPrice();

            int discount = listBILLDetail.getDiscount();
            BigDecimal discountPercentage = new BigDecimal(discount).divide(new BigDecimal(100));

            BigDecimal amount;

            if (discount > 0) {
                amount = sellPrice.multiply(new BigDecimal(listBILLDetail.getQuantity())).multiply(BigDecimal.ONE.subtract(discountPercentage));
            } else {
                amount = sellPrice.multiply(new BigDecimal(listBILLDetail.getQuantity()));
            }

            DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
            if (billAmountMap.containsKey(billID)) {

                BigDecimal currentAmount = billAmountMap.get(billID);
                billAmountMap.put(billID, currentAmount.add(amount));
            } else {

                billAmountMap.put(billID, amount);
            }
            tableModel.addRow(new Object[]{
                listBILLDetail.getInventory().getItemid(),
                listBILLDetail.getInventory().getItemname(),
                listBILLDetail.getInventory().getCategoryid().getCategoryname(),
                listBILLDetail.getInventory().getUnitid().getUnitname(),
                decimalFormat.format(listBILLDetail.getInventory().getSellPrice()),
                listBILLDetail.getQuantity(),
                listBILLDetail.getDiscount(),
                decimalFormat.format(amount)
            });
        }

    }

    public void showItemTakeBack() throws SQLException, ClassNotFoundException {
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        listItemstakebacks = itemTakeBackController.getAllItemTakeBack();
        for (Itemstakeback list : listItemstakebacks) {
            tableModel.addRow(new Object[]{
                list.getItemtkid(),
                list.getBillid().getBillID(),
                list.getCustomerid().getCustomerName(),
                list.getCanceldate(),});
        }
    }

//    public void showCustomer() throws SQLException, ClassNotFoundException {
//        int customerId = Integer.parseInt(jTextField3.getText());
//        BillController billController = new BillController();
//        int customerID = billController.getBillByIDIntCustomer(customerId);
//        CustomerController customerController = new CustomerController();
//        Customer customer = customerController.getCustomerByID(customerId);
//        jTextField2.setText(customer.getCustomerName());
//        jTextField2.disable();
//    }

    public int getCustomerIdByName(String customerName) throws SQLException, ClassNotFoundException {
        CustomerController customerController = new CustomerController();
        return customerController.getCustomerIDByName(customerName);
    }

    public int insertITemTakeBack(int BillID, int CustomerID) throws SQLException, ClassNotFoundException {
        ItemTakeBackController itemTakeBackController = new ItemTakeBackController();
        int insertItemTKB = itemTakeBackController.addNewBill2Parameter(BillID, CustomerID);
        return insertItemTKB;
    }

    public boolean insertItemTKBDetail(int itemTKBID, int itemID, int quantity) throws SQLException, ClassNotFoundException {
        ItemTakeBackDetailController imteBackDetailController = new ItemTakeBackDetailController();
        boolean insertItemTakeBackDetail = imteBackDetailController.addNewIMPORTITEMS3Parameter(itemTKBID, itemID, quantity);
        return insertItemTakeBackDetail;
    }

    public void showBillID() throws SQLException, ClassNotFoundException {
        int BillID = Integer.parseInt(jTextField3.getText());
        jTextField4.setText(String.valueOf(BillID));
        jTextField4.disable();
        int idFindName=Integer.parseInt(jTextField4.getText());
        String customerName=billController.getCustomerNameByBillID(idFindName);
        jTextField2.setText(customerName);
        jTextField2.disable();

    }

    public void showItemDetail() throws SQLException, ClassNotFoundException {
        DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        tableModel.setRowCount(0);
        listItemTakItemstakebackdetails = itemTakeBackDetailController.getAllItemTakeBackDetailsByItemTakeBackID();
        for (Itemstakebackdetail itemstakebackDetail : listItemTakItemstakebackdetails) {
            BigDecimal sellPrice = itemstakebackDetail.getInventory().getSellPrice();
            BigDecimal amount = sellPrice.multiply(new BigDecimal(itemstakebackDetail.getQuantity()));

            tableModel.addRow(new Object[]{
                itemstakebackDetail.getItemstakeback().getItemtkid(),
                itemstakebackDetail.getInventory().getItemname(),
                decimalFormat.format(sellPrice),
                itemstakebackDetail.getQuantity(),
                decimalFormat.format(amount),
                itemstakebackDetail.getStatuz()

            });
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialogITTB = new javax.swing.JDialog();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jDialogReturn = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableBillDetail = new javax.swing.JTable();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableReturn = new javax.swing.JTable();
        jTextField4 = new javax.swing.JTextField();
        jDialogUpDate = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jButton11 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jTotalAmount3 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();

        jTextField3.setBackground(new java.awt.Color(255, 255, 255));
        jTextField3.setForeground(new java.awt.Color(0, 0, 0));
        jTextField3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(0, 0, 0), new java.awt.Color(255, 255, 255)));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("FILL ID BILL:");

        jButton4.setBackground(new java.awt.Color(51, 255, 51));
        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-check-all-20.png"))); // NOI18N
        jButton4.setText("ACCEPT");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 0, 0));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-cancel-20.png"))); // NOI18N
        jButton5.setText("CANCEL");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialogITTBLayout = new javax.swing.GroupLayout(jDialogITTB.getContentPane());
        jDialogITTB.getContentPane().setLayout(jDialogITTBLayout);
        jDialogITTBLayout.setHorizontalGroup(
            jDialogITTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogITTBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialogITTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jDialogITTBLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                    .addGroup(jDialogITTBLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );
        jDialogITTBLayout.setVerticalGroup(
            jDialogITTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialogITTBLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jDialogITTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jDialogITTBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("BILL ID");

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 255, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/icons8-new-product-20.png"))); // NOI18N
        jButton2.setText("RETURN");
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
        jLabel4.setText("CUSTOMER:");

        jTableBillDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Category", "Unit", "SellPrice", "Quantity", "Discount", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableBillDetail);

        jTabbedPane2.addTab("Bill Detail", jScrollPane3);

        jTableReturn.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Category", "Unit", "SellPrice", "Quantity", "Discount", "Refund"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTableReturn);

        jTabbedPane3.addTab("LIST ITEMS HAVE RETURNED", jScrollPane4);

        jTextField4.setForeground(new java.awt.Color(0, 0, 0));
        jTextField4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 364, Short.MAX_VALUE))
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
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
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jDialogReturnLayout = new javax.swing.GroupLayout(jDialogReturn.getContentPane());
        jDialogReturn.getContentPane().setLayout(jDialogReturnLayout);
        jDialogReturnLayout.setHorizontalGroup(
            jDialogReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialogReturnLayout.setVerticalGroup(
            jDialogReturnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jTextField5.setForeground(new java.awt.Color(0, 0, 0));
        jTextField5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("QUANTITY:");

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

        jTextField6.setForeground(new java.awt.Color(0, 0, 0));
        jTextField6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton9))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(94, 94, 94))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jTable1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "BILL ID", "CUSTOMER ", "DATE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTabbedPane1.addTab("LIST ITEM TAKE BACK", jScrollPane1);

        jTable2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ITEMTKID", "NAME", "SELLPRICE", "QUANTITY", "REFUND", "STATUS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1018, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("LIST ITEM RETURN", jPanel1);

        jButton1.setBackground(new java.awt.Color(51, 255, 51));
        jButton1.setFont(new java.awt.Font("Arial   ", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sims/icons/outline_new_label_white_24dp.png"))); // NOI18N
        jButton1.setText("CREATE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("ID NUMBER FILTER");

        jButton11.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(0, 0, 0));
        jButton11.setText("Date Filter");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(255, 51, 0));

        jTotalAmount3.setBackground(new java.awt.Color(255, 255, 255));
        jTotalAmount3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jTotalAmount3.setForeground(new java.awt.Color(0, 0, 0));
        jTotalAmount3.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTotalAmount3.setBorder(null);

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("TOTAL REFUND:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTotalAmount3, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTotalAmount3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel1)
                            .addGap(72, 72, 72)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 143, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jDialogITTB.setVisible(true);
        jDialogITTB.setSize(400, 200);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            DefaultTableModel importTable = (DefaultTableModel) jTableReturn.getModel();
            String idFind=jTextField4.getText().trim();
           int BillID = Integer.parseInt(idFind);
            jTextField4.setText(String.valueOf(BillID));
            jTextField4.disable();
            String CustomerName = jTextField2.getText();
            int billIDFind = billController.getBillByIDInt(BillID);
            int customerId = getCustomerIdByName(CustomerName);
            int itemTKID = insertITemTakeBack(billIDFind, customerId);

            for (int i = 0; i < importTable.getRowCount(); i++) {
                Object itemIdObj = importTable.getValueAt(i, 0);
                Object quantityObj = importTable.getValueAt(i, 5);

                if (itemIdObj != null && quantityObj != null) {
                    int itemId = Integer.parseInt(itemIdObj.toString());
                    System.out.println("itemId: " + itemId);

                    int quantity = Integer.parseInt(quantityObj.toString());
                    System.out.println("quantity: " + quantity);

                    boolean result = insertItemTKBDetail(itemTKID, itemId, quantity);
                }
            }

            JOptionPane.showMessageDialog(jButton2, "Item return is successful");
            jDialogReturn.setVisible(false);
            importTable.setRowCount(0);
            showItemTakeBack();
            showItemDetail();
        } catch (SQLException ex) {
            Logger.getLogger(ITEMTAKEBACK.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ITEMTAKEBACK.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException ex)
        {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DefaultTableModel purchaseTableModel = (DefaultTableModel) jTableReturn.getModel();
        int rowIndex = jTableReturn.getSelectedRow();
        if (rowIndex >= 0) {
            purchaseTableModel.removeRow(rowIndex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            jDialogReturn.setVisible(true);
            jDialogITTB.setVisible(false);
            jDialogReturn.setSize(1200, 800);
            showBillID();
            showBillDetail();
//            showCustomer();
            jTextField3.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(ITEMTAKEBACK.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ITEMTAKEBACK.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed

    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
        int indexRow = jTable2.getSelectedRow();
        if (indexRow >= 0 && indexRow < listItemTakItemstakebackdetails.size()) {
            Itemstakebackdetail selectedItemTakeBackDetail = listItemTakItemstakebackdetails.get(indexRow);
            int itemTakebackID = selectedItemTakeBackDetail.getItemstakeback().getItemtkid();
            String itemDetailName = selectedItemTakeBackDetail.getInventory().getItemname();
            Itemstakebackdetail selectedimportDetail = listItemTakItemstakebackdetails.get(indexRow);
            int importid = selectedimportDetail.getItemstakeback().getItemtkid();
            int quantity = selectedimportDetail.getQuantity();
            String quantityString = String.valueOf(quantity);
            jTextField3.setText(quantityString);
            jTextField6.setText(itemDetailName);
            jTextField6.disable();
            jDialogUpDate.setVisible(true);
            jDialogUpDate.setSize(400, 200);

        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        DefaultTableModel tableModelImport = (DefaultTableModel) jTable1.getModel();
        DefaultTableModel tableModelImportDetail = (DefaultTableModel) jTable2.getModel();

        int[] selectedRows = jTable1.getSelectedRows();

        if (selectedRows.length > 0) {
            int choice = JOptionPane.showConfirmDialog(jButton5, "Are you sure you want to delete selected import document?", "Delete import document", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    try {
                        int rowIndex = selectedRows[i];
                        int itemTKB = (int) tableModelImport.getValueAt(rowIndex, 0);

                        // Xóa các chi tiết nhập hàng trước
                        //                    importDetailController.deleteImportDetailByImportID(importID);
                        // Xóa hóa đơn nhập hàng
                        boolean deleteSuccess = itemTakeBackDetailController.deleteItemTakeBackDetailByID(itemTKB);

                        if (deleteSuccess) {
                            JOptionPane.showMessageDialog(jButton5, "Delete is successful");
                            tableModelImport.removeRow(rowIndex);
                            // Cập nhật bảng chi tiết nhập hàng
                            tableModelImportDetail.setRowCount(0); // Xóa toàn bộ dữ liệu trong bảng chi tiết
                            showItemDetail();
                        } else {
                            JOptionPane.showMessageDialog(jButton5, "Delete failed");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ITEMTAKEBACK.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ITEMTAKEBACK.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

    }//GEN-LAST:event_jButton7ActionPerformed

    public int getItemIdByName(String itemsName) throws SQLException, ClassNotFoundException {
        InventoryController inventoryController = new InventoryController();
        return inventoryController.getItemNameByName(itemsName);
    }

    public void updateTotalAmountBigDecimal(String billID) {
        DefaultTableModel tableModelDetail = (DefaultTableModel) jTable2.getModel();
        DefaultTableModel tableModelItem = (DefaultTableModel) jTable1.getModel();
        totalAmount = BigDecimal.ZERO;
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");

        int rowCountItem = tableModelItem.getRowCount();
        int rowCountDetail = tableModelDetail.getRowCount();

        for (int i = 0; i < rowCountDetail; i++) {

            String billID1 = tableModelDetail.getValueAt(i, 0).toString();
            String amountStr = tableModelDetail.getValueAt(i, 4).toString();

            try {
                BigDecimal amount = new BigDecimal(decimalFormat.parse(amountStr).doubleValue());

                if (billID.equals(billID1)) {
                    totalAmount = totalAmount.add(amount);
                    System.out.println("billID1+ " + billID1);
                    System.out.println("totalAmount1111+ " + totalAmount);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        jTotalAmount3.setText(decimalFormat.format(totalAmount));
        jTotalAmount3.setEnabled(false);
    }

    public void showCombobox1() {

        jComboBox1.removeAllItems();
        jComboBox1.addItem("All");
        for (Itemstakeback itemstakeback : listItemstakebacks) {
            jComboBox1.addItem(String.valueOf(itemstakeback.getItemtkid()));
        }
        String query = jComboBox1.getSelectedItem().toString();
        filter(query);

    }

    public void filter(String evt) {
        DefaultTableModel table1 = (DefaultTableModel) jTable1.getModel();
        DefaultTableModel tableDetail = (DefaultTableModel) jTable2.getModel();
        if (evt != null) {
            TableRowSorter<DefaultTableModel> tableSort = new TableRowSorter<>(table1);
            jTable1.setRowSorter(tableSort);

            TableRowSorter<DefaultTableModel> tableDetailSort = new TableRowSorter<>(tableDetail);
            jTable2.setRowSorter(tableDetailSort);

            if (!evt.equals("All")) {
                RowFilter<DefaultTableModel, Object> filter = RowFilter.numberFilter(
                        RowFilter.ComparisonType.EQUAL, Integer.parseInt(evt), 0);
                tableSort.setRowFilter(filter);

                RowFilter<DefaultTableModel, Object> filterDetail = RowFilter.numberFilter(
                        RowFilter.ComparisonType.EQUAL, Integer.parseInt(evt), 0);
                tableDetailSort.setRowFilter(filterDetail);
            } else {
                tableSort.setRowFilter(null);
                tableDetailSort.setRowFilter(null);
            }

        }
        updateTotalAmount(evt);
    }

    private void updateTotalAmount(String selectedBillid) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
        totalAmount = BigDecimal.ZERO;

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String amountStr = tableModel.getValueAt(i, 4).toString();
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

        jTotalAmount3.setText(decimalFormat.format(totalAmount));
        jTotalAmount3.disable();
    }


    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
            int rowIndex = jTable2.getSelectedRow();
            jTextField4.disable();
            String itemname;
            int itemTKID;

            if (rowIndex >= 0 && rowIndex < listItemTakItemstakebackdetails.size()) {
                try {
                    String quantityString = jTextField5.getText();
                    String itemString = jTextField6.getText();

                    int quantityChange = Integer.parseInt(quantityString);

                    int selectedRow = jTable2.getSelectedRow();
                    int itemID = getItemIdByName(itemString);
                    System.out.println("itemID: " + itemID);
                    itemTKID = (int) jTable2.getValueAt(selectedRow, 0);
                    System.out.println("itemTKID: " + itemTKID);
                    if (itemTKID != -1) {
                        boolean update = itemTakeBackDetailController.updateImportDetailByIDAndQuantity(itemTKID, quantityChange, itemID);
                        if (update) {
                            JOptionPane.showMessageDialog(this, "Update is successful");
                        } else {
                            JOptionPane.showMessageDialog(this, "Update is failed");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Import not found for given quantity and item");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Update is failed");
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Update is failed");
                }
            }
            showItemDetail();
            jDialogUpDate.setVisible(false);
            jTextField5.setText("");
            jTextField6.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(ITEMTAKEBACK.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ITEMTAKEBACK.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jDialogUpDate.setVisible(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        DefaultTableModel tableModelBill = (DefaultTableModel) jTable1.getModel();
        DefaultTableModel tableModelExcDetail = (DefaultTableModel) jTable2.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
        Date selectedDate = jDateChooser1.getDate();
        String dateString = sDate.format(selectedDate);

        System.out.println(selectedDate);
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(tableModelBill);
        jTable1.setRowSorter(trs);
        TableRowSorter<DefaultTableModel> trsDetail = new TableRowSorter<>(tableModelExcDetail);
        jTable2.setRowSorter(trsDetail);

        // Làm sạch totalAmount trước khi tính toán lại
        totalAmount = BigDecimal.ZERO;

        Object selectedValueObject = jComboBox1.getSelectedItem();
        if (selectedValueObject != null) {
            String selectedValue = selectedValueObject.toString();
            for (int i = 0; i < listItemstakebacks.size(); i++) {
                if ("All".equals(selectedValue) || Integer.toString(i).equals(selectedValue)) {
                    jDateChooser1.setDate(null);
                }
            }
        }

        if (selectedDate != null) {
            RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(dateString.trim(), 3);
            trs.setRowFilter(filter);

            List<Integer> itemList = new ArrayList<>();
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                itemList.add((Integer) jTable1.getValueAt(i, 0));
            }

            RowFilter<DefaultTableModel, Object> filterDetail = RowFilter.orFilter(itemList.stream()
                    .map(id -> RowFilter.regexFilter(String.valueOf(id), 0))
                    .collect(Collectors.toList()));
            trsDetail.setRowFilter(filterDetail);

            for (int i = 0; i < tableModelBill.getRowCount(); i++) {
                String currentBillDateString = tableModelBill.getValueAt(i, 3).toString();
                if (dateString.equals(currentBillDateString)) {
                    String amountStr = tableModelBill.getValueAt(i, 0).toString();
                    updateTotalAmountBigDecimal(amountStr);
                    System.out.println("amountStr+ " + amountStr);
                }
            }

            //        jTotalAmount3.setText(decimalFormat.format(totalAmount));
            System.out.println("jTotalAmount3+ " + totalAmount);
        }
        jComboBox1.setSelectedIndex(-1);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jDialogITTB.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JDialog jDialogITTB;
    private javax.swing.JDialog jDialogReturn;
    private javax.swing.JDialog jDialogUpDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTableBillDetail;
    private javax.swing.JTable jTableReturn;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTotalAmount3;
    // End of variables declaration//GEN-END:variables
}
