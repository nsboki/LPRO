package gui.standard.form;

import gui.main.form.MainFrame;

import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import tableModel.DrzaveTableModel;
import util.Column;
import util.ColumnList;
import actions.standard.form.AddAction;
import actions.standard.form.CommitAction;
import actions.standard.form.DeleteAction;
import actions.standard.form.FirstAction;
import actions.standard.form.HelpAction;
import actions.standard.form.LastAction;
import actions.standard.form.NextAction;
import actions.standard.form.NextFormAction;
import actions.standard.form.PickupAction;
import actions.standard.form.PreviousAction;
import actions.standard.form.RefreshAction;
import actions.standard.form.RollbackAction;
import actions.standard.form.SearchAction;

public class DrzavaStandardForm extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private JToolBar toolBar;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious;
	private JTextField tfSifra = new JTextField(5);
	private JTextField tfNaziv = new JTextField(20);
	private JTable tblGrid = new JTable(); 

	private static final int MODE_EDIT = 1;
	private static final int MODE_ADD = 2;
	private static final int MODE_SEARCH = 3;
	private int mode = 1;
	
	public int getMode() {
		return mode;
	}

	private ColumnList colList = null;
	
	
	public ColumnList getColList() {
		return colList;
	}

	public void setColList(ColumnList colList) {
		this.colList = colList;
	}

	private JLabel lblStatus = new JLabel("Režim: PREGLED/IZMENA");

	private String status;

	public DrzavaStandardForm(){

		setLayout(new MigLayout("fill"));

		setSize(new Dimension(800, 600));
		setTitle("Države");
		setLocationRelativeTo(MainFrame.getInstance());
		setModal(true);
		
		initToolbar();
		initTable();
		initGui();
		
	}
	
	private void initTable(){
		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		
		
		//Dodati u metodu za kreiranje tabele koja se  poziva iz konstruktora klase
		//frmDrzave:
		
		
		// Kreiranje TableModel-a, parametri: header-i kolona i broj redova 
		DrzaveTableModel tableModel = new DrzaveTableModel(new String[] {"Šifra",   "Naziv"}, 0);
		tblGrid.setModel(tableModel);
		
		try {
			tableModel.open();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska", 
			          JOptionPane.ERROR_MESSAGE);
			//e.printStackTrace();
		} 
			
		//Dozvoljeno selektovanje redova
		tblGrid.setRowSelectionAllowed(true);
		//Ali ne i selektovanje kolona 
		tblGrid.setColumnSelectionAllowed(false);
		
		//Dozvoljeno selektovanje samo jednog reda u jedinici vremena 
		tblGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tblGrid.getSelectionModel().addListSelectionListener(new
				ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting())
					return;
				sync();
			}
		});

	}
	
	private void initToolbar(){

		toolBar = new JToolBar();
		btnSearch = new JButton(new SearchAction(this));
		toolBar.add(btnSearch);


		btnRefresh = new JButton(new RefreshAction(this));
		toolBar.add(btnRefresh);

		btnPickup = new JButton(new PickupAction(this));
		toolBar.add(btnPickup);


		btnHelp = new JButton(new HelpAction());
		toolBar.add(btnHelp);

		toolBar.addSeparator();

		btnFirst = new JButton(new FirstAction(this));
		toolBar.add(btnFirst);

		btnPrevious = new JButton(new PreviousAction(this));
		toolBar.add(btnPrevious);

		btnNext = new JButton(new NextAction(this));
		toolBar.add(btnNext);

		btnLast = new JButton(new LastAction(this));
		toolBar.add(btnLast);

		toolBar.addSeparator();


		btnAdd = new JButton(new AddAction(this));
		toolBar.add(btnAdd);

		btnDelete = new JButton(new DeleteAction(this));
		toolBar.add(btnDelete);

		toolBar.addSeparator();

		btnNextForm = new JButton(new NextFormAction(this));
		toolBar.add(btnNextForm);

		add(toolBar, "dock north");
	}
	
	private void initGui(){
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new MigLayout("fillx"));
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new MigLayout("gapx 15px"));

		JPanel buttonsPanel = new JPanel();
		btnCommit = new JButton(new CommitAction(this));
		btnRollback = new JButton(new RollbackAction(this));


		JLabel lblSifra = new JLabel ("Šifra države:");
		JLabel lblNaziv = new JLabel("Naziv države:");

		dataPanel.add(lblSifra);
		dataPanel.add(tfSifra,"wrap");
		dataPanel.add(lblNaziv);
		dataPanel.add(tfNaziv);
		bottomPanel.add(dataPanel);


		buttonsPanel.setLayout(new MigLayout("wrap"));
		buttonsPanel.add(btnCommit);
		buttonsPanel.add(btnRollback);
		bottomPanel.add(buttonsPanel,"dock east");
		
		lblStatus = new JLabel("Rezim: pregled");
		
		add(bottomPanel, "grow, wrap");
		add(lblStatus, "grow, wrap");
	}

	public void goLast() {
		int rowCount = tblGrid.getModel().getRowCount(); 
		if (rowCount > 0)
			tblGrid.setRowSelectionInterval(rowCount - 1, rowCount - 1);
	}
	
	public void goFirst() {
		int rowCount = tblGrid.getModel().getRowCount(); 
		if (rowCount > 0)
			tblGrid.setRowSelectionInterval(0, 0);
	}

	public void goNext() {
		int rowCount = tblGrid.getModel().getRowCount();
		int selectedRow = tblGrid.getSelectedRow();
		if (rowCount > 0){
			if (rowCount == selectedRow+1){
				goFirst();
//				tblGrid.setRowSelectionInterval(0, 0);
			}else{
				
				tblGrid.setRowSelectionInterval(selectedRow+1, selectedRow+1);
			}
		}
	}		

	public void goPrevious() {
		int rowCount = tblGrid.getModel().getRowCount();
		int selectedRow = tblGrid.getSelectedRow();
		if (rowCount > 0){
			if (0 == selectedRow){
				tblGrid.setRowSelectionInterval(rowCount - 1,rowCount - 1);
			}else{
				tblGrid.setRowSelectionInterval(selectedRow-1, selectedRow-1);
			}
		}
	}	
	private void sync() {
		int index = tblGrid.getSelectedRow();
		if (index < 0) {
		tfSifra.setText("");
		tfNaziv.setText("");
		return;
		}
		String sifra = (String)tblGrid.getModel().getValueAt(index, 0);
		String naziv = (String)tblGrid.getModel().getValueAt(index, 1);
		tfSifra.setText(sifra);
		tfNaziv.setText(naziv);
		}
	
	public void removeRow() {
	    int index = tblGrid.getSelectedRow(); 
	    if (index == -1) //Ako nema selektovanog reda (tabela prazna)
	      return;        // izlazak 
	    //kada obrisemo tekuci red, selektovacemo sledeci (newindex):
	    int newIndex = index;  
	    //sem ako se obrise poslednji red, tada selektujemo prethodni
	    if (index == tblGrid.getModel().getRowCount() - 1) 
	       newIndex--; 
	    try {
	      DrzaveTableModel dtm = (DrzaveTableModel)tblGrid.getModel(); 
	      dtm.deleteRow(index); 
	      if (tblGrid.getModel().getRowCount() > 0)
	        tblGrid.setRowSelectionInterval(newIndex, newIndex);
	    } catch (SQLException ex) {
	      JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", 
	          JOptionPane.ERROR_MESSAGE);
	    }
	  }
	
	public void addRow() {
		String sifra = tfSifra.getText().trim();
		String naziv = tfNaziv.getText().trim();
		try {
			DrzaveTableModel dtm = (DrzaveTableModel)tblGrid.getModel();
			int index = dtm.insertRow(sifra, naziv);
			tblGrid.setRowSelectionInterval(index, index);
			setMode(MODE_ADD);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setMode(int modeAdd) {
		mode = modeAdd;
		switch (mode){
		case MODE_SEARCH:	
			status = " Režim: PRETRAGA";
			tfSifra.setText("");
			tfSifra.requestFocus();
			tfNaziv.setText("");
			break;
		case MODE_ADD: 
			status = " Režim: DODAVANJE"; 
			tfSifra.setText("");
			tfSifra.requestFocus();
			tfNaziv.setText("");
			break;
		case MODE_EDIT: 
			status = " Režim: PREGLED/IZMENA";
			break;
		}
		lblStatus.setText(status);
		
	}
	
	public void nextUp(){
		int index = tblGrid.getSelectedRow();
		Column col = new Column();
		col.setName("naseljeno_mesto.dr_sifra");
		col.setValue(tblGrid.getModel().getValueAt(index, 0));
		colList = new ColumnList();
		colList.add(col);
		Column col1 = new Column();
		col1.setName("dr_naziv");
		col1.setValue(tblGrid.getModel().getValueAt(index, 1));
		colList.add(col1);
	}
	
	public void pickUp(){
//		colList = null;
		int index = tblGrid.getSelectedRow();
		Column col = new Column();
		col.setName("naseljeno_mesto.dr_sifra");
		col.setValue(tblGrid.getModel().getValueAt(index, 0));
		colList = new ColumnList();
		colList.add(col);
		Column col1 = new Column();
		col1.setName("dr_naziv");
		col1.setValue(tblGrid.getModel().getValueAt(index, 1));
		colList.add(col1);
		
	}

	public void search() {
		String sifra = tfSifra.getText().trim();
		String naziv = tfNaziv.getText().trim();
		DrzaveTableModel dtm = (DrzaveTableModel)tblGrid.getModel();
		try {
			dtm.search(sifra, naziv);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska kod pretrage!", 
			          JOptionPane.ERROR_MESSAGE);
		}		
	}

	public void editRow() {
		int index = tblGrid.getSelectedRow(); 
		String sifra = tfSifra.getText().trim();
		String naziv = tfNaziv.getText().trim();
		
		try {
			DrzaveTableModel dtm = (DrzaveTableModel)tblGrid.getModel();
			dtm.deleteRow(index);
			index = dtm.insertRow(sifra, naziv);
			tblGrid.setRowSelectionInterval(index, index);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", 
					JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	public void refreshTable(){
		DrzaveTableModel dtm = (DrzaveTableModel)tblGrid.getModel();
		try {
			dtm.open();
			setMode(MODE_EDIT);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Greska nastala pri popunjavanju tabele!", 
			          JOptionPane.ERROR_MESSAGE);
		}
	}
	
}