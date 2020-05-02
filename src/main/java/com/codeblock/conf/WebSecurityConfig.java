package com.codeblock.conf;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * A Web security configuration class for determining web behaviour,Authorities and user roles
 * @author Hoffman
 *
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	/*
	 *Spring Dependency Injection 
	 */
    @Autowired
    private UserDetailsService userDetailsService;

    /*
     * Creates a {@link BCryptPasswordEncoder} Spring-Bean to be 
     * used when ever encryption  is need 
     * @return {@link BCryptPasswordEncoder}
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     *Main Web security configuration method 
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	  http
    	     .authorizeRequests()
    	     	//Permit all HTTP requests that trying to get a static resource (besides index.html) 
    	         .antMatchers("/resources/**", "/registration.html" ,"/codeblock/**" ,"/static/**").permitAll() 
    	         //Authenticate every HTTP request that trying to get to the main application page (index.html) by checking ROLE-USER
    	         .antMatchers("/index.html").hasRole("USER")
    	         .anyRequest().authenticated()
    	         .and()
    	       //Register a login page and a Success URL to be redirected to (upon successful user login) 
    	         .formLogin()
    	         .loginPage("/login.html").defaultSuccessUrl("/index.html")
    	         //Permit all HTTP requests for the .formLogin() section
    	         .permitAll()
    	         .and()
    	         .logout()
    	         //Register a custom logout filter listener
    	         .logoutRequestMatcher(new AntPathRequestMatcher("/codeblock/logout"))
    	         .addLogoutHandler(new CodeblockLogoutHandler())
    	         .logoutSuccessUrl("/codeblock/logout").permitAll()
    	         //Add custom filer {@link CsrfHeaderFilter} for managing CSRF-TOKEN right after Spring's own -  {@link CsrfFilter}
    	         .and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class); ;
    	  

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    
  @SuppressWarnings("unused")
private CsrfTokenRepository csrfTokenRepository() {
    	  HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    	  repository.setHeaderName("X-XSRF-TOKEN");
    	  return repository;
    	}
}