package actions.standard.form;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.NaseljenoMestoStandardForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import util.ColumnList;


public class NextFormAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;
	
	public NextFormAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/nextform.gif")));
		putValue(SHORT_DESCRIPTION, "Sledeća forma");
		this.standardForm  = standardForm;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		((DrzavaStandardForm)standardForm).nextUp();

		ColumnList colList = ((DrzavaStandardForm)standardForm).getColList();
		
		NaseljenoMestoStandardForm form = new NaseljenoMestoStandardForm(colList);
		form.fillZoom(colList);
		form.setVisible(true);
		
//		((DrzavaStandardForm)standardForm).pickUp();
//		
//		colList = ((DrzavaStandardForm)standardForm).getColList();
		
		
//		((NaseljenoMestoStandardForm)standardForm).fillZoom(colList);
	}
}
