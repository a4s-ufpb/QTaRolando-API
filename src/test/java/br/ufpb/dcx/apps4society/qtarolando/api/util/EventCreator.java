package br.ufpb.dcx.apps4society.qtarolando.api.util;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;

import java.time.LocalDateTime;

public class EventCreator {

    private static final String title = "Praia";
    private static final String subtitle = "subtitle";
    private static final int categoryId = 1;
    private static final String description = "description";
    private static final LocalDateTime initialDate = LocalDateTime.parse("2022-09-20T19:00:00");
    private static final LocalDateTime finalDate = LocalDateTime.parse("2022-09-27T16:00:00");
    private static final String imagePath = "imagePath";
    private static final int eventModality = 1;
    private static final String location = "location";
    private static final String phone = "phone";
    private static final String site = "site";


    public static EventDTO defaultEventDTO(){
        return new EventDTO(title, subtitle, categoryId, description,
                initialDate, finalDate, imagePath, eventModality, location,
                phone, site);
    }

    public static Event defaultEvent() {
        return new Event(title, subtitle, categoryId, description,
                initialDate, finalDate, imagePath, eventModality, location,
                phone, site);
    }

    public static Event customizedEvent(String title, String subtitle, Integer categoryId, String description,
                                        String initialDate, String finalDate, String imagePath,
                                        Integer eventModalityId, String location, String phone, String site) {

        LocalDateTime initialDateConverted = LocalDateTime.parse(initialDate);
        LocalDateTime finalDateConverted = LocalDateTime.parse(finalDate);

        return new Event(title, subtitle, categoryId, description,
                initialDateConverted, finalDateConverted, imagePath, eventModalityId, location,
                phone, site);
    }

    public static Event customizedEventTitle(String setTitle) {

        return new Event(setTitle, subtitle, categoryId, description,
                initialDate, finalDate, imagePath, eventModality, location,
                phone, site);
    }

    public static Event customizedEventTitleAndDate(String setTitle, String setInitialDate, String setFinalDate){

        LocalDateTime initialDateConverted = LocalDateTime.parse(setInitialDate);
        LocalDateTime finalDateConverted = LocalDateTime.parse(setFinalDate);

        return new Event(setTitle, subtitle, categoryId, description,
                initialDateConverted, finalDateConverted, imagePath, eventModality, location,
                phone, site);
    }
}
