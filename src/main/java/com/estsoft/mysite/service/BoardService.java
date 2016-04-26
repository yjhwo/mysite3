package com.estsoft.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.BoardDAO;
import com.estsoft.mysite.vo.BoardVO;

@Service
public class BoardService {
	
	@Autowired
	private BoardDAO boardDao;
	
	public List<BoardVO> getList(int page){
		return boardDao.getList(page);
	}
	
	public int getTotalCount(){
		return boardDao.getTotalCount();
	}
	
	public BoardVO get(Long no){
		return boardDao.get(no);
	}
	
	public void updateCount(Long no){
		boardDao.updateCount(no);
	}
	
}
