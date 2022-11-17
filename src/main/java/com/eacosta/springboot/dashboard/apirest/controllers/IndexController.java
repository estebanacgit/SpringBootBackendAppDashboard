package com.eacosta.springboot.dashboard.apirest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.eacosta.springboot.dashboard.apirest.dao.UserDao;
import com.eacosta.springboot.dashboard.apirest.usuario.User;
import com.eacosta.springboot.dashboard.apirest.utils.JWTUtils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
public class IndexController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private JWTUtils jwtUtils;

	@GetMapping(value = "/api/users/{id}")
	public User getUser(@PathVariable Long id) {

		return null;
	}

	@GetMapping(value = "/api/users")
	public List<User> getUsers(@RequestHeader(value = "Authorization") String token) {

		if(!validateToken(token)) {
			return null;
		}
		return userDao.getUsers();

	}

	@DeleteMapping(value = "/api/users/{id}")
	public void userDelete(@RequestHeader(value = "Authorization") String token, 
			@PathVariable Long id) {
		
		if(!validateToken(token)) {
			return;
		}
		userDao.deleteUser(id);
	}

	@PostMapping(value = "/api/users")
	public void registerUser(@RequestBody User user) {

		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		String hash = argon2.hash(1, 1024, 1, user.getPassword());
		user.setPassword(hash);

		userDao.registerUser(user);
	}

	private boolean validateToken(String token) {
		String userId = jwtUtils.getKey(token);
		return userId != null;
	}

}
