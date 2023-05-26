package Login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import User.userDAO;
import User.userDTO;



@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request,response);
	}
	private void doHandle (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out =response.getWriter();
		String userId=request.getParameter("userID");
		String userPassword=request.getParameter("userPassword");
		userDTO userDTO = new userDTO();
		userDTO.setUserID(userId);
		userDTO.setUserPassword(userPassword);
		userDAO dao = new userDAO();
		boolean isLoginSuccess= dao.isRightUser(userDTO);
		if(isLoginSuccess) {
			HttpSession session=request.getSession();
			session.setAttribute("log_id", userId);
			out.println("<script>");
			if (userId.equals("admin")) {
				out.println("alert('관리자님 환영합니다.');");
			} else {
				out.println("alert('"+"환영합니다.');");
			}
			out.println("location.href='{contextPath}/index.jsp';");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('아이디 또는 비밀번호를 잘못 입력했습니다.');");
			out.println("history.back();");
			out.println("</script>");
		}
	}

}
