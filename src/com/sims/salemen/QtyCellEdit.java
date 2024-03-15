/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.salemen;

import java.awt.Component;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatter;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class QtyCellEdit extends DefaultCellEditor {

    private JSpinner input;
    private JTable table;
    private int row;
    private JSpinner discount;

    public QtyCellEdit() {
        super(new JCheckBox());
        input = new JSpinner();
        discount = new JSpinner();
        SpinnerNumberModel numberModel = (SpinnerNumberModel) input.getModel();
        numberModel.setMinimum(1);
        input.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                inputChange();
            }
        });
        discount.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                inputChange();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        super.getTableCellEditorComponent(table, value, isSelected, row, column);
        this.table = table;
        this.row = row;

        // Get value quantity from table
        int qty = 0;
        Object qtyValue = table.getValueAt(row, 5);
        if (qtyValue instanceof Integer) {
            qty = (Integer) qtyValue;
        }
        input.setValue(qty);

        // Get value discount from table
        double discountValue = 0.0;
        Object discountObj = table.getValueAt(row, 6);
        if (discountObj instanceof Double) {
            discountValue = (Double) discountObj;
        }
        discount.setValue(discountValue);

        return input;
    }

    @Override
    public Object getCellEditorValue() {
        return input.getValue();
    }

    private void inputChange() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

        int qty = (int) input.getValue();
        double discountValue = Double.parseDouble(discount.getValue().toString());

        if (qty > 0) {
            String priceStr = tableModel.getValueAt(row, 4).toString().replace("$", "").replace(",", ".");
            BigDecimal price = new BigDecimal(priceStr);

            BigDecimal amount;
            if (discountValue > 0) {
                amount = price.multiply(new BigDecimal(qty)).multiply(new BigDecimal(discountValue / 100));
            } else {
                amount = price.multiply(new BigDecimal(qty));
            }

            tableModel.setValueAt(amount, row, 7);
        }
    }

    private String formatAmount(BigDecimal amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        return decimalFormat.format(amount);
    }
}
