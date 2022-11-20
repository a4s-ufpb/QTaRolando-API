package br.ufpb.dcx.apps4society.qtarolando.api.repository;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.util.EventCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest()
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setUp(){
        eventRepository.deleteAll();
    }

    @Test
    void save_persistEvent(){

        LocalDateTime initialDate = LocalDateTime.parse("2022-09-20T19:00:00");
        LocalDateTime finalDate = LocalDateTime.parse("2022-09-27T16:00:00");

        Event event = new Event();
        event.setTitle("Praia");
        event.setSubtitle("subtitle");
        event.setCategoryId(1);
        event.setDescription("description");
        event.setInitialDate(initialDate);
        event.setFinalDate(finalDate);
        event.setImagePath("imagePath");
        event.setLocation("location");
        event.setPhone("phone");
        event.setSite("site");


        Event savedEvent = eventRepository.save(event);

        assertNotNull(savedEvent);
        assertNotEquals("", savedEvent.getTitle());
        assertEquals(savedEvent.getTitle(), event.getTitle());
        assertEquals(savedEvent.getId(), event.getId());
    }

    @Test
    void save_ThrowsDataIntegrityViolationException_whenDescriptionIsBigThan2000(){
        StringBuilder description = new StringBuilder();
        int descriptionLenght = 2001;
        for(int i = 0; i < descriptionLenght; i++){
            description.append("a");
        }

        Event event = EventCreator.customizedEvent("Praia", "subtitle",1, description.toString(),
                "2022-09-20T19:00:00", "2022-09-27T16:00:00", "imagePath", 1, "location",
				"phone", "site");

        Assertions.assertThatThrownBy(() -> this.eventRepository.save(event))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void save_updatesEvent(){
        Event event = EventCreator.customizedEventTitle("Praça");
        Event savedEvent = eventRepository.save(event);

        savedEvent.setTitle("Passeio");
        Event updatedEvent = eventRepository.save(savedEvent);

        assertNotNull(savedEvent);
        assertEquals(updatedEvent.getId(), event.getId());
        assertEquals(updatedEvent.getTitle(), savedEvent.getTitle());
    }

    @Test
    void findAllByTitle_returnsListOfEvent(){
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());
        Event savedEvent2 = eventRepository.save(EventCreator.defaultEvent());

        String title = savedEvent.getTitle();
        List<Event> events = eventRepository.findAllByTitle(title);

        assertFalse(events.isEmpty());
        assertEquals(2, events.size());
        assertEquals(savedEvent.getId(), events.get(0).getId());
        assertEquals(savedEvent2.getId(), events.get(1).getId());
        assertNotEquals(savedEvent.getId(), events.get(1).getId());
        assertEquals(events.get(1).getTitle(), title);
        assertEquals(events.get(0).getTitle(), events.get(1).getTitle());
    }

    @Test
    void findAllByCategoryId_returnsListOfEvent(){
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());

        int categoryId = savedEvent.getCategoryId();
        List<Event> events = eventRepository.findAllByCategoryId(categoryId);

        assertFalse(events.isEmpty());
        assertNotNull(events.get(0));
        assertTrue(events.contains(savedEvent));
        assertEquals(events.get(0).getCategoryId(),categoryId);
    }

    @Test
    void findAllByEventModalityId_returnsListOfEvent(){
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());

        int modalityId = savedEvent.getEventModalityId();
        List<Event> events = eventRepository.findAllByEventModalityId(modalityId);

        assertFalse(events.isEmpty());
        assertNotNull(events.get(0));
        assertEquals(events.get(0).getEventModalityId(), modalityId);
    }

    @Test
    void findAllByDateRange_returnsListOfEvent(){
        Event savedEvent = eventRepository.save(EventCreator.defaultEvent());

        LocalDateTime initialDate = savedEvent.getInitialDate();
        LocalDateTime finalDate = savedEvent.getFinalDate();

        List<Event> events = eventRepository.findAllByDateRange(initialDate, finalDate);

        assertFalse(events.isEmpty());
        assertNotNull(events.get(0));
        assertEquals(events.get(0).getInitialDate(), initialDate);
        assertEquals(events.get(0).getFinalDate(), finalDate);
    }
}
