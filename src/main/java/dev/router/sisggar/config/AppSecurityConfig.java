package dev.router.sisggar.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.router.sisggar.exception.api.JWTAccessDeniedHandler;
import dev.router.sisggar.exception.api.JWTAuthenticationEntryPoint;
import dev.router.sisggar.filter.JWTTokenAuthorizationFilter;
import dev.router.sisggar.service.auth.UserService;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("UserDetailsService")
	private UserService userDetailsService;
	
	@Autowired
	private JWTTokenAuthorizationFilter jwtTokenAuthorizationFilter;
	
	@Autowired
	private JWTAccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private JWTAuthenticationEntryPoint authenticationEntryPoint;
	
	private static final String[] PUBLIC_URLS = {"/api/auth/v1/login"};

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests().antMatchers("/*").permitAll()
		.anyRequest().authenticated().and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
		.authenticationEntryPoint(this.authenticationEntryPoint)
		.and().addFilterBefore(jwtTokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(this.userDetailsService).passwordEncoder(this.bCryptPasswordEncoder());
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
