package br.com.qtarolando.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.qtarolando.api.dao.EventoRepository;
import br.com.qtarolando.api.model.Evento;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private EventoRepository repository;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@PostMapping("/")
	public String cadastrarEvento(Evento ev){
		repository.save(ev);
		return "redirect:/api/listar-todos";
	}
	
	@GetMapping("/listar-todos")
	public String listarTodos() {
		return "eventos";
	}
	
}
