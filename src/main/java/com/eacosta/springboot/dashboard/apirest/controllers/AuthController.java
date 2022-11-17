package com.eacosta.springboot.dashboard.apirest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eacosta.springboot.dashboard.apirest.dao.UserDao;
import com.eacosta.springboot.dashboard.apirest.usuario.User;
import com.eacosta.springboot.dashboard.apirest.utils.JWTUtils;

@RestController
public class AuthController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@PostMapping(value = "/api/login")
	public String loginUser(@RequestBody User user) {
		
		User userLogged = userDao.getUserWithCredentials(user);
		
		if(userLogged != null) {
			
			String tokenJwt = jwtUtils.create(String.valueOf(userLogged.getId()), userLogged.getEmail());
			

			return tokenJwt;
		}
		
		return "FAIL";
	}

}
