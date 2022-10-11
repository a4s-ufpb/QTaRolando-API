package br.ufpb.dcx.apps4society.qtarolando.api.repository;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.util.EventCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@DataJdbcTest
@DisplayName("Tests for Event Repository")
public class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void save_PersistEvent_WhenSuccessful(){
        Event eventToBeSaved = EventCreator.createEventToBeSaved();
        Event eventSaved = this.eventRepository.save(eventToBeSaved);


    }
}
