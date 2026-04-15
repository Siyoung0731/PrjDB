package db01;

import java.sql.*;

public class TestZipcode02 {
	private static String driver = "oracle.jdbc.OracleDriver";
	private static String url = "jdbc:oracle:thin:@192.168.0.228:1521:xe";
	private static String uid = "hr";
	private static String upwd = "1234";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, uid, upwd);
		
		Statement stmt = conn.createStatement();
		String sql = "select "; // java 에서 sql 문 쓸 때 사용
		System.out.println(sql);
		ResultSet rs = stmt.executeQuery(sql); // java 에서 select 할 때 필요한 단어 ResultSet
		
		
		while (rs.next() != false) {
			System.out.print(rs.getString(1) + ",");
			System.out.print(rs.getString(2) + ",");
			System.out.print(rs.getString(3) + ",");
			System.out.print(rs.getString(4) + ",");
			System.out.print(rs.getString(5) + ",");
			System.out.print(rs.getInt(6));
			System.out.println();
			/*
			System.out.print(rs.getString("zipcode") + ",");
			System.out.print(rs.getString("sido") + ",");
			System.out.print(rs.getString("gugun") + ",");
			System.out.print(rs.getString("dong") + ",");
			System.out.print(rs.getString("bj") + ",");
			System.out.print(rs.getInt("seq"));
			System.out.println();
			*/
		}
		
		rs.close();
		stmt.close();
		conn.close();
	}

}
