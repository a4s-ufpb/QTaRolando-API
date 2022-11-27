package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO implements Serializable {

    @NotEmpty(message = "Preenchimento obrigatório")
    private String title;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String subtitle;

    @NotEmpty(message = "Preenchimento obrigatório")
    private List<Category> categories = new ArrayList<>();

    @NotEmpty(message = "Preenchimento obrigatório")
    @Column(length = 2000)
    private String description;

    @NotEmpty(message = "Preenchimento obrigatório")
    private LocalDateTime initialDate;

    @NotEmpty(message = "Preenchimento obrigatório")
    private LocalDateTime finalDate;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String modality;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String imagePath;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String location;

    private String phone;

    private String site;
}
