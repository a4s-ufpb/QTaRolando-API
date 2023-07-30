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
public class UserInfoResponse {

    @Schema(description = "O UUID é gerado automaticamente pelo sistema", example = "93871b30-53aa-42fd-881e-5cd189787f29")
    private UUID id;

    @Schema(description = "O nome de usuário", example = "teste")
    private String username;

    @Schema(description = "O email do usuário", example = "teste@gmail.com")
    private String email;

    @Schema(description = "Os papeis que o usuário pode ter no sistema", example = "ADMIN,USER")
    private List<String> roles;

}
