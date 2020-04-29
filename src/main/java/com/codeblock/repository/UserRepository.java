package com.codeblock.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.codeblock.entity.User;


@RepositoryRestResource(path = "/User", collectionResourceRel = "User")
public interface UserRepository extends JpaRepository<User,Long> {
	
	@RestResource(exported = false)
	@Query("SELECT u FROM User AS u WHERE u.username = :username")
	public  User findByUsername(@Param("username")String username);

	@RestResource(exported = false)
	@Query("SELECT u FROM User AS u WHERE u.username = :username AND u.password = :password")
	public boolean login(@Param("username")String name , @Param("password")String password);
	
	@RestResource(exported = false)
	@Query("SELECT u FROM User  AS u WHERE u.id = :id")
	public List<User> getUserById(@Param("id")UUID id);
	
	@RestResource(exported = false)
	@Query("SELECT u FROM User  AS u")
	public List<User> getAllUsers();
}
