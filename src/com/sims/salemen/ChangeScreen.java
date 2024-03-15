/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.salemen;

import com.sims.login.Login;
import com.sims.salemen.BILLJPanel;
import com.sims.salemen.CUSTOMERJPanel;
import com.sims.salemen.EXCEPTIONSJPanel;
import com.sims.salemen.INFORMJPanel;
import com.sims.salemen.ITEMTAKEBACK;
import com.sims.salemen.ProductJPanel;
import com.sims.salemen.SELLING;
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

    private String password;
    private String username;
    private JPanel root;
    private String kindSelected = "";
    List<ListSaleMenForm> listitem = null;
    private boolean flag;
    private Login login;

    //Constructor
    public ChangeScreen(JPanel jpnRoot) throws SQLException, ClassNotFoundException {
        this.root = jpnRoot;
        this.login = new Login();
    }

    public ChangeScreen(JPanel jpnRoot, String username, String password) throws SQLException, ClassNotFoundException {
        this.root = jpnRoot;
        this.login = new Login();
        this.username = username;
        this.password = password;

    }

    public void setView(JPanel jpnItem, JLabel jlbItem) throws SQLException, ClassNotFoundException {
        kindSelected = "Product";
//        jpnItem.setBackground(new Color(76, 175, 80));
//        jlbItem.setBackground(new Color(76, 175, 80));
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new ProductJPanel());
        root.validate();
        root.repaint();
    }

    public void setEvent(List<ListSaleMenForm> listitem) {
        this.listitem = listitem;
        for (ListSaleMenForm itemList : listitem) {
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
            try {
                switch (kind) {
                    case "PRODUCT1": {
                        try {
                            node = new ProductJPanel();
                        } catch (SQLException ex) {
                            Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    
                    case "SALES": {
                        try {
                            node = new SELLING(username, password);
                        } catch (SQLException ex) {
                            Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    
                    case "BILL": {
                        try {
                            node = new BILLJPanel();
                        } catch (SQLException ex) {
                            Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    
                    case "EXCEPTIONS":
                        node = new EXCEPTIONSJPanel(username, password);
                        break;
//                case "INFORM":
//                    node = new INFORMJPanel();
////                    node.setVisible(false);
//                    break;
                    case "CUSTOMER":
                    {
                        try {
                            node = new CUSTOMERJPanel();
                        } catch (SQLException ex) {
                            Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    
                    case "ITEM TAKE BACK":
                    {
                        try {
                            node = new ITEMTAKEBACK();
                        } catch (SQLException ex) {
                            Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    
                    case "PROFILE": {
                        try {
                            node = new ProfileJPanelSalemen(username, password);
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
                        node = new JPanel();
                        
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
            } catch (SQLException ex) {
                Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
            jpnItem.setBackground(new Color(255, 102, 102));
            jlbItem.setBackground(new Color(255, 102, 102));

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
            jpnItem.setBackground(new Color(255, 102, 102));
            jlbItem.setBackground(new Color(255, 102, 102));

        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(243, 243, 243));
                jlbItem.setBackground(new Color(243, 243, 243));
            }

        }

    }

    private void setChangeBackground(String kind) {
        for (ListSaleMenForm item : listitem) {
            if (item.getKind().equalsIgnoreCase(kind)) {
                item.getJpn().setBackground(new Color(255, 102, 102));
                item.getJlb().setBackground(new Color(255, 102, 102));
            } else {
                item.getJpn().setBackground(new Color(243, 243, 243));
                item.getJlb().setBackground(new Color(243, 243, 243));
            }
        }
    }

}
