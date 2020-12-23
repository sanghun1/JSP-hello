<%@page import="com.cos.hello.model.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../layout/header.jsp" %>
<h1>User Info</h1>
<%
	String result = (String)session.getAttribute("result");
%>
<%=result %>

<h1>${sessionScope.result}</h1>
</body>
</html>