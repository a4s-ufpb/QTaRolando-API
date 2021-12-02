package br.ufpb.dcx.apps4society.qtarolando.api.service;

import java.util.List;
import java.util.Optional;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountNewDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.UserAccountRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.security.UserAccountSS;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repo;

	@Autowired
	private UserAccountService userAccountService;
	
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

	public Event fromDTO(Event objDto){
		return new Event(objDto.getId(),objDto.getTitle(),objDto.getCategoryId(),objDto.getDescription(),objDto.getInitialDate(),objDto.getFinalDate(),objDto.getImagePath(),objDto.getLocation(), objDto.getPhone(),objDto.getSite(),objDto.getPunchLine1(),objDto.getPunchLine2());
	}

	public Page<Event> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserAccountSS user = UserAccountService.getUserAuthenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Pageable pageable = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		UserAccount userAccount = userAccountService.find(user.getId());
		return repo.findByUserAccount(pageable, userAccount);
	}
}
