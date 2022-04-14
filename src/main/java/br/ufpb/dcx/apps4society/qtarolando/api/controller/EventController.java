package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.DateRangeDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventTitleDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

	@Autowired
	private EventService service;

	@GetMapping
	public List<Event> getAllEvents() {
		return service.getAllEvents();
	}

	@GetMapping("/{id}")
	public Event getEventById(@PathVariable("id") Integer id) {
		return service.getEventById(id);
	}

	@GetMapping("/category/{categoryId}")
	public List<Event> getEventsByCategoryId(@PathVariable("categoryId") Integer categoryId) {
		return service.getEventsByCategoryId(categoryId);
	}

	@GetMapping("/title")
	public List<Event> getEventsByTitle(@RequestBody EventTitleDTO eventTitleDTO) {
		return service.getEventsByTitle(eventTitleDTO);
	}

	@GetMapping("/eventModalityId/{eventModalityId}")
	public List<Event> getEventsByModality(@PathVariable("eventModalityId") Integer eventModalityId) {
		return service.getEventsByEventModalityId(eventModalityId);
	}

	@GetMapping("/byDateInterval")
	public List<Event> getEventsByDateRange(@RequestBody DateRangeDTO dateRangeDTO){
		return service.getEventsByDateRange(dateRangeDTO);
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('MANAGER')")
	public void createEvent(@RequestBody EventDTO eventDTO) {
		service.createEvent(eventDTO);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('MANAGER')")
	public void updateEvent(@PathVariable("id") Integer id, @RequestBody EventDTO newEventDTO) {
		service.updateEvent(id, newEventDTO);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('MANAGER')")
	public void deleteEvent(@PathVariable("id") Integer id) {
		service.deleteEvent(id);
	}

	@GetMapping(value="/page")
	public ResponseEntity<Page<Event>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="categoryId") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Event> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
}
