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
	
	/**
	 * Konstruktor
	 * @param Hostname oder IP
	 * @param User fuer die DB
	 * @param User-PW fuer die DB
	 * @param Datenbankname
	 */
	public Datenbank(String host, String user, String pw, String database){
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
	 * Generiert ein fertiges RM
	 * @return das fertige RM
	 */
	public String createRM(){
		String rm="";
		for(int i=0; i<tabs.size();++i){
			ArrayList<Attribut> att = tabs.get(i).getAttributs();
			rm+=tabs.get(i).getName()+"(";
			for(int j=0; j<att.size();++j){
				if((j+1)==att.size()){
					if(att.get(j).isInstanceOfPK()){
						rm+=att.get(j).getName()+"<<PK>>";
					}
					else {
						rm+=att.get(j).getName();
					}
				}
				else {
					if(att.get(j).isInstanceOfPK()){
						rm+=att.get(j).getName()+"<<PK>>, ";
					}
					else {
						rm+=att.get(j).getName()+", ";
					}
				}
			}
			rm+=")\n";
		}
		return rm;
	}
	
}
