
package mysqledit;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author ZeroDay
 */
public class uiDb extends JFrame {
       private JButton btnShowTable = new JButton("Open");
       private JButton btnKillConn = new JButton("Kill connection");
       private JComboBox cbTable = new JComboBox();
       JButton btnRefresh = new JButton();
        
                       
    public uiDb(String dbName) {
     setName("frameLogin");
		setTitle("Database:" + dbName);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(true);
                //setBounds(100, 100, 423, 283);
                        JPanel ctnTabKey = new JPanel();
                        ctnTabKey.setBorder(BorderFactory.createTitledBorder("Database content:"));
                        BoxLayout layoutTabKey = new BoxLayout(ctnTabKey, BoxLayout.X_AXIS);
                        ctnTabKey.setLayout(layoutTabKey);
                        
                        JPanel ctnTable = new JPanel();
                        ctnTable.setMinimumSize((new Dimension(100, 100)));
                        ctnTable.setBorder(BorderFactory.createTitledBorder("Tables:"));
                        BoxLayout layoutTable = new BoxLayout(ctnTable, BoxLayout.Y_AXIS);
                        ctnTable.setLayout(layoutTable);
                        
                        JPanel ctnKey = new JPanel();
                        ctnKey.setMinimumSize((new Dimension(100, 100)));
                        ctnKey.setBorder(BorderFactory.createTitledBorder("Primary key:"));
                        BoxLayout layoutKey = new BoxLayout(ctnKey, BoxLayout.Y_AXIS);
                        ctnKey.setLayout(layoutKey);

                        
                        JPanel ctnGo = new JPanel();
                        ctnGo.setBorder(BorderFactory.createTitledBorder("Go on:"));
                        BoxLayout layoutGo = new BoxLayout( ctnGo, BoxLayout.Y_AXIS);
                        ctnGo.setLayout(layoutGo);
                        cbTable.setAlignmentX(Component.CENTER_ALIGNMENT);
                        btnShowTable.setAlignmentX(Component.CENTER_ALIGNMENT);
                        btnKillConn.setAlignmentX(Component.CENTER_ALIGNMENT);
                        
                        ctnGo.add(cbTable);
                        ctnGo.add(btnShowTable);
                        ctnGo.add(btnKillConn);
                        ctnGo.setAlignmentY(TOP_ALIGNMENT);
                        ctnTabKey.add(ctnTable);
                        ctnTabKey.add(ctnKey);
                        
                        btnShowTable.addActionListener(new ActionbtnShowTable());
                        btnKillConn.addActionListener(new ActionbtnKillConn());
                        this.setLayout(new FlowLayout());
                        this.add(ctnTabKey);
                        //this.add(ctnTable);
                        //this.add(ctnKey);
                        this.add(ctnGo);
                        

                        //Auto fill
                        System.out.println("Starting\n");
                        coreDb.getTable(ctnTable, ctnKey, cbTable);
                        
        pack();
                        
}
    class ActionbtnShowTable implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
            coreDb.openTable(coreDb.getTableName(cbTable));
        }
    }
    
    class ActionbtnKillConn implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
            try {
                coreDb.killConn();
            } catch (SQLException ex) {
                Logger.getLogger(uiDb.class.getName()).log(Level.SEVERE, null, ex);
            }
            //uiLogin frmLogin = new uiLogin();
            dispose();
        }
    }
}
