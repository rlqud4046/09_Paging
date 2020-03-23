<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
table {
	border: 1px solid black;
	border-collapse: collapse; /* == cellspacing="0" */
	width: 350px;
}

th, td {
	border: 1px solid black;
	border-collapse: collapse; /* == cellspacing="0" */
	text-align: center;
}
</style>	
</head>
<body>

	<div align="center">
	<%@ include file="include/header.jsp" %>
		<hr width="50%" color="tomato">
		<h3>회원 로그인 화면</h3>
		<hr width="50%" color="tomato">
		<form action="<%=request.getContextPath()%>/login_ok.do" method="post" onsubmit="return login_check()">
			<table>
				<tr>
					<th>아이디</th>
					<td><input name="id">&emsp;<input type="submit" value="로그인"></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input name="pwd" type="password">&emsp;<input type="reset" value="취  소"></td>
				</tr>
				<tr>
					<td colspan="2"><input type="button"
						value="회원가입" onclick="location.href='join.do'">&emsp; <input
						type="button" value="비번찾기" onclick="pwd_find()"></td>
				</tr>

			</table>



		</form>

	</div>


</body>
</html>