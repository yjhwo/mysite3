package com.estsoft.mysite.service;

import java.util.List;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.domain.GuestBook;
import com.estsoft.mysite.repository.GuestBookRepository;

@Service
@Transactional
public class GuestBookService {

	@Autowired
	private GuestBookRepository guestbookRepository;
	
	public List<GuestBook> getList(){
		return guestbookRepository.findAll();
	}

	public void add(GuestBook vo){
		guestbookRepository.insert(vo);
	}	
	
	public Boolean delete(int no, String password){	
		GuestBook guestbook = new GuestBook();
		guestbook.setNo((long) no);
		guestbook.setPasswd(password);
		
		return guestbookRepository.remove(guestbook);
	}
}
