package com.estsoft.mysite.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estsoft.mysite.vo.BoardVO;

@Repository
public class BoardDAO {

	@Autowired
	private DataSource dataSource;	
	
	@Autowired
	private SqlSession sqlSession;
	
	public int getTotalCount(String keyword) {		
		String kwd = "%"+keyword+"%";
		return sqlSession.selectOne("board.getTotalCount2", kwd);
	}

	public int getTotalCount() {
		return sqlSession.selectOne("board.getTotalCount");
	}
	
	public void updateGroupOrder(BoardVO vo) {			// 같은 그룹중에 order no값 바꾸는 것
		sqlSession.update("board.updateGroupOrder", vo);
	}
	
	public List<BoardVO> search(String keyword, int p) {
		String kwd = "%"+keyword+"%";
		int page = (p-1)*5;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kwd", kwd);
		map.put("page", page);
		
		List<BoardVO> list = sqlSession.selectList("board.search", map);
		return list;
	}

	public void modify(BoardVO vo) {
		sqlSession.update("board.modify", vo);
	}

	public void updateCount(Long no) {
		sqlSession.update("board.updateCount", no);
	}

	public int delete(int no) {
		return sqlSession.delete("board.delete", no);
	}

	public void insert(BoardVO vo) {
		sqlSession.insert("board.insert", vo);
	}

	public BoardVO getReplyInform(Long no) { // 게시글 번호값을 이용해 group_no, order_no,
												// depth가져오기
		return sqlSession.selectOne("board.getReplyInform", no); 
	}

	public BoardVO get(Long no) { // 게시글 번호 얻어오기		
		return sqlSession.selectOne("board.getNo", no);
	}

	public List<BoardVO> getList(int p) {
		
		int page = (p-1)*5;
		return sqlSession.selectList("board.getList", page);
	}


}
