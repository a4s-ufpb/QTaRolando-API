package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Role;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserAccountDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Size(min = 3, max = 20, message = "Usuário deve conter entre 3 a 20 caracteres")
    private String username;

    private List<Role> roles = new ArrayList<>();

    public UserAccountDTO() {
    }

    public UserAccountDTO(String email, String userName, String password) {
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

    public List<Role> getProfiles() {
        return this.roles;
    }

    public void setProfiles(List<Role> roles) {
        this.roles = roles;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }
}
