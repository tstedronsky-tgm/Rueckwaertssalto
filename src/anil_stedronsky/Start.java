package anil_stedronsky;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
/**
 * RM wird gestartet 
 * @author Stedronsky Thomas
 * @version 2015-01-20
 */
public class Start {
	public static void main(String[] args) {
		Model m = new Model("localhost", "rueck", "fener", "schokoladenfabrik"); //DB Connection Parameter werden an das Model �bergeben
		System.out.println(m.getRM());
		m.close();
	}
}
