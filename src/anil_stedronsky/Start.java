package anil_stedronsky;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Start {
	public static void main(String[] args) {
		Model m = new Model("localhost", "rueck", "fener", "schokoladenfabrik");
		
		System.out.println(m.getRM());
		m.close();
	}
}
