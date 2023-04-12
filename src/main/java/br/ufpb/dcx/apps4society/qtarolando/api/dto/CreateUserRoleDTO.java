package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRoleDTO {

    @Schema(description = "UUID do usuário", example = "93871b30-53aa-42fd-881e-5cd189787f29")
    private UUID userId;

    @Schema(description = "id das roles que o usuário terá", example = "[1,2]")
    private List<Integer> idsRoles;

}