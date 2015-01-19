package anil_stedronsky;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Start {
	public static void main(String[] args) {
		Model m = new Model("localhost", "rueck", "fener", "premiere");
		
		 String[] tables=m.getTables();
		
		for(int i=0; i<tables.length;++i){
			System.out.println(tables[i]);
		}
		
		m.close();
	}
}
