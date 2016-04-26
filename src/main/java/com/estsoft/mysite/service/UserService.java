package com.estsoft.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.dao.UserDAO;
import com.estsoft.mysite.vo.UserVO;

// 달아줘야 root-application-context에 생성됨
@Service
public class UserService {
	
	@Autowired
	private UserDAO userDao;
	
	//@Autowired
	//private MailSender mailSender;
	
	public void join(UserVO vo){
		userDao.insert(vo);
		// 메일보내기
		// ..
	}
	
	public UserVO login(UserVO vo){
		UserVO authUser = userDao.get(vo);
		return authUser;
	}
	
	public UserVO getUser(String email){
		UserVO vo = userDao.get(email);
		return vo;
	}
	
	
}
