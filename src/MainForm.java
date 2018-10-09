import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainForm extends JFrame implements ActionListener{
	LoginForm loginForm;
	HomeForm homeForm;
	ViewAllTransactionForm viewAllTransactionForm;
	ManageProductForm manageProductForm;
	ManageWatchTypeForm manageWatchTypeForm;
	BuyProductForm buyProductForm;
	
	JFrame frame;
	JMenuBar menuBar;
	JMenu user, transaction, manage;
	JMenuItem signOut, exit;
	JMenuItem  buyProduct, viewAllTransaction;
	JMenuItem manageProduct, manageWatchType;
	JDesktopPane dp;
	
	JLabel lImage;
	JPanel pnl1;
	ImageIcon applicationLogo;
	
	JTextField tUserID;
	
	public MainForm(String userID, String role) {
		tUserID = new JTextField(userID);
		
		pnl1 = new JPanel();
		applicationLogo = new ImageIcon("asset/mainForm.jpg");
		lImage = new JLabel(applicationLogo);
		pnl1.add(lImage);
		
		frame = new JFrame();
		dp = new JDesktopPane();
		
		user = new JMenu("User");
		transaction = new JMenu("Transaction");
		manage = new JMenu("Manage");
		
		signOut = new JMenuItem("Sign Out");
		exit = new JMenuItem("Exit");
		
		buyProduct = new JMenuItem("Buy Product");
		viewAllTransaction = new JMenuItem("View All Transaction");
		
		manageProduct = new JMenuItem("Manage Product");
		manageWatchType = new JMenuItem("Manage Watch Type");
		
		menuBar = new JMenuBar();
		
		if(role.equals("Admin")){
			buyProduct.setVisible(false);
		}else if(role.equals("User")){
			manage.setVisible(false);
			viewAllTransaction.setVisible(false);
		}
		
		menuBar.add(user);
		menuBar.add(transaction);
		menuBar.add(manage);
		
		user.add(signOut);
		user.add(exit);
		
		transaction.add(buyProduct);
		transaction.add(viewAllTransaction);
		
		manage.add(manageProduct);
		manage.add(manageWatchType);
		
		signOut.addActionListener(this);
		exit.addActionListener(this);
		viewAllTransaction.addActionListener(this);
		buyProduct.addActionListener(this);
		manageProduct.addActionListener(this);
		manageWatchType.addActionListener(this);
		
		frame.add(pnl1,BorderLayout.CENTER);
		frame.setTitle("AGemo Watch Shop");
		frame.setJMenuBar(menuBar);
		frame.setContentPane(dp);
		frame.setExtendedState(MAXIMIZED_BOTH);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==signOut){
			frame.setVisible(false);
			homeForm = new HomeForm();
		}if(e.getSource()==exit){
			System.exit(0);
		}if(e.getSource()==viewAllTransaction){
			viewAllTransactionForm = new ViewAllTransactionForm();
			dp.add(viewAllTransactionForm);
		}if(e.getSource() == buyProduct){
			buyProductForm = new BuyProductForm(tUserID.getText());
			dp.add(buyProductForm);
		}if(e.getSource()==manageProduct){
			manageProductForm = new ManageProductForm();
			dp.add(manageProductForm);
		}if(e.getSource()==manageWatchType){
			manageWatchTypeForm = new ManageWatchTypeForm();
			dp.add(manageWatchTypeForm);
		}	
	}
}

