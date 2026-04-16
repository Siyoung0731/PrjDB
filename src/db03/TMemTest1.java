package db03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class TMemTest1 {
	private static String driver = "oracle.jdbc.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String dbuid = "sky";
	private static String dbpwd = "1234";

	static Scanner sc = new Scanner(System.in);
	static TMemDTO tmem = null;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		do {
			//화면 출력 반복
			System.out.println("========================================");
			System.out.println("				회원정보				");
			System.out.println("========================================");
			System.out.println("1. 회원 목록");
			System.out.println("2. 회원 조회");
			System.out.println("3. 회원 추가");
			System.out.println("4. 회원 수정");
			System.out.println("5. 회원 삭제");
			System.out.println("6. 종료");
			
			System.out.println("선택:");
			String choice = sc.nextLine();
			
			switch(choice) {
			case "1": //회원 목록
				ArrayList<TMemDTO> memList = getTMemList();
				displayList(memList);
				break; 
			case "2": //회원 조회 id
				System.out.println("조회할 아이디: ");
				String uid = sc.nextLine();
				tmem = getTMem(uid);
				display(tmem);
				break;
			case "3": 
				//회원 추가
				tmem = inputData();
				int aftcnt = addTMem(tmem);
				System.out.println(aftcnt + "건 저장되었습니다.");
				break;
			case "4": //회원 수정 uname
				tmem = Updatedata();
				uptTMem(tmem);
				displayudp(tmem);
				break;
			case "5": //회원 삭제
				break;
			case "q", "Q": //종료
				System.out.println("프로그램 종료");
				System.exit(0);;
				break;
			}
		} while (true); // 무한 루프
		
	}

	private static TMemDTO uptTMem(TMemDTO tmem) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		//tmem 에 있는 3개의 값을 추가
		String sql = "";
		sql += "UPDATE TMEM SET USERNAME = ? WHERE USERID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		pstmt.setString(1, tmem.getUsername());
		pstmt.setString(2, tmem.getUserid());
		
		pstmt.close();
		conn.close();
		
		return tmem;
	}

	private static TMemDTO Updatedata() {
		System.out.println("아이디: ");
		String userid = sc.nextLine();
		System.out.println("수정할 이름: ");
		String username = sc.nextLine();
		System.out.println("이메일: ");
		String email = sc.nextLine();
		
		TMemDTO tmem = new TMemDTO(userid, username, email);
		return tmem;
	}
	private static ArrayList<TMemDTO> getTMemList() 
			throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String sql = "SELECT * FROM TMEM ORDER BY USERID ASC";
		PreparedStatement pstmt = conn.prepareStatement(sql);		
		ResultSet rs = pstmt.executeQuery();
		
		ArrayList<TMemDTO> memList = new ArrayList<>();
		
		while (rs.next()) {
			String userid = rs.getString("userid");
			String username = rs.getString("username");
			String email = rs.getString("email");
			
			TMemDTO tmem = new TMemDTO(userid, username, email);
			memList.add(tmem);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return memList;
	}

	private static TMemDTO getTMem(String uid) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String sql = "SELECT * FROM TMEM WHERE USERID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, uid);
		
		TMemDTO tmem = null;
		
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			String userid = rs.getString("userid");
			String username = rs.getString("username");
			String email = rs.getString("email");
			
			tmem = new TMemDTO(userid, username, email);
		} else {
			
		}
		
		pstmt.close();
		conn.close();
		
		return tmem;
	}
	// INSERT
	private static int addTMem(TMemDTO tmem) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String sql = "";
		sql += "INSERT INTO TMEM VALUES(?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, tmem.getUserid());
		pstmt.setString(2, tmem.getUsername());
		pstmt.setString(3, tmem.getEmail());
		
		int aftcnt = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return aftcnt;
	}
	// 추가 데이터 키보드로 입력 받음
	private static TMemDTO inputData() {
		System.out.println("아이디: ");
		String userid = sc.nextLine();
		System.out.println("이름: ");
		String username = sc.nextLine();
		System.out.println("이메일: ");
		String email = sc.nextLine();
		
		TMemDTO tmem = new TMemDTO(userid, username, email);		
		return tmem;
	}
	private static void display(TMemDTO tmem) {
		if (tmem == null) {
			System.out.println("조회된 자료가 없습니다");
		} else {			
			String msg = String.format("%s %s %s", tmem.getUserid(), tmem.getUsername(), tmem.getEmail());
			System.out.println(msg);
		}
	}
	private static void displayList(ArrayList<TMemDTO> memList) {
		if(memList.size() == 0) {
			System.out.println("조회한 자료가 없습니다.");
			return;
		}
		String msg = "";
		for (TMemDTO tmem : memList) {
			String userid = tmem.getUserid();
			String username = tmem.getUsername();
			String email = tmem.getEmail();
			msg = """
					%s %s %s
					""".formatted(userid, username, email);
			System.out.print(msg);
		}
		System.out.println("Press any key ....");
		sc.nextLine();
	}
	private static void displayudp(TMemDTO tmem2) {
		String fmt = "%s %s %s";
		String msg = String.format(fmt, tmem.getUserid(), tmem.getUsername(), tmem.getEmail());
		System.out.println("업데이트 되었습니다.");
		System.out.println(msg);
	}
}
