package br.ufpb.dcx.apps4society.qtarolando.api.integration;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.DateRangeDTO;
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
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.time.LocalDateTime;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventRepository eventRepository;

//    Caso queria saber em qual porta o teste está rodando
//    @LocalServerPort
//    private int localServerPort;

    @Test
    public void shouldFindAllEvents() {
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());

        Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitle(
                "Praça"));

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

    }

    @Test
    @DisplayName("getEventsByTitle returns an empty list of event when event is not found")
    public void getEventsByTitle_ShouldReturnEmptyListOfEvent() {
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
    public void getEventsByTitle_ShouldReturnListOfEvent() {
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());
        Event savedEvent2 = eventRepository.save(EventCreator.defaultEvent());

        String title = savedEvent2.getTitle();

        List<Event> response = testRestTemplate.exchange(
                "/api/events/title?title=" + title, HttpMethod.GET, null,
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
    @DisplayName("getEventsByTitleContaining returns a list a of event with the letters specified when successful")
    public void getEventsByTitleContaining_ShouldReturnListOfEvent() {
        Event savedEvent = eventRepository.save(EventCreator.customizedEventTitle("Praia"));
        Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitle("Praça"));
        Event savedEvent3 = eventRepository.save(EventCreator.customizedEventTitle("Passeio"));

        String letters = "Pra";

        List<Event> response = testRestTemplate.exchange(
                "/api/events/title?title="+ letters, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Event>>() {
                }).getBody();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(response.get(0).getTitle())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedEvent.getTitle());

        Assertions.assertThat(response.get(1).getTitle())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedEvent2.getTitle());

        eventRepository.deleteAll();

    }

    @Test
    public void shouldFindEventById() {
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());
        Integer expectedId = savedEvent.getId();

        Event response = testRestTemplate.getForObject("/api/events/{id}", Event.class, expectedId);

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
//        ResponseEntity<Page<Event>> eventPage = testRestTemplate.exchange("/api/events/page", HttpMethod.GET, null,
//                new ParameterizedTypeReference<ResponseEntity<Page<Event>>>() {
//                }).getBody();


//        Assertions.assertThat(eventPage).isNotNull();
//        Assertions.assertThat(eventPage.toList())
//                .isNotEmpty()
//                .hasSize(1);
//        Assertions.assertThat(eventPage.toList().get(0).getTitle()).isEqualTo(savedEvent.getTitle());

    }

    //JSON parse error: Cannot deserialize instance
    //Esse erro esta acontecendo nesse test
//    @Test
//    public void shouldFindEventsByPeriodo() {
//        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());
//
//        Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitleAndDate(
//                "Circo", "2022-09-20T19:00:00", "2022-12-20T19:00:00"));
//
//        Event savedEvent3 = eventRepository.save(EventCreator.customizedEventTitleAndDate(
//                "Passeio Turisco", "2022-08-20T09:00:00", "2022-08-21T19:00:00"));
//
//        DateRangeDTO dateRangeDTO = new DateRangeDTO(LocalDateTime.parse(
//                "2022-09-20T19:00:00"), LocalDateTime.parse("2022-12-20T19:00:00"));
//
//        String url = "/api/events/byDateInterval?initialDate=" + dateRangeDTO.getInitialDate()
//                + "&finalDate=" + dateRangeDTO.getFinalDate();
//
//        List<Event> response = testRestTemplate.exchange(
//                "/api/events/byDateInterval?initialDate=2022-09-20T19:00:00&finalDate=2022-10-27T16:00:00",
//                HttpMethod.GET, null, new ParameterizedTypeReference<List<Event>>() {
//                }).getBody();
//
//        Assertions.assertThat(response)
//                .isNotNull()
//                .isNotEmpty()
//                .hasSize(2);
//
//        Assertions.assertThat(response.get(0).getTitle())
//                .isNotNull()
//                .isNotEmpty()
//                .isEqualTo("Praia");
//
//        Assertions.assertThat(response.get(0).getInitialDate())
//                .isNotNull()
//                .isEqualTo(savedEvent.getInitialDate());
//
//        Assertions.assertThat(response.get(0).getFinalDate())
//                .isNotNull()
//                .isEqualTo(savedEvent.getFinalDate());
//
//        Assertions.assertThat(response.get(0).getTitle())
//                .isNotNull()
//                .isNotEmpty()
//                .isEqualTo("Circo");
//
//        Assertions.assertThat(response.get(1).getInitialDate())
//                .isNotNull()
//                .isEqualTo(savedEvent.getInitialDate());
//
//        Assertions.assertThat(response.get(1).getFinalDate())
//                .isNotNull()
//                .isEqualTo(savedEvent.getFinalDate());
//
//        eventRepository.deleteAll();
//    }

}