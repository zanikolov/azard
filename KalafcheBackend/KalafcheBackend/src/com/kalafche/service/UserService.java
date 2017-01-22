package com.kalafche.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kalafche.dao.AuthRoleDao;
import com.kalafche.dao.UserDao;
import com.kalafche.model.AuthRole;
import com.kalafche.model.User;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	AuthRoleDao authRoleDao;
	
	@Autowired
	UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = userDao.getUserByUsername(username);
//		User user = new User();
//		user.setEnabled(true);
//		user.setId(1);
//		user.setPassword("test");
//		user.setUsername("test");
//		if (user == null) {
//			throw new UsernameNotFoundException("User with name " + username + " cannot be found!");
//		}
		
		//List<AuthRole> roles = authRoleDao.getAllRolesForUser(user.getId());
		
		List<AuthRole> roles = new ArrayList<AuthRole>();
		AuthRole role = new AuthRole();
		role.setId(1);
		role.setName("ROLE_ADMIN");
		roles.add(role);
		
		if (roles.isEmpty()) {
			throw new UsernameNotFoundException("User with name " + username + " does not have any roles!");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (AuthRole test : roles) {
			authorities.add(new SimpleGrantedAuthority(test.getName()));
		}
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);

		return userDetails;
	}

}
