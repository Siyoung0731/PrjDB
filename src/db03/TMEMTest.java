package db03;

import java.sql.*;
import java.util.Scanner;

public class TMEMTest {
	//연결 문자열
	private static String driver = "oracle.jdbc.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String dbuid = "sky";
	private static String dbpwd = "1234";
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//CRUD 예제, Create, Read, Update, Delete
		
		do {
			// 화면 출력
			System.out.println("=============================");
			System.out.println("			회원정보		 ");
			System.out.println("=============================");
			System.out.println("1. 회원 목록");
			System.out.println("2. 회원 조회");
			System.out.println("3. 회원 추가");
			System.out.println("4. 회원 수정");
			System.out.println("5. 회원 삭제");
			System.out.println("Q. 종료");
			
			System.out.println("선택:");
			String choice = sc.nextLine();
			
			switch(choice) {
			case "1": //회원목록
				break;
			case "2": //회원조회
				break;
			case "3": 
				//회원추가
				TMEMDTO tuser = inputData();
				int aftcnt = addTMem(tuser); // 입력할 데이터 3개를 받아서 추가. Create
				System.out.println(aftcnt + "건 저장되었습니다.");
				break;
			case "4": //회원수정
				break;
			case "5": //회원삭제
				break;
			case "q": 
				//종료;
				System.out.println("프로그램 종료");
				System.exit(0);
				break;
			}
			
		} while (true); // 무한 루프
	}
	// DB 에 insert 한다
	private static int addTMem(TMEMDTO tmem) throws ClassNotFoundException, SQLException {
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

	// 데이터를 키보드로 입력 받음
	private static TMEMDTO inputData() {
		System.out.println("아이디: ");
		String userid = sc.nextLine();
		System.out.println("이름: ");
		String username = sc.nextLine();
		System.out.println("이메일: ");
		String email = sc.nextLine();
		
		TMEMDTO tmem = new TMEMDTO(userid, username, email);
		return tmem;
	}
}
