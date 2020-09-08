package ar.edu.iua;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth.inMemoryAuthentication()
			.withUser("user").password( passwordEncoder().encode("123") ).roles("USER")
			.and()
			.withUser("admin").password( passwordEncoder().encode("1234") ).roles("ADMIN");
		// @formatter:on

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/anonymous*").anonymous()
			.antMatchers("/login*").permitAll()
			.antMatchers("/logout*").permitAll()
			.anyRequest().authenticated()
			.and()
			//.httpBasic();
			.formLogin()
			.loginPage("/login.html")
			.loginProcessingUrl("/dologin")
			.defaultSuccessUrl("/index.html",true)
			.failureUrl("/login.html?error=true")
			.and()
			.logout()
			.deleteCookies("JSESSIONID","rmiw3")
			.logoutSuccessUrl("/login.html")
			.and()
			.rememberMe().rememberMeParameter("rememberme-iw3").rememberMeCookieName("rmiw3")
			//.alwaysRemember(true)
			;
			
		// @formatter:on
	}
}
