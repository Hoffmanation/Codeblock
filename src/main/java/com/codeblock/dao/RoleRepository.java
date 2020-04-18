package com.codeblock.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.codeblock.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
