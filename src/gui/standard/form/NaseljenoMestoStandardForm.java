package gui.standard.form;

import gui.main.form.MainFrame;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import table.model.NaseljenoMestoTableModel;
import util.Column;
import util.ColumnList;
import util.Lookup;
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
import actions.standard.form.ZoomFormAction;

public class NaseljenoMestoStandardForm extends JDialog{
	private static final long serialVersionUID = 1L;


	public static final int MODE_EDIT   = 1;
	public static final int MODE_ADD    = 2;
	public static final int MODE_SEARCH = 3;
	private int mode;

	private JToolBar toolBar;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm,
	btnPickup, btnRefresh, btnRollback, btnSearch, btnPrevious;
	private JTable tblGrid = new JTable();
	private JTextField tfSifra = new JTextField(5);
	private JTextField tfNaziv = new JTextField(20);

	private JTextField tfNazivDrzave = new JTextField(20);
	private JTextField tfSifraDrzave = new JTextField(5);

	private JButton btnZoom = new JButton("...");


	public NaseljenoMestoStandardForm(ColumnList cols){

		
		
		setLayout(new MigLayout("fill"));

		setSize(new Dimension(800, 600));
		setTitle("Naseljena mesta");
		setLocationRelativeTo(MainFrame.getInstance());
		setModal(true);

		mode = MODE_EDIT;
		
		initToolbar();
		initTable(cols);
		initGui();

		if (cols != null){
			btnZoom.setEnabled(false);
			tfSifraDrzave.setEditable(false);
			
		}
		
	}
	
	private void initGui(){
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new MigLayout("fillx"));
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new MigLayout());

		JPanel buttonsPanel = new JPanel();
		btnCommit = new JButton(new CommitAction(this));
		btnRollback = new JButton(new RollbackAction(this));


		JLabel lblSifra = new JLabel ("Šifra mesta:");
		JLabel lblNaziv = new JLabel("Naziv mesta:");
		JLabel lblSifraDrzave = new JLabel ("Šifra države:");

		dataPanel.add(lblSifra);
		dataPanel.add(tfSifra,"wrap, gapx 15px");
		dataPanel.add(lblNaziv);
		dataPanel.add(tfNaziv,"wrap,gapx 15px, span 3");
		dataPanel.add(lblSifraDrzave);
		dataPanel.add(tfSifraDrzave, "gapx 15px");
		dataPanel.add(btnZoom);

		dataPanel.add(tfNazivDrzave,"pushx");
		tfNazivDrzave.setEditable(false);
		bottomPanel.add(dataPanel);

		buttonsPanel.setLayout(new MigLayout("wrap"));
		buttonsPanel.add(btnCommit);
		buttonsPanel.add(btnRollback);
		bottomPanel.add(buttonsPanel,"dock east");

		add(bottomPanel, "grow, wrap");
		
//		tfSifraDrzave.addFocusListener(new FocusAdapter() {
//			public void focusLost(FocusEvent e) {
//				String sifraDrzave = tfSifraDrzave.getText().trim();
//				try {
//					tfNazivDrzave.setText(Lookup.getDrzava(sifraDrzave));
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//		});
	
		btnZoom.addActionListener(new ZoomFormAction(this));
		
		tfSifraDrzave.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				String sifraDrzave = tfSifraDrzave.getText().trim();
				try {
					tfNazivDrzave.setText(Lookup.getDrzava(sifraDrzave));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
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
		
		btnPickup.setEnabled(false);
		add(toolBar, "dock north");
	}


	private void initTable(ColumnList cols){
	
		JScrollPane scrollPane = new JScrollPane(tblGrid);      
		add(scrollPane, "grow, wrap");
		NaseljenoMestoTableModel tableModel = new NaseljenoMestoTableModel(new String[] {"Šifra",   "Naziv", "Šifra države", "Naziv države"}, 0);
		tblGrid.setModel(tableModel);

		try {
			if (cols == null){
				tableModel.open();
			}
			else {
				tableModel.openAsChildForm(cols.getWhereClause());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		//Dozvoljeno selektovanje redova
		tblGrid.setRowSelectionAllowed(true);
		//Ali ne i selektovanje kolona 
		tblGrid.setColumnSelectionAllowed(false);

		//Dozvoljeno selektovanje samo jednog reda u jedinici vremena 
		tblGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tblGrid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting())
					return;
				sync();
			}
		});

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
		String sifraDrzave = (String)tblGrid.getModel().getValueAt(index, 2);
		String nazivDrzave = (String)tblGrid.getModel().getValueAt(index, 3);
		tfSifra.setText(sifra);
		tfNaziv.setText(naziv);
		tfSifraDrzave.setText(sifraDrzave);
		tfNazivDrzave.setText(nazivDrzave);
	}
	
	public void fillZoom(ColumnList colList){
		if(colList == null){
			return;
		}
		
		tfSifraDrzave.setText((String)colList.getValue("naseljeno_mesto.dr_sifra"));
		tfNazivDrzave.setText((String)colList.getValue("dr_naziv"));
		
	}

	
	
}

