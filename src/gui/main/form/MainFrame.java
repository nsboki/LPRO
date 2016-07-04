package gui.main.form;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import actions.main.form.DrzaveAction;
import actions.main.form.NaseljenoMestoAction;
import actions.main.form.TestAction;
import database.DBConnection;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public static MainFrame instance;
	private JMenuBar menuBar;

	public MainFrame(){

		setSize(new Dimension(800,600));
		setLocationRelativeTo(null);
		setTitle("Poslovna");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setUpMenu();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(MainFrame.getInstance(),
						"Da li ste sigurni?", "Pitanje",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					/*
					 * Zatvori konekciju
					 */
					DBConnection.close();
					System.exit(0);
				}
			}
		});
		
		setJMenuBar(menuBar);

	}

	private void setUpMenu(){
		menuBar = new JMenuBar();

		JMenu orgSemaMenu = new JMenu("Organizaciona šema"); // TODO: Organizaciona sema se koristi samo kao primer, kad ne bude vise potrebna izbaciti je
		JMenu sifrarniciMenu = new JMenu("Šifrarnici");	// TODO: Artikli, Komintenti, Kategorije artikala, Nacin placanja, Magacin, Stope poreza, Normativi?, Jedinica mere, Jedinice/podjedinice
		JMenu robaMenu = new JMenu("Roba"); 	// TODO: Evidencija robe: Cenovnik i stanje artikala, Kartica artikala, KEPU?, KNJIGA POPISA?
		JMenu ulazMenu = new JMenu("Ulaz");	 	// TODO: Prijemnica, Povratnica dobavljaca?, Povratnica kupca?, Porudzbenica, Nivelacija, Pocetno stanje
		JMenu izlazMenu = new JMenu("Izlaz"); 	// TODO: Racun-otpremnica, Predracun, Ponuda, Interna otpremnica?, Rezervacija?, Manjak robe?

		sifrarniciMenu.setMnemonic(KeyEvent.VK_S);
		JMenuItem komintentiMI = new JMenuItem(new TestAction());
		sifrarniciMenu.add(komintentiMI);
		JMenuItem artikliMI = new JMenuItem(new TestAction());
		sifrarniciMenu.add(artikliMI);
		JMenuItem grupaArtikalaMI = new JMenuItem(new TestAction());
		sifrarniciMenu.add(grupaArtikalaMI);
		JMenuItem jedinicaMereMI = new JMenuItem(new TestAction());
		sifrarniciMenu.add(jedinicaMereMI);
		JMenuItem stopaPorezaMI = new JMenuItem(new TestAction());
		sifrarniciMenu.add(stopaPorezaMI);
		JMenuItem magacinMI = new JMenuItem(new TestAction());
		sifrarniciMenu.add(magacinMI);
		
		robaMenu.setMnemonic(KeyEvent.VK_R);
		JMenuItem cenovnikMI = new JMenuItem(new TestAction());
		robaMenu.add(cenovnikMI);
		JMenuItem karticaArtikalaMI = new JMenuItem(new TestAction());
		robaMenu.add(karticaArtikalaMI);
		JMenuItem kepuMI = new JMenuItem(new TestAction());
		robaMenu.add(kepuMI);
		
		
		ulazMenu.setMnemonic(KeyEvent.VK_U);
		izlazMenu.setMnemonic(KeyEvent.VK_I);
		
		
		orgSemaMenu.setMnemonic(KeyEvent.VK_O);
		JMenuItem drzaveMI = new JMenuItem(new DrzaveAction());
		orgSemaMenu.add(drzaveMI);
		JMenuItem mestoMI = new JMenuItem(new NaseljenoMestoAction());
		orgSemaMenu.add(mestoMI);

		menuBar.add(orgSemaMenu);
		menuBar.add(sifrarniciMenu);
		menuBar.add(robaMenu);
		menuBar.add(ulazMenu);
		menuBar.add(izlazMenu);
		
		
	}



	public static MainFrame getInstance(){
		if (instance==null)
			instance=new MainFrame();
		return instance;

	}

}