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
			Connection con = ds.getConnection();
			// Abfrage vorbereiten und ausführen
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from xyq");
			ResultSetMetaData rsm=rs.getMetaData();
			System.out.println(rsm);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
