package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "O email do usuário", example = "teste@gmail.com")
    private String email;

    @NotBlank
    @Schema(description = "A senha do usuário", example = "12345678")
    private String password;
}
