package br.ufpb.dcx.apps4society.qtarolando.api.util;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;

import java.time.LocalDateTime;

public class EventCreator {

    public static Event createEventToBeSaved() {
        LocalDateTime initialDate = LocalDateTime.parse("2022-09-20T19:00:00");
        LocalDateTime finalDate = LocalDateTime.parse("2022-09-27T16:00:00");

        return new Event("Praia", "subtitle",1, "description",initialDate,finalDate, "imagePath", 1,
                "location", "phone", "site");
    }
}
