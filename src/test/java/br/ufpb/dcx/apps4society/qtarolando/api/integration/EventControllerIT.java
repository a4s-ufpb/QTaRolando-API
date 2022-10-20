package br.ufpb.dcx.apps4society.qtarolando.api.integration;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.util.EventCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

//    private String url = "http://localhost:8080/api/events";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void shouldFindAllEvents() {
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());

        Event savedEvent2 = eventRepository.save(EventCreator.customizedEvent(
                "Praça", "subtitle", 2, "description",
                "2022-09-20T19:00:00", "2022-09-20T19:00:00", "imagePath",
                1, "location", "phone", "site"));

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

        Assertions.assertThat(response.get(0).getTitle())
                .isNotNull()
                .isNotEqualTo(savedEvent2.getTitle());

        eventRepository.deleteAll();
    }

    @Test
    @DisplayName("getEventsByTitle returns an empty list of event when event is not found")
    public void GetEventsByTitle_ShouldReturnEmptyListOfEvent() {
        List<Event> response = testRestTemplate.exchange(
                "/api/events/title?title=Praia", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Event>>() {
                }).getBody();

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("getEventsByTitle returns a list a of event when successful")
    public void GetEventsByTitle_ShouldReturnListOfEvent() {
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());
        Event savedEvent2 = eventRepository.save(EventCreator.defaultEvent());

        List<Event> response = testRestTemplate.exchange(
                "/api/events/title?title=Praia", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Event>>() {
                }).getBody();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(response.get(0).getTitle())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedEvent2.getTitle());

        eventRepository.deleteAll();

    }

    @Test
    public void shouldFindEventById() {
        Event savedEvent = eventRepository.save(EventCreator.customizedEvent(
                "Praça", "subtitle", 2, "description",
                "2022-09-20T19:00:00", "2022-09-20T19:00:00", "imagePath",
                1, "location", "phone", "site"));

        Integer expectedId = savedEvent.getId();

        Event response = testRestTemplate.getForObject("/api/events/{id}", Event.class, savedEvent.getId());

        Assertions.assertThat(response).isNotNull();

        Assertions.assertThat(response.getId())
                .isNotNull()
                .isEqualTo(savedEvent.getId());

        Assertions.assertThat(response.getTitle())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedEvent.getTitle());

        Assertions.assertThat(response.getSubtitle())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedEvent.getSubtitle());

        Assertions.assertThat(response.getCategoryId())
                .isNotNull()
                .isEqualTo(savedEvent.getCategoryId());

        Assertions.assertThat(response.getDescription())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedEvent.getDescription());

        Assertions.assertThat(response.getInitialDate())
                .isNotNull()
                .isEqualTo(savedEvent.getInitialDate());

        Assertions.assertThat(response.getFinalDate())
                .isNotNull()
                .isEqualTo(savedEvent.getFinalDate());

        Assertions.assertThat(response.getImagePath())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedEvent.getImagePath());

        Assertions.assertThat(response.getEventModalityId())
                .isNotNull()
                .isEqualTo(savedEvent.getEventModalityId());

        Assertions.assertThat(response.getLocation())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedEvent.getLocation());

        Assertions.assertThat(response.getPhone())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedEvent.getPhone());

        Assertions.assertThat(response.getSite())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedEvent.getSite());

        eventRepository.delete(savedEvent);
    }

    @Test
    public void shouldFindEventsPaginados() {
//        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());
//
//        ResponseEntity<Page<Event>> animePage = testRestTemplate.exchange("/api/events/page", HttpMethod.GET, null,
//                new ParameterizedTypeReference<ResponseEntity<Page<Event>>>() {
//                }).getBody();



//        Assertions.assertThat(animePage).isNotNull();
//        Assertions.assertThat(animePage.toList())
//                .isNotEmpty()
//                .hasSize(1);
//        Assertions.assertThat(animePage.toList().get(0).getTitle()).isEqualTo(savedEvent.getTitle());

    }

    @Test
    public void shouldFindEventsByPeriodo() {

//        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());
//
//        LocalDateTime expectedInitialDate = savedEvent.getInitialDate();
//        LocalDateTime expectedFinalDate = savedEvent.getFinalDate();
//
//        String url = String.format("/api/events/byDateInterval?initialdate="
//                +expectedInitialDate+ "&" +expectedFinalDate);
//
//        List<Event> response = testRestTemplate.exchange(url, HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Event>>() {
//                }).getBody();
//
//        Assertions.assertThat(response.get(0).getTitle())
//                .isEqualTo("Praia");
//
//        eventRepository.delete(savedEvent);
    }

}