package com.kalafche.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kalafche.dao.UserDao;
import com.kalafche.model.User;

@Service
public class AuthenticationService {

	@Autowired
	UserDao userDao;
	
	public User getPrincipal() {
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userDao.getUserByUsername(currentUser);
		
		return user;
	}
}
