package com.cos.hello.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharFilter implements Filter{ // javax.servlet

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("1번 필터 걸림");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8"); // 여기서 모든 응답을 html로 걸고 아닌 부분은 덮어쓰기로 바꾼다
		chain.doFilter(request, response); // 다음 필터를 찾는다. 없으면 index로 들어감
	} 
	//
}
