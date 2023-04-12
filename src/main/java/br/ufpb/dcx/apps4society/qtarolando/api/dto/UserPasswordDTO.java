package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserPasswordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Size(min = 8, message = "Senha deve conter no minimo 8 caracteres")
    @Schema(description = "nova senha do usuário", example = "87654321")
    private String password;

    public UserPasswordDTO() {
    }

    public UserPasswordDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
