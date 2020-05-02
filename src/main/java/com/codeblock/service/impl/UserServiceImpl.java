package com.codeblock.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codeblock.entity.Blog;
import com.codeblock.entity.Role;
import com.codeblock.entity.User;
import com.codeblock.handler.BlogException;
import com.codeblock.repository.RoleRepository;
import com.codeblock.repository.UserRepository;
import com.codeblock.service.UserService;

/**
 * A Service-Implementation class  for the {@link User} DAO-Layer service
 * Uses Spring {@link Transactional}
 * @author Hoffman
 *
 */
@Component
public class UserServiceImpl implements UserService {

	/**
	 * Spring Dependency Injection
	 */
	@Autowired
	private UserRepository userDao;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/**
	 * CRUD methods for the {@link User} entity
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean createUser(User user) throws BlogException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role newRole = new Role("ROLE_USER");
        roleRepository.save(newRole);
        Set<Role> roles = new HashSet<>();
        roles.add(newRole);
		user.setRoles(roles);
		userDao.save(user);
		return true;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<User> getUserById(UUID id) throws BlogException {
			if (!userDao.getUserById(id).isEmpty()) {
				return userDao.getUserById(id);
			}
		return null;
	}


	//Not in used (No B-L is need for now)
	@Override
	public boolean logout() {
		return false;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean createUser(String name, String password) throws BlogException {
		User user = new User(name, password);
		user.setPassword(user.getPassword());
		user.setRoles(new HashSet<>(roleRepository.findAll()));
		userDao.save(user);
		return true;
	}
}