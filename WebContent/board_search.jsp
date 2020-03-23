<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/bootstrap_3-3-2.css">
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
		<%@ include file="include/header.jsp"%>
		<div class="clear"></div>
		<hr width="50%" color="red">
		<h3>MVC-2 모델 BOARD1 테이블 검색 결과</h3>
		<hr width="50%" color="red">

		<table>
			<tr>
				<th>글번호</th>
				<th>글제목</th>
				<th>조회수</th>
				<th>등록일</th>
			</tr>
			<c:set var="search" value="${Search }" />
			<c:if test="${!empty search }">
				<c:forEach items="${search }" var="dto">
					<tr>
						<td>${dto.getBoard_no() }</td>
						<td><a
							href="board_cont.do?no=${dto.getBoard_no() }&page=${page}&find_field=${find_field}&find_name=${find_name}&search=1">${dto.getBoard_title() }</a>
						</td>
						<td>${dto.getBoard_hit() }</td>
						<td>${dto.getBoard_regdate().substring(0,10) }</td>
					</tr>
				</c:forEach>
			</c:if>

			<c:if test="${empty search }">
				<tr>
					<td colspan="4"><h3>검색 결과가 없습니다.</h3></td>
				</tr>

			</c:if>
			
		</table>


		<div>
			<ul class="pagination">
				<c:if test="${page>block }">
					<li class="paginate_button previous"><a
						href="board_search.do?page=1&find_field=${find_field }&find_name=${find_name }">◀◀</a></li>
					<%-- 첫번째 페이지로 --%>
					<li><a href="board_search.do?page=${startBlock-1 }&find_field=${find_field }&find_name=${find_name }">◀</a></li>
					<%-- 이전 블록의 마지막 페이지로 --%>
				</c:if>
				<c:forEach begin="${startBlock }" end="${endBlock }" var="i">
					<c:if test="${i == page }">
						<%-- 현재 페이지를 클릭하려 할 경우 --%>
						<li class="active"><a href="board_search.do?page=${i }&find_field=${find_field }&find_name=${find_name }">${i }</a></li>
					</c:if>

					<c:if test="${i != page }">
						<li><a href="board_search.do?page=${i }&find_field=${find_field }&find_name=${find_name }">${i }</a></li>
					</c:if>



				</c:forEach>

				<c:if test="${endBlock < allPage }">
					<li><a href="board_search.do?page=${endBlock+1 }&find_field=${find_field }&find_name=${find_name }">▶</a></li>
					<%-- 이전 블록의 마지막 페이지로 --%>
					<li class="paginate_button next"><a
						href="board_search.do?page=${allPage }&find_field=${find_field }&find_name=${find_name }">▶▶</a></li>
					<%-- 첫번째 페이지로 --%>
				</c:if>
			</ul>
		</div>
		
		<div align ="center">
		<input type="button" value="전체목록" onclick="location.href='<%=request.getContextPath()%>/board_list.do'">
		</div>

	



	</div>
</body>
</html>