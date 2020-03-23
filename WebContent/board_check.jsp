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
		<form action="board_del_ok.do" method="post">
		<input type ="hidden" name ="no" value ="${no }">
		<input type ="hidden" name ="page" value ="${page }">
		<br>
		<br>
		<br>
		<br>
			비밀번호를 입력하세요 : <input type="password" name="pwd"> <input
				type="submit" value="확인">
		</form>

	</div>

</body>
</html>