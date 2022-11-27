package br.ufpb.dcx.apps4society.qtarolando.api.model;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.EventModality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;
	private String subtitle;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "event_categories", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories = new ArrayList<>();

	@Lob
	private String description;

	private LocalDateTime initialDate;
	private LocalDateTime finalDate;

	@Lob
	private String imagePath;
	private String modality;
	private String location;
	private String phone;
	private String site;

	public Event(EventDTO eventDTO) {
		this.title = eventDTO.getTitle();
		this.subtitle = eventDTO.getSubtitle();
		this.categories = eventDTO.getCategories();
		this.description = eventDTO.getDescription();
		this.initialDate = eventDTO.getInitialDate();
		this.finalDate = eventDTO.getFinalDate();
		this.imagePath = eventDTO.getImagePath();
		this.modality = eventDTO.getModality();
		this.location = eventDTO.getLocation();
	}

	public void setModalityId(String modality) {
		this.modality = EventModality.validate(modality);
	}

	public EventModality getModality() {
		return EventModality.toEnum(modality);
	}

}
