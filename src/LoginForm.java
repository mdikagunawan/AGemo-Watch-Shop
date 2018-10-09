import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JFrame  implements ActionListener{
	Connect con = new Connect();
	ResultSet rs;
	
	HomeForm homeForm;
	MainForm mainForm;
	
	
	JFrame frame;
	JPanel pnl1,pnl2,pnl3,pnl4;
	JLabel title, username, password;
	JTextField tUsername;
	JPasswordField pPassword;
	JButton signIn, cancel;
	
	public LoginForm() {
		
		frame = new JFrame();
		pnl1 = new JPanel(new GridLayout(1,1,5,5));
		pnl2 = new JPanel(new GridLayout(5,1,5,5));
		pnl3 = new JPanel(new GridLayout(2,1,5,5));
		
		title = new JLabel("SIGN IN");
		username = new JLabel("Username");
		password = new JLabel("Password");
		tUsername = new JTextField("");
		pPassword = new JPasswordField("");
		signIn = new JButton("Sign In");
		cancel = new JButton("Cancel");
		
		title.setFont(title.getFont().deriveFont(Font.BOLD, 14));
		title.setHorizontalAlignment(JLabel.CENTER);
		username.setHorizontalAlignment(JLabel.CENTER);
		password.setHorizontalAlignment(JLabel.CENTER);
		
		pnl1.add(title);
		pnl2.add(username);
		pnl2.add(tUsername);
		pnl2.add(password);
		pnl2.add(pPassword);
		pnl3.add(signIn);
		pnl3.add(cancel);
		
		signIn.addActionListener(this);
		cancel.addActionListener(this);
		
		frame.add(pnl1,BorderLayout.NORTH);
		frame.add(pnl2,BorderLayout.CENTER);
		frame.add(pnl3,BorderLayout.SOUTH);
		frame.setTitle("Sign In");
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==signIn){
			
			if(tUsername.getText().equals("")||pPassword.getText().equals("")){
				JOptionPane.showMessageDialog(this, "Username and Password must be filled","Error",JOptionPane.ERROR_MESSAGE);
			}else{
				String query = "SELECT * FROM MsUser WHERE Username = '"+tUsername.getText()+"' AND Password = '"+pPassword.getText()+"'";
				rs = con.executeQuery(query);
				
				try {
					if(rs.next()){
						JOptionPane.showMessageDialog(this, "Welcome, "+rs.getString("Username"));
						mainForm = new MainForm(rs.getString("UserID"),rs.getString("Role"));
						frame.setVisible(false);
					}else{
						JOptionPane.showMessageDialog(this, "invalid username or password");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}if(e.getSource()==cancel){
			frame.setVisible(false);
			homeForm = new HomeForm(); 
		}
	}
	

}
