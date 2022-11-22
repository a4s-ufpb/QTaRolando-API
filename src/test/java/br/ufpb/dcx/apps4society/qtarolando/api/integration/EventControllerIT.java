package br.ufpb.dcx.apps4society.qtarolando.api.integration;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.util.EventCreator;
import br.ufpb.dcx.apps4society.qtarolando.api.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventRepository eventRepository;

//    Caso queria saber em qual porta o teste está rodando
//    @LocalServerPort
//    private int localServerPort;

    @BeforeEach
    void setUp(){
        eventRepository.deleteAll();
    }

    @Test
    void shouldFindAllEvents() {
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
    void getEventsByTitle_ShouldReturnEmptyListOfEvent() {
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
    void getEventsByTitle_ShouldReturnListOfEvent() {
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


    }

    @Test
    @DisplayName("getEventsByTitleContaining returns a list of event with the letters specified when successful")
    void getEventsByTitleContaining_ShouldReturnListOfEvent() {
        Event savedEvent = eventRepository.save(EventCreator.customizedEventTitle("Praia"));
        Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitle("Praça"));

        String letters = "Pra";

        List<Event> response = testRestTemplate.exchange(
                "/api/events/title?title=" + letters, HttpMethod.GET, null,
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


    }

    @Test
    void shouldFindEventsByCategory() {
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());
        Event savedEven2 = eventRepository.save(EventCreator.defaultEvent());

        int expectedCategory = savedEvent.getCategoryId();
        String url = "/api/events/category/" + expectedCategory;

        List<Event> response = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Event>>() {
                }).getBody();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(response.get(0).getCategoryId())
                .isNotNull()
                .isEqualTo(expectedCategory);

        Assertions.assertThat(response.get(0).getTitle())
                .isNotNull()
                .isEqualTo(savedEvent.getTitle());

        Assertions.assertThat(response.get(1).getCategoryId())
                .isNotNull()
                .isNotEqualTo(savedEven2);

    }

    @Test
    void shouldFindEventById() {
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());
        int expectedId = savedEvent.getId();

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

    }

    @DisplayName("shouldFindEventsPaginados return the elements in the first page")
    @Test
    void shouldFindEventsPaginados() {

        List<Event> events = new ArrayList<>();
        int quantiEvent = 11;
        int pageLength = 5;
        int expectedTotalPages = 3;
        String url = "/api/events/page?size=" + pageLength;

        for (int i = 0; i < quantiEvent; i++) {
            events.add(EventCreator.defaultEvent());
        }
        eventRepository.saveAll(events);

        PageableResponse<Event> eventPage = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Event>>() {
                }).getBody();

        Assertions.assertThat(eventPage).isNotNull();

        //verify if length of eventPage is equal to ONE page
        Assertions.assertThat(eventPage.getNumberOfElements())
                .isEqualTo(pageLength);

        Assertions.assertThat(eventPage.toList().get(0).getTitle())
                .isEqualTo(events.get(0).getTitle());

        Assertions.assertThat(eventPage.toList().get(0).getId())
                .isNotEqualTo(events.get(1).getId());

        Assertions.assertThat(eventPage.getTotalPages())
                .isNotNull()
                .isEqualTo(expectedTotalPages);
    }

    @Test
    void shouldFindEventsByPeriodo() {

        Event savedEvent = eventRepository.save(EventCreator.customizedEventTitleAndDate(
                "Circo", "2022-09-20T19:00:00", "2022-12-20T19:00:00"));

        Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitleAndDate(
                "Passeio Turisco", "2022-08-20T09:00:00", "2022-08-21T19:00:00"));

        String initialDateExpected = "2022-08-20T09:00:00";
        String finalDateExpected = "2022-12-20T19:00:00";
        String url = "/api/events/byDateInterval?initialDate=" + initialDateExpected
                + "&finalDate=" + finalDateExpected;

        List<Event> response = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Event>>() {
                }).getBody();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(response.get(0).getTitle())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo("Circo");

        Assertions.assertThat(response.get(0).getInitialDate())
                .isNotNull()
                .isEqualTo(savedEvent.getInitialDate());

        Assertions.assertThat(response.get(0).getFinalDate())
                .isNotNull()
                .isEqualTo(savedEvent.getFinalDate());

        Assertions.assertThat(response.get(1).getTitle())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo("Passeio Turisco");

        Assertions.assertThat(response.get(1).getInitialDate())
                .isNotNull()
                .isEqualTo(savedEvent2.getInitialDate());

        Assertions.assertThat(response.get(1).getFinalDate())
                .isNotNull()
                .isEqualTo(savedEvent2.getFinalDate());

    }

    @Test
    void shouldFindOnlyOneEventByPeriodo() {

        Event savedEvent = eventRepository.save(EventCreator.customizedEventTitleAndDate(
                "Circo", "2022-09-20T19:00:00", "2022-12-20T19:00:00"));

        Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitleAndDate(
                "Passeio Turisco", "2022-08-20T09:00:00", "2022-08-21T19:00:00"));

        String initialDateExpected = "2022-08-20T09:00:00";
        String finalDateExpected = "2022-08-21T19:00:00";
        String url = "/api/events/byDateInterval?initialDate=" + initialDateExpected
                + "&finalDate=" + finalDateExpected;

        List<Event> response = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Event>>() {
                }).getBody();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(response.get(0).getTitle())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo("Passeio Turisco");

        Assertions.assertThat(response.get(0).getInitialDate())
                .isNotNull()
                .isEqualTo(savedEvent2.getInitialDate());

        Assertions.assertThat(response.get(0).getFinalDate())
                .isNotNull()
                .isEqualTo(savedEvent2.getFinalDate());

    }

}