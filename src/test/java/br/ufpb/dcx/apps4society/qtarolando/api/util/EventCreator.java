package br.ufpb.dcx.apps4society.qtarolando.api.util;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Category;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.Categories;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventCreator {

    private static final String title = "Praia";
    private static final String subtitle = "subtitle";
    private static final String description = "description";
    private static final LocalDateTime initialDate =
            LocalDateTime.parse("2022-09-20T19:00:00");
    private static final LocalDateTime finalDate =
            LocalDateTime.parse("2022-09-27T16:00:00");
    private static final String imagePath = "imagePath";
    private static final String modality = "PRESENCIAL";
    private static final String location = "location";
    private static final String phone = "phone";
    private static final String site = "site";

    public static EventDTO defaultEventDTO(){
        List<Category> categories = new ArrayList<>();

        Category e = new Category(Categories.ARTE.getCod(), Categories.ARTE.getName());
        categories.add(e);

        return new EventDTO(title, subtitle, categories, description,
                initialDate, finalDate, imagePath, modality, location,
                phone, site);
    }

    public static Event defaultEvent() {
        List<Category> categories = new ArrayList<>();
        Category e = new Category(5L, "Arte, Cinema e Lazer");
        categories.add(e);

        return new Event(title, subtitle, categories, description,
                initialDate, finalDate, imagePath, modality, location,
                phone, site);
    }

    public static Event customizedEvent(String title, String subtitle, List<Category>
            categories, String description,
                                        String initialDate, String finalDate, String imagePath,
                                        String modality, String location, String phone, String site) {

        LocalDateTime initialDateConverted = LocalDateTime.parse(initialDate);
        LocalDateTime finalDateConverted = LocalDateTime.parse(finalDate);

        return new Event(title, subtitle, categories, description,
                initialDateConverted, finalDateConverted, imagePath, modality,
                location,
                phone, site);
    }

    public static Event customizedEventTitle(String setTitle) {
        List<Category> categories = new ArrayList<>();
        Category e = new Category(5L, "Arte, Cinema e Lazer");
        categories.add(e);

        return new Event(setTitle, subtitle, categories, description,
                initialDate, finalDate, imagePath, modality, location,
                phone, site);
    }

    public static Event customizedEventTitleAndDate(String setTitle, String
            setInitialDate, String setFinalDate) {
        List<Category> categories = new ArrayList<>();

        Category e = new Category(5L, "Arte, Cinema e Lazer");
        categories.add(e);

        LocalDateTime initialDateConverted = LocalDateTime.parse(setInitialDate);
        LocalDateTime finalDateConverted = LocalDateTime.parse(setFinalDate);

        return new Event(setTitle, subtitle, categories, description,
                initialDateConverted, finalDateConverted, imagePath, modality, location,
                phone, site);
    }
}
