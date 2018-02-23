/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqledit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.Timer;

public class coreLogin {
    public JTextField jtxtUser;
    public static Connection conn = null;
    public static void affiche(JTextField jtxtUser) {
        JOptionPane.showMessageDialog(null, jtxtUser.getText() , "The purpose of this program", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static String connect(JTextField jtxtHost, JTextField jtxtUser, JTextField jtxtPass, JTextField jtxtDb, JProgressBar progressBar, JLabel lblConnected, JLabel lblConnecting) throws UnsupportedEncodingException{
            String url = "jdbc:mysql://"+jtxtHost.getText()+":3306/"+jtxtDb.getText()+"?useSSL=true";
            String username = jtxtUser.getText();
            String password = jtxtPass.getText();
            lblConnecting.setVisible(true);
            try (Connection con = DriverManager.getConnection(url, username, password)) {
                conn=DriverManager.getConnection(url, username, password);
                progressBar.setVisible(true);
                int i=0;
                   while(i<=100)
                   {
                       try {
                           Thread.sleep(50);
                       } catch (InterruptedException ex) {
                           Logger.getLogger(uiLogin.class.getName()).log(Level.SEVERE, null, ex);
                       }
                       progressBar.paintImmediately(0, 0, 200, 25);
                       progressBar.setValue(i);
                       progressBar.setStringPainted(true);
                       i=i+6;
                    }
                lblConnecting.setVisible(false);
                lblConnected.setVisible(true);
                /*try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(coreLogin.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                System.out.println("Database connected!");
                //Just pause
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(coreLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Open uiDb
                String dbName = getDbName(jtxtDb).toUpperCase();
                uiDb frmDb = new uiDb(dbName);
                return "1";

            } catch (SQLException e) {
                // throw new IllegalStateException("Cannot connect the database!", e);
                System.out.println("error");
                String error = e.toString();
                byte ptext[] = error.getBytes("ISO-8859-1"); 
                String value = new String(ptext, "UTF-8");
                return value;
            }
            
    }
    public static String listDb(JTextField jtxtHost, JTextField jtxtUser, JTextField jtxtPass, JTextField jtxtDb, JLabel lblConnected, JLabel lblConnecting, JComboBox cbListDb) throws UnsupportedEncodingException{
            cbListDb.removeAllItems();
            lblConnecting.setVisible(false);
            lblConnected.setVisible(false);
            lblConnecting.setText("Listing Db...");
            lblConnecting.setVisible(true);
            String url = "jdbc:mysql://"+jtxtHost.getText()+":3306/?useSSL=true";
            String username = jtxtUser.getText();
            String password = jtxtPass.getText();
           /* System.out.println("Connecting database...");
            System.out.println(url);
            System.out.println(username);
            System.out.println(password);*/

            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                //Label designing
                lblConnecting.setVisible(false);
                lblConnected.setText("Done!");
                lblConnected.setVisible(true);
                
                //Get databases list and fill combobox
                getDb(conn, cbListDb);
                System.out.println("Listed!");
                
                //***Reset label "done!" to null
                 Timer t = new Timer(2000, new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                         lblConnected.setText(null);
                         lblConnecting.setText(null);
                     }
                 });
                 t.start();
                 //**end reset
                
                 return "1";
            } catch (SQLException e) {
                // throw new IllegalStateException("Cannot connect the database!", e);
                lblConnecting.setVisible(false);
                lblConnected.setText("Error!");
                lblConnected.setVisible(true);
                Timer t = new Timer(2000, new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                         lblConnected.setText(null);
                         lblConnecting.setText(null);
                     }
                 });
                t.start();
                String error = e.toString();
                byte ptext[] = error.getBytes("ISO-8859-1"); 
                String value = new String(ptext, "UTF-8");
                return value;
            }

    }
    
    public static void getDb(Connection conn, JComboBox cbListDb) {
        DatabaseMetaData meta;
        try {
            meta = conn.getMetaData();
            ResultSet res = meta.getCatalogs();
            System.out.println("List of databases: "); 
            while (res.next()) {
                cbListDb.addItem(res.getString("TABLE_CAT"));
                System.out.println("   "+res.getString("TABLE_CAT"));
            }
                res.close();
        } catch (SQLException ex) {
            Logger.getLogger(coreLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }

    public static String getDbName(JTextField txtDbName) {
        return txtDbName.getText();
    }
}
