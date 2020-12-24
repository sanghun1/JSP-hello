package com.cos.hello.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cos.hello.dao.UsersDao;
import com.cos.hello.model.Users;
import com.cos.hello.util.Script;

public class UsersService {
	
	public void 로그인(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		System.out.println("================loginProc===============");
		
		System.out.println(username);
		System.out.println(password);
		
		System.out.println("================loginProc===============");
		
		Users user = Users.builder()
				.username(username)
				.password(password)
				.build();
		UsersDao usersDao = new UsersDao(); // 싱글톤
		Users userEntity = usersDao.login(user);
		
		if(userEntity != null) {
			HttpSession session = req.getSession();
			session.setAttribute("sessionUser", userEntity);
			// 한글처리를 위해 resp 객체를 건드린다
			// MINE 타입
			// Http Header에 Content-Type
			Script.href(resp, "index.jsp", "로그인 성공");
		}
		else {
			PrintWriter out = resp.getWriter();
			Script.back(resp, "로그인 실패");
		}
	}
	
	public void 회원가입(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
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
		
		if(result == 1) {
			resp.sendRedirect("auth/login.jsp");
		}
		else {
			resp.sendRedirect("auth/login.jsp");
		}
	}
	
	public void 유저정보보기(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

		HttpSession session = req.getSession();
		
		Users user = (Users)session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();
		
		if(user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity);
			RequestDispatcher dis = req.getRequestDispatcher("user/selectOne.jsp");
			dis.forward(req, resp);
		}
		else {
			resp.sendRedirect("auth/login.jsp");
		}
		
			
	}
	
	public void 유저정보수정페이지(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

		HttpSession session = req.getSession();
		
		Users user = (Users)session.getAttribute("sessionUser");
		UsersDao usersDao = new UsersDao();
		
		if(user != null) {
			Users userEntity = usersDao.selectById(user.getId());
			req.setAttribute("user", userEntity);
			RequestDispatcher dis = req.getRequestDispatcher("user/updateOne.jsp");
			dis.forward(req, resp);
		}
		else {
			resp.sendRedirect("auth/login.jsp");
		}
		
			
	}
	
	public void 유저정보수정(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("id"));
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		
		Users user = Users.builder()
				.id(id)
				.password(password)
				.email(email)
				.build();
		
		UsersDao usersDao = new UsersDao(); // 싱글톤
		int result = usersDao.update(user);
		
		if(result == 1) {

			resp.sendRedirect("index.jsp");
		}
		else {
			// 이전 페이지로 이동
			resp.sendRedirect("user?gubun=updateOne");
		}
	}
	public void 회원정보삭제(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("id"));

		UsersDao usersDao = new UsersDao(); // 싱글톤
		int result = usersDao.delete(id);
		
		if(result == 1) {
			HttpSession session = req.getSession();
			session.invalidate(); // 세션 무효화
			resp.sendRedirect("index.jsp");
		}
		else {
			// 이전 페이지로 이동
			resp.sendRedirect("user?gubun=selectOne");
		}
	}
}
