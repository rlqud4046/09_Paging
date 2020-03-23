package com.board.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.model.MemberDAO;

public class ZipCodeAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 사용자가 입력한 "동"을 대상으로 zipcode 테이블을 조회하는 클래스
		String dong = request.getParameter("dong").trim();
		
		MemberDAO dao = MemberDAO.getInstance();
		ArrayList zipList = dao.searchZipCode(dong);
		
		
		request.setAttribute("zip", zipList);
		request.setAttribute("dong", dong);
		
		
	}

}
