package br.ufpb.dcx.apps4society.qtarolando.api.integration;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.CredentialsDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountNewDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserInfoResponse;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.UserAccountRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.service.UserAccountService;
import br.ufpb.dcx.apps4society.qtarolando.api.util.EventCreator;
import br.ufpb.dcx.apps4society.qtarolando.api.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EventRepository eventRepository;

//    @Autowired
//    private UserAccountService userAccountService;

//    @Autowired
//    @Qualifier(value = "testRestTemplateRoleAdmin")
//    private TestRestTemplate testRestTemplateRoleAdmin;
//
//    @Autowired
//    private UserAccountRepository userAccountRepository;

    private static final String BASE_URL = "/api/events/";

//    private static final UserAccount ADMIN = UserAccount.builder()
//            .username("ADMIN")
//            .password("(83) 97512-2554")
//            .email("admin@gmail.com")
//            .build();
//
//    @TestConfiguration
//    @Lazy
//    static class config{
//        @Bean(name = "testRestTemplateRoleAdmiin")
//        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
//            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
//                    .rootUri("http://localhost:" + port)
//                    .basicAuthentication("ADMIN", "(83) 97512-2554");
//            return new TestRestTemplate(restTemplateBuilder);
//        }
//    }

    @BeforeEach
    void setUp(){
        eventRepository.deleteAll();
    }

//    @DisplayName("shouldFindEventsPaginados return the elements in the first page")
//    @Test
//    void shouldFindEventsPaginados() {
//
//        List<Event> events = new ArrayList<>();
//        int quantiEvent = 11;
//        int pageLength = 5;
//        int expectedTotalPages = 3;
//        String url = "/api/events/page?size=" + pageLength;
//
//        for (int i = 0; i < quantiEvent; i++) {
//            events.add(EventCreator.defaultEvent());
//        }
//        eventRepository.saveAll(events);
//
//        PageableResponse<Event> eventPage = testRestTemplate.exchange(url, HttpMethod.GET, null,
//                new ParameterizedTypeReference<PageableResponse<Event>>() {
//                }).getBody();
//
//        Assertions.assertThat(eventPage).isNotNull();
//
//        //verify if length of eventPage is equal to ONE page
//        Assertions.assertThat(eventPage.getNumberOfElements())
//                .isEqualTo(pageLength);
//
//        Assertions.assertThat(eventPage.toList().get(0).getTitle())
//                .isEqualTo(events.get(0).getTitle());
//
//        Assertions.assertThat(eventPage.toList().get(0).getId())
//                .isNotEqualTo(events.get(1).getId());
//
//        Assertions.assertThat(eventPage.getTotalPages())
//                .isNotNull()
//                .isEqualTo(expectedTotalPages);
//    }

//    @Test
//    void shouldFindAllEvents() {
//        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());
//
//        Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitle(
//                "Praça"));
//
//        List<Event> response = testRestTemplate.exchange("/api/events", HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Event>>() {
//                }).getBody();
//
//        Assertions.assertThat(response)
//                .isNotNull()
//                .isNotEmpty()
//                .hasSize(2);
//
//        Assertions.assertThat(response.get(0).getTitle())
//                .isNotNull()
//                .isEqualTo(savedEvent.getTitle());
//
//        Assertions.assertThat(response.get(0).getId())
//                .isNotNull()
//                .isNotEqualTo(savedEvent2.getId());
//
//        Assertions.assertThat(response.get(0).getTitle())
//                .isNotNull()
//                .isNotEqualTo(savedEvent2.getTitle());
//
//    }

    @Test
    void shouldFindEventById() {
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());
        int expectedId = savedEvent.getId();

        Event response = testRestTemplate.getForObject(BASE_URL+ "{id}", Event.class, expectedId);



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

        Assertions.assertThat(response.getCategories().get(0).getId())
                .isNotNull()
                .isEqualTo(savedEvent.getCategories().get(0).getId());

        Assertions.assertThat(response.getCategories().get(0).getName())
                .isNotNull()
                .isEqualTo(savedEvent.getCategories().get(0).getName());

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

        Assertions.assertThat(response.getModality())
                .isNotNull()
                .isEqualTo(savedEvent.getModality());

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

    @Test
    @DisplayName("getEventsByTitle returns an empty list of event when event is not found")
    void getEventsByTitle_ShouldReturnEmptyListOfEvent() {

        PageableResponse<Event> response = testRestTemplate.exchange(BASE_URL + "filter?title=Praia", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Event>>() {
                }).getBody();


        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();

    }

