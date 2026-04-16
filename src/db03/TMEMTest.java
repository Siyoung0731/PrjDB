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
		TMemDTO tmem = null;
		
		do {
			// 화면 출력 반복
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
			case "2": //회원조회 (id) -- tuser
				System.out.println("조회할 아이디를 입력: ");
				String uid = sc.nextLine();
				tmem = getTMem(uid);
//				System.out.println(tmem.toString()); 
				display(tmem);
				break;
			case "3": 
				//회원추가 -- tmem
				tmem = inputData(); // inputdata() 함수 호출 
				int aftcnt = addTMem(tmem); // 입력할 데이터 3개를 받아서 추가. Create // aftcnt -> affected count : 총 몇개
				System.out.println(aftcnt + "건 저장되었습니다.");
				break;
			case "4": //회원수정
				break;
			case "5": //회원삭제
				break;
			case "q", "Q": 
				//종료;
				System.out.println("프로그램 종료");
				System.exit(0);
				break;
			}
			
		} while (true); // 무한 루프
		
	}
	
	//입력 받은 아이디로 한줄을 db 에서 조회
	private static TMemDTO getTMem(String uid) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		String sql = "SELECT * FROM TMEM WHERE USERID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, uid);
		
		TMemDTO tmem = null; // 지역 변수를 공용으로 사용하려면 전역 변수로 만들고 = null 이라고 초기화해줘야 함(필수)
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) { //해당 자료가 있는 경우
			String userid = rs.getString("userid");
			String username = rs.getString("username");
			String email = rs.getString("email");
			
			tmem = new TMemDTO(userid, username, email);
			
		} else { //해당 자료가 없는 경우 : primary key
			
		}
		
		pstmt.close();
		conn.close();
		
		return tmem;
	}
	//TMem 한줄 출력
	private static void display(TMemDTO tmem) {
		if (tmem == null) {
			System.out.println("조회한 자료가 없습니다.");
		} else {
			String msg = String.format("%s %s %s", tmem.getUserid(), tmem.getUsername(), tmem.getEmail());
			System.out.println(msg);
		}
	}
	
	
	// DB 에 insert 한다 - 저장된 데이터를 db 에 추가
	private static int addTMem(TMemDTO tmem) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		//tmem 에 있는 3개의 값을 추가
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
	private static TMemDTO inputData() {
		System.out.println("아이디: ");
		String userid = sc.nextLine();
		System.out.println("이름: ");
		String username = sc.nextLine();
		System.out.println("이메일: ");
		String email = sc.nextLine();
		
		TMemDTO tmem = new TMemDTO(userid, username, email); // userid, username, email 이 3개의 값을 tmem 변수에 저장을 하고
		return tmem; // tmem 값을 반환
	}
}
