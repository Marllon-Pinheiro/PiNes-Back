package br.com.pines.dev.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import br.com.pines.dev.UserService;

@Configuration
@EnableAuthorizationServer
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService user;

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth
		.userDetailsService(user)
		.passwordEncoder(passwordEncoder());
		
}
   
   @Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
   }
   
   @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors()
		.and()		
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http
		  	.authorizeHttpRequests()
		  	.antMatchers("/products/**").permitAll()
		  	.antMatchers("/users/**").permitAll()
		  	.antMatchers("/oauth/token/**").permitAll()
		  	.anyRequest().authenticated();
		  	
	}
   
   public PasswordEncoder passwordEncoder() {
	   return new BCryptPasswordEncoder();
   }
		
}
   
