package com.codearchive.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.codearchive.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
