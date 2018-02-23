/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqledit;

import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ZeroDay
 */
public class uiTable extends JFrame{
    public String table_Name;
    public JTable tabTable = new JTable();
    public JPanel panelDgv = new JPanel();
    public JPanel ctnAddUp = new JPanel();
    public JPanel ctn = new JPanel();
    public JPanel panelAddUp = new JPanel();
    public uiTable(String tableName) {
        table_Name=tableName;
        setName("frameTable");
	setTitle("Table: " + tableName);
	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new MigLayout("", "[grow]", "[][grow]"));
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, "cell 0 1,grow");

                    //Tabpane TABLE
                    JPanel panelTable = new JPanel();
                    tabbedPane.addTab("Table", null, panelTable, null);
                    panelTable.setLayout(new MigLayout("", "[grow]", "[grow]"));

                    panelTable.add(panelDgv, "cell 0 0,grow");

                    panelTable.add(panelAddUp, "cell 1 0,grow");
                    panelAddUp.setLayout(new BoxLayout(panelAddUp, BoxLayout.Y_AXIS));

                    ctnAddUp.setLayout(new MigLayout("center"));

                    tabTable = coreTable.populate(tableName, ctnAddUp);

                    JButton btnAdd = new JButton("Add");
                    btnAdd.addActionListener(new ActionbtnAdd());
                    ctnAddUp.add(btnAdd, "split2, growx");
                    JButton btnAdd1 = new JButton("Add1");
                    btnAdd1.addActionListener(new ActionbtnAdd());
                 
                    JButton btnUpdate = new JButton("Update");
                    btnUpdate.addActionListener(new ActionbtnUpdate());
                    ctnAddUp.add(btnUpdate, "");
                    JButton btnDelete = new JButton("Delete");
                    btnDelete.addActionListener(new ActionbtnDelete());
                    ctnAddUp.add(btnDelete, "");
                    panelAddUp.add(ctnAddUp);
		    tabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    tabTable.addMouseListener(new tableTotextField());
		    JScrollPane jscrollTable = new JScrollPane(tabTable);
                    jscrollTable.setPreferredSize(new Dimension(750, 400));
		    jscrollTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		    jscrollTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    panelDgv.add(jscrollTable);
                
                //Tabpane QUERY
		JPanel panelQuery = new JPanel();
		tabbedPane.addTab("Query", null, panelQuery, null);
                 panelQuery.setLayout(new MigLayout("center"));
                 JTextArea TbQuery = new JTextArea();
                 JScrollPane jscroll = new JScrollPane();
    TbQuery.setAutoscrolls(true);
    TbQuery.setWrapStyleWord(true);
    TbQuery.setLineWrap(true);
    TbQuery.setMargin(new Insets(10,10,10,10));
    jscroll = new JScrollPane(TbQuery, 22, 31);
    panelQuery.add(jscroll, "w 60%, h 30%,span,wrap");
    panelQuery.add(btnAdd1, "spanx 2,growx,wrap 5");

    
                //TabPane DESCRIPTION
                JPanel panelDescription = new JPanel();
		tabbedPane.addTab("Description", null, panelDescription, null);
		panelDescription.setLayout(new MigLayout("", "[60px,grow][24px][]", "[14px][][grow]"));
		
		JLabel lblDescription = new JLabel("Description: ");
		panelDescription.add(lblDescription, "flowx,cell 0 1,alignx left,aligny top");
		
		JPanel ctnTabDescription = new JPanel();
		panelDescription.add(ctnTabDescription, "cell 0 2 3 1,grow");
		JTable tabTableDescription = coreTable.populateDescribeTable(tableName, ctnTabDescription);
		JScrollPane jscrollTableDescription = new JScrollPane(tabTableDescription);
                jscrollTableDescription.setPreferredSize(new Dimension(750, 500));
		jscrollTableDescription.setAutoscrolls(true);
		jscrollTableDescription.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jscrollTableDescription.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		ctnTabDescription.add(jscrollTableDescription);
		
		JLabel lblTable = new JLabel(table_Name);
		panelDescription.add(lblTable, "cell 0 1,alignx left,aligny top");
                
		pack();
		setVisible(true);
    }
   
    
    class tableTotextField implements MouseListener{
       
        public void mouseClicked(MouseEvent e) {
            coreTable.tabTotextField(tabTable);
        }

        public void mousePressed(MouseEvent e) {
           coreTable.tabTotextField(tabTable);
        }

        public void mouseReleased(MouseEvent e) {
             coreTable.removeOnMouseExit();
        }

        public void mouseEntered(MouseEvent e) {
           
        }

        public void mouseExited(MouseEvent e) {
          
        }
    }
    
    class ActionbtnAdd implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
                coreTable.add(table_Name);
                refresh();
        }
    }
    
    class ActionbtnDelete implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
                coreTable.delete(table_Name);
                refresh();
        }
    }
    
    class ActionbtnUpdate implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
                coreTable.update(table_Name);
                refresh();
        }
    }
    
    public void refresh() {
        coreTable.removePositioning(panelDgv, ctnAddUp, panelAddUp);
        tabTable = coreTable.populate(table_Name, ctnAddUp);
        tabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabTable.addMouseListener(new uiTable.tableTotextField());
        JScrollPane jscrollTable = new JScrollPane(tabTable);
        jscrollTable.setPreferredSize(new Dimension(750, 400));
        jscrollTable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jscrollTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelDgv.add(jscrollTable);

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new uiTable.ActionbtnAdd());
        ctnAddUp.add(btnAdd, "split2, growx");
        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new uiTable.ActionbtnUpdate());
        ctnAddUp.add(btnUpdate, "");
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new uiTable.ActionbtnDelete());
        ctnAddUp.add(btnDelete, "");
        panelDgv.repaint();
        ctnAddUp.repaint();
        panelAddUp.repaint();
        panelAddUp.revalidate();
        panelDgv.revalidate();
        ctnAddUp.revalidate(); 
        pack();
    }
}
