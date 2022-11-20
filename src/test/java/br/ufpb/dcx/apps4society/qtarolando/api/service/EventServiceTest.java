package br.ufpb.dcx.apps4society.qtarolando.api.service;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.AuthorizationException;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.ObjectNotFoundException;
import br.ufpb.dcx.apps4society.qtarolando.api.util.EventCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepositoryMock;

    @BeforeEach
    void setUp(){
        List<Event> events = new ArrayList<>() ;
        events.add(EventCreator.defaultEvent());
        events.get(0).setId(1);

        PageImpl<Event> eventPage = new PageImpl<>(events);

        BDDMockito.when(eventRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(eventPage);

        BDDMockito.when(eventRepositoryMock.findAll())
                .thenReturn(events);

        BDDMockito.when(eventRepositoryMock.findAllByTitle(ArgumentMatchers.anyString()))
                .thenReturn(events);

        BDDMockito.when(eventRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.ofNullable(events.get(0)));

        BDDMockito.when(eventRepositoryMock.findAllByCategoryId(ArgumentMatchers.anyInt()))
                .thenReturn(events);

        BDDMockito.when(eventRepositoryMock.findAllByEventModalityId(ArgumentMatchers.anyInt()))
                .thenReturn(events);

        BDDMockito.when(eventRepositoryMock.findAllByDateRange(
                        events.get(0).getInitialDate(), events.get(0).getFinalDate()))
                .thenReturn(events);

        BDDMockito.doNothing().when(eventRepositoryMock).delete(ArgumentMatchers.any(Event.class));

//        BDDMockito.when(eventRepositoryMock.save(ArgumentMatchers.any(Event.class)))
//                .thenReturn(events.get(0));


    }

    @Test
    void listAllUsingPage_ReturnsListOfEventInsidePage(){
        String expectedSubtitle = EventCreator.defaultEvent().getSubtitle();

        Page<Event> eventPage = eventService.listAllUsingPage(PageRequest.of(1,1));

        Assertions.assertThat(eventPage).isNotNull();

        Assertions.assertThat(eventPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(eventPage.toList().get(0).getSubtitle())
                .isEqualTo(expectedSubtitle);

    }

    @Test
    void getAllEvents_ReturnsListOfEvents(){
        String expectedDescription = EventCreator.defaultEvent().getDescription();

        List<Event> events = eventService.getAllEvents();

        Assertions.assertThat(events)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(events.get(0).getDescription())
                .isEqualTo(expectedDescription);
    }

    @Test
    void getEventById_ReturnsEvent(){
        int expectedId = 1;

        Event eventFound = eventService.getEventById(expectedId);

        Assertions.assertThat(eventFound).isNotNull();

        Assertions.assertThat(eventFound.getId())
                .isNotNull()
                .isEqualTo(expectedId);

    }

    @Test
    void getEventById_ThrowsObjectNotFoundException(){
        BDDMockito.when(eventRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenThrow(ObjectNotFoundException.class);

        int expectedId = 2;

        Assertions.assertThatThrownBy(() -> eventService.getEventById(expectedId))
                .isInstanceOf(ObjectNotFoundException.class);
    }

    @Test
    void getEventById_doesNotThrowAnyException(){
        int expectedId = 1;

        Assertions.assertThatCode(() -> eventService.getEventById(expectedId))
                .doesNotThrowAnyException();

    }

    @Test
    void getEventsByCategoryId_ReturnsListOfEvents(){
        int expectedCategoryId = EventCreator.defaultEvent().getCategoryId();

        List<Event> events = eventService.getEventsByCategoryId(expectedCategoryId);

        Assertions.assertThat(events)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(events.get(0).getCategoryId())
                .isEqualTo(expectedCategoryId)
                .isNotNull();
    }

    @Test
    void findAllByCategoryId_ReturnsEmptyListOfEvent(){
        BDDMockito.when(eventRepositoryMock.findAllByCategoryId(ArgumentMatchers.anyInt()))
                .thenReturn(Collections.emptyList());

        int expectedCategoryId = EventCreator.defaultEvent().getCategoryId();

        List<Event> events = eventService.getEventsByCategoryId(expectedCategoryId);

        Assertions.assertThat(events)
                .isNotNull()
                .hasSize(0)
                .isEmpty();

    }

    @Test
    void getEventByTitle_ReturnsEvent(){
        String expectedTitle = EventCreator.defaultEvent().getTitle();

        List<Event> events = eventService.getEventsByTitle(expectedTitle);

        Assertions.assertThat(events)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(events.get(0).getTitle())
                .isNotNull()
                .isEqualTo(expectedTitle);

    }

    @Test
    void findAllByTitle_ReturnsEmptyListOfEvent(){
        BDDMockito.when(eventRepositoryMock.findAllByTitle(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        String expectedTitle = EventCreator.defaultEvent().getTitle();

        List<Event> events = eventService.getEventsByTitle(expectedTitle);

        Assertions.assertThat(events)
                .isNotNull()
                .hasSize(0)
                .isEmpty();

    }

    @Test
    void getEventsByEventModalityId_ReturnsListOfEvents(){
        int expectedModalityId = EventCreator.defaultEvent().getEventModalityId();

        List<Event> events = eventService.getEventsByEventModalityId(expectedModalityId);

        Assertions.assertThat(events)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(events.get(0).getEventModalityId())
                .isEqualTo(expectedModalityId);
    }

    @Test
    void getEventsByDateRange_ReturnsListOfEvents(){
        String initialDateExpected = "2022-09-20T19:00:00";
        String finalDateExpected = "2022-09-27T16:00:00";
        String expectedTitle = "Praia";

        List<Event> events = eventService.getEventsByDateRange(initialDateExpected, finalDateExpected);

        Assertions.assertThat(events)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(events.get(0).getTitle())
                .isNotNull()
                .isEqualTo(expectedTitle);


    }

//    @Test
//    void createEvent(){
//        EventDTO eventDTO = EventCreator.defaultEventDTO();
//
//        Assertions.assertThat(eventService.createEvent(eventDTO))
//    }

    @Test
    void updateEvent_ThrowsAuthorizationException_whenDontHaveAccessToUpdate() {
        int expectedId = 1;
        EventDTO newEvent = EventCreator.defaultEventDTO();


        Assertions.assertThatThrownBy(() -> eventService.updateEvent(expectedId, newEvent))
                .isInstanceOf(AuthorizationException.class)
        ;

    }

    @Test
    void deleteEvent_ThrowsAuthorizationException_whenDontHaveAccessToRemove() {

        Assertions.assertThatThrownBy(() -> eventService.deleteEvent(1))
                .isInstanceOf(AuthorizationException.class)
        ;

    }

}
