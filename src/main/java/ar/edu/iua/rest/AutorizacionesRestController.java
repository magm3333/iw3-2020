package ar.edu.iua.rest;

import java.util.List;
import java.util.stream.Collector;
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

@RestController
@RequestMapping(Constantes.URL_AUTH)
public class AutorizacionesRestController extends BaseRestController {
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> onlyAdmin() {
		return new ResponseEntity<String>("Servicio admin", HttpStatus.OK);
	}

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
	
	//@PreAuthorize permite acceder a los parámetros de entrada
	@PreAuthorize("#username == authentication.principal.username")
	@GetMapping("/misroles")
	public ResponseEntity<String> getMyRoles(@RequestParam("username")  String username) {
		return new ResponseEntity<String>(getUserLogged().getAuthorities().toString(), HttpStatus.OK);
	}

	@GetMapping("/variable")
	public ResponseEntity<String> onlyAdminProgramado(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_ADMIN")) {
			return new ResponseEntity<String>("Servicio dinámico para ADMIN", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Servicio dinámico para USER", HttpStatus.OK);
		}
		
	}
	

	//@PostAuthorize permite acceder al resultado
	@PostAuthorize("returnObject.username == #username")
	@GetMapping("/mifulldata")
	public User postAuthorize(@RequestParam("username")  String username) {
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
