package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import javax.validation.constraints.NotEmpty;

public class EventTitleDTO {
    @NotEmpty(message="Preenchimento obrigatório")
    private String title;

    public EventTitleDTO() {
    }

    public EventTitleDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
