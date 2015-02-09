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
	 * @param Name der Tabelle
	 */
	public Tabelle(Datenbank db, String name){
		this.db=db;
		this.name=name;
		att = new ArrayList<Attribut>();
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
			attr = al.toArray(new String[al.size()]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<attr.length;++i){
			att.add(new Attribut(this.db, attr[i]));
		}
	}
}
