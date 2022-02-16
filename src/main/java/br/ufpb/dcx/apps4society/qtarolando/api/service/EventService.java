package br.ufpb.dcx.apps4society.qtarolando.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountNewDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.Profile;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.UserAccountRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.security.UserAccountSS;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.AuthorizationException;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.EmptyListException;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
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
	private EventRepository eventRepository;

	@Autowired
	private UserAccountService userAccountService;
	
	public List<Event> getAllEvents(){
		return eventRepository.findAll();
	}
	
	public Event getEventById(Integer id) throws ObjectNotFoundException{
		Event event = eventRepository.findById(id).get();
		if (event == null){
			throw new ObjectNotFoundException("Evento não encontrado");
		}

		return event;
	}

	public List<Event> getEventsByCategoryId(Integer categoryId){
		List<Event> events = eventRepository.findAllByCategoryId(categoryId);
		if (events.isEmpty()){
			throw new EmptyListException("Não foram encontrados eventos desta categoria");
		}

		return events;
	}
	
	public void createEvent(Event event) {
		eventRepository.save(event);

		UserAccountSS user = userAccountService.getUserAuthenticated();

		UserAccount userAccount = userAccountService.find(user.getId());
		userAccount.getEvents().add(event);
		userAccountService.updateUserEvents(userAccount);
	}
	
	public void updateEvent(Integer id, EventDTO newEventDTO) throws ObjectNotFoundException{
		UserAccountSS userSS = userAccountService.getUserAuthenticated();
		if (userSS == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Event> event = eventRepository.findById(id);
		if (!event.isPresent()){
			throw new ObjectNotFoundException("Evento não encontrado");
		}

		UserAccount userAccount = userAccountService.findByEmail(userSS.getEmail());
		if (userAccount.getEvents().contains(event.get()) || userSS.hasRole(Profile.ADMIN)){
			Event newEvent = event.get();
			BeanUtils.copyProperties(newEventDTO, newEvent,"id");
			eventRepository.save(newEvent);
		} else {
			throw new AuthorizationException("Acesso negado");
		}
	}

	public void deleteEvent(Integer id) throws ObjectNotFoundException{
		UserAccountSS userSS = userAccountService.getUserAuthenticated();
		if (userSS == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Event> event = eventRepository.findById(id);
		if (!event.isPresent()){
			throw new ObjectNotFoundException("Evento não encontrado");
		}

		UserAccount userAccount = userAccountService.findByEmail(userSS.getEmail());
		if (userAccount.getEvents().contains(event.get()) || userSS.hasRole(Profile.ADMIN)){
			eventRepository.delete(event.get());
		} else {
			throw new AuthorizationException("Acesso negado");
		}

	}

	public Event fromDTO(Event objDto){
		return new Event(objDto.getId(),objDto.getTitle(),objDto.getCategoryId(),objDto.getDescription(),objDto.getInitialDate(),objDto.getFinalDate(),objDto.getImagePath(),objDto.getLocation(), objDto.getPhone(),objDto.getSite(),objDto.getPunchLine1(),objDto.getPunchLine2());
	}

	public Page<Event> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserAccountSS user = userAccountService.getUserAuthenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Pageable pageable = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		UserAccount userAccount = userAccountService.find(user.getId());

		Page<Event> eventsPage = eventRepository.findByUserAccount(pageable, userAccount);

		if (eventsPage.isEmpty()){
			throw new EmptyListException("Não foram encontrados eventos do usuário: " + user.getUsername());
		}

		return eventsPage;
	}
}
