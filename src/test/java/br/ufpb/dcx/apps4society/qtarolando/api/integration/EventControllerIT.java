package br.ufpb.dcx.apps4society.qtarolando.api.integration;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void shouldFindAllEvents() {

        ResponseEntity<Event[]> response = testRestTemplate.exchange(
                "/api/events", HttpMethod.GET, null,
                Event[].class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(2, eventRepository.findAll().size());
    }

    @Test
    public void shouldFindEventsByTitle() {
        //TODO

//        List<Event> events = eventRepository.findAllByTitle("Praia");
//
//        ResponseEntity<Event[]> response = testRestTemplate.exchange(
//                "/api/events/title?param=Praia", HttpMethod.GET, null,
//                Event[].class);
//
//        assertEquals(HttpStatus.OK,response.getStatusCode());

    }

    @Test
    public void shouldFindEventsById() {

        Optional<Event> event = eventRepository.findById(2);

        if (event.isPresent()) {
            Integer eventId = event.get().getId();

            ResponseEntity<Event> response = testRestTemplate.exchange(
                    "/api/events/" + eventId, HttpMethod.GET, null,
                    Event.class);

            assertEquals(response.getStatusCode(), HttpStatus.OK);
            assertEquals("Praça", event.get().getTitle());
        }

    }

    @Test
    public void shouldFindEventsPaginados() {
        //TODO

    }

    @Test
    public void shouldFindEventsByPeriodo() {
        //TODO

//        List<Event> events = eventRepository.findAllByDateRange(LocalDateTime.now(), LocalDateTime.now());
//
//
//        ResponseEntity<Event[]> response = testRestTemplate.exchange(
//                "/api/events/byDateInterval" + , HttpMethod.GET, null,
//                Event[].class);
//
//        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

}