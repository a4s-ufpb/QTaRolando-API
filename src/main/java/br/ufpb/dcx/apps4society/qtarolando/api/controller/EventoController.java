package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import java.util.List;
import java.util.Optional;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Evento;
import br.ufpb.dcx.apps4society.qtarolando.api.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
public class EventoController {
	
	@Autowired
	private EventoService service;

	@GetMapping("/listar-todos")
	@ResponseBody
	public List<Evento> getEventos(){
		return service.todosEventos();
	}
	
	@GetMapping("/evento/{id}")
	@ResponseBody
	public Optional<Evento> getEventoId(@PathVariable("id") int id) {		
		return service.econtrarEventoPorId(id);
	}
	
	@PostMapping("/cadastrar")
	@ResponseBody
	public void cadastrarEvento(@RequestBody Evento evento){
		service.cadastrarEvento(evento);
	}
	
	// Muda todos os campos na requisicao, permanecendo apenas o id
	@PutMapping("/evento/{id}")
	@ResponseBody
	public void atualizarEvento(@PathVariable("id") int id, @RequestBody Evento novoEvento) {
		service.atualizarEvento(id,novoEvento);
	}
	
	@DeleteMapping("/{id}")
	@ResponseBody
	public void deletarEvento(@PathVariable("id") int id) {
		service.deletarEvento(id);
	}
		
}
