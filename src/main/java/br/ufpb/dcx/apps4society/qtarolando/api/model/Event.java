package br.ufpb.dcx.apps4society.qtarolando.api.model;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.EventModality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;
	private String subtitle;
//	@Column(name = "c")
	private Integer categoryId;
	@Column(length = 2000)
	private String description;
	private LocalDateTime initialDate;
	private LocalDateTime finalDate;
	private String imagePath;
	private Integer eventModalityId;
	private String location;
	private String phone;
	private String site;


	public Event(String title, String subtitle, Integer categoryId, String description,
				 LocalDateTime initialDate, LocalDateTime finalDate, String imagePath, Integer eventModalityId,
				 String location, String phone, String site) {
		this.title = title;
		this.subtitle = subtitle;
		this.categoryId = categoryId;
		this.description = description;
		this.initialDate = initialDate;
		this.finalDate = finalDate;
		this.imagePath = imagePath;
		this.eventModalityId = eventModalityId;
		this.location = location;
		this.phone = phone;
		this.site = site;
	}

	public Event(EventDTO eventDTO){
		this.title = eventDTO.getTitle();
		this.subtitle = eventDTO.getSubtitle();
		this.categoryId = eventDTO.getCategoryId();
		this.description = eventDTO.getDescription();
		this.initialDate = eventDTO.getInitialDate();
		this.finalDate = eventDTO.getFinalDate();
		this.imagePath = eventDTO.getImagePath();
		this.eventModalityId = eventDTO.getEventModalityId();
		this.location = eventDTO.getLocation();
	}

	public Event() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public void setModalityId(Integer modalityId) {
		this.eventModalityId = EventModality.validate(modalityId);
	}

	public EventModality getModality(){
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Event)) return false;
		Event event = (Event) o;
		return id.equals(event.id) && categoryId.equals(event.categoryId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, categoryId);
	}
}
