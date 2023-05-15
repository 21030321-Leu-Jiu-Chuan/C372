/**
 * 
 * I declare that this code was written by me, 21030321. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Leu Jiu Chuan
 * Student ID: 21030321
 * Class: E63C
 * Date created: 2023-Jan-10 9:55:52 am 
 * 
 */

package e63c.leujiuchuan.GA;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 21030321
 *
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public MemberDetailsService memberDetailsService() {
		return new MemberDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(memberDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/categories/add","/categories/edit/*","/categories/save","/categories/delete/*",
				"/shirt/add","/shirt/edit/*","/shirt/save","/shirt/delete/*" ,
				"/members","/members/add","/members/edit/*","/members/save","/members/delete/*" 
				)
		
		.hasRole("ADMIN")
		.antMatchers("/").permitAll()
		.antMatchers("/bootstrap/*/*").permitAll()
		.antMatchers("/images/*").permitAll()
		.antMatchers("/shirt/{id}").permitAll()
		.antMatchers("/shirt").permitAll()
		.antMatchers("/uploads/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").permitAll()
		.and()
		.logout().logoutUrl("/logout").permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/403")
		;
		
		
	}
}
