package table.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import database.DBConnection;

public class NaseljenoMestoTableModel extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String basicQuery = "SELECT nm_sifra, nm_naziv, naseljeno_mesto.dr_sifra, dr_naziv FROM naseljeno_mesto JOIN drzava on naseljeno_mesto.dr_sifra = drzava.dr_sifra";
	private String orderBy = " ORDER BY nm_sifra";
	private String whereStmt = "";

	public NaseljenoMestoTableModel(Object[] colNames, int rowCount) {
		super(colNames, rowCount);
	}

	//Otvaranje upita
	public void open() throws SQLException {
	    fillData(basicQuery + whereStmt + orderBy);
	}

	private void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String sifra = rset.getString("NM_SIFRA");
			String naziv = rset.getString("NM_NAZIV");
			String sifraDrzave = rset.getString("DR_SIFRA");
			String nazivDrzave = rset.getString("DR_NAZIV");
			addRow(new String[]{sifra, naziv, sifraDrzave, nazivDrzave});
		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}



	/**
	 * Inicijalno popunjavanje forme kada se otvori iz forme Drzave preko next mehanizma
	 * @param where
	 * @throws SQLException
	 */
	public void openAsChildForm(String where) throws SQLException{
		String sql = ""; //upotrebiti where parametar
		whereStmt = " where " + where;
		sql = basicQuery + whereStmt + orderBy;
		fillData(sql);
	}

}



