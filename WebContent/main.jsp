<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<div align="center">
		<%@ include file="include/header.jsp"%>
		<div class="clear"></div>
		<hr width="50%" color="skyblue">
		<h3>MVC-2 모델 방식의 BOARD 테이블 메인페이지</h3>
		<hr width="50%" color="skyblue">
		<a href="<%=request.getContextPath()%>/board_list.do">[게시물 목록]</a>


	</div>

</body>
</html>