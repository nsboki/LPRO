package actions.standard.form;

import gui.main.form.MainFrame;
import gui.standard.form.DrzavaStandardForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import database.DBConnection;

public class DeleteAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;
	
	public DeleteAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/remove.gif")));
		putValue(SHORT_DESCRIPTION, "Brisanje");
		this.standardForm=standardForm;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (JOptionPane.showConfirmDialog(null,
				"Da li ste sigurni?", "Pitanje",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		
			((DrzavaStandardForm)standardForm).removeRow();
		}
		((DrzavaStandardForm)standardForm).setMode(1);


	}
}
