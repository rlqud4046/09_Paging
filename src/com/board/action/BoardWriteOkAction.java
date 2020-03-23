package com.board.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.model.BoardDAO;
import com.board.model.BoardDTO;

public class BoardWriteOkAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String board_writer = request.getParameter("writer");
		String board_title = request.getParameter("title");
		String board_cont = request.getParameter("content");
		String board_pwd = request.getParameter("pwd");

		BoardDTO dto = new BoardDTO();
		dto.setBoard_writer(board_writer);
		dto.setBoard_title(board_title);
		dto.setBoard_cont(board_cont);
		dto.setBoard_pwd(board_pwd);

		BoardDAO dao = BoardDAO.getInstance();
		int res = dao.boardWrite(dto);
		
		PrintWriter out = response.getWriter();
		
		if(res>0) {
			out.println("<script>");
			out.println("location.href='board_list.do'");
			out.println("</script>");
		}else{
			out.println("<script>");
			out.println("alert('무언가 잘못됨')");
			out.println("history.back()");
			out.println("</script>");
		}
			

	}
}