//    @Test
//    void deleteTest(){
//        Event e = eventRepository.save(EventCreator.defaultEvent());
//        e.setId(1);
//        int expected = e.getId();
//
//        UserAccountNewDTO user = new UserAccountNewDTO("w02@gmail.com", "wellington", "12345678");
//        userAccountService.insert(user);
//        CredentialsDTO credentialsDTO = new CredentialsDTO(user.getEmail(), user.getPassword());
//
//        ResponseEntity<UserInfoResponse> responseLogin = testRestTemplate.postForEntity("/api/auth/login", credentialsDTO, UserInfoResponse.class);
//
//        Assertions.assertThat(responseLogin).isNotNull();


//        TestRestTemplate testRestTemplateUser = new TestRestTemplate("wellington", "12345678");
//
//        testRestTemplate.exchange("/api/events/{id}", HttpMethod.DELETE,
//                null, Void.class, expected);
//
//        Assertions.assertThat(eventRepository.findById(1)).isNull();
//    }


    @Test
    @DisplayName("getEventsByTitle returns a list a of event when successful")
    void getEventsByTitle_ShouldReturnListOfEvent() {
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());
        Event savedEvent2 = eventRepository.save(EventCreator.defaultEvent());

        String title = savedEvent2.getTitle();

        PageableResponse<Event> response = testRestTemplate.exchange(
                BASE_URL + "filter?title=" + title, HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Event>>() {
                }).getBody();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(response.toList().get(0).getTitle())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedEvent2.getTitle());


    }

    @Test
    @DisplayName("getEventsByTitleContaining returns a list of event with the letters specified when successful")
    void getEventsByTitleContaining_ShouldReturnListOfEvent() {
        Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitle("Praia"));
        Event savedEvent = eventRepository.save(EventCreator.customizedEventTitle("Praça"));
        Event savedEvent3 = eventRepository.save(EventCreator.customizedEventTitle("Passeio turisco"));


        String letters = "Pra";

        PageableResponse<Event> response = testRestTemplate.exchange(
                BASE_URL + "filter?title=" + letters, HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Event>>() {
                }).getBody();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(response.toList().get(0).getTitle())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedEvent.getTitle());

        Assertions.assertThat(response.toList().get(1).getTitle())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(savedEvent2.getTitle());

    }

    @Test
    void shouldFindEventsByCategory() {
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());
        Event savedEven2 = eventRepository.save(EventCreator.defaultEvent());

        Long expectedCategory = savedEvent.getCategories().get(0).getId();

        PageableResponse<Event> response = testRestTemplate.exchange(BASE_URL + "filter?categoryId=" + expectedCategory, HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Event>>() {
                }).getBody();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2);

        Assertions.assertThat(response.toList().get(0).getCategories().get(0).getId())
                .isNotNull()
                .isEqualTo(expectedCategory);

        Assertions.assertThat(response.toList().get(0).getTitle())
                .isNotNull()
                .isEqualTo(savedEvent.getTitle());

    }

//    @Test
//    void shouldFindEventsByPeriodo() {
//
//        Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitleAndDate(
//                "Circo", "2022-09-20T19:00:00", "2022-12-20T19:00:00"));
//
//        Event savedEvent = eventRepository.save(EventCreator.customizedEventTitleAndDate(
//                "Passeio Turisco", "2022-08-20T09:00:00", "2022-08-21T19:00:00"));
//
//        String initialDateExpected = "2022-09-20T09:00:00";
//        String finalDateExpected = "2022-12-20T19:00:00";
//        String url = "/api/events/filter?dateType=ESCOLHER_INTERVALO&initialDate=" + initialDateExpected
//                + "&finalDate=" + finalDateExpected;
//
//        PageableResponse<Event> response = testRestTemplate.exchange(url, HttpMethod.GET, null,
//                new ParameterizedTypeReference<PageableResponse<Event>>() {
//                }).getBody();
//
//        Assertions.assertThat(response)
//                .isNotNull()
//                .isNotEmpty()
//                .hasSize(2);
//
//
//        Assertions.assertThat(response.toList().get(0).getTitle())
//                .isNotNull()
//                .isNotEmpty()
//                .isEqualTo("Passeio Turisco");
//
//        Assertions.assertThat(response.toList().get(0).getInitialDate())
//                .isNotNull()
//                .isEqualTo(savedEvent.getInitialDate());
//
//        Assertions.assertThat(response.toList().get(0).getFinalDate())
//                .isNotNull()
//                .isEqualTo(savedEvent.getFinalDate());
//
//        Assertions.assertThat(response.toList().get(1).getTitle())
//                .isNotNull()
//                .isNotEmpty()
//                .isEqualTo("Circo");
//
//        Assertions.assertThat(response.toList().get(1).getInitialDate())
//                .isNotNull()
//                .isEqualTo(savedEvent2.getInitialDate());
//
//        Assertions.assertThat(response.toList().get(1).getFinalDate())
//                .isNotNull()
//                .isEqualTo(savedEvent2.getFinalDate());
//    }

