package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class userDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private DataSource dataFactory;

	public userDAO() {
		try {
			// JNDI(Java Naming and Directory Interface)에 적븐하기 위해 기본 경로(javaL/comp/env)를 지정
			// (자바 애플리케이션을 외부 디렉터리 서비스에 연결
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");

		} catch (Exception e) {
			System.out.println("오라클 연결 오류");
			e.printStackTrace();
		}
	}
	public void addMember(userDTO userDTO) {
	     try {
	        conn = dataFactory.getConnection();
	        String userId = userDTO.getUserID();
	        String userPassword = userDTO.getUserPassword();
	        String userEmail= userDTO.getUserEmail();
	        String query = "insert into lectureuser (userId,userPassword,userEmail) values(?,?,?)";
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, userId);
	        pstmt.setString(2, userPassword);
	        pstmt.setString(3, userEmail);
	        pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	     } catch (Exception e) {
	        System.out.println("DB 등록 중 에러!!");
	        e.printStackTrace();
	     }
	  }
	 public boolean isRightUser(userDTO userDTO) {
			boolean isLoginSuccess=false;
			String userId=userDTO.getUserID();
			String userPassword=userDTO.getUserPassword();
			try {
				conn=dataFactory.getConnection();
				String query="SELECT userId FROM lectureuser WHERE userId=? AND userPassword=?";
				pstmt=conn.prepareStatement(query);
				pstmt.setString(1,userId);
				pstmt.setString(2,userPassword);
				ResultSet rs=pstmt.executeQuery();
				while (rs.next()) {
					 isLoginSuccess = true;
				}
			} catch (Exception e) {
				System.out.println("DB 연결 오류");
				e.printStackTrace();
			}
			  return isLoginSuccess;
	}

}
