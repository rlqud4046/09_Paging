<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/main.css">
</head>
<body>

	<div id="header">
		<div id="logo">
			<a href="./main.jsp"><img src="images/naver.jpg"
				width="30" border="0"></a>
		</div>

		<div id="top_login">
			<ul>
				<li><a href="./main.jsp">홈</a></li>
				<li>
				<c:if test="${!empty name }">
				${name }님
				</c:if>
				<c:if test="${empty name }">
				<a href="<%=request.getContextPath()%>/login.do">로그인</a>
				</c:if>
				</li>
				<li><a href="<%=request.getContextPath()%>/join.do">회원가입</a></li>
				<li><a href="<%=request.getContextPath()%>/logout.do">로그아웃</a></li>
			</ul>

		</div>
	</div>
</body>
</html>