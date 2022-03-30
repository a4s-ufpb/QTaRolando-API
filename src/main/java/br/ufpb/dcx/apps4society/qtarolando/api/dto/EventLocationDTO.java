package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import javax.validation.constraints.NotEmpty;

public class EventLocationDTO {
    @NotEmpty(message="Preenchimento obrigatório")
    private String eventLocation;

    public EventLocationDTO() {
    }

    public EventLocationDTO(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
}
