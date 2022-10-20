package br.ufpb.dcx.apps4society.qtarolando.api.util;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;

import java.time.LocalDateTime;

public class EventCreator {

    public static Event defaultEvent() {
        LocalDateTime initialDate = LocalDateTime.parse("2022-09-20T19:00:00");
        LocalDateTime finalDate = LocalDateTime.parse("2022-09-27T16:00:00");

        return new Event("Praia", "subtitle",1, "description",
                initialDate, finalDate, "imagePath", 1, "location",
                "phone", "site");
    }

    public static Event customizedEvent(String title, String subtitle, Integer categoryId, String description,
                                        String initialDate, String finalDate, String imagePath,
                                        Integer eventModalityId, String location, String phone, String site){

        LocalDateTime initialDateConverted = LocalDateTime.parse(initialDate);
        LocalDateTime finalDateConverted = LocalDateTime.parse(finalDate);

        return new Event(title, subtitle,categoryId, description,
                initialDateConverted, finalDateConverted, imagePath, eventModalityId, location,
                phone, site);
    }
}
