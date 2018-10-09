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
import javax.swing.JComboBox;
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

public class ManageProductForm extends JInternalFrame  implements ActionListener, MouseListener, ListSelectionListener{
	Connect con = new Connect();
	ResultSet rs;
	
	MainForm mainForm;
	
	JPanel pnl1, pnl2, pnl3, pnl4, pnl5, pnlTable, pnlProduct, pnlButton1, pnlButton2;
	JLabel title, productID, productName, watchType, price, stock;
	JTextField tProductID;
	JTextField tProductName, tPrice;
	JComboBox cWatchType;
	JSpinner sStock;
	JButton bInsert, bUpdate, bDelete, bSubmit, bCancel;
	
	JTable tableDb;
	DefaultTableModel modelDb;
	Vector<Object> lWatchType, tHeader, tRow;
	
	public void fillWatchType(){
		cWatchType.addItem("Please Choose Type");
		con.rs = con.executeQuery("SELECT TypeName FROM MsWatchType");
		try {
			while (con.rs.next()) {
				lWatchType = new Vector<Object>();
				for (int i = 1; i <= con.rsm.getColumnCount(); i++)
					lWatchType.add(con.rs.getObject(i) + "");
					String s = lWatchType.toString();
					s=s.replace("[","");
					s=s.replace("]","");
					cWatchType.addItem(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
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

	public void generateID(){
		if(tableDb.getRowCount()<1){
			tProductID.setText("PR001");
		}else{
			String lastID = tableDb.getValueAt(tableDb.getRowCount()-1, 0).toString() ;
			int id = Integer.parseInt(lastID.substring(lastID.indexOf("R")+1,lastID.length()));
			if(id < 9){
				tProductID.setText("PR00"+(id+1));	
			}else if(id < 99){
				tProductID.setText("PR0"+(id+1));
			}else if (id < 999){
				tProductID.setText("PR"+(id+1));
			}
		}
		
	}
	
	public ManageProductForm() {
		super("",false,true,false);
		
		tHeader = new Vector<Object>();
		tHeader.add("Product ID");
		tHeader.add("Product Name");
		tHeader.add("Watch Type");
		tHeader.add("Product Price");
		tHeader.add("Product Stock");
		
		title = new JLabel("Manage Product");
		
		productID = new JLabel("Product ID");
		productName = new JLabel("Product Name");
		watchType = new JLabel("Watch Type");
		price = new JLabel("Price");
		stock = new JLabel("Stock");
		
		tProductID = new JTextField();
		tProductName = new JTextField();
		cWatchType = new JComboBox<Object>();
		tPrice = new JTextField();
		sStock = new JSpinner();
		
		bInsert = new JButton("Insert");
		bUpdate = new JButton("Update");
		bDelete = new JButton("Delete");
		bSubmit = new JButton("Submit");
		bCancel = new JButton("Cancel");
		
		tableDb = new JTable();
		fillTable();
		fillWatchType();

		title.setFont(title.getFont().deriveFont(Font.BOLD, 14));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		pnl1 = new JPanel(new GridLayout(1, 1,5,5));
		pnl2 = new JPanel(new GridLayout(2,1,10,10));
		pnl3 = new JPanel(new GridLayout(1, 2,5,5));
		pnl4 = new JPanel();
		pnl5 = new JPanel();
		pnlTable = new JPanel(new GridLayout(1, 1,5,5));
		pnlProduct = new JPanel(new GridLayout(5, 2,5,5));
		pnlButton1 = new JPanel(new GridLayout(1, 3,5,5));
		pnlButton2 = new JPanel(new GridLayout(1, 2,5,5));
		
		pnlTable.add(new JScrollPane(tableDb));
		
		pnlProduct.add(productID);
		pnlProduct.add(tProductID);
		pnlProduct.add(productName);
		pnlProduct.add(tProductName);
		pnlProduct.add(watchType);
		pnlProduct.add(cWatchType);
		pnlProduct.add(price);
		pnlProduct.add(tPrice);
		pnlProduct.add(stock);
		pnlProduct.add(sStock);
		
		pnlButton1.add(bInsert);
		pnlButton1.add(bUpdate);
		pnlButton1.add(bDelete);
		
		pnlButton2.add(bSubmit);
		pnlButton2.add(bCancel);
		
		pnl1.add(title);
		pnl2.add(pnlTable);
		pnl2.add(pnlProduct);
		pnl3.add(pnlButton1);
		pnl3.add(pnlButton2);
		
		tProductID.setEditable(false);
		tProductName.setEditable(false);
		cWatchType.setEnabled(false);
		cWatchType.setEditable(false);
		tPrice.setEditable(false);
		sStock.setEnabled(false);
		sStock.setValue(0);
		sStock.setEditor(new JSpinner.NumberEditor(sStock));
		bInsert.setEnabled(true);
		bUpdate.setEnabled(true);
		bDelete.setEnabled(true);
		bSubmit.setEnabled(false);
		bCancel.setEnabled(false);
		
		bInsert.addActionListener(this);
		bUpdate.addActionListener(this);
		bDelete.addActionListener(this);
		bSubmit.addActionListener(this);
		bCancel.addActionListener(this);
		
		tableDb.addMouseListener(this);
		tableDb.getSelectionModel().addListSelectionListener(this);
		
		add(pnl1,BorderLayout.NORTH);
		add(pnl2,BorderLayout.CENTER);
		add(pnl3,BorderLayout.SOUTH);
		add(pnl4,BorderLayout.WEST);
		add(pnl5,BorderLayout.EAST);
		setTitle("Manage Product");
		setSize(new Dimension(600, 450));
		setLocation(100,100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void fillField(){
		try{
			tProductID.setText(tableDb.getValueAt(tableDb.getSelectedRow(), 0).toString());
			tProductName.setText(tableDb.getValueAt(tableDb.getSelectedRow(), 1).toString());
			cWatchType.setSelectedItem(tableDb.getValueAt(tableDb.getSelectedRow(), 2).toString());
			tPrice.setText(tableDb.getValueAt(tableDb.getSelectedRow(), 3).toString());
			String stringStock = tableDb.getValueAt(tableDb.getSelectedRow(), 4).toString();
			int iStock = Integer.parseInt(stringStock);
			sStock.setValue(iStock);
		}catch(Exception E){}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bInsert){
			generateID();
	
			tProductName.setText("");
			tProductName.setEditable(true);
			tPrice.setText("");
			tPrice.setEditable(true);
			
			sStock.setValue(0);
			sStock.setEnabled(true);
			
			cWatchType.setSelectedIndex(0);
			cWatchType.setEnabled(true);
			
			bSubmit.setEnabled(true);
			bCancel.setEnabled(true);
			
			bInsert.setEnabled(false);
			bUpdate.setEnabled(false);
			bDelete.setEnabled(false);
		}if(e.getSource()==bUpdate){
			if(tableDb.getSelectedRow()<0){
				JOptionPane.showMessageDialog(this, "Must select a data first","Error",JOptionPane.ERROR_MESSAGE);
			}else{
				tProductName.setEditable(true);
				tPrice.setEditable(true);
				sStock.setEnabled(true);
				cWatchType.setEnabled(true);
				
				bSubmit.setEnabled(true);
				bCancel.setEnabled(true);
				
				bInsert.setEnabled(false);
				bUpdate.setEnabled(false);
				bDelete.setEnabled(false);
			}
		}if(e.getSource()==bDelete){
			if(tableDb.getSelectedRow()<0){
				JOptionPane.showMessageDialog(this, "Must select a data first","Error",JOptionPane.ERROR_MESSAGE);
			}else{
				int confirmDialog=JOptionPane.showConfirmDialog(this, "Delete this Product?","Delete",JOptionPane.YES_NO_OPTION);
				if(confirmDialog==JOptionPane.NO_OPTION){
					
				}else if(confirmDialog==JOptionPane.CLOSED_OPTION){
					
				}
				else if(confirmDialog==JOptionPane.YES_OPTION){
					String query = "SELECT * FROM MsProduct WHERE ProductID = '"+tProductID.getText()+"'";
					rs = con.executeQuery(query);
					query = "DELETE FROM MsProduct WHERE ProductID = '"+tProductID.getText()+"'";
					if(con.executeUpdate(query)>0){
						JOptionPane.showMessageDialog(this, "Product Deleted");
					}
				}
				fillTable();
				fillField();
			}
		}if(e.getSource()==bSubmit){
			
			int intPrice = 0;
			int intStock = 0;
			
			try{
				intPrice = Integer.parseInt(tPrice.getText());
				intStock = (Integer) sStock.getValue();
			}catch(Exception E){}
			
			if(tProductName.getText().equals("")||tPrice.getText().equals("")){
				JOptionPane.showMessageDialog(this, "All fields must be filled","Error",JOptionPane.ERROR_MESSAGE);
			}else if(tProductName.getText().length()<5||tProductName.getText().length()>35){
				JOptionPane.showMessageDialog(this, "Product Name length must be between 5 and 35 characters","Error",JOptionPane.ERROR_MESSAGE);
			}else if(cWatchType.getSelectedIndex()==0){
				JOptionPane.showMessageDialog(this, "Watch Type must be chosen","Error",JOptionPane.ERROR_MESSAGE);
			}else if(intPrice<50000){
				JOptionPane.showMessageDialog(this, "Price must be numeric and more than 50000","Error",JOptionPane.ERROR_MESSAGE);
			}else if(intStock<=0){
				JOptionPane.showMessageDialog(this, "Minimum Stock must more than 0","Error",JOptionPane.ERROR_MESSAGE);
			}else{
				String query = "SELECT * FROM MsProduct WHERE ProductID = '"+tProductID.getText()+"'";
				rs = con.executeQuery(query);
				try {
					if(rs.next()){
						query = "UPDATE MsProduct SET ProductName='"+tProductName.getText()+"', WatchType='"+cWatchType.getSelectedItem()+"', ProductPrice='"+intPrice+"', Stock='"+intStock+"' WHERE ProductID = '"+tProductID.getText()+"'";
						if(con.executeUpdate(query)>0){
							JOptionPane.showMessageDialog(this, "Product Updated");
						}
						}else{
						query = "INSERT INTO MsProduct(ProductID,ProductName,WatchType,ProductPrice,Stock) VALUES('"+tProductID.getText()+"','"+tProductName.getText()+"','"+cWatchType.getSelectedItem()+"','"+intPrice+"','"+intStock+"')";
						if(con.executeUpdate(query)>0){
							JOptionPane.showMessageDialog(this, "Product Added");
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				tProductID.setText("");
				tProductName.setText("");
				cWatchType.setSelectedIndex(0);
				tPrice.setText("");
				sStock.setValue(0);
				
				tProductID.setEditable(false);
				tProductName.setEditable(false);
				cWatchType.setEnabled(false);
				cWatchType.setEditable(false);
				tPrice.setEditable(false);
				sStock.setEnabled(false);
				
				bInsert.setEnabled(true);
				bUpdate.setEnabled(true);
				bDelete.setEnabled(true);
				bSubmit.setEnabled(false);
				bCancel.setEnabled(false);
				
				fillTable();
			}
		}if(e.getSource()==bCancel){
			tProductID.setEditable(false);
			tProductName.setEditable(false);
			cWatchType.setEnabled(false);
			cWatchType.setEditable(false);
			tPrice.setEditable(false);
			sStock.setEnabled(false);
			
			bInsert.setEnabled(true);
			bUpdate.setEnabled(true);
			bDelete.setEnabled(true);
			
			bSubmit.setEnabled(false);
			bCancel.setEnabled(false);
			
			fillField();
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		fillField();
		
	}
}


