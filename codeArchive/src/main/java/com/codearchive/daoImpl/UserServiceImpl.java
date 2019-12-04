package com.codearchive.daoImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codearchive.dao.RoleRepository;
import com.codearchive.dao.UserRepository;
import com.codearchive.dao.UserService;
import com.codearchive.entity.Role;
import com.codearchive.entity.User;
import com.codearchive.exce.BlogException;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserRepository userDao;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public boolean createUser(User user) {
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
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<User> getUserById(UUID id) {
			if (!userDao.getUserById(id).isEmpty()) {
				return userDao.getUserById(id);
			}
		return null;
	}



	@Override
	public boolean logout() {
		return false;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean createUser(String name, String password, String password2) throws BlogException {
		User user = new User(name, password, password2);
		user.setPassword(user.getPassword());
		user.setRoles(new HashSet<>(roleRepository.findAll()));
		userDao.save(user);
		return true;
	}
}