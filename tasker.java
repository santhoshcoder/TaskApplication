import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;

public class tasker {
	
	private JFrame frmTasker;
	int panel_2row = 0,panel_2col = 1,panel_1row = 2,panel_1col = 0;
	String selected_cat = "uncat";
	JPanel panel_1 = new JPanel();
	JPanel panel_2 = new JPanel();
	private JTextField textField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tasker window = new tasker();
					window.frmTasker.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try 
		{
			//Select data from the database
			Connection con = getConnection();
			PreparedStatement pr = con.prepareStatement("SELECT * FROM initial");
			ResultSet rs = pr.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString("task"));
				System.out.println(rs.getString("cat"));
				System.out.println(rs.getString("status"));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws Exception{
		try
		  {
			   String driver = "com.mysql.cj.jdbc.Driver";
			   String url = "jdbc:mysql://localhost:3306/tasks";  //Database Name: tasks
			   String username = "root";
			   String password = "";
			   Class.forName(driver);
			   Connection conn = DriverManager.getConnection(url,username,password);
			   System.out.println("Connected");
			   return conn;
		  } 
		  catch(Exception e)
		  {
			  System.out.println(e);
		  }
		  return null;
	}
	/**
	 * Create the application.
	 */
	public tasker() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTasker = new JFrame();
		frmTasker.setTitle("Tasker");
		frmTasker.setBounds(100, 100, 450, 300);
		frmTasker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTasker.getContentPane().setLayout(null);
		
		//JPanel panel_1 = new JPanel();
		panel_1.setForeground(new Color(128, 128, 128));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 0, 150, 253);
		frmTasker.getContentPane().add(panel_1);
		panel_1.setLayout(new MigLayout("", "[grow]", "[][]"));
		
		JLabel lblCategories = new JLabel("Categories");
		lblCategories.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_1.add(lblCategories, "cell 0 0,growx");
		
		textField = new JTextField();
		panel_1.add(textField, "flowx,cell 0 1,growx");
		textField.setColumns(10);
		
		JButton button = new JButton("+");
		panel_1.add(button, "cell 0 1");
		
		//JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(148, 0, 284, 253);
		frmTasker.getContentPane().add(panel_2);
		panel_2.setLayout(new MigLayout("", "[]", "[]"));
		
		addPanel1Elements();
	}
	private void addPanel1Elements() {
		// TODO Auto-generated method stub
		try 
		{
			//Select data from the database
			Connection con = getConnection();
			PreparedStatement pr = con.prepareStatement("SELECT DISTINCT(cat) FROM initial");
			ResultSet rs = pr.executeQuery();
			
			while(rs.next()) {
				String column_name = rs.getString("cat");
				JCheckBox j = new JCheckBox(column_name);
				panel_1.add(j,"cell "+panel_1col+" "+panel_1row+",growx");
				panel_1row++;
			}
			panel_2.revalidate();
			panel_2.repaint();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
