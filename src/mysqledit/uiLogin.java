package mysqledit;

/**
 *
 * @author ZeroDay
 */
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.MenuListener;
import net.miginfocom.swing.MigLayout;
public class uiLogin extends JFrame {
 	private JPanel contentPane;
	private JTextField jtxtHost;
	private JTextField jtxtUser;
	private JTextField jtxtDb;
	private JPasswordField jtxtPass;
        private coreLogin coreLogin;
        private Dimension dBt = new Dimension(75,25); //Sets the size of the button in the  JMenuBar
        private JProgressBar progressBar = new JProgressBar();
        private JLabel lblConnected = new JLabel("Connected!");
        private JLabel lblConnecting = new JLabel("Connecting...");
        private JComboBox cbListDb = new JComboBox();
    public  uiLogin() {
         try {
             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(uiLogin.class.getName()).log(Level.SEVERE, null, ex);
         } catch (InstantiationException ex) {
             Logger.getLogger(uiLogin.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IllegalAccessException ex) {
             Logger.getLogger(uiLogin.class.getName()).log(Level.SEVERE, null, ex);
         } catch (UnsupportedLookAndFeelException ex) {
             Logger.getLogger(uiLogin.class.getName()).log(Level.SEVERE, null, ex);
         }
     setName("frameLogin");
		setTitle("MySQLEdit");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 423, 275);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenuItem mnAbout = new JMenuItem("About");
                mnAbout.setMinimumSize(dBt);
                mnAbout.setPreferredSize(dBt);
                mnAbout.setMaximumSize(dBt);
                mnAbout.addActionListener(new ActionListener() 
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            //Execute when JMenu is pressed
                                 JOptionPane.showMessageDialog(null, "\nCross-platform Java application for editing MySQL Databases \nCopyright(c) 2015, 2017 Hu Sonny\nVersion: 1.0\n\nThis is free software licensed under the GPL License\n MySQLEditor comes with ABSOLUTELY NO WARRANTY." , "About", JOptionPane.INFORMATION_MESSAGE);
                        }
                    });
               // mnAbout.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(mnAbout);

		
		JMenuItem mnExit = new JMenuItem("Exit");
                mnExit.addActionListener(new exit());
                //mnExit.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(mnExit);
                JMenu mnSkin = new JMenu("Skin");
                JMenuItem mnItemAluOxide = new JMenuItem("AluOxide");
                mnItemAluOxide.addActionListener(new skinAluOxide());
                JMenuItem mnItemPlain = new JMenuItem("Plain");
                mnItemPlain.addActionListener(new skinPlain());
                JMenuItem mnItemNimbus = new JMenuItem("Nimbus");
                mnItemNimbus.addActionListener(new skinNimbus());
                mnSkin.add(mnItemNimbus);
                mnSkin.add(mnItemPlain);
                mnSkin.add(mnItemAluOxide);
                menuBar.add(mnSkin);
                
		contentPane = new JPanel();
		contentPane.setBorder(UIManager.getBorder("ToolBar.border"));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][][][][]"));
		
		JLabel lblHost = new JLabel("Host :");
		lblHost.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblHost, "cell 1 1,alignx trailing");
		
		jtxtHost = new JTextField();
		contentPane.add(jtxtHost, "flowx,cell 2 1,alignx left");
		jtxtHost.setColumns(16);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblUsername, "cell 1 2,alignx trailing");
		
		jtxtUser = new JTextField();
		contentPane.add(jtxtUser, "cell 2 2,alignx left");
		jtxtUser.setColumns(16);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblPassword, "cell 1 3,alignx trailing");
		
		jtxtPass = new JPasswordField();
		jtxtPass.setColumns(16);
		contentPane.add(jtxtPass, "cell 2 3,alignx left");
		
		JLabel lblDatabase = new JLabel("Database:");
		lblDatabase.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblDatabase, "cell 1 4,alignx trailing");
		
                jtxtDb = new JTextField();
		contentPane.add(jtxtDb, "cell 2 4,alignx left");
		jtxtDb.setColumns(16);
		
		Box horizontalBox = Box.createHorizontalBox();
		contentPane.add(horizontalBox, "flowx,cell 1 6");
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		contentPane.add(horizontalBox_1, "flowx,cell 2 6");
		
		//JProgressBar progressBar = new JProgressBar();
		progressBar.setVisible(false);
		contentPane.add(progressBar, "cell 2 8");
		
                lblConnecting.setVisible(false);
		contentPane.add(lblConnecting, "cell 1 8,alignx right");
		contentPane.add(progressBar, "flowx,cell 2 8");
                
		JButton btnListDb = new JButton("List databases");
                btnListDb.addActionListener(new ActionbtnListDb());
		contentPane.add(btnListDb, "flowx,cell 2 10,alignx right");
		
		JButton btnConnect = new JButton("Connect");
                btnConnect.addActionListener(new ActionbtnConnect());
		contentPane.add(btnConnect, "cell 2 10,alignx right");
		
		JLabel lblAvailableDatabases = new JLabel("Available databases:");
		lblAvailableDatabases.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(lblAvailableDatabases, "cell 1 6,alignx trailing");
		

		cbListDb.setMaximumRowCount(20);
                cbListDb.addItemListener(new ActioncbListDb());
		contentPane.add(cbListDb, "cell 2 6,alignx left");
                
                lblConnected.setVisible(false);
		contentPane.add(lblConnected, "cell 2 8");
                
                getRootPane().setDefaultButton(btnConnect);
                //pack();
                this.setResizable(true);
                this.setVisible(true);
    }
    
    class ActionbtnConnect implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
            try {
                if(coreLogin.connect(jtxtHost,jtxtUser,jtxtPass,jtxtDb, progressBar, lblConnected, lblConnecting)!="1") {
                    JOptionPane.showMessageDialog(null, coreLogin.connect(jtxtHost,jtxtUser,jtxtPass,jtxtDb, progressBar, lblConnected, lblConnecting), "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    dispose();
                }
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(uiLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class ActionbtnListDb implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
            try {
                if(coreLogin.listDb(jtxtHost,jtxtUser,jtxtPass,jtxtDb, lblConnected, lblConnecting, cbListDb)!="1") {
                    JOptionPane.showMessageDialog(null, coreLogin.connect(jtxtHost,jtxtUser,jtxtPass,jtxtDb, progressBar, lblConnected, lblConnecting), "Error", JOptionPane.ERROR_MESSAGE);
                } 
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(uiLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //***********SKIN
    class skinAluOxide implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
            try {
                UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
            SwingUtilities.updateComponentTreeUI(getRootPane());
            pack();
        }
    }
    
    class skinPlain implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
            try {
                UIManager.setLookAndFeel(new SyntheticaPlainLookAndFeel());
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
            SwingUtilities.updateComponentTreeUI(getRootPane());
            pack();
        }
    }
    
     class skinNimbus implements ActionListener{
        public void actionPerformed(ActionEvent arg0) {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
            SwingUtilities.updateComponentTreeUI(getRootPane());
            pack();
        }
    }
    //*********Write jcombobox to jtextfield
    class ActioncbListDb implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange() == ItemEvent.SELECTED) {
            jtxtDb.setText((String) cbListDb.getSelectedItem());
        }
        }
       
    }
     
     //**********Exit
    class exit implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
    
}


