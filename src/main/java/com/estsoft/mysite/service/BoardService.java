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
	
	public List<BoardVO> search(String kwd, int page){
		return boardDao.search(kwd, page);
	}
	
	public int getTotalCount(){
		return boardDao.getTotalCount();
	}
	
	public int getTotalCount(String kwd){
		return boardDao.getTotalCount(kwd);
	}
	
	public BoardVO get(Long no){
		return boardDao.get(no);
	}
	
	public void updateCount(Long no){
		boardDao.updateCount(no);
	}
	
	public void insert(BoardVO vo){
		boardDao.insert(vo);
	}
	
	public BoardVO getReplyInform(Long no){
		return boardDao.getReplyInform(no);
	}
	
	public void updateGroupOrder(BoardVO vo){
		boardDao.updateGroupOrder(vo);
	}
	
	public void modify(BoardVO vo){
		boardDao.modify(vo);
	}
	
	public int delete(int no){
		return boardDao.delete(no);
	}
}
