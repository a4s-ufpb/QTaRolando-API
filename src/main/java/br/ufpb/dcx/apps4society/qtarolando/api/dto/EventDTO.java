package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.EventModality;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

public class EventDTO{

    @NotEmpty(message="Preenchimento obrigatório")
    private String title;

    @NotEmpty(message="Preenchimento obrigatório")
    private String subtitle;

    @NotEmpty(message="Preenchimento obrigatório")
    private Integer categoryId;

    @NotEmpty(message="Preenchimento obrigatório")
    @Column(length = 2000)
    private String description;

    @NotEmpty(message="Preenchimento obrigatório")
    private LocalDateTime initialDate;

    @NotEmpty(message="Preenchimento obrigatório")
    private LocalDateTime finalDate;

    @NotEmpty(message="Preenchimento obrigatório")
    private Integer eventModalityId;

    @NotEmpty(message="Preenchimento obrigatório")
    private String imagePath;

    @NotEmpty(message="Preenchimento obrigatório")
    private String location;

    private String phone;

    private String site;

    public EventDTO(){}

    public EventDTO(String title,String subtitle, Integer categoryId, String description, LocalDateTime initialDate,
                    LocalDateTime finalDate, Integer eventModalityId,String imagePath, String location, String phone,
                    String site) {
        this.title = title;
        this.subtitle = subtitle;
        this.categoryId = categoryId;
        this.description = description;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.eventModalityId = EventModality.validate(eventModalityId);
        this.imagePath = imagePath;
        this.location = location;
        this.phone = phone;
        this.site = site;
    }

    //construtor temporario
    public EventDTO(String title, String subtitle, Integer categoryId, String description,
                 String imagePath, Integer eventModalityId,
                 String location, String phone, String site) {
        this.title = title;
        this.subtitle = subtitle;
        this.categoryId = categoryId;
        this.description = description;
        this.imagePath = imagePath;
        this.eventModalityId = eventModalityId;
        this.location = location;
        this.phone = phone;
        this.site = site;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getEventModalityId() {
        return eventModalityId;
    }

    public void setEventModalityId(Integer eventModalityId) {;
        this.eventModalityId = EventModality.validate(eventModalityId);
    }

    public EventModality getEventModality(){
        return EventModality.toEnum(eventModalityId);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
