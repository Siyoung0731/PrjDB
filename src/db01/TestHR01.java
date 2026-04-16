package db01;

import java.sql.*;

public class TestHR01 {
	private static String driver = "oracle.jdbc.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String dbuid = "hr";
	private static String dbpwd = "1234";
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		Statement stmt = conn.createStatement();
		String sql = "select d.department_name 부서명, e.first_name || ' ' || e.last_name 이름, e.phone_number 전화 "
				+ "from employees e join departments d on e.department_id = d.department_id";
		
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("[부서명]" + " " + "[이름]" + " " + "[전화]");
		while (rs.next() != false) {
			System.out.print("[" + rs.getString("부서명") + "]");
			System.out.print("[" + rs.getString("이름") + "]");
			System.out.print("[" + rs.getString("전화") + "]");
			System.out.println();			
		}
		
		rs.close();
		stmt.close();
		conn.close();
	}

}
