/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.inventorymanager;


import com.sims.inventorymanager.IMPORTJPanel;

import com.sims.inventorymanager.REPORT;
import com.sims.inventorymanager.INFORMJPanel;
import com.sims.inventorymanager.CHECKVALIDATEINVENTORY;
import com.sims.inventorymanager.InventoryJPanel;
import com.sims.login.Login;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
//import java.awt.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ChangeScreen {

    private JPanel root;
    private String kindSelected = "";
    List<ListInventoryForm> listitem = null;
    private boolean flag;
    private Login login;
    private String username;
    private String password;
    
    
    public ChangeScreen(JPanel jpnRoot) throws SQLException, ClassNotFoundException {
        this.root = jpnRoot;
        this.login=new Login();
    }
    
      public ChangeScreen(JPanel jpnRoot,String username,String password) throws SQLException, ClassNotFoundException {
        this.root = jpnRoot;
        this.login=new Login();
        this.username=username;
        this.password=password;
    }
    
    public void setView(JPanel jpnItem, JLabel jlbItem) throws SQLException, ClassNotFoundException {
        kindSelected = "Inventory";

        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new InventoryJPanel());
        root.validate();
        root.repaint();
    }

    public void setEvent(List<ListInventoryForm> listitem) {
        this.listitem = listitem;
        for (ListInventoryForm itemList : listitem) {
            itemList.getJlb().addMouseListener(new LabelEvent(root, itemList.getKind(), itemList.getJpn(), itemList.getJlb()));
        }

    }

    class LabelEvent implements MouseListener {

        private JPanel node;
        private String kind;
        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(JPanel node, String kind, JPanel jpnItem, JLabel jlbItem) {
            this.node = node;
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "INVENTORY1":
                {
                    try {
                        node = new InventoryJPanel();
                    } catch (SQLException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;

               
                case "IMPORT":
                {
                    try {
                        node = new IMPORTJPanel(username,password);
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;

//                case "REPORT":
//                    node = new REPORT();
//                   node.setVisible(false);
//                    break;
//                case "INFORM":
//                    node = new INFORMJPanel();
//                      node.setVisible(false);
//                    break;
//                case "ITEM TAKE BACK":
//                    node = new CHECKVALIDATEINVENTORY();
//                      node.setVisible(false);
//                    break;
                case "PROFILE":
                 {
                    try {
                        node = new ProfileJPanelInventory(username,password);
                    } catch (SQLException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;

                case "SIGN OUT":
                  login.setVisible(true);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(root);
                    JPanel newNode = new JPanel();
                    node = newNode;
                    if (frame != null) {
                        frame.dispose();
                    }
                    break;
                default:
                    throw new AssertionError();
            }

            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();

        }

        @Override
        public void mousePressed(MouseEvent e) {
            jpnItem.setBackground(new Color(0, 0, 204));
            jlbItem.setBackground(new Color(0, 0, 204));

//            kindSelected = kind;
//            jpnItem.setBackground(new Color(255, 102, 102));
//            jlbItem.setBackground(new Color(255, 102, 102));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
//            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(0, 0, 204));
            jlbItem.setBackground(new Color(0, 0, 204));

        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(243, 243, 243));
                jlbItem.setBackground(new Color(243, 243, 243));
            }

        }

    }

//    private void setChangeBackground(String kind) {
//        for (ListInventoryForm item : listitem) {
//            if (item.getKind().equalsIgnoreCase(kind)) {
//                item.getJpn().setBackground(new Color(255, 102, 102));
//                item.getJlb().setBackground(new Color(255, 102, 102));
//            } else {
//                item.getJpn().setBackground(new Color(243, 243, 243));
//                item.getJlb().setBackground(new Color(243, 243, 243));
//            }
//        }
//    }

}
