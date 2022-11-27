package br.ufpb.dcx.apps4society.qtarolando.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
import org.springframework.transaction.annotation.Transactional;

import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventCustomRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventRepository;

@Service
public class EventService {

	@Autowired
	private EventCustomRepository eventCustomRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserAccountService userAccountService;

	public Page<Event> listAll(Pageable pageable) {
		return eventRepository.findAll(pageable);
	}

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

	@Transactional
	public Page<Event> getEventsByFilter(String title, Long categoryId, String modality,String dateType, String initialDate,
			String finalDate, Integer page, Integer pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		return eventCustomRepository.find(title, categoryId, modality, dateType, initialDate, finalDate, pageable);
	}

	public List<Event> getEventsByTitle(EventTitleDTO eventTitleDTO) {
		return eventRepository.findAllByTitle(eventTitleDTO.getTitle());
	}

	public List<Event> getEventsByEventModality(String modality) {
		return eventRepository.findAllByModality(modality);
	}

	public List<Event> getEventsByDateRange(String initialDate, String finalDate) {
		LocalDateTime initialD = LocalDateTime.parse(initialDate);
		LocalDateTime finalD = LocalDateTime.parse(finalDate);

		return eventRepository.findAllByDateRange(initialD, finalD);
	}

	@Transactional
	public void createEvent(EventDTO eventDTO) {
		Event newEvent = new Event(eventDTO);
		eventRepository.save(newEvent);

		UserPrincipal user = UserAccountService.getUserAuthenticated();

		UserAccount userAccount = userAccountService.find(user.getId());
		userAccount.getEvents().add(newEvent);
		userAccountService.updateUserEvents(userAccount);
	}

	@Transactional
	public void updateEvent(Integer id, EventDTO newEventDTO) throws ObjectNotFoundException {
		UserPrincipal userSS = UserAccountService.getUserAuthenticated();
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

	@Transactional
	public void deleteEvent(Integer id) throws ObjectNotFoundException {
		UserPrincipal userSS = UserAccountService.getUserAuthenticated();
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
		UserPrincipal user = UserAccountService.getUserAuthenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		Pageable pageable = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		UserAccount userAccount = userAccountService.find(user.getId());

		Page<Event> eventsPage = eventRepository.findByUserAccount(pageable, userAccount);

		return eventsPage;
	}
}
