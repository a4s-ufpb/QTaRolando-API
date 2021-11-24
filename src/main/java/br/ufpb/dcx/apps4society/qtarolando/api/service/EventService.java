package br.ufpb.dcx.apps4society.qtarolando.api.service;

import java.util.List;
import java.util.Optional;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.ufpb.dcx.apps4society.qtarolando.api.dao.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repo;
	
	public List<Event> getAllEvents(){
		return repo.findAll();
	}
	
	public Optional<Event> getEventById(Integer id) {
		return repo.findById(id);
	}
	
	public void createEvent(Event event) {
		repo.save(event);
	}
	
	public void updateEvent(Integer id, Event novoEvent) {
		Event event = repo.findById(id).get();
		novoEvent.setId(event.getId());
		repo.save(novoEvent);
	}

	public void deleteEvent(Integer id) {
		repo.delete(repo.findById(id).get());
	}
		
}
