package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/events")
public class EventController {

	@Autowired
	private EventService eventService;

	@GetMapping("/page")
	public ResponseEntity<Page<Event>> list(Pageable pageable) {
		return ResponseEntity.ok(eventService.listAll(pageable));
	}

	@GetMapping
	public List<Event> getAllEvents() {
		return eventService.getAllEvents();
	}

	@GetMapping("/{id}")
	public Event getEventById(@PathVariable("id") Integer id) {
		return eventService.getEventById(id);
	}

	@GetMapping("/category/{categoryId}")
	public List<Event> getEventsByCategoryId(@PathVariable("categoryId") Integer categoryId) {
		return eventService.getEventsByCategoryId(categoryId);
	}

	@GetMapping("/filter")
	public Page<Event> getEventsByFilter(
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "categoryId", required = false) Long categoryId,
			@RequestParam(value = "modality", required = false) String modality,
			@RequestParam(value = "dateType", required = false) String dateType,
			@RequestParam(value = "initialDate", required = false) String initialDate,
			@RequestParam(value = "finalDate", required = false) String finalDate,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "pageSize", defaultValue = "24") Integer pageSize) {
		return eventService.getEventsByFilter(title, categoryId, modality, dateType, initialDate, finalDate, page,
				pageSize);
	}

	@GetMapping("/title")
	public List<Event> getEventsByTitle(@RequestParam String title) {
		return eventService.getEventsByTitle(title);
	}

	@GetMapping("/eventModalityId/{eventModalityId}")
	public List<Event> getEventsByModality(@PathVariable("modality") String modality) {
		return eventService.getEventsByEventModality(modality);
	}

	@GetMapping("/byDateInterval")
	public List<Event> getEventsByDateRange(@RequestParam("initialDate") String initialDate,
			@RequestParam("finalDate") String finalDate) {

		return eventService.getEventsByDateRange(initialDate, finalDate);
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public void createEvent(@RequestBody EventDTO eventDTO) {
		eventService.createEvent(eventDTO);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public void updateEvent(@PathVariable("id") Integer id, @RequestBody EventDTO newEventDTO) {
		eventService.updateEvent(id, newEventDTO);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public void deleteEvent(@PathVariable("id") Integer id) {
		eventService.deleteEvent(id);
	}

}
