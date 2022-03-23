package br.ufpb.dcx.apps4society.qtarolando.api.model;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountNewDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    @Column(unique = true)
    private String email;

    @Size(min = 3, max = 20, message = "Usuário deve conter entre 3 a 20 caracteres")
    private String userName;

    @Size(min = 5, message = "Senha deve conter no minimo 20 caracteres")
    @JsonIgnore
    private String password;

    @OneToMany
    @JoinColumn(name = "user_account_id")
    private List<Event> events = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PROFILES")
    private Set<Integer> profiles = new HashSet<>();

    public UserAccount(){
    }

    public UserAccount(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public UserAccount(UserAccountNewDTO userAccountNewDTO){
        this.email = userAccountNewDTO.getEmail();
        this.userName = userAccountNewDTO.getUserName();
        this.password = userAccountNewDTO.getPassword();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setUserName(String usuario) {
        this.userName = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Set<Profile> getProfiles() {
        return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(Profile profile) {
        profiles.add(profile.getCod());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount)) return false;
        UserAccount that = (UserAccount) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
