import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class ViewAllTransactionForm extends JInternalFrame implements MouseListener, ListSelectionListener{
	Connect con = new Connect();
	ResultSet rs;
	
	MainForm mainForm;
	
	JPanel pnl1, pnl2, pnl3, pnl4, pnlTable, pnlProduct;
	JLabel title, username, productID, productName, watchType, quantity, price, totalPrice;
	JTextField tUsername, tProductID, tProductName, tWatchType, tQuantity, tPrice, tTotalPrice;

	JTable tableDb;
	DefaultTableModel modelDb;
	Vector<Object> tHeader, tRow;
	
	public void fillTable() {
		modelDb = new DefaultTableModel(tHeader, 0);
		con.rs = con.executeQuery("SELECT TransactionID, UserID, TransactionDate FROM TrTransaction");
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
	
	public ViewAllTransactionForm() {
		super("",false,true,false);
		
		tHeader = new Vector<Object>();
		tHeader.add("TransactionID");
		tHeader.add("UserID");
		tHeader.addElement("TransactionDate");
		
		title = new JLabel("View All Transaction");
		username = new JLabel("Username");
		productID = new JLabel("Product ID");
		productName = new JLabel("Product Name");
		watchType = new JLabel("Watch Type");
		quantity = new JLabel("Quantity");
		price = new JLabel("Price");
		totalPrice = new JLabel("Total Price");
		
		tUsername = new JTextField();
		tProductID = new JTextField();
		tProductName = new JTextField();
		tWatchType = new JTextField();
		tQuantity = new JTextField();
		tPrice = new JTextField();
		tTotalPrice = new JTextField();
		
		tableDb = new JTable();
		fillTable();
		
		title.setFont(title.getFont().deriveFont(Font.BOLD, 14));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		pnl1 = new JPanel(new GridLayout(1, 1,5,5));
		pnl2 = new JPanel(new GridLayout(2,1,10,10));
		pnl3 = new JPanel();
		pnl4 = new JPanel();
		pnlTable = new JPanel(new GridLayout(1,1,5,5));
		pnlProduct = new JPanel(new GridLayout(7,2,5,5));
		
		pnlTable.add(new JScrollPane(tableDb));
		
		pnlProduct.add(username);
		pnlProduct.add(tUsername);
		pnlProduct.add(productID);
		pnlProduct.add(tProductID);
		pnlProduct.add(productName);
		pnlProduct.add(tProductName);
		pnlProduct.add(watchType);
		pnlProduct.add(tWatchType);
		pnlProduct.add(quantity);
		pnlProduct.add(tQuantity);
		pnlProduct.add(price);
		pnlProduct.add(tPrice);
		pnlProduct.add(totalPrice);
		pnlProduct.add(tTotalPrice);
		
		pnl1.add(title);
		pnl2.add(pnlTable);
		pnl2.add(pnlProduct);
	
		tUsername.setEditable(false);
		tProductID.setEditable(false);
		tProductName.setEditable(false);
		tWatchType.setEditable(false);
		tQuantity.setEditable(false);
		tPrice.setEditable(false);
		tTotalPrice.setEditable(false);
		
		tableDb.addMouseListener(this);
		tableDb.getSelectionModel().addListSelectionListener(this);
		
		add(pnl1,BorderLayout.NORTH);
		add(pnl2,BorderLayout.CENTER);
		add(pnl3,BorderLayout.WEST);
		add(pnl4,BorderLayout.EAST);
		setTitle("View All Transaction");
		setSize(new Dimension(600, 450));
		setLocation(100,100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void fillField(){
		
		String query = "SELECT u.Username, t.ProductID, p.ProductName, t.WatchType, t.Quantity, p.ProductPrice, t.Quantity * p.ProductPrice AS Total FROM TrTransaction t, MsUser u, MsProduct p WHERE t.UserID = u.UserID AND t.ProductID = p.ProductID AND t.TransactionDate =  '"+tableDb.getValueAt(tableDb.getSelectedRow(), 2).toString()+"'";
		rs=con.executeQuery(query);
		try{
			if(rs.next()){
				tUsername.setText(rs.getString("u.Username"));
				tProductID.setText(rs.getString("t.ProductID"));
				tProductName.setText(rs.getString("p.ProductName"));
				tWatchType.setText(rs.getString("t.WatchType"));
				tQuantity.setText(rs.getString("t.Quantity"));
				tPrice.setText(rs.getString("p.ProductPrice"));
				tTotalPrice.setText(rs.getString("Total"));
			}
		}catch(SQLException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		fillField();
	}

}
