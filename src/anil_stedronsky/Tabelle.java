package anil_stedronsky;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Die Tabelle mit den jeweiligen Attributen
 * @author Stedronsky Thomas
 * @version 2015-02-09
 */
public class Tabelle {
	private String name;
	private ArrayList<Attribut> att;
	private Datenbank db;
	
	/**
	 * Konstruktor
	 * @param db	Die Datenbank 
	 * @param name	Name  der Tabelle
	 */
	public Tabelle(Datenbank db, String name){
		this.db=db;
		this.name=name;
		att = new ArrayList<Attribut>();
		
		this.setAttribut();
	}
	
	/**
	 * Eine Methode die mit der Connection die Attribute der jeweiligen Tabelle ausliest
	 */
	public void setAttribut(){
		ArrayList<String> al= new ArrayList<String>();
		String[] attr = null;
		try {
			ResultSet rs = this.db.getDatabaseMetaData().getColumns(null, null, this.name,null); //ResultSet für die Attributnamen
			while(rs.next()){
				al.add(rs.getString("COLUMN_NAME")); //Die Attributte werden ausgelesen
			}
			/*rs = this.db.getDatabaseMetaData().getImportedKeys(null, null, this.name);
			while(rs.next()){
				al.add(rs.getString("FKCOLUMN_NAME")+":"+rs.getString("PKTABLE_NAME")+"."+rs.getString("PKCOLUMN_NAME"));
			}*/
			attr = al.toArray(new String[al.size()]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<attr.length;++i){
			att.add(new Attribut(this.db, attr[i], this.name));
		}
	}
	
	/**
	 * Liefert die Attribute der Tabelle zurück
	 * @return die Attribute in einer ArrayList
	 */
	public ArrayList<Attribut> getAttributs(){
		return this.att;
	}
	
	/**
	 * Liefert den Namen der Tabelle zurück
	 * @return den Namen der Tabelle zurück
	 */
	public String getName(){
		return this.name;
	}
}
