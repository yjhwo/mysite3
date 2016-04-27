package com.estsoft.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.estsoft.mysite.dao.GuestBookDAO;
import com.estsoft.mysite.exception.GuestbookGetListException;
import com.estsoft.mysite.vo.GuestBookVO;
import com.estsoft.mysite.vo.UserVO;

@Service
public class GuestBookService {

	@Autowired
	private GuestBookDAO guestbookDao;
	
	public List<GuestBookVO> getList(){
		return guestbookDao.getList();
	}
	
	public List<GuestBookVO> getList(int page) throws Exception{	// ajax_list
		return guestbookDao.getList(page);
	}
	
	public Long add(GuestBookVO vo){
		return guestbookDao.insert(vo);
	}
	
	public GuestBookVO get(Long no){
		return guestbookDao.get(no);
	}
	
	
	public int delete(int no, String password){						// return값이 0보다 크면 성공, 아니면 실패
		return guestbookDao.delete(no, password);
	}
}
