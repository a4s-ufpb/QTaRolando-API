package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserAccountNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Size(min = 3, max = 20, message = "Usuário deve conter entre 3 a 20 caracteres")
    private String username;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Size(min = 5, message = "Senha deve conter no minimo 20 caracteres")
    private String password;

    private List<Role> roles = new ArrayList<>();

    public UserAccountNewDTO() {
    }

    public UserAccountNewDTO(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
