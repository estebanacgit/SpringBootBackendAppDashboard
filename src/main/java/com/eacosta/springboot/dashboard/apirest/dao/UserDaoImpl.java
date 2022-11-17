package com.eacosta.springboot.dashboard.apirest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eacosta.springboot.dashboard.apirest.usuario.User;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> getUsers() {
		String query = "FROM User";
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public void deleteUser(Long id) {
		User user = entityManager.find(User.class, id);
		entityManager.remove(user);

	}

	@Override
	public void registerUser(User user) {
		entityManager.merge(user);
	}

	@Override
	public User getUserWithCredentials(User user) {
		String query = "FROM User WHERE email = :email";
		@SuppressWarnings("unchecked")
		List<User> list = entityManager.createQuery(query).setParameter("email", user.getEmail()).getResultList();

		if (list.isEmpty()) {
			return null;
		}

		String passwordHashed = list.get(0).getPassword();

		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		if (argon2.verify(passwordHashed, user.getPassword())) {
			return list.get(0);
		}

		return null;

	}

}
