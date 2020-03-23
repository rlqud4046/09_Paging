package com.board.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.model.BoardDAO;

public class BoardDelOkAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int no = Integer.parseInt(request.getParameter("no"));
		int page = Integer.parseInt(request.getParameter("page"));
		String pwd = request.getParameter("pwd");

		BoardDAO dao = BoardDAO.getInstance();

		int res = dao.boardDel(no, pwd);

		PrintWriter out = response.getWriter();

		if (res > 0) {
			out.println("<script>");
			out.println("alert('삭제 완료')");
			out.println("location.href='board_list.do'");
			out.println("</script>");

		} else if (res == 0) {
			out.println("<script>");
			out.println("alert('삭제 실패 이유가뭘까')");
			out.println("history.back()");
			out.println("</script>");
		}
	}

}
