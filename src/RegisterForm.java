import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class RegisterForm extends JFrame  implements ActionListener {
	
	Connect con = new Connect();
	ResultSet rs;
	
	HomeForm homeForm;
	
	JFrame frame;
	JPanel pnl1, pnl2, pnl3, pnlGender, pnlAddress, pnlUser, pnlNull;
	JLabel title, userId, username, password, email, address, gender;
	JTextField tUserId, tUsername, tEmail;
	JPasswordField pPassword;
	JTextArea tAddress;
	JRadioButton male, female;
	JButton submit, cancel;
	ButtonGroup bGroup = new ButtonGroup();
	
	JTable tableDb;
	DefaultTableModel modelDb;
	Vector<Object> tHeader, tRow;
	
	public void fillTable() {
		modelDb = new DefaultTableModel(tHeader, 0);
		con.rs = con.executeQuery("SELECT UserID,Username,Password,Email,Address,Gender,Role FROM MsUser");
		try {
			while (con.rs.next()) {
				tRow = new Vector<Object>();
				for (int i = 1; i <= con.rsm.getColumnCount(); i++)
					tRow.add(con.rs.getObject(i) + "");
				modelDb.addRow(tRow);
			}
			tableDb.setModel(modelDb);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Updating table view...");
		RowSorter<TableModel> sort = new TableRowSorter<TableModel>(modelDb);
		tableDb.setRowSorter(sort);
	}

	public void generateID(){
		if(tableDb.getRowCount()<1){
			tUserId.setText("US001");
		}else{
			String lastID = tableDb.getValueAt(tableDb.getRowCount()-1, 0).toString() ;
			int id = Integer.parseInt(lastID.substring(lastID.indexOf("S")+1,lastID.length()));
			if(id < 9){
				tUserId.setText("US00"+(id+1));	
			}else if(id < 99){
				tUserId.setText("US0"+(id+1));
			}else if (id < 999){
				tUserId.setText("US"+(id+1));
			}
		}
		
	}
	
	public RegisterForm( ){
		tHeader = new Vector<Object>();
		tHeader.add("UserID");
		tHeader.add("Username");
		tHeader.add("Password");
		tHeader.add("Email");
		tHeader.add("Address");
		tHeader.add("Gender");
		tHeader.add("Role");
		
		frame = new JFrame();
		pnl1 = new JPanel(new GridLayout(2,1,5,5));
		pnl2 = new JPanel(new GridLayout(2,2,5,5));
		pnl3 = new JPanel(new GridLayout(3,1,5,5));
		pnlUser = new JPanel(new GridLayout(5, 2,5,5));
		pnlGender = new JPanel(new GridLayout(1,2,5,5));
		pnlAddress = new JPanel(new GridLayout(1,2,5,5));
		pnlNull = new JPanel(new GridLayout(1, 1,5,5));
		
		title = new JLabel("SIGN UP");
		userId = new JLabel("User ID");
		tUserId = new JTextField("");
		username = new JLabel("Username");
		tUsername= new JTextField("");
		password = new JLabel("Password");
		pPassword = new JPasswordField("");
		email = new JLabel("Email");
		tEmail = new JTextField("");
		gender = new JLabel("Gender");
		male = new JRadioButton("Male");
		female = new JRadioButton("Female");
		address = new JLabel("Address");
		tAddress = new JTextArea("");
		JScrollPane scrollPane = new JScrollPane(tAddress);
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		
		tableDb = new JTable();
		fillTable();
		generateID();
		
		title.setFont(title.getFont().deriveFont(Font.BOLD, 14));
		title.setHorizontalAlignment(JLabel.CENTER);
		tUserId.setEditable(false);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);;
		
		bGroup.add(male);
		bGroup.add(female);
		
		pnl1.add(title);
		pnlUser.add(userId);
		pnlUser.add(tUserId);
		pnlUser.add(username);
		pnlUser.add(tUsername);
		pnlUser.add(password);
		pnlUser.add(pPassword);
		pnlUser.add(email);
		pnlUser.add(tEmail);
		pnlUser.add(gender);
		pnlGender.add(male);
		pnlGender.add(female);
		pnlUser.add(pnlGender);
		pnlAddress.add(address);
		pnlAddress.add(scrollPane);
		pnl2.add(pnlUser);
		pnl2.add(pnlAddress);
		pnl3.add(pnlNull);
		pnl3.add(submit);
		pnl3.add(cancel);
		
		submit.addActionListener(this);
		cancel.addActionListener(this);		
		
		frame.add(pnl1,BorderLayout.NORTH);
		frame.add(pnl2,BorderLayout.CENTER);
		frame.add(pnl3,BorderLayout.SOUTH);
		frame.setTitle("Sign Up");
		frame.setSize(450, 450);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==submit){
			
			String alpha = "(.*[a-z].*)";
			String numeric = "(.*[0-9].*)";
			
			String query = "SELECT * FROM MsUser WHERE Username = '"+tUsername.getText()+"'";
			rs = con.executeQuery(query);
			
			try{
				if(tUsername.getText().equals("")||pPassword.getText().equals("")||tEmail.getText().equals("")||tAddress.getText().equals("")){
					JOptionPane.showMessageDialog(this, "All fields must be filled","Error",JOptionPane.ERROR_MESSAGE);
				}else if(!male.isSelected()&&!female.isSelected()){
					JOptionPane.showMessageDialog(this, "Gender must be selected","Error",JOptionPane.ERROR_MESSAGE);
				}else if(tUsername.getText().length()<5||tUsername.getText().length()>30){
					JOptionPane.showMessageDialog(this, "Username length must be between 5 and 30 characters","Error",JOptionPane.ERROR_MESSAGE);
				}else if(rs.next()){
					JOptionPane.showMessageDialog(this, "Username must be unique","Error",JOptionPane.ERROR_MESSAGE);
				}else if(!pPassword.getText().matches(alpha)){
					JOptionPane.showMessageDialog(this, "Password must be alphanumeric","Error",JOptionPane.ERROR_MESSAGE);
				}else if(!pPassword.getText().matches(numeric)){
					JOptionPane.showMessageDialog(this, "Password must be alphanumeric","Error",JOptionPane.ERROR_MESSAGE);
				}else if(pPassword.getText().length()<5||pPassword.getText().length()>20){
					JOptionPane.showMessageDialog(this, "Password length must be between 5 and 20 characters","Error",JOptionPane.ERROR_MESSAGE);
				}else if(tEmail.getText().startsWith("@")||tEmail.getText().startsWith(".")){
					JOptionPane.showMessageDialog(this, "Email must not starts with '@' and '.'","Error",JOptionPane.ERROR_MESSAGE);
				}else if(tEmail.getText().endsWith("@")||tEmail.getText().endsWith(".")){
					JOptionPane.showMessageDialog(this, "Email must not ends with '@' and '.'","Error",JOptionPane.ERROR_MESSAGE);
				}else if(!tEmail.getText().endsWith(".com")&&!tEmail.getText().endsWith("co.id")){
					JOptionPane.showMessageDialog(this, "Email must ends with \".com\" or \".co.id\"","Error",JOptionPane.ERROR_MESSAGE);
				}else if(!tAddress.getText().endsWith(" Street")){
					JOptionPane.showMessageDialog(this, "Address must ends with \"Street\"","Error",JOptionPane.ERROR_MESSAGE);			
				}else{
					String gender = "Male";
				
					if(male.isSelected()){
						gender = "Male";
					}else if(female.isSelected()){
						gender = "Female";
					}
				
					query = "INSERT INTO MsUser(UserID,Username,Password,Email,Address,Gender,Role) VALUES('"+tUserId.getText()+"','"+tUsername.getText()+"','"+pPassword.getText()+"','"+tEmail.getText()+"','"+tAddress.getText()+"','"+gender+"','User')";
					if(con.executeUpdate(query)>0){
						JOptionPane.showMessageDialog(this, "Registration Succesfull","Message",JOptionPane.INFORMATION_MESSAGE);			
					}
				
				
					frame.setVisible(false);
					homeForm = new HomeForm();
				}
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}if(e.getSource()==cancel){
			frame.setVisible(false);
			homeForm = new HomeForm();
		}
	}

}
