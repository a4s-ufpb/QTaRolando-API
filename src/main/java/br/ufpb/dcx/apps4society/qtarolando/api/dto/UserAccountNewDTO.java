package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserAccountNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Email(message = "Email inválido")
    @NotNull(message = "Preenchimento obrigatório")
    private String email;

    @NotNull(message = "Preenchimento obrigatório")
    private String username;

    @NotNull
    @Size(min = 8, message = "Usuário deve conter no mínimo 8 caracteres")
    private String password;

    private Set<String> roles;

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

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
