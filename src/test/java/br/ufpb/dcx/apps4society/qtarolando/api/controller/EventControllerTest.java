package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.service.EventService;
import br.ufpb.dcx.apps4society.qtarolando.api.util.EventCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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

        BDDMockito.when(eventServiceMock.listAllUsingPage(ArgumentMatchers.any()))
                .thenReturn(eventPage);

        BDDMockito.when(eventServiceMock.getAllEvents())
                .thenReturn(events);

        BDDMockito.when(eventServiceMock.getEventsByTitle(ArgumentMatchers.anyString()))
                .thenReturn(events);
    }

    @Test
    void listAllUsingPage_ReturnsListOfEventInsidePage(){
        String expectedSubtitle = EventCreator.defaultEvent().getSubtitle();

        Page<Event> eventPage = eventController.list(null).getBody();

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
}
