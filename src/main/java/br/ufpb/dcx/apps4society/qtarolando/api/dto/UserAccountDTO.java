package br.ufpb.dcx.apps4society.qtarolando.api.dto;


import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.Profile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class UserAccountDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty(message="Preenchimento obrigatório")
    @Email(message="Email inválido")
    private String email;

    @NotEmpty(message="Preenchimento obrigatório")
    @Size(min = 3, max = 20, message = "Usuário deve conter entre 3 a 20 caracteres")
    private String userName;

    private Set<Integer> profiles = new HashSet<>();

    public UserAccountDTO(){}

    public UserAccountDTO(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
    }

    public UserAccountDTO(UserAccount obj) {
        this.email = obj.getEmail();
        this.userName = obj.getUserName();
        this.profiles = obj.getProfiles().stream().map(profile -> profile.getCod()).collect(Collectors.toSet());
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

    public Set<Profile> getProfiles() {
        return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
    }

    public void setProfiles(Set<Integer> profiles) {
        this.profiles = profiles;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
