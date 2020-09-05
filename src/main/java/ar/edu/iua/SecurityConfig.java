package ar.edu.iua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.edu.iua.auth.CustomTokenAuthenticationFilter;
import ar.edu.iua.authtoken.IAuthTokenBusiness;
import ar.edu.iua.business.UserBusiness;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserDetailsService persistenceUserDetailService;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(persistenceUserDetailService);
	}

	@Autowired
	private IAuthTokenBusiness authTokenBusiness;

	@Autowired
	private UserBusiness userBusiness;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http.csrf().disable()
			.authorizeRequests()
	        .antMatchers("/admin/**").hasRole("ADMIN")
	        .antMatchers("/anonymous*").anonymous()
	        .antMatchers("/login*").permitAll()
	        .antMatchers("/logout*").permitAll()
	        .anyRequest().authenticated().and()
	        .headers().frameOptions().disable();
		//@formatter:on
		http.httpBasic();

		http.addFilterAfter(new CustomTokenAuthenticationFilter(authTokenBusiness, userBusiness),
				UsernamePasswordAuthenticationFilter.class);

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

}
