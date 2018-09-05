import java.awt.EventQueue;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import java.awt.Font;

public class taskApplication {

	private JFrame frame;
	private JTextField textField;
	int col,row;
	Vector<String> items = new Vector<String>();
	JPanel Notes; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					taskApplication window = new taskApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public taskApplication() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	public class Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Button Clicked");
			// TODO Auto-generated method stub
			String newItem = textField.getText();
			newItem.trim();
			if(newItem.trim().length() > 0){
				try
				{
					FileWriter fw = new FileWriter("input.txt", true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw);
					items.add(newItem);
					JCheckBox c1 = new JCheckBox(newItem);
					items.add(newItem);
					Notes.add(c1," cell "+col+" "+row);
					row++;
					out.println(newItem);
					out.close();
					textField.setText("");
				}
				catch (Exception exp)
				{
					exp.printStackTrace();
				}
				Notes.revalidate();
				Notes.repaint();
			}
		}
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		Notes = new JPanel();
		Notes.setBackground(new Color(204, 204, 204));
		GridBagConstraints gbc_Notes = new GridBagConstraints();
		gbc_Notes.fill = GridBagConstraints.BOTH;
		gbc_Notes.gridx = 0;
		gbc_Notes.gridy = 0;
		frame.getContentPane().add(Notes, gbc_Notes);
		Notes.setLayout(new MigLayout("", "[][grow][1px][grow][]", "[][][][][][1px][][][]"));
		Notes.setLayout(new MigLayout("", "[1px][][][]", "[1px][]"));
		Listener l = new Listener();
		
		JLabel lblNotes = new JLabel("Tasks");
		lblNotes.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		Notes.add(lblNotes, "cell 1 0,alignx center,growy");
		
		textField = new JTextField();
		Notes.add(textField, "cell 1 1,grow");
		textField.setColumns(10);
		
		JButton btnAdd = new JButton("+");
		btnAdd.addActionListener(l);
		Notes.add(btnAdd, "cell 2 1,alignx left,growy");
		
		try {
			FileReader fr = new FileReader("input.txt");
			BufferedReader br = new BufferedReader(fr);
			String line;
			col = 1;
			row = 2;
			while((line = br.readLine())!=null) {
				JCheckBox c1 = new JCheckBox(line);
				items.add(line);
				Notes.add(c1," cell "+col+" "+row);
				row++;
			}
			br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
