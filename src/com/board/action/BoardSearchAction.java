package com.board.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.model.BoardDAO;
import com.board.model.BoardDTO;

public class BoardSearchAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String find_field = request.getParameter("find_field");
		String find_name = request.getParameter("find_name");


		// 페이징 처리 기법
		int rowsize = 3; // 한 페이지당 보여질 게시글의 수
		int block = 3; // 아래 보여질 페이지의 최대 수
		int totalRecord = 0; // 데이터베이스 상의 전체 레코드(게시글)의 수
		int allPage = 0; // 전체 페이지 수 = totalRecord / rowsize

		int page = 1; // 현재 페이지 번호

		if (request.getParameter("page") != null) { // 검색 후 나온 페이지에서 다른 페이지를 누른 경우
			page = Integer.parseInt(request.getParameter("page"));
		}

		// 해당 페이지에서의 시작 번호
		int startNo = (page * rowsize) - (rowsize - 1);
		// 해당 페이지에서의 끝번호
		int endNo = page * rowsize;

		// 해당 페이지의 시작 블럭 (page) > block이어야 페이지가 넘어가므로 당연!
		int startBlock = (((page - 1) / block) * block) + 1;
		// 해당 페이지의 끝 블럭
		int endBlock = (((page - 1) / block) * block) + block;
		
		BoardDAO dao = BoardDAO.getInstance();
		// List<BoardDTO> search = dao.boardSearch(find_field, find_name);
		
		totalRecord = dao.searchListCount(find_field, find_name);
		/*
		검색된 전체 게시글의 수를 한 페이지당 보여질 게시물의 수로 나누어 주면 된다
		이런 작업을 거치면 전체 페이지 수가 나온다
		전체 페이지가 나올 때 나머지가 있으면 무조건 올림이 되어야 한다.
		*/
		allPage = (int)(Math.ceil(totalRecord/(double)rowsize));
		
		if(endBlock > allPage) { // 올페이지가 7인 경우 엔드블럭은 9이므로 8,9페이지를 날려주기 위함
			endBlock = allPage;
		}
		
		List<BoardDTO> search = dao.boardSearch(find_field, find_name, page, rowsize);
		
		request.setAttribute("page", page);					// O
		request.setAttribute("rowsize", rowsize);
		request.setAttribute("block", block);				// O
		request.setAttribute("totalRecord", totalRecord);
		request.setAttribute("allPage", allPage);			// O
		request.setAttribute("startNo", startNo);
		request.setAttribute("endNo", endNo);
		request.setAttribute("startBlock", startBlock);		// O
		request.setAttribute("endBlock", endBlock);			// O
		request.setAttribute("Search", search);
		request.setAttribute("find_field", find_field);
		request.setAttribute("find_name", find_name);
		
		
		
		
	}

}
