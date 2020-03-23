package com.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;

	/*
	 * 싱글톤 객체 만들기 1. 싱글톤 객체를 만들때는 우선적으로 접근지정자는 private로 선언한다. 2. 정적(static) 멤버로 선언을
	 * 한다.
	 */

	private static BoardDAO instance = new BoardDAO();

	/*
	 * 3. 기본생성자는 외부에서 접근이 되지 않도록 해야한다. 외부에서 new를 사용하지 못하게 하는 접근 기법
	 */

	private BoardDAO() {
	}

	// 4. 생성자 대신에 싱글톤 객체를 retrun 해 주는 getInstance() 메서드를 만들어 준다.
	public static BoardDAO getInstance() {
		if (instance == null) {
			instance = new BoardDAO();
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

	// board 테이블의 전체 게시물의 수를 조회하는 메서드
	public int getListCount() {
		int count = 0;

		try {
			openConn();
			sql = "select count(*) from board1";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			closeConn(rs, pstmt, con);

		}

		return count;
	}

	// board1 테이블에서 게시물을 가져오는 메서드
	public List<BoardDTO> getBoardList(int page, int rowsize) {
		List<BoardDTO> list = new ArrayList<BoardDTO>();

		// 해당 페이지의 시작 번호
		int startNo = (page * rowsize) - (rowsize - 1);
		// 해당 페이지의 끝 번호
		int endNo = page * rowsize;

		try {
			openConn();
			// row_number() over : 특정 컬럼을 기준으로 행 번호를 부여할 때 사용하는 함수
			// 1. row_number() over(order by board_no desc) rnum : board_no를 내림차수로 정렬한 결과를
			// 순차적으로 rnum으로 번호를 저장
			// 2. (select p.*, 위의 1번 from board1 p : board1 테이블을 p로 별칭을 주고 거기에서 board1에 위의
			// 1번을 추가로 뽑아온다
			// 3. where rnum>=? and rnum<=? : 각각 startNo, endNo

			sql = "select * from " + "(select p.*, row_number() " + "over(order by board_no desc) rnum "
					+ "from board1 p) " + "where rnum >=? and rnum<=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBoard_no(rs.getInt("board_no"));
				dto.setBoard_writer(rs.getString("board_writer"));
				dto.setBoard_title(rs.getString("board_title"));
				dto.setBoard_cont(rs.getString("board_cont"));
				dto.setBoard_pwd(rs.getString("board_pwd"));
				dto.setBoard_hit(rs.getInt("board_hit"));
				dto.setBoard_regdate(rs.getString("board_regdate"));
				list.add(dto);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	} // getBoardList() 메서드 end

	public int boardWrite(BoardDTO dto) {
		int result = 0;
		int count = 0;

		try {
			openConn();
			con.setAutoCommit(false);
			sql = "select max(board_no) from board1";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1) + 1;
			} else {
				count = 1;
			}

			sql = "insert into board1 values(?,?,?,?,?,default,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setString(2, dto.getBoard_writer());
			pstmt.setString(3, dto.getBoard_title());
			pstmt.setString(4, dto.getBoard_cont());
			pstmt.setString(5, dto.getBoard_pwd());
			result = pstmt.executeUpdate();
			con.commit();

		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	} // boardWrite() 메서드 end

	public void boardHit(int no) {

		try {
			openConn();
			sql = "update board1 set board_hit = board_hit + 1 where board_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

	} // boardHit() 메서드 end

	// board1 테이블의 게시물 번호에 해당하는 글을 상세 조회하는 메서드
	public BoardDTO getCont(int no) {

		BoardDTO dto = new BoardDTO();

		try {
			openConn();
			sql = "select * from board1 where board_no = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setBoard_no(rs.getInt("board_no"));
				dto.setBoard_writer(rs.getString("board_writer"));
				dto.setBoard_title(rs.getString("board_title"));
				dto.setBoard_cont(rs.getString("board_cont"));
				dto.setBoard_pwd(rs.getString("board_pwd"));
				dto.setBoard_hit(rs.getInt("board_hit"));
				dto.setBoard_regdate(rs.getString("board_regdate"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;
	} // getCont() 메서드 end

	public int boardEdit(BoardDTO dto) {
		int result = 0;

		try {
			openConn();

			sql = "update board1 set board_title=?, board_cont=?, board_regdate=sysdate where board_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getBoard_title());
			pstmt.setString(2, dto.getBoard_cont());
			pstmt.setInt(3, dto.getBoard_no());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;

	}

	public int boardDel(int no, String pwd) {
		int result = 0;
		String pcheck = null;

		try {
			openConn();
			con.setAutoCommit(false);
			sql = "select * from board1 where board_no=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				pcheck = rs.getString("board_pwd");
			}

			if (pcheck.equals(pwd)) {
				sql = "delete from board1 where board_no=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, no);
				result = pstmt.executeUpdate();
				con.commit();
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return result;
	}

	// board1 테이블에서 검색어에 해당하는 레코드의 수를 조회하는 메서드
	public int searchListCount(String field, String name) {
		int count = 0;
		try {
			openConn();
			if (field.equals("title")) {
				sql = "select count(*) from board1 where board_title like ?";

			} else if (field.equals("cont")) {
				sql = "select count(*) from board1 where board_cont like ?";

			} else if (field.equals("title_cont")) {
				sql = "select count(*) from board1 where board_title like ? or board_cont like ?";

			} else if (field.equals("writer")) {
				sql = "select count(*) from board1 where board_writer like ?";

			}
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + name + "%");
			if (field.equals("title_cont"))
				pstmt.setString(2, "%" + name + "%");

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return count;
	}

	// board1 테이블에서 검색한 내용을 가지고 페이징 처리를 하는 메서드
	public List<BoardDTO> boardSearch(String field, String name, int page, int rowsize) {
		List<BoardDTO> search = new ArrayList<BoardDTO>();

		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = page * rowsize;

		try {
			openConn();
			if (field.equals("title")) {

				sql = "select * from " + "(select p.*, row_number() " + "over(order by board_no desc) rnum "
						+ "from board1 p where board_title like ?) " + "where rnum >=? and rnum<=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%" + name + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

			} else if (field.equals("cont")) {
				sql = "select * from " + "(select p.*, row_number() " + "over(order by board_no desc) rnum "
						+ "from board1 p where board_cont like ?) " + "where rnum >=? and rnum<=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%" + name + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

			} else if (field.equals("title_cont")) {
				sql = "select * from " + "(select p.*, row_number() " + "over(order by board_no desc) rnum "
						+ "from board1 p where board_title like ? or board_cont like ?) " + "where rnum >=? and rnum<=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%" + name + "%");
				pstmt.setString(2, "%" + name + "%");
				pstmt.setInt(3, startNo);
				pstmt.setInt(4, endNo);

			} else if (field.equals("writer")) {
				sql = "select * from " + "(select p.*, row_number() " + "over(order by board_no desc) rnum "
						+ "from board1 p where board_writer like ?) " + "where rnum >=? and rnum<=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%" + name + "%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);

			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardDTO dto = new BoardDTO();
				dto.setBoard_no(rs.getInt("board_no"));
				dto.setBoard_writer(rs.getString("board_writer"));
				dto.setBoard_title(rs.getString("board_title"));
				dto.setBoard_cont(rs.getString("board_cont"));
				dto.setBoard_pwd(rs.getString("board_pwd"));
				dto.setBoard_hit(rs.getInt("board_hit"));
				dto.setBoard_regdate(rs.getString("board_regdate"));
				search.add(dto);

				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return search;
	}

}
