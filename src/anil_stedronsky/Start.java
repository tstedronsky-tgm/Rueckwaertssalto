package anil_stedronsky;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Start {
	public static void main(String[] args) {
		MysqlDataSource ds= new MysqlDataSource();
		try {
			ds.setServerName("localhost");
			ds.setUser("rueck");
			ds.setPassword("fener");
			ds.setDatabaseName("premiere");
			Connection con = ds.getConnection();
			// Abfrage vorbereiten und ausführen
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from film");
			ResultSetMetaData rsm=rs.getMetaData();
			String[] cname = new String[rsm.getColumnCount()];
			String tabname= rsm.getTableName(1);
			
			for(int i=1; i<=rsm.getColumnCount(); ++i){
				cname[i-1]=rsm.getColumnName(i);
			}
			
			for(int i=0; i<rsm.getColumnCount();++i){
				System.out.println(cname[i]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
