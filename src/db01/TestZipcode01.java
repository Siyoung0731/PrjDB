package db01;

import java.sql.*;

public class TestZipcode01 {
	// 연결 문자열 : Connection String
	private static String driver = "oracle.jdbc.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String dbuid = "sky";
	private static String dbpwd = "1234";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		Statement stmt = conn.createStatement();
		String sql = "select count(zipcode) cnt from zipcode";
		
		ResultSet rs = stmt.executeQuery(sql);
		
		rs.next();
		
		System.out.println(rs.getInt("cnt"));
		
		stmt.close();
		conn.close();
	}

}
