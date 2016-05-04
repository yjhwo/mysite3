package com.estsoft.mysite.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

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
		System.out.println("GuestBookDAO delete_"+no+password);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("no",no);
		map.put("password",password);				// map에 있는 이름으로 참조하기 때문에 guestbook.xml에서 사용할 때 이 이름으로 적어줘야한다!!
		
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
		
		if(count==0){
			return 0L;
		}
		System.out.println(count+":"+vo.getNo());		// primary키가 아닌 그냥 개수
		
		/*
		 * 파라미터로 넘어온 DTO(혹은 VO)객체에 auto_increment 값을 구해서 저장해야 하는 property를 지정해주면
		 * 쿼리가 수행된 후 파라미터로 넘어온 DTO객체에 값이 저장되게 됩니다.
		 */
		return vo.getNo();
	}

	public List<GuestBookVO> getList() {		
		List<GuestBookVO> list = sqlSession.selectList("guestbook.selectList");	//query ID를 적어줌, 여러 xml에서 ID가 같은 경우 namespace로 구분(ex. guestbook.selectList)
		return list;
	}

	// 오버로딩
	public List<GuestBookVO> getList(int page) throws GuestbookGetListException{
		int p = (page - 1) * 5 + 5;
		List<GuestBookVO> list = sqlSession.selectList("guestbook.selectAjaxList", p);
		
		return list;
	}

}
