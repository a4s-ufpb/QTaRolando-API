package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Role;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

public class UserAccountDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido")
    @Schema(description = "Esse é o email do usuário", example = "teste@gmail.com")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Schema(description = "Esse é o nome do usuário", example = "teste")
    private String username;

    private Set<Role> roles = new HashSet<>();

    public UserAccountDTO() {
    }

    public UserAccountDTO(String email, String userName, String password, Role role) {
        this.email = email;
        this.username = userName;
    }

    public UserAccountDTO(UserAccount obj) {
        this.email = obj.getEmail();
        this.username = obj.getUsername();
        this.roles = obj.getRoles();
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

    public Set<Role> getProfiles() {
        return this.roles;
    }

    public void setProfiles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }
}
