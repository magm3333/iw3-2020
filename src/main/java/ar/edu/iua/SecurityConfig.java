package ar.edu.iua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.edu.iua.auth.CustomTokenAuthenticationFilter;
import ar.edu.iua.auth.PersistenceUserDetailService;
import ar.edu.iua.authtoken.IAuthTokenBusiness;
import ar.edu.iua.business.IUserBusiness;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private PersistenceUserDetailService persistenceUserDetailService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(persistenceUserDetailService);
		// @formatter:off
		//auth.inMemoryAuthentication()
		//	.withUser("user").password( passwordEncoder().encode("123") ).roles("USER")
		//	.and()
		//	.withUser("admin").password( passwordEncoder().encode("1234") ).roles("ADMIN");
		// @formatter:on

	}

	@Autowired
	private IAuthTokenBusiness authTokenBusiness;

	@Autowired
	private IUserBusiness userBusiness;

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

		 	.antMatchers("/index.html").permitAll()
			.antMatchers("/favicon.*").permitAll()	   
			.antMatchers("/ui/**").permitAll()

			.anyRequest().authenticated();
			// @formatter:on

		http.addFilterAfter(new CustomTokenAuthenticationFilter(authTokenBusiness, userBusiness),
				UsernamePasswordAuthenticationFilter.class);

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Cliente (Brower) ----> | App Server (tomcat) --> App --> (Filtro1 , Filtro2,
		// FiltroN) (Spring)REST-->Capa Negocio --> Persistencia --> Modelo

		// .httpBasic();
		/*
		 * .formLogin() .loginPage("/login.html") .loginProcessingUrl("/dologin")
		 * .defaultSuccessUrl("/index.html",true) .failureUrl("/login.html?error=true")
		 * .and() .logout() .deleteCookies("JSESSIONID","rmiw3")
		 * .logoutSuccessUrl("/login.html") .and()
		 * .rememberMe().rememberMeParameter("rememberme-iw3").rememberMeCookieName(
		 * "rmiw3") //.alwaysRemember(true) ;
		 */

		// @formatter:on
	}
}
