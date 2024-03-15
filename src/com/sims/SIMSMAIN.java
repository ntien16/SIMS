/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.sims;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.ui.FlatComboBoxUI;
import com.formdev.flatlaf.ui.FlatDesktopPaneUI;
import com.formdev.flatlaf.ui.FlatInternalFrameUI;
import com.formdev.flatlaf.ui.FlatTextFieldUI;
import com.sims.administrator.ADMINISTRATORFRAME;
import com.sun.tools.javac.Main;

import com.sims.inventorymanager.INVENTORYMANAGER;
import com.sims.login.Login;
import com.sims.salemanager.SALEMANAGERFRAME;
import com.sims.salemen.SALESMENFRAME;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class SIMSMAIN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            JDesktopPane desktopPane = new JDesktopPane();
            desktopPane.setUI(new FlatDesktopPaneUI());
//            new SALESMENFRAME().setVisible(true);
//        new SALEMANAGERFRAME().setVisible(true);
//        new INVENTORYMANAGER().setVisible(true);
//            new ADMINISTRATORFRAME().setVisible(true);
//            JTextField c = new JTextField();
//            FlatTextFieldUI.createUI(c);
            new Login().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FlatIntelliJLaf.setup();

    }

}
