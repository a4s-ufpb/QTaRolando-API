package br.com.qtarolando.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.qtarolando.api.dao.EventoRepository;
import br.com.qtarolando.api.model.Evento;

@RestController
@RequestMapping("/api")
public class EventoController {
	
	@Autowired
	private EventoRepository repository;
	
	@GetMapping("/listar-todos")
	public List<Evento> getEventos(){
		return repository.findAll();
	}
	
//	@PostMapping
//	public Evento addEvento(@RequestBody Evento ev){
//		return repository.save(ev);
//	}

}
