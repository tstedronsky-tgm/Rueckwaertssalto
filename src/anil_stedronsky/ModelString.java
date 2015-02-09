package anil_stedronsky;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ModelString {
	private MysqlDataSource ds;
	private Connection con;
	private DatabaseMetaData db;

	/**
	 * Konstruktor der host, user, pw und database einliest
	 * @param die Hostadresse
	 * @param Der DB User
	 * @param Das PW für die DB
	 * @param Die DB die angesprochen werden soll. 
	 */
	public ModelString(String host, String user, String pw, String database){
		this.ds= new MysqlDataSource();
		ds.setServerName(host);
		ds.setUser(user);
		ds.setPassword(pw);
		ds.setDatabaseName(database);
		try {
			con = ds.getConnection();
			db = con.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tables werden zurück gegebem
	 * @return Array mit den Tables
	 */
	public String[] getTables() {
		ArrayList<String> al= new ArrayList<String>();
		String[] tables=null;
		try {
			String[] types = {"TABLE"};
			ResultSet rs = db.getTables(null, null, "%", types); //ResultSet für die Tabellennamen
			while(rs.next()){
				al.add(rs.getString("TABLE_NAME")); //Tabellenname wird ausgelesen
			}
			tables=al.toArray(new String[al.size()]); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tables;

	}

	/**
	 * Tables werden zurück gegebem
	 * @return Array mit den Tables
	 */
	public String[] getAtt(String tabname) {
		String[] prk=getPK(tabname);
		String[] frk=getFK(tabname);
		ArrayList<String> al= new ArrayList<String>();
		String[] att = null;
		try {
			ResultSet rs = db.getColumns(null, null, tabname,null); //ResultSet für die Attributnamen
			while(rs.next()){
				al.add(rs.getString("COLUMN_NAME")); //Die Attributte werden ausgelesen
			}
			for(int i=0; i<frk.length;++i){
				al.add(frk[i]);
			}
			att = al.toArray(new String[al.size()]);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*Schleife die den Attributen den PK hinzufügt*/
		for(int i=0; i<att.length;++i){
			for(int j=0; j<prk.length;++j){
				if(att[i].equals(prk[j])){
					att[i]+="<<PK>>";
				}
			}
		}
		return att;

	}

	/**
	 * Liefert den/die PKs zurück 
	 * @param Die Tabelle die auf PK überprüft werden soll
	 * @return die PK
	 */
	public String[] getPK(String tabname){
		ArrayList<String> pk = new ArrayList<String>();
		ResultSet pks;
		try {
			pks = db.getPrimaryKeys(null, null, tabname);  //ResultSet für den PK
			while (pks.next()) {
				pk.add(pks.getString("COLUMN_NAME"));  //PK in einer bestimmten Tabelle auslesen
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String[] prk=pk.toArray(new String[pk.size()]);

		return prk;
	}

	/**
	 * Liefert den/die FKs zurück 
	 * @param Die Tabelle die auf PK überprüft werden soll
	 * @return die PK
	 */
	public String[] getFK(String tabname){
		ArrayList<String> fk = new ArrayList<String>();
		ResultSet fks;
		String[] pk = getPK(tabname);
		try {
			fks = db.getImportedKeys(null, null, tabname);
			while (fks.next()) {
				boolean isPk=false;
				//Den FK mit den Relations
				for(int i=0; i<pk.length;++i){
					if(pk[i].equals(fks.getString("PKCOLUMN_NAME"))){
						isPk=true;
					}
				}
				//Checkt ob nicht nur ein FK sondern auch ein PK enthalten ist.
				if(isPk==true){
					fk.add(fks.getString("FKCOLUMN_NAME")+":"+fks.getString("PKTABLE_NAME")+"."+fks.getString("PKCOLUMN_NAME")+"<<FK>><<PK>>");
				}
				else {
					fk.add(fks.getString("FKCOLUMN_NAME")+":"+fks.getString("PKTABLE_NAME")+"."+fks.getString("PKCOLUMN_NAME")+"<<FK>>");
				}
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String[] frk=fk.toArray(new String[fk.size()]);
		return frk;
	}
	

	/**
	 * Liefert das fertige RM zurück
	 * @return das fertige RM
	 */
	public String getRM() {
		String rm="";
		String[] tables = getTables();
		for(int i=0; i<tables.length;++i){
			String[] att = getAtt(tables[i]);
			rm+=tables[i]+"(";
			for(int j=0; j<att.length;++j){
				if((j+1)==att.length){
					rm+=att[j];
				}
				else {
					rm+=att[j]+",";
				}
			}
			rm+=")\n";
		}
		return rm;
	}
	
	/**
	 * Aufräumen der Verbindung
	 */
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Erzeugt ein File aus dem RM
	 */
	public void generateFile(String name){
		try {
			RandomAccessFile f = new RandomAccessFile(name+".txt", "rwd");
			f.writeBytes(getRM());
			f.close();
		} catch (IOException e) {
			System.err.println("Fehler beim Schreiben des Files");
		}
		
	}
}
