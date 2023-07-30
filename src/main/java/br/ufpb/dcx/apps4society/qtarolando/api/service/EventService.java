package br.ufpb.dcx.apps4society.qtarolando.api.service;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.Roles;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventCustomRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.security.UserPrincipal;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.AuthorizationException;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventCustomRepository eventCustomRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserAccountService userAccountService;

    public Event getEventById(Integer id) throws ObjectNotFoundException {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Evento não encontrado! Id: " + id + ", Tipo: " + Event.class.getName()));
    }

    @Transactional
    public Page<Event> getEventsByFilter(String title, Long categoryId, String modality,
                                         String dateType,
                                         String initialDate,
                                         String finalDate, Integer page, Integer pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        return eventCustomRepository.find(title, categoryId, modality, dateType, initialDate, finalDate,
                pageable);
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
            throw new ObjectNotFoundException("Evento não encontrado! Id: " + id + ", Tipo: " + Event.class.getName());
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
            throw new ObjectNotFoundException("Evento não encontrado! Id: " + id + ", Tipo: " + Event.class.getName());
        }

        UserAccount userAccount = userAccountService.findByEmail(userSS.getEmail());
        if (userAccount.getEvents().contains(event.get())) {
            eventRepository.delete(event.get());
        } else {
            throw new AuthorizationException("Acesso negado");
        }

    }
}