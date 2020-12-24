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
import com.cos.hello.service.UsersService;


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
	
	private void route(String gubun, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UsersService usersService = new UsersService();
		if(gubun.equals("login")) {
			resp.sendRedirect("auth/login.jsp");
		}
		else if(gubun.equals("join")) {
			resp.sendRedirect("auth/join.jsp");
		}
		else if(gubun.equals("selectOne")) { // 유저정보 보기
			// 인증이 필요한 페이지
			
			usersService.유저정보보기(req, resp);
			
		}
		else if(gubun.equals("updateOne")) {
			usersService.유저정보수정페이지(req, resp);
		}
		else if(gubun.equals("joinProc")) { // 회원가입 수행
			// 데이터 원형 username=ssar&password=1234email=ssar@nate.com
			
			// 1번 form의 input태그에 있는 3가지 값 username, password, email받기
			
			// getParameter함수는 get 방식의 데이터와 post방식의 데이터를 다 받을 수 있다.
			// 단 방식에서는 데이터 타입이 x-www-form-urlencoded 상식만 받을 수 있음
			
			// 2번 DB에 연결해서 3가지 값을 INSERT하기
			usersService.회원가입(req, resp);

		}
		else if(gubun.equals("loginProc")) {
			
			// SELEST id, username, email FROM users WHERE username = ? AND password = ? AND email = ?
			// DAO의 함수명 : login() return을 Users 오브젝트를 리턴 // 재사용 불가능
			// 정상이면 : 세션에 Users 오브젝트를 담고 index.jsp 이동
			// 비정상이면 : login.jsp 이동
			usersService.로그인(req, resp);
			
		}
		else if(gubun.equals("updateProc")) {
			System.out.println("회원정보수정");
			usersService.유저정보수정(req, resp);
			
		}
		else if(gubun.equals("deleteProc")) {
			System.out.println("회원정보삭제");
			usersService.회원정보삭제(req, resp);
			
		}
	}
}
