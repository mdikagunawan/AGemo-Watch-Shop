import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class BuyProductForm extends JInternalFrame implements ActionListener, MouseListener, ListSelectionListener{
	Connect con = new Connect();
	ResultSet rs;
	
	MainForm mainForm;
	
	JPanel pnl1, pnl2, pnl3,pnl4,pnl5, pnlTable, pnlProduct;
	JLabel title, productID, productName, watchType, price, quantity;
	JTextField tProductID, tProductName, tWatchType, tPrice;
	JSpinner sQuantity;
	JButton buy;
	JScrollPane scroll;
	
	JTable tableDb,tableTr;
	DefaultTableModel modelDb,modelTr;
	Vector<Object> tHeader, tRow, trHeader, trRow;
	
	JTextField tUID;
	
	public void fillTable() {
		modelDb = new DefaultTableModel(tHeader, 0);
		con.rs = con.executeQuery("SELECT ProductID, ProductName, WatchType, ProductPrice, Stock FROM MsProduct");
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
	
	public void fillTransaction() {
		modelTr = new DefaultTableModel(trHeader, 0);
		con.rs = con.executeQuery("SELECT TransactionID,UserID,TransactionDate,ProductID,WatchType,Quantity FROM TrTransaction");
		try {
			while (con.rs.next()) {
				trRow = new Vector<Object>();
				for (int i = 1; i <= con.rsm.getColumnCount(); i++)
					trRow.add(con.rs.getObject(i) + "");
				modelTr.addRow(trRow);
			}
			tableTr.setModel(modelTr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Updating table view...");
		RowSorter<TableModel> sort = new TableRowSorter<TableModel>(modelTr);
		tableTr.setRowSorter(sort);
	}
	
	public String generateID(){
		String tTransactionID = null;
		if(tableTr.getRowCount()<1){
			tTransactionID="TR001";
		}else{
			String lastID = tableTr.getValueAt(tableTr.getRowCount()-1, 0).toString() ;
			int id = Integer.parseInt(lastID.substring(lastID.indexOf("R")+1,lastID.length()));
			if(id < 9){
				tTransactionID="TR00"+(id+1);	
			}else if(id < 99){
				tTransactionID="TR0"+(id+1);
			}else if (id < 999){
				tTransactionID="TR"+(id+1);
			}
		}
		
		return tTransactionID;
		
	}
	
	public BuyProductForm(String tUserID) {
		super("",false,true,false);

		tUID = new JTextField(tUserID);
		
		tHeader = new Vector<Object>();
		tHeader.add("ProductID");
		tHeader.add("ProductName");
		tHeader.add("WatchType");
		tHeader.add("ProductPrice");
		tHeader.add("Stock");
		
		trHeader = new Vector<Object>();
		trHeader.add("TransactionID");
		trHeader.add("UserID");
		trHeader.add("TransactionDate");
		trHeader.add("ProductID");
		trHeader.add("WatchType");
		trHeader.add("Quantity");
				
		
		title = new JLabel("Buy Product");
	
		productID = new JLabel("Product ID");
		productName = new JLabel("Product Name");
		watchType = new JLabel("Product Type");
		price = new JLabel("Price");
		quantity = new JLabel("Quantity");
		
		tProductID = new JTextField();
		tProductName = new JTextField();
		tWatchType = new JTextField();
		tPrice = new JTextField();
		sQuantity = new JSpinner();
		

		tableDb = new JTable();
		tableTr = new JTable();
		fillTable();

		title.setFont(title.getFont().deriveFont(Font.BOLD, 14));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		buy = new JButton("Buy");
		
		pnl1 = new JPanel(new GridLayout(1, 1,5,5));
		pnl2 = new JPanel(new GridLayout(2,1,10,10));
		pnl3 = new JPanel(new GridLayout(1, 3,5,5));
		pnl4 = new JPanel();
		pnl5 = new JPanel();
		
		pnlTable = new JPanel(new GridLayout(1,1,5,5));
		pnlProduct = new JPanel(new GridLayout(5,2,5,5));
		
		pnlTable.add(new JScrollPane(tableDb));
		
		pnlProduct.add(productID);
		pnlProduct.add(tProductID);
		pnlProduct.add(productName);
		pnlProduct.add(tProductName);
		pnlProduct.add(watchType);
		pnlProduct.add(tWatchType);
		pnlProduct.add(price);
		pnlProduct.add(tPrice);
		pnlProduct.add(quantity);
		pnlProduct.add(sQuantity);
		
		pnl1.add(title);
		pnl2.add(pnlTable);
		pnl2.add(pnlProduct);
		pnl3.add(buy);
		
		tProductID.setEditable(false);
		tProductName.setEditable(false);
		tWatchType.setEditable(false);
		tPrice.setEditable(false);
		sQuantity.setEnabled(false);
		sQuantity.setValue(0);
		sQuantity.setEditor(new JSpinner.NumberEditor(sQuantity));
		
		buy.addActionListener(this);
		
		tableDb.addMouseListener(this);
		tableDb.getSelectionModel().addListSelectionListener(this);
		
		add(pnl1,BorderLayout.NORTH);
		add(pnl2,BorderLayout.CENTER);
		add(pnl3,BorderLayout.SOUTH);
		add(pnl4,BorderLayout.WEST);
		add(pnl5,BorderLayout.EAST);
		setTitle("AGemo Watch Shop:Buy Product");
		setSize(new Dimension(600, 450));
		setLocation(100,100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
	}
	
	public void fillField(){
		try{
			tProductID.setText(tableDb.getValueAt(tableDb.getSelectedRow(), 0).toString());
			tProductName.setText(tableDb.getValueAt(tableDb.getSelectedRow(), 1).toString());
			tWatchType.setText(tableDb.getValueAt(tableDb.getSelectedRow(), 2).toString());
			tPrice.setText(tableDb.getValueAt(tableDb.getSelectedRow(), 3).toString());
			sQuantity.setValue(0);
		}catch(Exception E){}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==buy){
		
			String stringStock;
			
			int intQuantity = 0;
			int intPrice = 0;
			int intStock = 0;
			int totalPrice;
			
			
			try{
				stringStock = tableDb.getValueAt(tableDb.getSelectedRow(), 4).toString();
				intStock = Integer.parseInt(stringStock);
				intQuantity = (Integer) sQuantity.getValue();
				intPrice = Integer.parseInt(tPrice.getText());
			}catch(Exception E){}
			
			if(tableDb.getSelectedRow()<0){
				JOptionPane.showMessageDialog(this, "Must select a data first","Error",JOptionPane.ERROR_MESSAGE);
			}else{
				if(intQuantity<=0){
					JOptionPane.showMessageDialog(this, "Minimum input for Quantity is more than 0","Error",JOptionPane.ERROR_MESSAGE);
				}else if(intQuantity>intStock){
					JOptionPane.showMessageDialog(this, "Quantitiy can't be more than available stock","Error",JOptionPane.ERROR_MESSAGE);
				}else{
					
					totalPrice = intQuantity*intPrice;
					int confirmDialog=JOptionPane.showConfirmDialog(this, "Are you sure want to buy this product with total price: "+totalPrice+" ?","Buy",JOptionPane.YES_NO_OPTION);
					if(confirmDialog==JOptionPane.NO_OPTION){
						
					}else if(confirmDialog==JOptionPane.CLOSED_OPTION){
						
					}
					else if(confirmDialog==JOptionPane.YES_OPTION){
						intStock=intStock-intQuantity;
						String query = "SELECT * FROM MsProduct WHERE ProductID = '"+tProductID.getText()+"'";
						rs = con.executeQuery(query);
						try {
							if(rs.next()){
								query = "UPDATE MsProduct SET Stock='"+intStock+"' WHERE ProductID = '"+tProductID.getText()+"'";
								if(con.executeUpdate(query)>0){
									fillField();
									fillTransaction();
									String gId = generateID();
									query = "INSERT INTO TrTransaction(TransactionID,UserID,TransactionDate,ProductID,WatchType,Quantity) VALUES('"+gId+"','"+tUID.getText()+"',NOW(),'"+tProductID.getText()+"','"+tWatchType.getText()+"','"+intQuantity+"')";
									if(con.executeUpdate(query)>0){
										JOptionPane.showMessageDialog(this, "Product purchased");
									}
									
								}
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						fillTable();
						fillField();
					}
				}
			}
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		sQuantity.setEnabled(true);
		fillField();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
