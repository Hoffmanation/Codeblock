package com.codeblock.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * This Entity class will be use to store the user-role information in the DB that will be validate and authenticate by Spring-Security
 * @author Hoffman
 *
 */
@Entity
@Table(name = "role")
public class Role {
    private Long id;
    private String name;
    private Set<User> users;

 public Role() {
	// TODO Auto-generated constructor stub
}

 
	public Role(String name) {
	super();
	this.name = name;

}


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
