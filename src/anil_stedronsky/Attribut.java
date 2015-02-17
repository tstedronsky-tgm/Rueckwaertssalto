package anil_stedronsky;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Attribut mit Eigenschaft
 * @author Stedronsky Thomas
 * @version 2015-02-09
 */
public class Attribut {
	private String att="";
	private String tabname="";
	private Datenbank db;
	private Eigenschaft eig;
	private String PKtable="";
	private String pkname="";
	
	/**
	 * Konstruktor
	 * @param db 		Datenbank
	 * @param att		Attributname
	 * @param tabname	Tabellenanme
	 */
	public Attribut(Datenbank db, String att, String tabname){
		this.db=db;
		this.att=att;
		this.tabname=tabname;
		
		this.isPK(); //Checkt ob das Att ein PK ist
	}
	
	/**
	 * Liefert den Namen des Attributs zurück
	 * @return den Namen das Attributs
	 */
	public String getName(){
		return this.att;
	}
	
	/**
	 * Setzt das Attribut auf einen PK
	 */
	public void isPK(){
		ArrayList<String> pk = new ArrayList<String>();
		ResultSet pks;
		try {
			pks = this.db.getDatabaseMetaData().getPrimaryKeys(null, null, this.tabname );  //ResultSet für den PK
			while (pks.next()) {
				pk.add(pks.getString("COLUMN_NAME"));  //PK in einer bestimmten Tabelle auslesen
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String[] prk=pk.toArray(new String[pk.size()]);
		
		for(int i=0; i<prk.length;++i){
			if(prk[i].equals(this.att)){
				this.eig=new PK();
			}
		}
	}
	
	/**
	 * Schaut ob die Eigenschaft eine Instanz von PK ist
	 * @return true wenn es eine Instanz ist und false wenn es keine ist.
	 */
	public boolean isInstanceOfPK(){
		if(this.eig instanceof PK){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 */
	public void isFK(){
		ArrayList<String> fk = new ArrayList<String>();
		ResultSet fks;
		try {
			fks = db.getDatabaseMetaData().getImportedKeys(null, null, this.tabname);
			while (fks.next()) {
				if(fks.getString("FKCOLUMN_NAME").equals(this.att)){
					this.att=this.att+":"+fks.getString("PKTABLE_NAME")+"."+fks.getString("PKCOLUMN_NAME");
				}	
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String[] frk=fk.toArray(new String[fk.size()]);
	}
	
	
}