/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sims.administrator;

import com.sims.administrator.USER;
import com.sims.administrator.ProductJPanel;
import com.sims.login.Login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Window;
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
    private JFrame AdminFrame;
    private String kindSelected = "";
    List<ListAdministratorForm> listitem = null;
    private boolean flag;
    private Login login;
    private String adminname;
    private String passwordAdmin;

    public ChangeScreen(JPanel jpnRoot) throws SQLException, ClassNotFoundException {
        this.root = jpnRoot;
        this.login = new Login();

    }
      public ChangeScreen(JPanel jpnRoot,String usename,String password) throws SQLException, ClassNotFoundException {
        this.root = jpnRoot;
        this.login = new Login();
        this.adminname=usename;
        this.passwordAdmin=password;

    }

    public void setView(JPanel jpnItem, JLabel jlbItem) throws ClassNotFoundException, SQLException {
        kindSelected = "USER";
//        jpnItem.setBackground(new Color(76, 175, 80));
//        jlbItem.setBackground(new Color(76, 175, 80));
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new USER());
        root.validate();
        root.repaint();
    }

    public void setEvent(List<ListAdministratorForm> listitem) {
        this.listitem = listitem;
        for (ListAdministratorForm itemList : listitem) {
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
                case "PRODUCT":
                {
                    try {
                        node = new ProductJPanel();
                    } catch (SQLException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;

                case "USER1":
                {
                    try {
                        node = new USER();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;

                case "PROFILE":
                {
                    try {
                        node = new ProfileJPanelAdmin(adminname,passwordAdmin);
                    } catch (SQLException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;

                case "SUPPLIER":
                {
                    try {
                        node = new SUPPLIERJPanel();
                    } catch (SQLException ex) {
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
            jpnItem.setBackground(new Color(255, 0, 51));
            jlbItem.setBackground(new Color(255, 0, 51));

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
            jpnItem.setBackground(new Color(255, 0, 51));
            jlbItem.setBackground(new Color(255, 0, 51));

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
//        for (ListAdministratorForm item : listitem) {
//            if (item.getKind().equalsIgnoreCase(kind)) {
//                item.getJpn().setBackground(new Color(255, 0, 51));
//                item.getJlb().setBackground(new Color(255, 0, 51));
//            } else {
//                item.getJpn().setBackground(new Color(243, 243, 243));
//                item.getJlb().setBackground(new Color(243, 243, 243));
//            }
//        }
//    }
}
