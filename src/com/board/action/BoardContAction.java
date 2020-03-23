package com.board.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.model.BoardDAO;
import com.board.model.BoardDTO;

public class BoardContAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 글 제목을 클릭했을 때 상세 내용을 보여주는 클래스
		int board_no = Integer.parseInt(request.getParameter("no"));
		int nowPage = Integer.parseInt(request.getParameter("page"));
		int search = Integer.parseInt(request.getParameter("search"));
		String find_field = request.getParameter("find_field");
		String find_name = request.getParameter("find_name");
		
		BoardDAO dao =BoardDAO.getInstance();
		dao.boardHit(board_no);		// 조회수 증가 메서드 호출
		
		BoardDTO dto = dao.getCont(board_no);		// 게시물 상세 내역 조회하는 메서드 호출
		request.setAttribute("cont", dto);
		request.setAttribute("page", nowPage);
		request.setAttribute("search", search);
		request.setAttribute("find_field", find_field);
		request.setAttribute("find_name", find_name);
		
		
		
	}

}
