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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import jdk.nashorn.internal.scripts.JO;

public class ManageWatchTypeForm extends JInternalFrame  implements ActionListener, MouseListener, ListSelectionListener{
	
	Connect con = new Connect();
	ResultSet rs;
	
	MainForm mainForm;
	
	JPanel pnl1, pnl2, pnl3, pnl4, pnl5, pnlTable, pnlWatchType, pnlButton1, pnlButton2;
	JLabel title, typeID, typeName;
	JTextField tTypeID;
	JTextField tTypeName;
	JButton bInsert, bUpdate, bDelete, bSubmit, bCancel;
	
	JTable tableDb;
	DefaultTableModel modelDb;
	Vector<Object> tHeader, tRow;
	
	public void fillTable() {
		modelDb = new DefaultTableModel(tHeader, 0);
		con.rs = con.executeQuery("SELECT TypeID, TypeName FROM MsWatchType");
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
			tTypeID.setText("WT001");
		}else{
			String lastID = tableDb.getValueAt(tableDb.getRowCount()-1, 0).toString() ;
			int id = Integer.parseInt(lastID.substring(lastID.indexOf("T")+1,lastID.length()));
			if(id < 9){
				tTypeID.setText("WT00"+(id+1));	
			}else if(id < 99){
				tTypeID.setText("WT0"+(id+1));
			}else if (id < 999){
				tTypeID.setText("WT"+(id+1));
			}
		}
		
	}
	
	public ManageWatchTypeForm() {
		super("",false,true,false);
		
		tHeader = new Vector<Object>();
		tHeader.add("Type ID");
		tHeader.add("Type Name");
		
		title = new JLabel("Manage Watch Type");
		
		typeID = new JLabel("Type ID");
		typeName = new JLabel("Type Name");
		
		tTypeID = new JTextField();
		tTypeName = new JTextField();
		
		bInsert = new JButton("Insert");
		bUpdate = new JButton("Update");
		bDelete = new JButton("Delete");
		bSubmit = new JButton("Submit");
		bCancel = new JButton("Cancel");
		
		tableDb = new JTable();
		fillTable();
		
		title.setFont(title.getFont().deriveFont(Font.BOLD, 14));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		tTypeID.setEditable(false);
		tTypeName.setEditable(false);
		
		bSubmit.setEnabled(false);
		bCancel.setEnabled(false);
		
		pnl1 = new JPanel(new GridLayout(1, 1,5,5));
		pnl2 = new JPanel(new GridLayout(2,1,10,10));
		pnl3 = new JPanel(new GridLayout(1, 2,5,5));
		pnl4 = new JPanel();
		pnl5 = new JPanel();
		
		pnlTable = new JPanel(new GridLayout(1, 1,5,5));
		pnlWatchType = new JPanel(new GridLayout(2, 2,5,5));
		pnlButton1 = new JPanel(new GridLayout(1, 3,5,5));
		pnlButton2 = new JPanel(new GridLayout(1, 2,5,5));
		
		pnlTable.add(new JScrollPane(tableDb));
		
		pnlWatchType.add(typeID);
		pnlWatchType.add(tTypeID);
		pnlWatchType.add(typeName);
		pnlWatchType.add(tTypeName);
		
		pnlButton1.add(bInsert);
		pnlButton1.add(bUpdate);
		pnlButton1.add(bDelete);
		
		pnlButton2.add(bSubmit);
		pnlButton2.add(bCancel);
		
		pnl1.add(title);
		pnl2.add(pnlTable);
		pnl2.add(pnlWatchType);
		pnl3.add(pnlButton1);
		pnl3.add(pnlButton2);
		
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
		setTitle("Manage Watch Type");
		setSize(new Dimension(600, 450));
		setLocation(100,100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void fillField(){
		try{
			tTypeID.setText(tableDb.getValueAt(tableDb.getSelectedRow(), 0).toString());
			tTypeName.setText(tableDb.getValueAt(tableDb.getSelectedRow(), 1).toString());
		}catch(Exception E){}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bInsert){
			generateID();
			
			tTypeName.setText("");
			tTypeName.setEditable(true);
			
			bSubmit.setEnabled(true);
			bCancel.setEnabled(true);
			
			bInsert.setEnabled(false);
			bUpdate.setEnabled(false);
			bDelete.setEnabled(false);
		}if(e.getSource()==bUpdate){
			if(tableDb.getSelectedRow()<0){
				JOptionPane.showMessageDialog(this, "Must select a data first","Error",JOptionPane.ERROR_MESSAGE);
			}else{
				tTypeName.setEditable(true);
				
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
				int confirmDialog=JOptionPane.showConfirmDialog(this, "Delete this Watch Type?","Delete",JOptionPane.YES_NO_OPTION);
				if(confirmDialog==JOptionPane.NO_OPTION){
					
				}else if(confirmDialog==JOptionPane.CLOSED_OPTION){
					
				}
				else if(confirmDialog==JOptionPane.YES_OPTION){
					String query = "SELECT * FROM MsWatchType WHERE TypeID = '"+tTypeID.getText()+"'";
					rs = con.executeQuery(query);
					query = "DELETE FROM MsWatchType WHERE TypeID = '"+tTypeID.getText()+"'";
					if(con.executeUpdate(query)>0){
						JOptionPane.showMessageDialog(this, "Watch Type Deleted");
					}
				}
				fillTable();
				fillField();
			}
		}if(e.getSource()==bSubmit){
			if(tTypeName.getText().equals("")){
				JOptionPane.showMessageDialog(this, "All fields must be filled","Error",JOptionPane.ERROR_MESSAGE);
			}else if(tTypeName.getText().length()<5||tTypeName.getText().length()>30){
				JOptionPane.showMessageDialog(this, "Type Name length must be between 5 and 30 characters","Error",JOptionPane.ERROR_MESSAGE);
			}else{
				String query = "SELECT * FROM MsWatchType WHERE TypeID = '"+tTypeID.getText()+"'";
				rs = con.executeQuery(query);
				try {
					if(rs.next()){
						query = "UPDATE MsWatchType SET TypeName='"+tTypeName.getText()+"'WHERE TypeID = '"+tTypeID.getText()+"'";
						if(con.executeUpdate(query)>0){
							JOptionPane.showMessageDialog(this, "Watch Type Updated");
						}
						}else{
						query = "INSERT INTO MsWatchType(TypeID,TypeName) VALUES('"+tTypeID.getText()+"','"+tTypeName.getText()+"')";
						if(con.executeUpdate(query)>0){
							JOptionPane.showMessageDialog(this, "Watch Type Added");
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tTypeID.setText("");
				tTypeName.setText("");
				
				tTypeID.setEditable(false);
				tTypeName.setEditable(false);
				
				bInsert.setEnabled(true);
				bUpdate.setEnabled(true);
				bDelete.setEnabled(true);
				bSubmit.setEnabled(false);
				bCancel.setEnabled(false);
				
				fillTable();
			}
		}if(e.getSource()==bCancel){
			
			tTypeID.setEditable(false);
			tTypeName.setEditable(false);
			
			bInsert.setEnabled(true);
			bUpdate.setEnabled(true);
			bDelete.setEnabled(true);
			bSubmit.setEnabled(false);
			bCancel.setEnabled(false);
			
			fillField();
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
	public void mouseReleased(MouseEvent arg0) {
		fillField();
		
	}

}
