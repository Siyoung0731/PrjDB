package db03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
// JDBC 방식
public class TMemTest1 {
	private static String driver = "oracle.jdbc.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String dbuid = "sky";
	private static String dbpwd = "1234";

	static Scanner sc = new Scanner(System.in);
	static TMemDTO tmem = null;
	static int aftcnt = 0;
	
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
				aftcnt = addTMem(tmem);
				System.out.println(aftcnt + "건 저장되었습니다.");
				break;
			case "4": //회원 수정 uname
				System.out.println("수정할 아이디 입력: ");
				String orgUid = sc.nextLine(); // 검색할 데이터, 변경대상 X
				System.out.println("수정할 내용 입력: ");
				tmem = Updatedata();
				aftcnt = uptTMem(orgUid, tmem);
				System.out.println(aftcnt + "행 이(가) 업데이트되었습니다.");
				break;
			case "5": //회원 삭제
				System.out.println("삭제할 아이디 입력:");
				String orgUid1 = sc.nextLine();
				aftcnt = dltTMem(orgUid1);
				System.out.println(aftcnt + "건 삭제되었습니다.");
				sc.nextLine();
				break;
			case "q", "Q": //종료
				System.out.println("프로그램 종료");
				System.exit(0);;
				break;
			}
		} while (true); // 무한 루프
		
	}
	// pstmt.executeUpdate() : sql문 실행 후 커밋
	//함수 호출
	//1. ArrayList 회원 목록
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
	//2. Select 회원 조회
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
	//3. INSERT 회원 추가
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
	//4. Update 회원 수정
	private static int uptTMem(String orgUid, TMemDTO tmem) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		//tmem 에 있는 3개의 값을 추가
		String sql = "UPDATE TMEM"
				+ " set USERNAME = ?, EMAIL = ?"
				+ " where USERID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, tmem.getUsername());
		pstmt.setString(2, tmem.getEmail());
		pstmt.setString(3, orgUid);
		
		int aftcnt = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return aftcnt;
	}
	//5. Delete 회원 삭제
	private static int dltTMem(String orgUid1) throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, dbuid, dbpwd);
		
		//tmem 에 있는 3개의 값을 추가
		String sql = "DELETE FROM TMEM WHERE USERID = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, orgUid1);
		
		int aftcnt = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return aftcnt;
	}
//-----------------------------------------------------------------------
//	데이터 입력
	// 추가 데이터 
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
	// 수정할 데이터
	private static TMemDTO Updatedata() {
		System.out.println("이름: ");
		String username = sc.nextLine();
		System.out.println("이메일: ");
		String email = sc.nextLine();
		
		TMemDTO tmem = new TMemDTO(username, email);	
		return tmem;
	}
//-----------------------------------------------------------------------
//	Display()
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
}
