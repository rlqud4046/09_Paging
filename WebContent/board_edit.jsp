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
		<hr width="50%" color="skyblue">
		<h3>MVC-2 모델 게시물 수정폼</h3>
		<hr width="50%" color="skyblue">
		<c:set var="dto" value="${cont }" />
		<form action="board_edit_ok.do" method="post">
		<input type="hidden" name="no" value="${dto.getBoard_no() }">
		<input type="hidden" name="page" value="${page }">
			<table border="1" cellspacing="0" width="400">
				<c:if test="${!empty dto }">
					<tr>
						<th>작성자</th>
						<td>${dto.getBoard_writer() }</td>
					</tr>
					<tr>
						<th>글제목</th>
						<td><input name="board_title"
							value="${dto.getBoard_title() }"></td>
					</tr>
					<tr>
						<th>글내용</th>
						<td><textarea rows="7" cols="40" name="board_cont"
								style="resize: none;">${dto.getBoard_cont() }</textarea></td>
					</tr>
					<tr>
						<th>조회수</th>
						<td>${dto.getBoard_hit() }</td>
					</tr>
					<tr>
						<th>작성일</th>
						<td>${dto.getBoard_regdate() }</td>
					</tr>
					<c:if test="${empty dto}">
						<tr>
							<td colspan="2" align="center">
								<h3>검색된 레코드가 없습니다</h3>
							</td>
						</tr>
					</c:if>
				</c:if>
				<tr>
					<td colspan="2" align="center">
					<input type="submit" value="확인">		
					<input type="reset" value="취소"> 
					<input type="button" value="목록" onclick="location.href='board_list.do?page=${page}'"></td>
				</tr>
			</table>
		</form>


	</div>


</body>
</html>