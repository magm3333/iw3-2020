package ar.edu.iua.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.iua.business.IUserBusiness;
import ar.edu.iua.business.exception.BusinessException;
import ar.edu.iua.model.User;

//Admin Qmp0MEZBSTNBQkd2Njlob2Z5Tm9Vdz09OkN5Rjl3bGdvMTQ0ZWRCVHFGanRxM0E9PQ
//User WWpTS1paUzlTYURRODVVTzRrRm9oUT09OkxubGNDSEw1a1BkdFErYVNWOUlobVE9PQ
@RestController
@RequestMapping(Constantes.URL_AUTH)
public class AutorizacionesRestController extends BaseRestController {

	// curl --location --request GET 'localhost:8080/api/v1/auth/admin' -v \
	// --header 'xauthtoken:
	// d2ZYcHlJSnRseEw1QXViMlZJNytGQT09OlJwT3VEQ0Nqd2laRE0vRnd5SG50Y2c9PQ'
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> onlyAdmin() {
		return new ResponseEntity<String>("Servicio admin", HttpStatus.OK);
	}
	// curl --location --request GET 'localhost:8080/api/v1/auth/user' -v \
	// --header 'xauthtoken:
	// cnAvOEhSZHM0bVhvWDYwSXR4ZTQ3Zz09OnRKRFEyL0wzcUNtT1JxRlBFb2hyeVE9PQ'

	@GetMapping("/user")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<String> onlyUser() {
		return new ResponseEntity<String>("Servicio user", HttpStatus.OK);
	}

	@GetMapping("/adminoruser")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<String> adminOrUser() {
		return new ResponseEntity<String>("Servicio admin or user", HttpStatus.OK);
	}

	// curl --location --request GET
	// 'localhost:8080/api/v1/auth/misroles?username=magm' -v \
	// --header 'xauthtoken:
	// Qmp0MEZBSTNBQkd2Njlob2Z5Tm9Vdz09OkN5Rjl3bGdvMTQ0ZWRCVHFGanRxM0E9PQ'

	// @PreAuthorize permite acceder a los parámetros de entrada
	@PreAuthorize("#username == authentication.principal.username")
	@GetMapping("/misroles")
	public ResponseEntity<String> getMyRoles(@RequestParam("username") String username) {
		return new ResponseEntity<String>(getUserLogged().getAuthorities().toString(), HttpStatus.OK);
	}

	@GetMapping("/variable")
	// @PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> onlyAdminProgramado(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_ADMIN")) {
			return new ResponseEntity<String>("Servicio dinámico para ADMIN", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Servicio dinámico para USER", HttpStatus.OK);
		}

	}
	
	//curl --location --request GET 'localhost:8080/api/v1/auth/mifulldata?username=magm' -v \
	//--header 'xauthtoken: WWpTS1paUzlTYURRODVVTzRrRm9oUT09OkxubGNDSEw1a1BkdFErYVNWOUlobVE9PQ'


	// @PostAuthorize permite acceder al resultado
	@PostAuthorize("returnObject.username == #username")
	@GetMapping("/mifulldata")
	public User postAuthorize(@RequestParam("username") String username) {
		return getUserLogged();
	}
	

	@PostAuthorize("returnObject.body.username == #username")
	@GetMapping("/mifulldata-re")
	public ResponseEntity<User> postAuthorizeRE(@RequestParam("username")  String username) {
		return new ResponseEntity<User>(getUserLogged(), HttpStatus.OK);
	}
	
	// Excelente ejemplo de uso customizado de procesador de expresiones de seguridad en spring: 
	// https://www.baeldung.com/spring-security-create-new-custom-security-expression
	

	@Autowired
	private IUserBusiness userBusiness;
	
	//curl --location --request GET 'localhost:8080/api/v1/auth/todes-excpeto-actual' -v \
	//--header 'xauthtoken: WWpTS1paUzlTYURRODVVTzRrRm9oUT09OkxubGNDSEw1a1BkdFErYVNWOUlobVE9PQ'

	
	@PostFilter("filterObject != authentication.principal.username")
	@GetMapping("/todes-excpeto-actual")
	public List<String> todesExceptoActual() {
		List<String> r=null;
		try {
			r = userBusiness.list().stream().map(u -> u.getUsername()).collect(Collectors.toList());
			return r;
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return r;
	}

}
