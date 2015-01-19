package anil_stedronsky;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		try {
			String[] types = {"TABLE"};
			ResultSet rs = db.getTables(null, null, "%", types);
			int count=rs.getMetaData().getColumnCount();
			String[] tables = new String[count];
			int i=0;
			while(rs.next()){
				tables[i]=rs.getString("TABLE_NAME");
				++i;
			}
			return tables;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	/**
	 * Tables werden zurück gegebem
	 * @return Array mit den Tables
	 */
	public String[] getAtt() {
		try {
			String[] types = {"TABLE"};
			ResultSet rs = db.getTables(null, null, "%", types);
			int count=rs.getMetaData().getColumnCount();
			String[] att = new String[count];
			int i=0;
			while(rs.next()){
				att[i]=rs.getString("TABLE_NAME");
			}
			return att;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
