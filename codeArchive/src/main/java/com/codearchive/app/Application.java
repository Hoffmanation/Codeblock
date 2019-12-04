package com.codearchive.app;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.codearchive.dao.BlogService;
import com.codearchive.dao.UserService;
import com.codearchive.entity.Language;
import com.codearchive.entity.User;

@SpringBootApplication
@ComponentScan({ "com.codearchive.conf", "com.codearchive.daoImpl", "com.codearchive.entity", "com.codearchive.rest" ,"com.codearchive.util" })
@EntityScan({ "com.codearchive.entity" })
@EnableJpaRepositories({ "com.codearchive.dao", "com.codearchive.daoImpl"})
public class Application  {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}



	}