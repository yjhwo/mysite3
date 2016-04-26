package com.estsoft.mysite.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.exception.GuestbookGetListException;
import com.estsoft.mysite.vo.GuestBookVO;

@Repository
public class GuestBookDAO {

	@Autowired
	private DataSource dataSource;	// dataSource객체가 여기로 주입됨, 직접 연결하는 게 아니고 connection pool에 있는 8개 중 아무거나 가져오는 것(DataSource마음, Queue)
	
	@Autowired
    private SqlSession sqlSession;
	
//	public int delete(GuestBookVO vo) {			
//	int countDeleted = sqlSession.delete("guestbook.delete", vo);	// 삭제된 개수
//	return countDeleted;
//	
//}
	public int delete(int no, String password) {	//여러 파라미터 값을 이용할 경우엔 Map으로 묶어준다.
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no",no);
		map.put("password",password);
		
		int countDeleted = sqlSession.delete("guestbook.delete2", map);
		return countDeleted;
		
	}

	public GuestBookVO get(Long no) {
		
		GuestBookVO vo = sqlSession.selectOne("guestbook.selectByNo", no);
		return vo;
		
	}

	// long값 return하게..
	public Long insert(GuestBookVO vo) {
		
		int count = sqlSession.insert("guestbook.insert", vo);
		
		System.out.println(count+":"+vo.getNo());		// primary키가 아닌 그냥 개수
		return 0L;
	}

	public List<GuestBookVO> getList() {
		
		List<GuestBookVO> list = sqlSession.selectList("guestbook.selectList");	//query ID를 적어줌, 여러 xml에서 ID가 같은 경우 namespace로 구분(ex. guestbook.selectList)
		return list;
	}

	// 오버로딩
	public List<GuestBookVO> getList(int page) throws GuestbookGetListException{
		
		List<GuestBookVO> list = new ArrayList<GuestBookVO>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();

			String sql = "SELECT no,name,DATE_FORMAT(reg_date,'%Y-%m-%d %h:%i:%s'),message from guestbook "
					+ "ORDER BY reg_date desc LIMIT " + (page - 1) * 5 + ",5";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String reg_date = rs.getString(3);
				String message = rs.getString(4);

				GuestBookVO vo = new GuestBookVO(no, name, reg_date, message);
				list.add(vo);
			}

		} catch (SQLException ex) {
			throw new GuestbookGetListException();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return list;
	}

}
