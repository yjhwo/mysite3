package com.estsoft.mysite.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.db.DBConnection;
import com.estsoft.mysite.vo.BoardVO;

@Repository
public class BoardDAO {
	@Autowired
	private DBConnection dbConnection;

	public int getTotalCount(String kwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try{
			conn = dbConnection.getConnection();
			String sql = "select count(*) from board "
						+"where title like '%"+kwd+"%' or content like '%"+kwd+"%'";
	
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
			
		}catch( SQLException ex ) {
			System.out.println( "error:" + ex );
		} finally {
			try{
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			}catch( SQLException ex ) {
				ex.printStackTrace();
			}
		}		
		
		return count;
	}

	public int getTotalCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try{
			conn = dbConnection.getConnection();
			String sql = "select count(*) from board";
	
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				count = rs.getInt(1);
			}
			
		}catch( SQLException ex ) {
			System.out.println( "error:" + ex );
		} finally {
			try{
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			}catch( SQLException ex ) {
				ex.printStackTrace();
			}
		}		
		
		return count;
	}
	
	public void updateGroupOrder(BoardVO vo) {			// 같은 그룹중에 order no값 바꾸는 것
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			conn = dbConnection.getConnection();
			
			String sql = "UPDATE board SET order_no = order_no + 1" +
						" WHERE group_no = ? AND order_no >= ?";
			
			pstmt = conn.prepareStatement( sql );
			pstmt.setLong( 1, vo.getGroup_no() );
			pstmt.setLong( 2, vo.getOrder_no() );
			
			pstmt.executeUpdate();
		} catch( SQLException ex ) {
			System.out.println( "error:" + ex );
		} finally {
			try{
				if( pstmt != null ) {
					pstmt.close();
				}
				if( conn != null ) {
					conn.close();
				}
			}catch( SQLException ex ) {
				ex.printStackTrace();
			}
		}		

	}
	
	public List<BoardVO> search(String kwd, int page) {
		List<BoardVO> list = new ArrayList<BoardVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbConnection.getConnection();
			String sql = "SELECT b.no, b.title, b.content, u.no as user_no, u.name, b.viewCount, DATE_FORMAT(b.reg_date,'%Y-%m-%d %h:%i:%s') "
					+ "FROM board b, user u where b.user_no = u.no AND (b.title LIKE ? OR b.content LIKE ?) "
					+ "ORDER BY b.group_no DESC, b.order_no ASC "
					+ "limit ?, 5";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + kwd + "%");
			pstmt.setString(2, "%" + kwd + "%");
			pstmt.setInt(3, (page-1)*5);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Long user_no = rs.getLong(4);
				String user_name = rs.getString(5);
				Long viewCount = rs.getLong(6);
				String reg_date = rs.getString(7);

				BoardVO vo = new BoardVO(no, title, content, user_no, user_name, viewCount, reg_date);
				list.add(vo);
			}

		} catch (SQLException ex) {
			System.out.println("error:" + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return list;
	}

	public void modify(BoardVO vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbConnection.getConnection();

			String sql = "UPDATE board SET title = ?, content=? WHERE no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println("error: " + ex);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateCount(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbConnection.getConnection();

			String sql = "UPDATE board SET viewCount = viewCount+1 WHERE no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println("error: " + ex);
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public int delete(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = -1;

		try {
			conn = dbConnection.getConnection();

			String sql = "DELETE FROM board WHERE no=?";
			pstmt = conn.prepareStatement(sql);

			if (no == 0) {
				System.out.println("번호가 잘 못 들어왔습니다.");
			}

			pstmt.setLong(1, no);

			cnt = pstmt.executeUpdate(); // 1 이상 성공

		} catch (SQLException ex) {
			System.out.println("error:" + ex);
			ex.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return cnt;
	}

	public void insert(BoardVO vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbConnection.getConnection();

			if (null == vo.getGroup_no()) {// 새 글인 경우
				String sql = "insert into board values(null,?,?,now(),0, "
						+ "(select ifnull(max(group_no),0)+1 from board as b),1,0,?)";

				pstmt = conn.prepareStatement(sql);

				System.out.println("Board Insert" + vo.getTitle() + ":" + vo.getContent() + ":" + vo.getUser_no());

				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setLong(3, vo.getUser_no());
				
			} else { // 답글 등록
				
				String sql = "insert into board values(null,?,?,now(),0,?,?,?,?)";

				pstmt = conn.prepareStatement(sql);

				System.out.println("Board Insert" + vo.getTitle() + ":" + vo.getContent() + ":" + vo.getUser_no());

				pstmt.setString(1, vo.getTitle());
				pstmt.setString(2, vo.getContent());
				pstmt.setLong(3, vo.getGroup_no());
				pstmt.setLong(4, vo.getOrder_no());
				pstmt.setLong(5, vo.getDepth());
				pstmt.setLong(6, vo.getUser_no());
			}

			pstmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println("error:" + ex);
			ex.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public BoardVO getReplyInform(Long no) { // 게시글 번호값을 이용해 group_no, order_no,
												// depth가져오기
		BoardVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbConnection.getConnection();
			String sql = "SELECT group_no, order_no, depth FROM board WHERE no = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				Long group_no = rs.getLong(1);
				Long order_no = rs.getLong(2);
				Long depth = rs.getLong(3);

				vo = new BoardVO(group_no, order_no, depth);
			}

		} catch (SQLException ex) {
			System.out.println("error:" + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return vo;
	}

	public BoardVO get(Long no) { // 게시글 번호 얻어오기
		BoardVO vo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbConnection.getConnection();
			String sql = "select title, content from board WHERE no = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String title = rs.getString(1);
				String content = rs.getString(2);

				vo = new BoardVO(title, content);
			}

		} catch (SQLException ex) {
			System.out.println("error:" + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return vo;
	}

	public List<BoardVO> getList(int page) {
		List<BoardVO> list = new ArrayList<BoardVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbConnection.getConnection();
			

			String sql = "SELECT b.no, b.title, b.content, u.no as user_no, u.name, b.viewCount, DATE_FORMAT(b.reg_date,'%Y-%m-%d %h:%i:%s'), "
					+ "b.group_no, b.order_no, b.depth "
					+ "from board b, user u where b.user_no = u.no " + "ORDER BY b.group_no DESC, b.order_no ASC "
					+ "limit ?, 5";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (page-1)*5);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Long user_no = rs.getLong(4);
				String user_name = rs.getString(5);
				Long viewCount = rs.getLong(6);
				String reg_date = rs.getString(7);
				Long group_no = rs.getLong(8);
				Long order_no = rs.getLong(9);
				Long depth = rs.getLong(10);
				
				
				BoardVO vo = new BoardVO(no, title, content, reg_date, viewCount, group_no, order_no, depth, user_no, user_name);
				list.add(vo);
			}

		} catch (SQLException ex) {
			System.out.println("error:" + ex);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return list;
	}


}
