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


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	  http
    	     .authorizeRequests()
    	         .antMatchers("/resources/**", "/registration.html" ,"/codeblock/**" ,"/static/**").permitAll() .antMatchers("/index.html").hasRole("USER")
    	         .anyRequest().authenticated()
    	         .and()
    	         .formLogin()
    	         .loginPage("/login.html").defaultSuccessUrl("/index.html")
    	         .permitAll()
    	         .and()
    	         .logout()
    	         .logoutRequestMatcher(new AntPathRequestMatcher("/codeblock/logout"))
    	         .addLogoutHandler(new CodeblockLogoutHandler())
    	         .logoutSuccessUrl("/codeblock/logout").permitAll().and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class); ;
    	  

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    
  private CsrfTokenRepository csrfTokenRepository() {
    	  HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    	  repository.setHeaderName("X-XSRF-TOKEN");
    	  return repository;
    	}
}