package ar.edu.iua.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@GetMapping(value = { "", "/" })
	public String defaultPage() {
		return "redirect:/index.html";
	}
}
