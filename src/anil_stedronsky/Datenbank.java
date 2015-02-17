package anil_stedronsky;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Datenbank mit Connection, Dataource und den jeweiligen Tabellen der DB
 * @author Stedronsky Thomas
 * @version 2015-02-09
 */
public class Datenbank {
	private ArrayList<Tabelle> tabs;
	private MysqlDataSource ds;
	private Connection con;
	private DatabaseMetaData db;
	private String name;
	
	/**
	 * Konstruktor
	 * @param host		Hostname oder IP
	 * @param user		User fuer die DB
	 * @param pw		User-PW fuer die DB
	 * @param database	Datenbankname
	 */
	public Datenbank(String host, String user, String pw, String database){
		this.ds= new MysqlDataSource();
		ds.setServerName(host);
		ds.setUser(user);
		ds.setPassword(pw);
		ds.setDatabaseName(database);
		this.name=database;
		try {
			con = ds.getConnection();
			db = con.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tabs = new ArrayList<Tabelle>();
		
		this.setTables();
	}
	
	/**
	 * Eine Methode die mit der Connection die Tabellen der jeweiligen DB ausliest
	 */
	public void setTables(){
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
		
		for(int i=0; i<tables.length;++i){
			tabs.add(new Tabelle(this, tables[i]));
		}
	}
	
	/**
	 * Liefert die MetaDaten der DB zurück
	 * @return die DatabaseMetadata der jeweiligen DB 
	 */
	public DatabaseMetaData getDatabaseMetaData(){
		return this.db;
	}
	
	/**
	 * Liefert die Tabellen in der DB zurück
	 * @return die Tabellen der DB in einer ArrayList
	 */
	public ArrayList<Tabelle> getTables(){
		return this.tabs;
	}
	
	/**
	 * Liefert den Namen der DB zurück
	 * @return den Namen der DB
	 */
	public String getName(){
		return this.name;
	}
	
	
	
}
