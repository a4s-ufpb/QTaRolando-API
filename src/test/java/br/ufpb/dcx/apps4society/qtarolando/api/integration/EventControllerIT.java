package br.ufpb.dcx.apps4society.qtarolando.api.integration;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.util.EventCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerIT {

//    private  String url = "http://localhost:/api/events";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventRepository eventRepository;
    
    @Test
    public void shouldFindAllEvents() {

        Event savedEvent = eventRepository.save(EventCreator.createEventToBeSaved());
        Event savedEvent2 = eventRepository.save(EventCreator.createEventToBeSaved());

        List<Event> response = testRestTemplate.exchange("/api/events", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Event>>() {
                }).getBody();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(response.get(0).getTitle())
                .isNotNull()
                .isEqualTo(savedEvent.getTitle());

        Assertions.assertThat(response.get(0).getId())
                .isNotNull()
                .isNotEqualTo(savedEvent2.getId());

        eventRepository.deleteAll();
    }

    @Test
    public void shouldFindEventsByTitle() {
        //TODO

//        List<Event> response = testRestTemplate.exchange(
//                "/api/events/title?title=Praia", HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Event>>() {
//                }).getBody();
//
//        Assertions.assertThat(response)
//                .isNotNull()
//                .isEmpty();

    }

    @Test
    public void shouldFindEventById() {
        Event savedEvent = eventRepository.save(EventCreator.createEventToBeSaved());
        int expectedId = savedEvent.getId();

        Event response = testRestTemplate.getForObject("/api/events/{id}", Event.class, expectedId);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getId())
                .isNotNull()
                .isEqualTo(expectedId);

        eventRepository.delete(savedEvent);
    }

    @Test
    public void shouldFindEventsPaginados() {
        //TODO

    }

    @Test
    public void shouldFindEventsByPeriodo() {

//        Event savedEvent = eventRepository.save(EventCreator.createEventToBeSaved());
//
//        String expectedInitialDate = "2022-09-20T19:00:00";
//
//        String url = String.format("/api/events/byDateInterval?initialdate=%s", expectedInitialDate);
//
//        List<Event> response = testRestTemplate.exchange(url, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Event>>() {
//                }).getBody();
//
//        Assertions.assertThat(response.get(0).getInitialDate())
//                .isEqualTo(expectedInitialDate);

    }

}