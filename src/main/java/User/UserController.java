package User;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/register/*"})

public class UserController extends HttpServlet {
	userDAO userDAO;
	public void init(ServletConfig config) throws ServletException {
		userDAO = new userDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	      request.setCharacterEncoding("utf-8");
	      response.setContentType("text/html;charset=utf-8");
	      String nextPage = null;
	      String action = request.getPathInfo();   // URL 요청명을 가져온다.
	      System.out.println("요청매핑이름 : " + action);
	      if (action == null || action.equals("/register")) {
	         nextPage = "/register/register.jsp";
	      }else if(action.equals("/addMember.do")) {
	    	  String userId = request.getParameter("userId");
	    	  String userPassword = request.getParameter("userPassword");
	    	  String userEmail = request.getParameter("userEmail");
	         userDTO userDTO = new userDTO(userId,userPassword,userEmail);
	         userDAO.addMember(userDTO);
	         request.setAttribute("msg", "addMember");
	         nextPage = "/index/index.jsp";
	      }
	      RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response); // 컨트롤러에서 화면 출력하는 listMembers.jsp 포워딩
	   }
}