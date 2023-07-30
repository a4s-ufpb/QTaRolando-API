package br.ufpb.dcx.apps4society.qtarolando.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "id da role", example = "1")
    private int id;

    @Schema(description = "nome da role", example = "ADMIN")
    private String name;

    public Role(int id) {
        this.id = id;
    }

}