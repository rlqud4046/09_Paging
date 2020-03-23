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
		<hr width="50%" color="tomato">
		<h3>MVC-2 모델 게시판 글쓰기 폼</h3>
		<hr width="50%" color="tomato">
		<br>
		<br>
		<form action="<%=request.getContextPath()%>/board_write_ok.do"
			method="post">
			<table border="1" cellspacing="0" width="400">
				<tr>
					<th>작성자</th>
					<td><input name="writer" ></td>
				</tr>
				<tr>
					<th>글제목</th>
					<td><input name="title"></td>
				</tr>
				<tr>
					<th>글내용</th>
					<td><textarea rows="7" cols="30" name="content"></textarea></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="pwd"></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="글쓰기">&emsp;<input type="reset" value="취소"></td>
				</tr>
			</table>
		</form>


	</div>
</body>
</html>