package com.study.springboot.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.study.springboot.oauth2.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	public AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/css/**", "/js/**", "/img/**", "/webjars/**").permitAll()
			.antMatchers("/security/**","/classimages/**","/images/**").permitAll()
			.antMatchers("/mypage/**", "/view/**" ).hasAnyRole("USER")
//			.antMatchers("/member/**").hasAnyRole("USER", "ADMIN")
//			.antMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated();
		
		http.formLogin().loginPage("/security/loginpage")
						.loginProcessingUrl("/security/j_spring_security_check")
						.failureUrl("/security/loginpage?error")
						.failureHandler(authenticationFailureHandler)
						.usernameParameter("j_username")
						.passwordParameter("j_password")
						.defaultSuccessUrl("/security/mainpage")
						.permitAll();
		
		http.logout().logoutUrl("/logout")  //default
					.logoutSuccessUrl("/")
					.deleteCookies("JSESSIONID")
					.invalidateHttpSession(true)
					.permitAll();
		
		http.csrf().disable()
		.headers().frameOptions().disable() // header 가 충돌 방지
        .and()
        .oauth2Login()
            .userInfoEndpoint()
                .userService(customOAuth2UserService)
                ;
		
	}
	
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select userid as userid, userpassword, enabled from users where userid=?")
			.authoritiesByUsernameQuery("select userid as userid, authority from users where userid=?")
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
