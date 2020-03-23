package com.board.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.board.model.BoardDAO;
import com.board.model.MemberDAO;
import com.board.model.MemberDTO;

public class LoginOkAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 로그인폼 페이지에서 넘어온 아이디이와 비밀번호가 DB에 있는지 여부를 체크하는 클래스
		String mem_id = request.getParameter("id").trim();
		String mem_pwd = request.getParameter("pwd").trim();

		MemberDAO dao = MemberDAO.getInstance();
		int res = dao.userCheck(mem_id, mem_pwd);

		PrintWriter out = response.getWriter();
		/*
		세션 객체를 생성하는 방법
		request.getSession() 메서드는 session이 생성되어 있는 경우에는
		session을 리턴 하고, 생성되어 있지 않은 경우에는 새롭게 session을 생성하여 리턴한다. 
		*/
		HttpSession session = request.getSession();
		
		
		
		
		if (res >0 ) {
			MemberDTO dto = dao.getMember(mem_id);
			
			// 회원의 정보를 세션 객체에 저장
			session.setAttribute("id", dto.getMember_id());
			session.setAttribute("name", dto.getMember_name());
			session.setAttribute("nickname", dto.getMember_nickname());
			
			out.println("<script>");
			out.println("alert('"+mem_id+"님 환영합니다')");
			out.println("location.href='main.jsp'");
			out.println("</script>");
		} else if (res == -1) {
			out.println("<script>");
			out.println("alert('비밀번호를 확인해주세요')");
			out.println("history.back()");
			out.println("</script>");
		} else if (res == -2) {
			out.println("<script>");
			out.println("alert('ID를 찾을 수 없습니다. 회원가입 후 이용해주세요.')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('로그인에 실패 했습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}
	}

}
