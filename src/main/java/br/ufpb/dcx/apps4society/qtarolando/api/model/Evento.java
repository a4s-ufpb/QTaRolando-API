package br.ufpb.dcx.apps4society.qtarolando.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "evento")
public class Evento {

	@Id
	@GeneratedValue
	private int id;
	private int categoryId;
	@Column(length = 2000)
	private String description;
	private LocalDateTime initialDate;
	private LocalDateTime finalDate;
	private String imagePath;
	private String location;
	private String phone;
	private String punchLine1;
	private String punchLine2;
	private String site;
	private String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
