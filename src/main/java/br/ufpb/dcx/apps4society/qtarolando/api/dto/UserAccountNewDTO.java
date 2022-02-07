package br.ufpb.dcx.apps4society.qtarolando.api.dto;

import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.Profile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserAccountNewDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message="Preenchimento obrigatório")
    @Email(message="Email inválido")
    private String email;

    @NotEmpty(message="Preenchimento obrigatório")
    @Size(min = 3, max = 20, message = "Usuário deve conter entre 3 a 20 caracteres")
    private String userName;

    @NotEmpty(message="Preenchimento obrigatório")
    @Size(min = 5, message = "Senha deve conter no minimo 20 caracteres")
    private String password;

    private Set<Integer> profiles = new HashSet<>();

    public UserAccountNewDTO(){}

    public UserAccountNewDTO(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Profile> getProfiles() {
        return this.profiles.stream().map(profile -> Profile.toEnum(profile)).collect(Collectors.toSet());
    }

    public void setProfiles(Set<Integer> profiles) {
        this.profiles = profiles;
    }
}
