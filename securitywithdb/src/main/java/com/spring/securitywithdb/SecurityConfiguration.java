package com.spring.securitywithdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.securitywithdb.repository.UserRepository;
import com.spring.securitywithdb.service.UserDetailsServiceImpl;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
@EnableJpaRepositories(basePackageClasses=UserRepository.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("**/getAllItems").authenticated()
		.anyRequest().permitAll()
		.and().formLogin().permitAll();
	}
	
	private PasswordEncoder getPasswordEncoder(){
		return new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence charSequence, String str) {
				
				return true;
			}
			
			@Override
			public String encode(CharSequence charSequence) {
				
				return charSequence.toString();
			}
		};
	}
}
