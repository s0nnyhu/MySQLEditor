/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqledit;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static mysqledit.coreLogin.conn;

/**
 *
 * @author ZeroDay
 */
public class coreDb {
    public static void getTable(JPanel jpaneTable, JPanel jpaneKey, JComboBox jcb) {
        if(coreLogin.conn != null) {
             try {
                DatabaseMetaData md = conn.getMetaData();
                ResultSet rs = md.getTables(null, null, "%", null);
                ResultSet primaryKey = null;
                while (rs.next()) {

                    String catalog = rs.getString("TABLE_CAT");
                    String schema = rs.getString("TABLE_SCHEM");
                    String tableName = rs.getString("TABLE_NAME");
                    primaryKey = md.getPrimaryKeys(catalog, schema, tableName);
                    //Get Table name
                    System.out.println("Table: " + tableName);
                    JLabel Jlb = new JLabel("<html>"+ tableName + "<br></html>");
                    jpaneTable.add(Jlb);
                    jcb.addItem(rs.getString(3));
                    jpaneTable.revalidate();
                    jpaneTable.repaint();
                    
                    //Get Primary key
                        while (primaryKey.next()) {
                            System.out.println("Primary key: " + primaryKey.getString("COLUMN_NAME"));
                            JLabel JlPk = new JLabel(primaryKey.getString("COLUMN_NAME"));
                            jpaneKey.add(JlPk);
                            jpaneKey.revalidate();
                            jpaneKey.repaint();
                        }
                        primaryKey = md.getPrimaryKeys(catalog, schema, tableName);
                       // ResultSet primaryKeys = md.getPrimaryKeys(catalog, schema, tableName);
                        if (primaryKey.next()!=true) {
					   JLabel JlPk = new JLabel("Empty PK");
					   jpaneKey.add(JlPk);
					   jpaneKey.revalidate();
					   jpaneKey.repaint();
					   System.out.println("getPrimaryKeys(): columnName= Empty");
			}
        
                }
            } catch (SQLException ex) {
                Logger.getLogger(coreDb.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Connection to database does not exist");
        }
    }
    
    public static void killConn() throws SQLException {
        if (conn != null) {
        try {
            conn.close();
            System.out.println("ok");
        } catch (SQLException e) { 
            JOptionPane.showMessageDialog(null, e.toString());
        }
        }
       
    }
    
    public static String getTableName(JComboBox cbTableName) {
        return cbTableName.getSelectedItem().toString();
    }
    
    public static void openTable(String tableName) {
        uiTable uiTable = new uiTable(tableName);
    }
}
