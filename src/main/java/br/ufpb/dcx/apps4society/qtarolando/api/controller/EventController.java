package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/events")
public class EventController {

	@Autowired
	private EventService eventService;

	@GetMapping("/{id}")
	@Operation(summary = "Pesquisa um evento pelo seu id",
			tags = {"event"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operação feita com sucesso"),
			@ApiResponse(responseCode = "404", description = "Quando o evento não é encontrado")
	})
	public Event getEventById(@PathVariable("id") Integer id) {
		return eventService.getEventById(id);
	}

	@GetMapping("/filter")
	@Operation(summary = "Filtro usado para a pesquisa de evento",
			description = "Caso não passe nenhum dos parametros a seguir ele irá retornar uma page com todos os eventos " +
					"cadastrados. A pesquisa pode ser feita pelo titulo do evento, id da categoria ou modalidade. " +
					"A pesquisa pela data de inicio e data final de um evento deve ser feita usando alguns dos dateType do sistema, " +
					"também é possível especificar como deve ser o tamanho e a quantidade de paginas desejadas",
			tags = {"event"})
	@ApiResponse(responseCode = "200", description = "Operação feita com sucesso")
	public Page<Event> getEventsByFilter(
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "categoryId", required = false) Long categoryId,
			@RequestParam(value = "modality", required = false) String modality,
			@RequestParam(value = "dateType", required = false) String dateType,
			@RequestParam(value = "initialDate", required = false) String initialDate,
			@RequestParam(value = "finalDate", required = false) String finalDate,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "pageSize", defaultValue = "24") Integer pageSize) {
		return eventService.getEventsByFilter(title, categoryId, modality, dateType, initialDate, finalDate,
				page,
				pageSize);
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "Cria um evento",
			description = "Cria um evento e o associa ao usuário logado",
			tags = {"event"})
	@ApiResponse(responseCode = "201", description = "Evento criado com sucesso")
	@ResponseStatus(HttpStatus.CREATED)
	//É possivel criar um mesmo evento multiplas vezes?
	public void createEvent(@RequestBody EventDTO eventDTO) {
		eventService.createEvent(eventDTO);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "Atualiza os dados de um evento",
			description = "É preciso estar logado no sistema",
			tags = {"event"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Operação feita com sucesso"),
			@ApiResponse(responseCode = "401", description = "Quando o usuário não está logado"),
			@ApiResponse(responseCode = "403", description = "Quando não é o criador do evento"),
			@ApiResponse(responseCode = "404", description = "Quando o evento não é encontrado")
	})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateEvent(@PathVariable("id") Integer id, @RequestBody EventDTO newEventDTO) {
		eventService.updateEvent(id, newEventDTO);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@Operation(summary = "Deleta os dados de um evento",
			description = "É preciso estar logado no sistema",
			tags = {"event"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Operação feita com sucesso"),
			@ApiResponse(responseCode = "401", description = "Quando o usuário não está logado"),
			@ApiResponse(responseCode = "403", description = "Quando não é o criador do evento"),
			@ApiResponse(responseCode = "404", description = "Quando o evento não é encontrado")
	})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEvent(@PathVariable("id") Integer id) {
		eventService.deleteEvent(id);
	}

}