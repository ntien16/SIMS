package com.sims.salemanager;

import com.sims.login.Login;
import com.sims.salemanager.BILLJPanel;
import com.sims.salemanager.ProfileJPanelSaleManager;
import com.sims.salemanager.MONEY;
import com.sims.salemanager.INFORMJPanel;
import com.sims.salemanager.ITEMTAKEBACK;
import com.sims.salemanager.ProductJPanel;
import com.sims.salemanager.SELLING;
import java.awt.BorderLayout;
import java.awt.Color;
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

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ChangeScreen {

    private JPanel root;
    private String kindSelected = "";
    private Login login;
    List<ListSaleManagerForm> listitem = null;
    private String username;
    private String password = "";

    public ChangeScreen(JPanel jpnRoot) throws SQLException, ClassNotFoundException {
        this.root = jpnRoot;
        this.login = new Login();
    }

    public ChangeScreen(JPanel jpnRoot, String username, String password) throws SQLException, ClassNotFoundException {
        this.root = jpnRoot;
        this.username = username;
        this.password = password;
        this.login = new Login();
    }

    public void setView(JPanel jpnItem, JLabel jlbItem) throws SQLException, ClassNotFoundException {
        kindSelected = "PROFILE";
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new ProfileJPanelSaleManager(this.username, this.password));
        root.validate();
        root.repaint();
    }

    public void setEvent(List<ListSaleManagerForm> listitem) {
        this.listitem = listitem;
        for (ListSaleManagerForm itemList : listitem) {
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
                case "PROFILE1": {
                    try {
                        node = new ProfileJPanelSaleManager(username, password);
                    } catch (SQLException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

                case "EXCEPTIONS": {
                    try {
                        node = new EXCEPTIONSJPanel();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

                case "BILL": {
                    try {
                        node = new BILLJPanel();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

                case "MONEY":
                {
                    try {
                        node = new MONEY();
                    } catch (SQLException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;

//                case "INFORM":
//                {
//                    try {
//                        node = new INFORMJPanel();
//                        node.setVisible(true);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (ClassNotFoundException ex) {
//                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                    break;

                case "SIGN OUT":
                    login.setVisible(true);
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(root);
                    node = new JPanel();

                    if (frame != null) {
                        frame.dispose();
                    }
                    break;
                case "ITEM TAKE BACK": {
                    try {
                        node = new ITEMTAKEBACK();
                        
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ChangeScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
        //60-/63-65

        @Override
        public void mousePressed(MouseEvent e) {
            jpnItem.setBackground(new Color(102, 255, 204));
            jlbItem.setBackground(new Color(102, 255, 204));

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(102, 255, 204));
            jlbItem.setBackground(new Color(102, 255, 204));

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
        for (ListSaleManagerForm item : listitem) {
            if (item.getKind().equalsIgnoreCase(kind)) {
                item.getJpn().setBackground(new Color(102, 255, 204));
                item.getJlb().setBackground(new Color(102, 255, 204));
            } else {
                item.getJpn().setBackground(new Color(243, 243, 243));
                item.getJlb().setBackground(new Color(243, 243, 243));
            }
        }
    }
}
