<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	// 현재 로그인 된 사용자의 모든 세션 정보를 만료시켜야 한다.
	session.invalidate(); // 모든 세션 속성을 만료시키는 메서드
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
		alert("로그아웃 되었습니다.");
		location.href = "login.do";
	</script>
</body>
</html>