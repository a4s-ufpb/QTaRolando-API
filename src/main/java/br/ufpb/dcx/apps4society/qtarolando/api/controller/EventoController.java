package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventoController {

	@Autowired
	private EventService service;

	@GetMapping
	public List<Event> getAllEvents() {
		return service.getAllEvents();
	}

	@GetMapping("/{id}")
	public Optional<Event> getEventById(@PathVariable("id") Integer id) {
		return service.getEventById(id);
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('MANAGER')")
	public void createEvent(@RequestBody Event event) {
		service.createEvent(event);
	}

	// Muda todos os campos na requisicao, permanecendo apenas o id
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('MANAGER')")
	public void updateEvent(@PathVariable("id") Integer id, @RequestBody Event novoEvent) {
		service.updateEvent(id, novoEvent);
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
