package anil_stedronsky;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Model {
	private MysqlDataSource ds;
	private Connection con;
	private DatabaseMetaData db;

	public Model(String host, String user, String pw, String database){
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
			ResultSet rs = db.getTables(null, null, "%", types);
			while(rs.next()){
				al.add(rs.getString("TABLE_NAME"));
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
		ArrayList<String> pk = new ArrayList<String>();
		ResultSet pks;
		try {
			pks = db.getPrimaryKeys(null, null, tabname);
			while (pks.next()) {
				pk.add(pks.getString("COLUMN_NAME"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String[] prk=pk.toArray(new String[pk.size()]);
		
		

		ArrayList<String> al= new ArrayList<String>();
		String[] att = null;
		try {
			ResultSet rs = db.getColumns(null, null, tabname,null);
			while(rs.next()){
				al.add(rs.getString("COLUMN_NAME"));
			}
			att = al.toArray(new String[al.size()]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<att.length;++i){
			for(int j=0; j<prk.length;++j){
				if(att[i].equals(prk[j])){
					att[i]+="<PK>";
				}
			}
		}
		return att;

	}
	
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
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
