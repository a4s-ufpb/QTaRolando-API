package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserPasswordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message="Preenchimento obrigatório")
    @Size(min = 20, message = "Senha deve conter no minimo 20 caracteres")
    private String password;

    public UserPasswordDTO(){}

    public UserPasswordDTO(String password){
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
