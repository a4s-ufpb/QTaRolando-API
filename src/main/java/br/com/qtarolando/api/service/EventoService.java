package br.com.qtarolando.api.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.qtarolando.api.dao.EventoRepository;
import br.com.qtarolando.api.model.Evento;

@Service
public class EventoService {
	
	@Autowired
	private EventoRepository repo;
	
	public List<Evento> todosEventos(){
		return repo.findAll();
	}
	
	public Optional<Evento> econtrarEventoPorId(int id) {
		return repo.findById(id);
	}
	
	public void cadastrarEvento(Evento evento) {
		repo.save(evento);
	}
	
	public void atualizarEvento(int id, Evento novoEvento) {
		Evento evento = repo.findById(id).get();
		novoEvento.setId(evento.getId());
		repo.save(novoEvento);
	}

	public void deletarEvento(int id) {
		repo.delete(repo.findById(id).get());
	}
		
}