//    @Test
//    void shouldFindOnlyOneEventByPeriodoHOJE() {
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
//        String formattedInitialDateTime = currentDateTime.format(formatter);
//
//        Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitleAndDate(
//                "Circo", "2022-09-20T19:00:00", "2022-12-20T19:00:00"));
//
//
//        Event savedEvent = eventRepository.save(EventCreator.customizedEventTitleAndDate(
//                "Passeio Turisco", formattedInitialDateTime, "2022-12-31T19:00:00"));
//
//        String url = "/api/events/filter?dateType=HOJE";
//
//        PageableResponse<Event> response = testRestTemplate.exchange(url, HttpMethod.GET, null,
//                new ParameterizedTypeReference<PageableResponse<Event>>() {
//                }).getBody();
//
//        Assertions.assertThat(response)
//                .isNotNull()
//                .isNotEmpty()
//                .hasSize(1);
//
//        Assertions.assertThat(response.toList().get(0).getTitle())
//                .isNotNull()
//                .isNotEmpty()
//                .isEqualTo("Passeio Turisco");
//
//        Assertions.assertThat(response.toList().get(0).getInitialDate())
//                .isNotNull()
//                .isEqualTo(savedEvent.getInitialDate());
//
//        Assertions.assertThat(response.toList().get(0).getFinalDate())
//                .isNotNull()
//                .isEqualTo(savedEvent.getFinalDate());
//
//    }

//    @Test
//    void shouldFindOnlyOneEventByPeriodoAMANHA() {
////        LocalDateTime currentDateTime = LocalDateTime.MAX.plusDays('1');
////        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
////        String formattedInitialDateTime = currentDateTime.format(formatter);
//
//        Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitleAndDate(
//                "Circo", "2022-09-20T19:00:00", "2022-11-20T19:00:00"));
//
//        Event savedEvent = eventRepository.save(EventCreator.customizedEventTitleAndDate(
//                "Passeio Turisco", "2022-12-03T09:00:00", "2022-12-31T19:00:00"));
//
//        String url = "/api/events/filter?AMANHA";
//
//        PageableResponse<Event> response = testRestTemplate.exchange(url, HttpMethod.GET, null,
//                new ParameterizedTypeReference<PageableResponse<Event>>() {
//                }).getBody();
//
//        Assertions.assertThat(response)
//                .isNotNull()
//                .isNotEmpty()
//                .hasSize(1);
//
//        Assertions.assertThat(response.toList().get(0).getTitle())
//                .isNotNull()
//                .isNotEmpty()
//                .isEqualTo("Passeio Turisco");
//
//        Assertions.assertThat(response.toList().get(0).getInitialDate())
//                .isNotNull()
//                .isEqualTo(savedEvent2.getInitialDate());
//
//        Assertions.assertThat(response.toList().get(0).getFinalDate())
//                .isNotNull()
//                .isEqualTo(savedEvent2.getFinalDate());
//
//    }

//    @Test
//    void shouldFindOnlyOneEventByPeriodoHOJE() {
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
//        String formattedInitialDateTime = currentDateTime.format(formatter);
//
//        Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitleAndDate(
//                "Circo", "2022-09-20T19:00:00", "2022-12-20T19:00:00"));
//
//        Event savedEvent = eventRepository.save(EventCreator.customizedEventTitleAndDate(
//                "Passeio Turisco", "2022-12-20T19:00:00", "2022-12-31T19:00:00"));
//
//        String url = "/api/events/filter?dateType=ESTE_MES";
//
//        PageableResponse<Event> response = testRestTemplate.exchange(url, HttpMethod.GET, null,
//                new ParameterizedTypeReference<PageableResponse<Event>>() {
//                }).getBody();
//
//        Assertions.assertThat(response)
//                .isNotNull()
//                .isNotEmpty()
//                .hasSize(1);
//
//        Assertions.assertThat(response.toList().get(0).getTitle())
//                .isNotNull()
//                .isNotEmpty()
//                .isEqualTo("Passeio Turisco");
//
//        Assertions.assertThat(response.toList().get(0).getInitialDate())
//                .isNotNull()
//                .isEqualTo(savedEvent.getInitialDate());
//
//        Assertions.assertThat(response.toList().get(0).getFinalDate())
//                .isNotNull()
//                .isEqualTo(savedEvent.getFinalDate());
//
//    }

//    @Test
//    @DisplayName("save returns event when successful")
//    void save_ReturnsEvent_WhenSuccessful() {
//        EventDTO e = EventCreator.defaultEventDTO();
//        userAccountRepository.save(ADMIN);
//
//        ResponseEntity<Event> eventResponseEntity = testRestTemplateRoleAdmin.postForEntity("/api/events", e, Event.class);
//
//        Assertions.assertThat(eventResponseEntity).isNotNull();
//        Assertions.assertThat(eventResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        Assertions.assertThat(eventResponseEntity.getBody()).isNotNull();
//        Assertions.assertThat(eventResponseEntity.getBody().getId()).isNotNull();
//    }

}