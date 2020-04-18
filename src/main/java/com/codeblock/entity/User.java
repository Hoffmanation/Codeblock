package com.codeblock.entity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")
public class User {
    private UUID userId = UUID.randomUUID();
    private String username;
    private String password;
    private String passwordConfirm;
    private Set<Role> roles;

    
    
    public User( String username, String password, String passwordConfirm) {
		super();
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	@Id
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", passwordConfirm="
				+ passwordConfirm + ", roles=" + roles + "]";
	}
    
    
}
