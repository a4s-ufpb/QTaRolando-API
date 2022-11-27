// package br.ufpb.dcx.apps4society.qtarolando.api.integration;

// import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
// import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventRepository;
// import br.ufpb.dcx.apps4society.qtarolando.api.util.EventCreator;
// import br.ufpb.dcx.apps4society.qtarolando.api.wrapper.PageableResponse;
// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.core.ParameterizedTypeReference;
// import org.springframework.http.HttpMethod;

// import java.util.ArrayList;
// import java.util.List;

// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// class EventControllerI

           @Autowi
           private TestRestTemplate testRestTempla

           @Autowi
           private EventRepository eventReposito

        //    Caso queria saber em qual porta o teste está roda
        //    @LocalServerP
        //    private int localServerPo

           @T
           public void shouldFindAllEvents(
                   Event savedEvent = eventRepository.save(EventCreator.defaultEvent(

                   Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTit
                                   "Praça"

                   List<Event> response = testRestTemplate.exchange("/api/events", HttpMethod.GET, nu
                                   new ParameterizedTypeReference<List<Event>>(
                                   }).getBody

                   Assertions.assertThat(respon
                                   .isNotNul
                                   .isNotEmpt
                                   .hasSize(

                   Assertions.assertThat(response.get(0).getTitle
                                   .isNotNul
                                   .isEqualTo(savedEvent.getTitle(

                   Assertions.assertThat(response.get(0).getId
                                   .isNotNul
                                   .isNotEqualTo(savedEvent2.getId(

                   Assertions.assertThat(response.get(0).getTitle
                                   .isNotNul
                                   .isNotEqualTo(savedEvent2.getTitle(

         

           @T
           @DisplayName("getEventsByTitle returns an empty list of event when event is not foun
           public void getEventsByTitle_ShouldReturnEmptyListOfEvent(
                   List<Event> response = testRestTemplate.exchan
                                   "/api/events/title?title=Praia", HttpMethod.GET, nu
                                   new ParameterizedTypeReference<List<Event>>(
                                   }).getBody

                   Assertions.assertThat(respon
                                   .isNotNul
                                   .isEmpty

         

           @T
           @DisplayName("getEventsByTitle returns a list a of event when successfu
           public void getEventsByTitle_ShouldReturnListOfEvent(
                   Event savedEvent = eventRepository.save(EventCreator.defaultEvent(
                   Event savedEvent2 = eventRepository.save(EventCreator.defaultEvent(

                   String title = savedEvent2.getTitle

                   List<Event> response = testRestTemplate.exchan
                                   "/api/events/title?title=" + title, HttpMethod.GET, nu
                                   new ParameterizedTypeReference<List<Event>>(
                                   }).getBody

                   Assertions.assertThat(respon
                                   .isNotNul
                                   .isNotEmpt
                                   .hasSize(

                   Assertions.assertThat(response.get(0).getTitle
                                   .isNotNul
                                   .isNotEmpt
                                   .isEqualTo(savedEvent2.getTitle(

                   eventRepository.deleteAll

         

           @T
           @DisplayName("getEventsByTitleContaining returns a list of event with the letters specified when successfu
           public void getEventsByTitleContaining_ShouldReturnListOfEvent(
                   Event savedEvent = eventRepository.save(EventCreator.customizedEventTitle("Praia"
                   Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitle("Praça"

                   String letters = "Pr

                   List<Event> response = testRestTemplate.exchan
                                   "/api/events/title?title=" + letters, HttpMethod.GET, nu
                                   new ParameterizedTypeReference<List<Event>>(
                                   }).getBody

                   Assertions.assertThat(respon
                                   .isNotNul
                                   .isNotEmpt
                                   .hasSize(

                   Assertions.assertThat(response.get(0).getTitle
                                   .isNotNul
                                   .isNotEmpt
                                   .isEqualTo(savedEvent.getTitle(

                   Assertions.assertThat(response.get(1).getTitle
                                   .isNotNul
                                   .isNotEmpt
                                   .isEqualTo(savedEvent2.getTitle(

                   eventRepository.deleteAll

         

           @T
           public void shouldFindEventsByCategory(
                   Event savedEvent = eventRepository.save(EventCreator.defaultEvent(
                   Event savedEven2 = eventRepository.save(EventCreator.defaultEvent(

                   int expectedCategory = savedEvent.getCategoryId
                   String url = "/api/events/category/" + expectedCatego

                   List<Event> response = testRestTemplate.exchange(url, HttpMethod.GET, nu
                                   new ParameterizedTypeReference<List<Event>>(
                                   }).getBody

                   Assertions.assertThat(respon
                                   .isNotNul
                                   .isNotEmpt
                                   .hasSize(

                   Assertions.assertThat(response.get(0).getCategoryId
                                   .isNotNul
                                   .isEqualTo(expectedCategor

                   Assertions.assertThat(response.get(0).getTitle
                                   .isNotNul
                                   .isEqualTo(savedEvent.getTitle(

                   Assertions.assertThat(response.get(1).getCategoryId
                                   .isNotNul
                                   .isNotEqualTo(savedEven

                   eventRepository.deleteAll
         

           @T
           public void shouldFindEventById(
                   Event savedEvent = eventRepository.save(EventCreator.defaultEvent(
                   Integer expectedId = savedEvent.getId

                   Event response = testRestTemplate.getForObject("/api/events/{id}", Event.class, expectedI

                   Assertions.assertThat(response).isNotNull

                   Assertions.assertThat(response.getId
                                   .isNotNul
                                   .isEqualTo(savedEvent.getId(

                   Assertions.assertThat(response.getTitle
                                   .isNotNul
                                   .isNotEmpt
                                   .isEqualTo(savedEvent.getTitle(

                   Assertions.assertThat(response.getSubtitle
                                   .isNotNul
                                   .isNotEmpt
                                   .isEqualTo(savedEvent.getSubtitle(

                   Assertions.assertThat(response.getCategoryId
                                   .isNotNul
                                   .isEqualTo(savedEvent.getCategoryId(

                   Assertions.assertThat(response.getDescription
                                   .isNotNul
                                   .isNotEmpt
                                   .isEqualTo(savedEvent.getDescription(

                   Assertions.assertThat(response.getInitialDate
                                   .isNotNul
                                   .isEqualTo(savedEvent.getInitialDate(

                   Assertions.assertThat(response.getFinalDate
                                   .isNotNul
                                   .isEqualTo(savedEvent.getFinalDate(

                   Assertions.assertThat(response.getImagePath
                                   .isNotNul
                                   .isNotEmpt
                                   .isEqualTo(savedEvent.getImagePath(

                   Assertions.assertThat(response.getEventModalityId
                                   .isNotNul
                                   .isEqualTo(savedEvent.getEventModalityId(

                   Assertions.assertThat(response.getLocation
                                   .isNotNul
                                   .isNotEmpt
                                   .isEqualTo(savedEvent.getLocation(

                   Assertions.assertThat(response.getPhone
                                   .isNotNul
                                   .isNotEmpt
                                   .isEqualTo(savedEvent.getPhone(

                   Assertions.assertThat(response.getSite
                                   .isNotNul
                                   .isNotEmpt
                                   .isEqualTo(savedEvent.getSite(

                   eventRepository.delete(savedEven
         

           @DisplayName("shouldFindEventsPaginados return the elements in the first pag
           @T
           public void shouldFindEventsPaginados(

                   List<Event> events = new ArrayList<>
                   int quantiEvent = 
                   int pageLength =
                   int expectedTotalPages =
                   String url = "/api/events/page?size=" + pageLeng

                   for (int i = 0; i < quantiEvent; i++
                           events.add(EventCreator.defaultEvent(
                 
                   eventRepository.saveAll(event

                   PageableResponse<Event> eventPage = testRestTemplate.exchange(url, HttpMethod.GET, nu
                                   new ParameterizedTypeReference<PageableResponse<Event>>(
                                   }).getBody

                   Assertions.assertThat(eventPage).isNotNull

                    //verify if length of eventPage is equal to ONE p
                   Assertions.assertThat(eventPage.getNumberOfElements
                                   .isEqualTo(pageLengt

                   Assertions.assertThat(eventPage.toList().get(0).getTitle
                                   .isEqualTo(events.get(0).getTitle(

                   Assertions.assertThat(eventPage.toList().get(0).getId
                                   .isNotEqualTo(events.get(1).getId(

                   Assertions.assertThat(eventPage.getTotalPages
                                   .isNotNul
                                   .isEqualTo(expectedTotalPage

                   eventRepository.deleteAll
         

           @T
           public void shouldFindEventsByPeriodo(

                   Event savedEvent = eventRepository.save(EventCreator.customizedEventTitleAndDa
                                   "Circo", "2022-09-20T19:00:00", "2022-12-20T19:00:00"

                   Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitleAndDa
                                   "Passeio Turisco", "2022-08-20T09:00:00", "2022-08-21T19:00:00"

                   String initialDateExpected = "2022-08-20T09:00:0
                   String finalDateExpected = "2022-12-20T19:00:0
                   String url = "/api/events/byDateInterval?initialDate=" + initialDateExpec
                                   + "&finalDate=" + finalDateExpect

                   List<Event> response = testRestTemplate.exchange(url, HttpMethod.GET, nu
                                   new ParameterizedTypeReference<List<Event>>(
                                   }).getBody

                   Assertions.assertThat(respon
                                   .isNotNul
                                   .isNotEmpt
                                   .hasSize(

                   Assertions.assertThat(response.get(0).getTitle
                                   .isNotNul
                                   .isNotEmpt
                                   .isEqualTo("Circo

                   Assertions.assertThat(response.get(0).getInitialDate
                                   .isNotNul
                                   .isEqualTo(savedEvent.getInitialDate(

                   Assertions.assertThat(response.get(0).getFinalDate
                                   .isNotNul
                                   .isEqualTo(savedEvent.getFinalDate(

                   Assertions.assertThat(response.get(1).getTitle
                                   .isNotNul
                                   .isNotEmpt
                                   .isEqualTo("Passeio Turisco

                   Assertions.assertThat(response.get(1).getInitialDate
                                   .isNotNul
                                   .isEqualTo(savedEvent2.getInitialDate(

                   Assertions.assertThat(response.get(1).getFinalDate
                                   .isNotNul
                                   .isEqualTo(savedEvent2.getFinalDate(

                   eventRepository.deleteAll
         

           @T
           public void shouldFindOnlyOneEventByPeriodo(

                   Event savedEvent = eventRepository.save(EventCreator.customizedEventTitleAndDa
                                   "Circo", "2022-09-20T19:00:00", "2022-12-20T19:00:00"

                   Event savedEvent2 = eventRepository.save(EventCreator.customizedEventTitleAndDa
                                   "Passeio Turisco", "2022-08-20T09:00:00", "2022-08-21T19:00:00"

                   String initialDateExpected = "2022-08-20T09:00:0
                   String finalDateExpected = "2022-08-21T19:00:0
                   String url = "/api/events/byDateInterval?initialDate=" + initialDateExpec
                                   + "&finalDate=" + finalDateExpect

                   List<Event> response = testRestTemplate.exchange(url, HttpMethod.GET, nu
                                   new ParameterizedTypeReference<List<Event>>(
                                   }).getBody

                   Assertions.assertThat(respon
                                   .isNotNul
                                   .isNotEmpt
                                   .hasSize(

                   Assertions.assertThat(response.get(0).getTitle
                                   .isNotNul
                                   .isNotEmpt
                                   .isEqualTo("Passeio Turisco

                   Assertions.assertThat(response.get(0).getInitialDate
                                   .isNotNul
                                   .isEqualTo(savedEvent2.getInitialDate(

                   Assertions.assertThat(response.get(0).getFinalDate
                                   .isNotNul
                                   .isEqualTo(savedEvent2.getFinalDate(

                   eventRepository.deleteAll
           }

// }