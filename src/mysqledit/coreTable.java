/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqledit;

import java.awt.Dimension;
import java.util.List;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import static mysqledit.coreLogin.conn;
import net.miginfocom.swing.MigLayout;
import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;
import static org.springframework.web.util.HtmlUtils.htmlEscape;

/**
 *
 * @author ZeroDay
 */
public class coreTable {
    private static Collection<JTextField> ColtextFields = new ArrayList<JTextField>();
    private static Collection<String> stringCol = new ArrayList<String>();
    private static Collection<String> stringColSave = new ArrayList<String>();
    private static Collection<String> tabTableValue = new ArrayList<String>();
    private static Collection<String> tabUpdateValue = new ArrayList<String>();
    private static Collection<String> stringColumn = new ArrayList<String>();
    static ArrayList<String> list = new ArrayList();
    static int columnTab;
    static String insertStr ="";
    
    public static JTable populate(String tableName, JPanel ctnAddUp) {
        JTable tabTable = new JTable();
        try {
            if(coreLogin.conn != null) {
                Statement stm = coreLogin.conn.createStatement();
                String request = "SELECT * from "+ tableName;
                ResultSet res = stm.executeQuery(request);
                tabTable = new JTable(buildTableModel(res, tableName));
                populateForEdit(res, ctnAddUp);
            }
        } catch (SQLException ex) {
                System.out.println(ex);
        }
         return tabTable; 
    }
    
    public static void populateForEdit(ResultSet rs, JPanel ctnAddUp) throws SQLException {
                stringColumn.clear();
                ColtextFields.clear();
                stringCol.clear();
                insertStr="";
	    	ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
	 	    Vector<String> columnNames = new Vector<String>();
	 	    int columnCount = metaData.getColumnCount();
	 	    columnTab=metaData.getColumnCount();
	 	    for (int column = 1; column <= columnCount; column++) {
	 	        columnNames.add(metaData.getColumnName(column));
	 	        JLabel jlColumn = new JLabel(metaData.getColumnName(column));
	 	        JTextField tbColumn = new JTextField(15);
                        ColtextFields.add(tbColumn);
	 	        stringColumn.add(metaData.getColumnName(column));
	 	        ctnAddUp.add(jlColumn, "alignx trailing,split2");
	 	        ctnAddUp.add(tbColumn, "wrap");
	 	        //textFields.add(tbColumn);
	 	    } 	
    }
    
    
    
