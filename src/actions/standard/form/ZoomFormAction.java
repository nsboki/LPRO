package actions.standard.form;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.NaseljenoMestoStandardForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import util.ColumnList;


public class ZoomFormAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;

	
	public ZoomFormAction(JDialog standardForm) {
		putValue(SHORT_DESCRIPTION, "Zoom");
		putValue(NAME, "...");
		this.standardForm=standardForm;

	}
	
	

	@Override
	public void actionPerformed(ActionEvent event) {
		DrzavaStandardForm form = new DrzavaStandardForm();
		form.setVisible(true);
		
		ColumnList colList = form.getColList();
		
		((NaseljenoMestoStandardForm)standardForm).fillZoom(colList);
		
		
		
		
	}
	
}
