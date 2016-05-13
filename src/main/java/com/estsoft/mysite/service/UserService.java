package com.estsoft.mysite.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estsoft.mysite.domain.User;
import com.estsoft.mysite.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	//@Autowired
	//private MailSender mailSender;
	
	public void join(User user){
		userRepository.insert(user);
		// 메일보내기
		// ..
	}
	
	public User login(User user){
		User authUser = userRepository.get(user);
		return authUser;
	}
	
	public User getUser(String email){
		User user = userRepository.get(email);
		return user;
	}
	
	
}
