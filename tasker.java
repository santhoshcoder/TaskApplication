import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JFrame;

public class tasker {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tasker window = new tasker();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		try {
			getConnection();
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
