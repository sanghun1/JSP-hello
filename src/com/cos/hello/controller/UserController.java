package com.cos.hello.controller;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
// javax로 시작하는 패키지는 톰켓이 들고 있는 라이브러리
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.comfig.DBConn;
import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;


// 디스패쳐의 역살 = 분기 = 필요한 View를 응답해주는 것
public class UserController extends HttpServlet{
	
	// req와 res는 톰켓이 만들어줌 (클라이언트 요텅이 있을때 마다)
	// req는 BufferedReader 할 수 있는 ByteStream
	// res는 BufferedWriter 할 수 있는 ByteStream
	
	// https://localhost:8000/hello/front
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("FrontController 실행됨");
		
		String gubun = req.getParameter("gubun"); // gubun 뒤 값 파싱
//		String gubun = req.getRequestURI(); // hello/front
		System.out.println(gubun);
		
		route(gubun, req, resp);
		
		// 라우터 
//		resp.sendRedirect("auth/join.jsp"); // 한번 더 request
		
//		resp.sendRedirect("auth/login.jsp"); // 한번 더 request
	}
	
	private void route(String gubun, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		if(gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp");
		}
		else if(gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp");
		}
		else if(gubun.equals("selectOne")) { // 유저정보 보기
			// 인증이 필요한 페이지
			String result;
			
			HttpSession session = req.getSession();
			
			if(session.getAttribute("sessionUser")!= null) {
				Users user = (Users)session.getAttribute("sessionUser");
				result = "인증되었습니다.";
				System.out.println(user);
			}
			else {
				result = "인증되지 않았습니다.";
			}
			req.setAttribute("result", result);
			
			RequestDispatcher dis = req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
			
			resp.sendRedirect("user/selectOne.jsp");
		}
		else if(gubun.equals("updateOne")) {
			resp.sendRedirect("user/updateOne.jsp");
		}
		else if(gubun.equals("joinProc")) { // 회원가입 수행
			// 데이터 원형 username=ssar&password=1234email=ssar@nate.com
			
			// 1번 form의 input태그에 있는 3가지 값 username, password, email받기
			
			// getParameter함수는 get 방식의 데이터와 post방식의 데이터를 다 받을 수 있다.
			// 단 방식에서는 데이터 타입이 x-www-form-urlencoded 상식만 받을 수 있음
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String email = req.getParameter("email");
			
			System.out.println("================joinProc===============");
			
			System.out.println(username);
			System.out.println(password);
			System.out.println(email);
			
			System.out.println("================joinProc===============");
			
			Users user = Users.builder()
					.username(username)
					.password(password)
					.email(email)
					.build();
			
			UsersDao usersDao = new UsersDao(); // 싱글톤
			int result = usersDao.insert(user);
			
			// 2번 DB에 연결해서 3가지 값을 INSERT하기
			if(result == 1) {
				resp.sendRedirect("auth/login.jsp");
			}
			else {
				resp.sendRedirect("auth/login.jsp");
			}

		}
		else if(gubun.equals("loginProc")) {
			// 1번 전달되는 값 받기
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			System.out.println("================joinProc===============");
			
			System.out.println(username);
			System.out.println(password);
			
			System.out.println("================joinProc===============");
			
			// 2번 데이터베이스 값이 있는 select해서 확인
			// 생략
			Users user = Users.builder()
					.id(1)
					.username(username)
					.build();
			// 3번
			HttpSession session = req.getSession();
			// session에는 사용자 패스워드 절대 넣지 않기 
			session.setAttribute("sessionUser", user);
			//모든 응답에는 jSessionId가 쿠키로 추가된다
			resp.setHeader("Set-Cookie", "9998");
			// 4번 index.jsp로 이동 
			resp.sendRedirect("index.jsp");
		}
	}
}
