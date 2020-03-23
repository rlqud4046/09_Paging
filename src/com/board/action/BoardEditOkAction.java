package com.board.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.model.BoardDAO;
import com.board.model.BoardDTO;

public class BoardEditOkAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String board_title = request.getParameter("board_title");
		String board_cont = request.getParameter("board_cont");
		int board_no = Integer.parseInt(request.getParameter("no"));
		int board_page = Integer.parseInt(request.getParameter("page"));

		BoardDTO dto = new BoardDTO();
		dto.setBoard_cont(board_cont);
		dto.setBoard_title(board_title);
		dto.setBoard_no(board_no);
		BoardDAO dao = BoardDAO.getInstance();
		int res = dao.boardEdit(dto);

		PrintWriter out = response.getWriter();

		if (res > 0) {
			out.println("<script>");
			out.println("alert('글 수정 완료')");
			out.println("location.href='board_cont.do?no=" + board_no + "&page=" + board_page + "'");
			out.println("</script>");
		} else {
			out.println("<script>");
			out.println("alert('글 수정 실패')");
			out.println("location.href='board_edit.do?no=" + board_no + "&page=" + board_page + "'");
			out.println("</script>");
		}
	}
}
