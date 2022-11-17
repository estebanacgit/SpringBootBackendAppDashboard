package com.eacosta.springboot.dashboard.apirest.dao;

import java.util.List;

import com.eacosta.springboot.dashboard.apirest.usuario.User;

public interface UserDao {
	
	public List<User> getUsers();

	public void deleteUser(Long id);

	public void registerUser(User user);

	public User getUserWithCredentials(User user);

}
