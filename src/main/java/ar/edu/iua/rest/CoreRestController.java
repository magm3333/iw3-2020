package ar.edu.iua.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.business.UserBusiness;
import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.business.exception.NotFoundException;
import ar.edu.iua.model.User;

@RestController
public class CoreRestController extends BaseRestController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserBusiness userBusiness;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping(value = "/login-token", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> loginToken(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		try {

			User u = userBusiness.load(username);
			if (!passwordEncoder.matches(password, u.getPassword()))
				return new ResponseEntity<String>("BAD_PASSWORD", HttpStatus.UNAUTHORIZED);

			if (!u.isEnabled())
				return new ResponseEntity<String>("ACCOUNT_NOT_ENABLED", HttpStatus.UNAUTHORIZED);
			if (!u.isAccountNonLocked())
				return new ResponseEntity<String>("ACCOUNT_LOCKED", HttpStatus.UNAUTHORIZED);
			if (!u.isCredentialsNonExpired())
				return new ResponseEntity<String>("CREDENTIALS_EXPIRED", HttpStatus.UNAUTHORIZED);

			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(u, null,
					u.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
			return new ResponseEntity<String>(userToJson(getUserLogged()).get("authtoken").toString(), HttpStatus.OK);

		} catch (BusinessException e) {
			log.error(e.getMessage());
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<String>("BAD_ACCOUNT_NAME", HttpStatus.UNAUTHORIZED);
		}

	}

}
