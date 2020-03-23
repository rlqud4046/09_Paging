package com.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class MemberDAO {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;

	/*
	 * 싱글톤 객체 만들기 1. 싱글톤 객체를 만들때는 우선적으로 접근지정자는 private로 선언한다. 2. 정적(static) 멤버로 선언을
	 * 한다.
	 */

	private static MemberDAO instance = new MemberDAO();

	/*
	 * 3. 기본생성자는 외부에서 접근이 되지 않도록 해야한다. 외부에서 new를 사용하지 못하게 하는 접근 기법
	 */

	private MemberDAO() {
	}

	// 4. 생성자 대신에 싱글톤 객체를 retrun 해 주는 getInstance() 메서드를 만들어 준다.
	public static MemberDAO getInstance() {
		if (instance == null) {
			instance = new MemberDAO();
		}
		return instance;
	}

	public Connection openConn() {

		try {
			// 1. JNDI객체 생성
			InitialContext ic = new InitialContext();

			// 2.lookup() 메서드를 이용하여 매칭되는 커넥션을 찾는다
			DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/myoracle");

			// 3. DataSource 객체를 이용하여 커넥션 객체를 하나 가져온다.
			con = ds.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;

	} // openConn() 메서드 end

	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// DB상에 아이디와 비밀번호를 확인하는 메서드
	public int userCheck(String id, String pwd) {
		int result = 0; // 회원 여부를 저장할 변수

		try {
			openConn();
			sql = "select * from jsp_member where member_id=? and member_state=1"; // member_state=1 >> 활동회원,
																					// member_state=2 >> 탈퇴한 회원
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (pwd.equals(rs.getString("member_pwd"))) {
					result = 1;
				} else { // 비밀번호가 틀린 경우
					result = -1;
				}
			} else { // 아이디가 없거나 탈퇴 회원인 경우
				result = -2;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return result;
	}

	// DB상에 인자로 넘어온 아이디에 해당하는 회원의 정보를 반환하느 메서드
	public MemberDTO getMember(String id) {
		MemberDTO dto = new MemberDTO();

		openConn();
		sql = "select * from jsp_member where member_id=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dto.setMember_id(rs.getString("member_id"));
				dto.setMember_pwd(rs.getString("member_pwd"));
				dto.setMember_name(rs.getString("member_name"));
				dto.setMember_nickname(rs.getString("member_nickname"));
				dto.setMember_zip1(rs.getString("member_zip1"));
				dto.setMember_zip2(rs.getString("member_zip2"));
				dto.setMember_addr1(rs.getString("member_addr1"));
				dto.setMember_addr2(rs.getString("member_addr2"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return dto;
	}

	// 회원가입시 중복 아이디체크 처리 관련메서드
	public int checkMemberId(String id) {
		int result = 0;
		try {
			openConn();

			sql = "select member_id from jsp_member where member_id =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = 1;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	}

	public ArrayList searchZipCode(String dong) {
		ArrayList zip = new ArrayList<>();

		try {
			openConn();
			sql = " select * from zipcode where dong like ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + dong + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String zipCode = rs.getString("zipcode");
				String sido = rs.getString("sido");
				String gugun = rs.getString("gugun");
				String dong1 = rs.getString("dong");
				String bunji = rs.getString("bunji");

				// 번지를 뺀 주소 저장
				String addr1 = sido + gugun + dong1;
				// 번지를 포함한 주소 저장
				String addr2 = sido + gugun + dong1 + bunji;

				// list에 레코드 혀태로 자장
				zip.add(zipCode + "," + addr1 + "," + addr2);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);

		}

		return zip;
	}

}
