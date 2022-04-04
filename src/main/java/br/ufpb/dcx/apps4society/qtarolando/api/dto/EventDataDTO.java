package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import javax.validation.constraints.NotEmpty;

public class EventDataDTO {
    @NotEmpty(message="Preenchimento obrigatório")
    private String eventDataDTO;

    public EventDataDTO() {
    }

    public EventDataDTO(String eventDataDTO) {
        this.eventDataDTO = eventDataDTO;
    }

    public String getEventData() {
        return eventDataDTO;
    }

    public void setEventdata(String eventDataDTO) {
        this.eventDataDTO = eventDataDTO;
    }
}
