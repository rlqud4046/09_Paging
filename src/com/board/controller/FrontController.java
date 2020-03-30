package com.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.action.*;

public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 5679718683735199043L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 한글 인코딩 처리
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		// getRequestURI() : "/프로젝트명/파일명(*.do)라는 문자열을 반환하는 메서드
		String uri = request.getRequestURI();
		System.out.println("URI : " + uri);

		// getContextPath() : 현재 프로젝트명을 문자열로 반환하는 메서드
		String path = request.getContextPath();
		System.out.println("path : " + path);

		String command = uri.substring(path.length() + 1);
		System.out.println("command : " + command);

		Action action = null;
		String viewPage = null;
		if (command.equals("login.do")) {
			viewPage = "login.jsp";
		} else if (command.equals("login_ok.do")) {
			action = new LoginOkAction();
			action.execute(request, response);
		} else if (command.equals("board_list.do")) {
			action = new BoardListAction();
			action.execute(request, response);
			viewPage = "board_list.jsp";
		} else if (command.equals("board_write.do")) {
			viewPage = "board_write.jsp";
		} else if (command.equals("board_write_ok.do")) {
			action = new BoardWriteOkAction();
			action.execute(request, response);
		} else if (command.equals("board_cont.do")) {
			action = new BoardContAction();
			action.execute(request, response);
			viewPage = "board_cont.jsp";
		} else if (command.equals("board_edit.do")) {
			action = new BoardEditAction();
			action.execute(request, response);
			viewPage = "board_edit.jsp";
		} else if (command.equals("board_edit_ok.do")) {
			action = new BoardEditOkAction();
			action.execute(request, response);
		} else if (command.equals("board_del.do")) {
			action = new BoardDelAction();
			action.execute(request, response);
			viewPage = "board_check.jsp";
		} else if (command.equals("board_del_ok.do")) {
			action = new BoardDelOkAction();
			action.execute(request, response);
		} else if (command.equals("board_search.do")) {
			action = new BoardSearchAction();
			action.execute(request, response);
			viewPage = "board_search.jsp";
		} else if (command.equals("join.do")) {
			viewPage = "member_join.jsp";
		} else if (command.equals("zipcode_find.do")) {
			viewPage = "zipcode.jsp";
		} else if (command.equals("zipcode_ok.do")) {
			action = new ZipCodeAction();
			action.execute(request, response);
			viewPage = "zipcode.jsp";
		} else if (command.equals("logout.do")) {
			viewPage = "logout.jsp";
		}

		RequestDispatcher rd = request.getRequestDispatcher(viewPage);
		rd.forward(request, response);

	}
}