    public static DefaultTableModel buildTableModel(ResultSet rs, String tableName) throws SQLException {
	    ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
            ResultSet primaryKey = null;
            DatabaseMetaData md = coreLogin.conn.getMetaData();
	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
            /*ABANDONNE
            //GET ALL PRIMARY KEY IN A LIST
            primaryKey = md.getPrimaryKeys(null, null, tableName);
                List<String> list = new ArrayList<String>();
                while (primaryKey.next()) {
                    list.add(primaryKey.getString("COLUMN_NAME"));
                }
                System.out.println("List:"+list);
                System.out.println(list.size());
            //FILL WITH WORDS TO MAKE LIST AND TABLE COLUMN SAME SIZE
                for (int column = list.size(); column <= columnCount; column++) {
                        list.add("hello");
                }
                System.out.println("///////END OF FIRST PRINT");
            //COMPARE COLUMN NAME AND LIST TO CHECK IF COLUMN IS A PK
	    for (int column = 1; column <= columnCount; column++) {
                System.out.println("colonne : " +metaData.getColumnName(column));
                System.out.println("liste : "+list.get(column-1));
                if(metaData.getColumnName(column) == null ? list.get(column-1) == null : metaData.getColumnName(column).equals(list.get(column-1))) {
                    columnNames.add(metaData.getColumnName(column) +"(pk)");
                } else {
                //System.out.println(primaryKey);
                    columnNames.add(metaData.getColumnName(column));
                }
 
	    }*/
                for (int column = 1; column <= columnCount; column++) {
                    columnNames.add(metaData.getColumnName(column));
                }
	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);
	  //End function
	} 
    
    public static JTable populateDescribeTable(String tableName, JPanel ctnAddUp) {
        JTable tabDescribeTable = new JTable();
          try {
            if(coreLogin.conn != null) {
                Statement stm = coreLogin.conn.createStatement();
                String request = "DESCRIBE "+ tableName;
                ResultSet res = stm.executeQuery(request);
                tabDescribeTable = new JTable(buildTableModel(res, tableName));
                
                ResultSetMetaData mdkey = res.getMetaData();
                int col = mdkey.getColumnCount();
                for (int i = 1; i <= col; i++){
                 String col_name = mdkey.getColumnName(i);
                 System.out.print(col_name+"\t");
                }
                while(res.next()){
                 for (int columnIndex = 1; columnIndex <= col; columnIndex++) {
                                System.out.println(res.getObject(columnIndex));
                }}
            }
        } catch (SQLException ex) {
                System.out.println(ex);
        }
          return tabDescribeTable;
           
    }
    
    //*******ADD
    public static void add(String tableName) {
        stringCol.clear();
        for (JTextField textField : ColtextFields) {
            stringCol.add(htmlEscape(textField.getText()));
        }
        System.out.println("StringCol: "+stringCol);
        for (int i=0; i<columnTab;i++) {
            String txtBox=(String) stringCol.toArray()[i];
            insertStr += "'"+ txtBox + "', ";
        }

        String queryAdd="INSERT INTO " + tableName + " VALUES( " + insertStr;
        System.out.println("\nColumntab:" + columnTab);
        System.out.println("\nQueryAdd: " + queryAdd);
        String finalQueryAdd=queryAdd.substring(0,queryAdd.length()-2) + " )";
        System.out.println(finalQueryAdd);
        try {
            Statement state = coreLogin.conn.createStatement();
            state.executeQuery("SET NAMES 'UTF8'");
            state.executeQuery("SET CHARACTER SET 'UTF8'");
            state.executeUpdate(finalQueryAdd);
            JOptionPane.showMessageDialog(null, "Query executed with success!" , "Status", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
           System.out.println(ex);
           JOptionPane.showMessageDialog(null, ex.toString(), "Status" , JOptionPane.ERROR_MESSAGE);
        }
        stringCol.clear();
        stringCol.removeAll(stringCol);
        insertStr="";
        System.err.println("Final result test" + stringCol);
    }
    
    //****UPDATE
    public static void update(String tableName) {
      stringCol.clear();
      String condWhereTb = "";
      String condWhereLb = "";
      for (JTextField textField : ColtextFields) {
        stringCol.add(htmlEscape(textField.getText()));
      }
      for (String colName : stringColumn) {
        stringColSave.add(colName.toString());
      }
      System.out.println("stringCol" + stringCol);
      System.out.println("stringCol" + stringColSave);
      for (int i = 0; i < columnTab; i++) {
        String col = (String)stringColSave.toArray()[i];
        String txtBox = (String)stringCol.toArray()[i];
        insertStr = insertStr + col + "='" + txtBox.replaceAll("\\s+$", "") + "', ";
        condWhereTb = (String)stringCol.toArray()[0];
        condWhereLb = (String)stringColSave.toArray()[0];
      }
      
      String m = "UPDATE " + tableName + " SET " + insertStr;
      String n = m.substring(0, m.length() - 2) + " WHERE " + condWhereLb + "='" + condWhereTb.replaceAll("\\s+$", "") + "'";
      System.out.println(m);
      System.out.println(n);
      
      try
      {
        Statement state = coreLogin.conn.createStatement();
        state.executeQuery("SET NAMES 'UTF8'");
        state.executeQuery("SET CHARACTER SET 'UTF8'");
        state.executeUpdate(n);
        JOptionPane.showMessageDialog(null, "Updated with success!" , "Status", JOptionPane.INFORMATION_MESSAGE);
        state.close();
      }
      catch (SQLException ex)
      {
        JOptionPane.showMessageDialog(null, ex.toString(), "Status" , JOptionPane.ERROR_MESSAGE);
      }
      insertStr = "";
      stringCol.clear();
      stringColSave.clear();
    }
    
    //***DELETE
    public static void delete(String tableName) {
        String condWhereTb = "";
        String condWhereLb = "";
        for (JTextField textField : ColtextFields) {
          stringCol.add(textField.getText());
        }
        for (String colName : stringColumn) {
          stringColSave.add(colName.toString());
        }
        condWhereTb = (String) stringCol.toArray()[0];
        condWhereLb = (String) stringColSave.toArray()[0];

        String m = "DELETE FROM " + tableName + " where " + condWhereLb + "='" + condWhereTb + "'";
        System.out.println(m);

        try
        {
          Statement state = coreLogin.conn.createStatement();
          state.executeUpdate(m);
          JOptionPane.showMessageDialog(null, "Deleted!" , "Status", JOptionPane.INFORMATION_MESSAGE);
          state.close();
        }
        catch (SQLException e1)
        {
          JOptionPane.showMessageDialog(null, e1.toString(), "Status" , JOptionPane.ERROR_MESSAGE);
        }
        m = "";
        stringCol.clear();
        stringColSave.clear();
    }
    //***PUT JTABLE VALUE IN TEXTFIELD
    public static void tabTotextField(JTable tabTable) {
        tabTableValue.clear();
        list.clear();
        int selectedRow = tabTable.getSelectedRow();
        int k = 0;
        int j = 0;
        for (int i = 0; i < tabTable.getColumnCount(); i++) {
            tabTableValue.add(tabTable.getValueAt(selectedRow, j) + "");
        j++;
        }
        for (String tabValue : tabTableValue) {
            list.add(tabValue.toString());
        }
      
        for (JTextField textField : ColtextFields) {
            textField.setText(((String)list.get(k)).toString());
            k++;
        }
    }
    
    public static void removeOnMouseExit() {
        for (JTextField textField : ColtextFields) {
            textField.setText("");
        }
    }

     
    public static void removePositioning(JPanel paneTable, JPanel ctnAddUp, JPanel paneAddUp) {
        paneTable.removeAll();
        ctnAddUp.removeAll();
        ctnAddUp.setLayout(new MigLayout("center"));
        paneAddUp.add(ctnAddUp);
    }
    
   
}
