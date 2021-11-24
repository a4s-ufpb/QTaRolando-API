package br.ufpb.dcx.apps4society.qtarolando.api.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;
	private Integer categoryId;
	@Column(length = 2000)
	private String description;
	private LocalDateTime initialDate;
	private LocalDateTime finalDate;
	private String imagePath;
	private String location;
	private String phone;
	private String site;
	private String punchLine1;
	private String punchLine2;

	public Event(){
	}

	public Event(Integer id, String title, Integer categoryId, String description, LocalDateTime initialDate, LocalDateTime finalDate, String imagePath, String location, String phone, String site, String punchLine1, String punchLine2) {
		this.id = id;
		this.title = title;
		this.categoryId = categoryId;
		this.description = description;
		this.initialDate = initialDate;
		this.finalDate = finalDate;
		this.imagePath = imagePath;
		this.location = location;
		this.phone = phone;
		this.site = site;
		this.punchLine1 = punchLine1;
		this.punchLine2 = punchLine2;
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

	public String getPunchLine1() {
		return punchLine1;
	}

	public void setPunchLine1(String punchLine1) {
		this.punchLine1 = punchLine1;
	}

	public String getPunchLine2() {
		return punchLine2;
	}

	public void setPunchLine2(String punchLine2) {
		this.punchLine2 = punchLine2;
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
