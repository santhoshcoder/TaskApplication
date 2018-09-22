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
import javax.swing.border.LineBorder;

public class tasker {
	
	private JFrame frmTasker;
	int panel_2row = 0,panel_2col = 5,panel_1row = 2,panel_1col = 0;
	String selected_cat = "uncat";
	JPanel panel_1 = new JPanel();
	JPanel panel_2 = new JPanel();
	JTextField jt = new JTextField();
	JButton jb = new JButton("+");
	private JTextField textField;
	public void resetPanel2(){
		panel_2.removeAll();
		panel_2col = 5;
		panel_2row = 0;
	}
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
		frmTasker.getContentPane().setBackground(Color.WHITE);
		frmTasker.setTitle("Tasker");
		frmTasker.setBounds(100, 100, 474, 528);
		frmTasker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTasker.getContentPane().setLayout(null);
		panel_1.setForeground(new Color(128, 128, 128));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 50, 150, 432);
		frmTasker.getContentPane().add(panel_1);
		panel_1.setLayout(new MigLayout("", "[grow]", "[][]"));
		textField = new JTextField();
		panel_1.add(textField, "flowx,cell 0 0,growx");
		textField.setColumns(10);
		JButton button = new JButton("+");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String catName = textField.getText();
				catName = catName.trim();
				if(catName.length() != 0){
					catName = catName.toLowerCase();
					//Check if the category already exists
					try {
						Connection con = getConnection();
						/* Future Change:
						 * Setup the database with primary keys and reduce it to normal form. Once the tables
						 * are set perfectly you don't need to check if the category already exists or not
						 * (Directly insert the new record and make the changes depending on query status)
						 */
						PreparedStatement pr = con.prepareStatement("SELECT * FROM initial WHERE cat = ?");
						pr.setString(1,catName);
						ResultSet rs = pr.executeQuery();
						if(rs.next()) {
							System.out.println("Category Already Exists");
						}
						else {
							pr = con.prepareStatement("INSERT INTO initial values(?,?,?)");
							Statement s = con.createStatement();
							String sql = "INSERT INTO initial values("+null+","+"'"+catName+"'"+","+null+")";
							s.execute(sql);
							System.out.println("Query Executed");
							//Capitalize the button for clean look.
							String cap_column_name = catName.substring(0, 1).toUpperCase() + catName.substring(1).toLowerCase();
							JButton j = new JButton(cap_column_name);
							categoryListener cl = new categoryListener();
							j.addActionListener(cl);
							panel_1.add(j,"cell "+panel_1col+" "+panel_1row+",growx");
							panel_1row++;
							panel_1.revalidate();
							panel_1.repaint();
						}
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
				else {
					System.out.println("Invalid Category Name:"+"*"+catName+"*");
				}
				textField.setText("");
			}
		});
		panel_1.add(button, "cell 0 0");
		//JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(148, 50, 315, 432);
		frmTasker.getContentPane().add(panel_2);
		panel_2.setLayout(new MigLayout("", "[][][][][][]", "[][][][]"));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 150, 50);
		frmTasker.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblCategories = new JLabel("Categories");
		lblCategories.setForeground(new Color(0, 0, 0));
		lblCategories.setBounds(22, 10, 95, 25);
		panel.add(lblCategories);
		lblCategories.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setBounds(148, 0, 315, 50);
		frmTasker.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblTasks = new JLabel("Tasks");
		lblTasks.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTasks.setBounds(36, 13, 56, 16);
		panel_3.add(lblTasks);
		
		addPanel1Elements();
	}
	public class categoryListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton b = (JButton) e.getSource();
			String text = b.getText();
			selected_cat = text.substring(0, 1).toLowerCase() + text.substring(1);
			resetPanel2();
			System.out.println("Cateogry Selected is:"+selected_cat);
			addPanel2Elements();
		}
		
	}
	public class taskListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String text = jt.getText();
			text = text.trim();
			if(text.length() != 0){
				System.out.println("Entered Text is:"+text);
				System.out.println("Selected Category is:"+selected_cat);
				jt.setText("");
				//Insert the data into database and add a checkbox with data into panel_2
			}
		}
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
				String cap_column_name = column_name.substring(0, 1).toUpperCase() + column_name.substring(1).toLowerCase();
				JButton j = new JButton(cap_column_name);
				categoryListener cl = new categoryListener();
				j.addActionListener(cl);
				panel_1.add(j,"cell "+panel_1col+" "+panel_1row+",growx");
				panel_1row++;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public class checkboxListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JCheckBox c = (JCheckBox)e.getSource();
			String text = c.getText();
			try {
				System.out.println("Selected Category is: "+selected_cat);
				System.out.println("Selected Task is: "+text);
				Connection con = getConnection();
				PreparedStatement pr = con.prepareStatement("UPDATE INITIAL SET status = ? WHERE cat = ? AND task = ?");
				pr.setString(1,"c");
				pr.setString(2,selected_cat);
				pr.setString(3,text);
				pr.executeUpdate();
				panel_2.remove(c);
				panel_2.revalidate();
				panel_2.repaint();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	private void addPanel2Elements() {
		// TODO Auto-generated method stub
		panel_2.add(jt,"cell "+panel_2col+" "+panel_2row);
		jt.setColumns(10);
		panel_2.add(jb,"cell "+panel_2col+" "+panel_2row);
		taskListener t1 = new taskListener();
		jb.addActionListener(t1);
		panel_2row++;
		try 
		{
			//System.out.println("Selected Category is:"+selected_cat);
			//Select data from the database
			Connection con = getConnection();
			PreparedStatement pr = con.prepareStatement("SELECT task FROM initial WHERE cat = ? AND task IS NOT NULL AND status = ?");
			pr.setString(1,selected_cat);
			pr.setString(2,"nc");
			ResultSet rs = pr.executeQuery();
			while(rs.next()) {
				String name = rs.getString("task");
				System.out.println("Task Name is:"+name);	
				String task_name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
				JCheckBox j1 = new JCheckBox(task_name);
				checkboxListener clr = new checkboxListener();
				j1.addActionListener(clr);
				panel_2.add(j1,"cell "+panel_2col+" "+panel_2row+",growx");
				panel_2row++;
			}
			panel_2.revalidate();
			panel_2.repaint();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
