import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


public class HomeForm extends JFrame  implements ActionListener{
	
	Connect con = new Connect();
	ResultSet rs;
	
	LoginForm loginForm;
	RegisterForm registerForm;
	MainForm mainForm;
	
	JFrame frame;
	JLabel lImage;
	JPanel pnl1,pnl2;
	ImageIcon applicationLogo = new ImageIcon("asset/homeForm.jpg");
	JButton signIn, signUp;
	
	public HomeForm(){
		
		frame = new JFrame();
		pnl1 = new JPanel();
		pnl2 = new JPanel(new GridLayout(2,1,5,5));
		
		signIn = new JButton("Sign In");
		signUp = new JButton("Sign Up");
		
		lImage = new JLabel(applicationLogo);
		
		pnl1.add(lImage);
		pnl2.add(signIn);
		pnl2.add(signUp);
		
		signIn.addActionListener(this);
		signUp.addActionListener(this);
		
		frame.add(pnl1,BorderLayout.CENTER);
		frame.add(pnl2,BorderLayout.SOUTH);
		frame.setTitle("AGemo Watch Shop");
		frame.setSize(450, 450);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==signIn){
			frame.setVisible(false);
			loginForm = new LoginForm();
			
		}if(e.getSource()==signUp){
			frame.setVisible(false);
			registerForm = new RegisterForm();
		}
		
	}
	
}