package br.udesc.man_gym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteController {
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/error")
	public String error() {
		return "error";
	}
	
	@GetMapping("/inicio")
	public String inicio() {
		return "index";
	}
	
	@GetMapping("/medidas")
	public String medidas() {
		return "medidas";
	}
	

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
}
