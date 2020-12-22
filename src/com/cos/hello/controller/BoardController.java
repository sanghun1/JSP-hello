package com.cos.hello.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardController extends HttpServlet{

	// req와 res는 톰켓이 만들어줌 (클라이언트 요텅이 있을때 마다)
		// req는 BufferedReader 할 수 있는 ByteStream
		// res는 BufferedWriter 할 수 있는 ByteStream
		
		// https://localhost:8000/hello/front
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doProcess(req, resp);
		}
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doProcess(req, resp);
		}
		
		protected void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			String gubun = req.getParameter("gubun"); // gubun 뒤 값 파싱
//			String gubun = req.getRequestURI(); // hello/front
			System.out.println(gubun);
			
			if(gubun.equals("deleteOne")) {
				resp.sendRedirect("board/deleteOne.jsp");
			}
			else if(gubun.equals("insertOne")) {
				resp.sendRedirect("board/insertOne.jsp");
			}
			else if(gubun.equals("updateOne")) {
				resp.sendRedirect("board/updateOne.jsp");
			}
			else if(gubun.equals("selectAll")) {
				resp.sendRedirect("board/selectAll.jsp");
			}
		}
}
