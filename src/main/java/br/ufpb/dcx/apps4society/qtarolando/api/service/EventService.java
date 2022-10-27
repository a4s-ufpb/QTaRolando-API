package br.ufpb.dcx.apps4society.qtarolando.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.DateRangeDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventTitleDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.Roles;
import br.ufpb.dcx.apps4society.qtarolando.api.security.UserPrincipal;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.AuthorizationException;
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

	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

	public Event getEventById(Integer id) throws ObjectNotFoundException {
		Event event = eventRepository.findById(id).get();
		if (event == null) {
			throw new ObjectNotFoundException("Evento não encontrado");
		}

		return event;
	}

	public List<Event> getEventsByTitle(String title) {
		return eventRepository.findAllByTitle(title);
	}

	public List<Event> getEventsByCategoryId(Integer categoryId) {
		return eventRepository.findAllByCategoryId(categoryId);
	}

	public List<Event> getEventsByEventModalityId(Integer eventModalityId) {
		return eventRepository.findAllByEventModalityId(eventModalityId);
	}

	public List<Event> getEventsByDateRange(String initialDate, String finalDate) {
		LocalDateTime initialD = LocalDateTime.parse(initialDate);
		LocalDateTime finalD = LocalDateTime.parse(finalDate);

		return eventRepository.findAllByDateRange(initialD, finalD);
	}

	public void createEvent(EventDTO eventDTO) {
		Event newEvent = new Event(eventDTO);
		eventRepository.save(newEvent);

		UserPrincipal user = userAccountService.getUserAuthenticated();

		UserAccount userAccount = userAccountService.find(user.getId());
		userAccount.getEvents().add(newEvent);
		userAccountService.updateUserEvents(userAccount);
	}

	public void updateEvent(Integer id, EventDTO newEventDTO) throws ObjectNotFoundException {
		UserPrincipal userSS = userAccountService.getUserAuthenticated();
		if (userSS == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Event> event = eventRepository.findById(id);
		if (!event.isPresent()) {
			throw new ObjectNotFoundException("Evento não encontrado");
		}

		UserAccount userAccount = userAccountService.findByEmail(userSS.getEmail());
		if (userAccount.getEvents().contains(event.get()) || userSS.hasRole(Roles.ADMIN)) {
			Event newEvent = event.get();
			BeanUtils.copyProperties(newEventDTO, newEvent, "id");
			eventRepository.save(newEvent);
		} else {
			throw new AuthorizationException("Acesso negado");
		}
	}

	/** */
	public void deleteEvent(Integer id) throws ObjectNotFoundException {
		UserPrincipal userSS = userAccountService.getUserAuthenticated();
		if (userSS == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Event> event = eventRepository.findById(id);
		if (!event.isPresent()) {
			throw new ObjectNotFoundException("Evento não encontrado");
		}

		UserAccount userAccount = userAccountService.findByEmail(userSS.getEmail());
		if (userAccount.getEvents().contains(event.get()) || userSS.hasRole(Roles.ADMIN)) {
			eventRepository.delete(event.get());
		} else {
			throw new AuthorizationException("Acesso negado");
		}

	}

	public Page<Event> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserPrincipal user = userAccountService.getUserAuthenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Pageable pageable = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		UserAccount userAccount = userAccountService.find(user.getId());

		Page<Event> eventsPage = eventRepository.findByUserAccount(pageable, userAccount);

		return eventsPage;
	}
}
