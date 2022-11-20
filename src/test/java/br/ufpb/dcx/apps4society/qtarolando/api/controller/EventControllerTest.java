package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.service.EventService;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventServiceMock;

    @BeforeEach
    void setUp(){
        List<Event> events = new ArrayList<>() ;
        events.add(EventCreator.defaultEvent());
        PageImpl<Event> eventPage = new PageImpl<>(events);

        EventDTO eventDTO = EventCreator.defaultEventDTO();

        BDDMockito.when(eventServiceMock.listAllUsingPage(ArgumentMatchers.any()))
                .thenReturn(eventPage);

        BDDMockito.when(eventServiceMock.getAllEvents())
                .thenReturn(events);

        BDDMockito.when(eventServiceMock.getEventsByTitle(ArgumentMatchers.anyString()))
                .thenReturn(events);

        BDDMockito.when(eventServiceMock.getEventsByCategoryId(ArgumentMatchers.anyInt()))
                .thenReturn(events);

        BDDMockito.when(eventServiceMock.getEventsByEventModalityId(ArgumentMatchers.anyInt()))
                .thenReturn(events);

        BDDMockito.when(eventServiceMock.getEventsByDateRange(
                        "2022-09-20T19:00:00", "2022-09-27T16:00:00"))
                .thenReturn(events);

        BDDMockito.doNothing().when(eventServiceMock).createEvent(eventDTO);

        BDDMockito.doNothing().when(eventServiceMock).updateEvent(1, eventDTO);

        BDDMockito.doNothing().when(eventServiceMock).deleteEvent(ArgumentMatchers.anyInt());

    }

    @Test
    void listAll_ReturnsListOfEventInsidePage(){
        String expectedSubtitle = EventCreator.defaultEvent().getSubtitle();

        Page<Event> eventPage = eventController.listAll(null).getBody();

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

        List<Event> events = eventController.getAllEvents();

        Assertions.assertThat(events)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(events.get(0).getDescription())
                .isEqualTo(expectedDescription);
    }

    @Test
    void getEventsByCategoryId_ReturnsListOfEvents(){
        int expectedCategoryId = EventCreator.defaultEvent().getCategoryId();

        List<Event> events = eventController.getEventsByCategoryId(expectedCategoryId);

        Assertions.assertThat(events)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(events.get(0).getCategoryId())
                .isEqualTo(expectedCategoryId)
                .isNotNull();
    }

    @Test
    void getEventsByCategoryId_ReturnsEmptyListOfEvent(){
        BDDMockito.when(eventServiceMock.getEventsByCategoryId(ArgumentMatchers.anyInt()))
                .thenReturn(Collections.emptyList());

        int expectedCategoryId = EventCreator.defaultEvent().getCategoryId();

        List<Event> events = eventController.getEventsByCategoryId(expectedCategoryId);

        Assertions.assertThat(events)
                .isNotNull()
                .hasSize(0)
                .isEmpty();

    }

    @Test
    void getEventByTitle_ReturnsEvent(){
        String expectedTitle = EventCreator.defaultEvent().getTitle();

        List<Event> events = eventController.getEventsByTitle(expectedTitle);

        Assertions.assertThat(events)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(events.get(0).getTitle())
                .isNotNull()
                .isEqualTo(expectedTitle);

    }

    @Test
    void getEventByTitle_ReturnsEmptyListOfEvent(){
        BDDMockito.when(eventServiceMock.getEventsByTitle(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        String expectedTitle = EventCreator.defaultEvent().getTitle();

        List<Event> events = eventController.getEventsByTitle(expectedTitle);

        Assertions.assertThat(events)
                .isNotNull()
                .hasSize(0)
                .isEmpty();

    }

    @Test
    void getEventsByModality_ReturnsListOfEvents(){
        int expectedModalityId = EventCreator.defaultEvent().getEventModalityId();

        List<Event> events = eventController.getEventsByModality(expectedModalityId);

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

        List<Event> events = eventController.getEventsByDateRange(initialDateExpected, finalDateExpected);

        Assertions.assertThat(events)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(events.get(0).getTitle())
                .isNotNull()
                .isEqualTo(expectedTitle);


    }

    @Test
    void createEvent_doesNotThrowAnyException(){
        EventDTO eventDTO = EventCreator.defaultEventDTO();

        Assertions.assertThatCode(() -> eventController.createEvent(eventDTO)).doesNotThrowAnyException();

    }

    @Test
    void updateEvent_doesNotThrowAnyException(){
        EventDTO eventDTO = EventCreator.defaultEventDTO();
        int idToBeReplaced = 1;

        Assertions.assertThatCode(() -> eventController.updateEvent(idToBeReplaced, eventDTO)).doesNotThrowAnyException();

    }

    @Test
    void deleteEvent_doesNotThrowAnyException(){
        int idToBedeleted = 1;

        Assertions.assertThatCode(() -> eventController.deleteEvent(idToBedeleted)).doesNotThrowAnyException();

    }
}
