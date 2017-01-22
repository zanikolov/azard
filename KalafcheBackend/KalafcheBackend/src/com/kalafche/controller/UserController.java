package com.kalafche.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalafche.dao.UserDao;
import com.kalafche.model.User;

@CrossOrigin
@RestController
@RequestMapping({ "/service/user" })
public class UserController {
	@Autowired
	private UserDao userDao;

	@RequestMapping(value = { "/getUserInfo" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET }, params = { "username" })
	public User getUserInfo(@RequestParam(value = "username") String username) {
		User userInfo = this.userDao.getUserInfo(username);

		return userInfo;
	}
}
