<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	function check(form) { 
		if(postform.dong.value == ""){
			alert("동을 입력해 주세요.");
			postform.dong.focus();
			return false;
		}
	}


</script>

</head>
<body bgcolor="#fff" topmargin="0" leftmargin="0"
	onload="postform.dong.focus()">
	<form name="postform" method="post"
		action="<%=request.getContextPath()%>/zipcode_ok.do"
		onsubmit="return check(this)">
		<table width="410" height="100" cellspacing="0" align="center">
			<tr>
				<td align="center" bgcolor="#999"><input type="image"
					src="images/ZipCode_img01.gif" width="413" height="58"></td>
			</tr>
			<tr>
				<td bgcolor="#F5FFEA" align="center"><strong><font
						color="#466d1b"><span class="style1">[거주지의 면, 동을
								입력하고 '찾기' 버튼을 누르세요]</span></font></strong></td>
			</tr>
			<tr height="30">
				<td bgcolor="#f5ffea" align="center"><input type="text"
					name="dong" size="10">&nbsp;<input type="image"
					src="images/m-i02.gif" width="69" height="19"></td>
			</tr>
		</table>

	</form>


</body>
</html>