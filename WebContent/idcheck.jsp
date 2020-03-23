<%@page import="com.board.model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("userId");

	// DB와 연동
	MemberDAO dao = MemberDAO.getInstance();
	int res = dao.checkMemberId(id);
	out.println(res);
%>