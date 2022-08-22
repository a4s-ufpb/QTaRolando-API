package br.ufpb.dcx.apps4society.qtarolando.api.service;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountNewDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserPasswordDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.Roles;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.UserAccountRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.security.UserPrincipal;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.AuthorizationException;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.DataIntegrityException;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAccountService {
    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private UserAccountRepository repo;

    public UserAccount find(UUID id) {
        UserPrincipal user = getUserAuthenticated();
        if (user == null || !user.hasRole(Roles.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<UserAccount> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + UserAccount.class.getName()));
    }

    @Transactional
    public UserAccount insert(UserAccount obj) {
        obj.setId(null);
        obj = repo.save(obj);
        return obj;
    }

    public UserAccount update(UserAccount obj) {
        UserAccount newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(UUID id) {
        if (!repo.findById(id).get().getEvents().isEmpty()) {
            throw new DataIntegrityException("Não é possível excluir porque há eventos relacionados");
        }
        repo.deleteById(id);
    }

    public List<UserAccount> findAll() {
        return repo.findAll();
    }

    public UserAccount findByEmail(String email) {
        UserPrincipal user = getUserAuthenticated();
        if (user == null || !user.hasRole(Roles.ADMIN) && !email.equals(user.getEmail())) {
            throw new AuthorizationException("Acesso negado");
        }

        UserAccount obj = repo.findByEmail(email);
        if (obj == null) {
            throw new ObjectNotFoundException("Usuário não encontrado!");
        }
        return obj;
    }

    public UserAccount findByUsername(String userName) {
        UserPrincipal user = getUserAuthenticated();
        if (user == null || !user.hasRole(Roles.ADMIN) && !userName.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }

        UserAccount obj = repo.findByUsername(userName);
        if (obj == null) {
            throw new ObjectNotFoundException("Usuário não encontrado!");
        }
        return obj;
    }

    public Page<UserAccount> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        Pageable pageable = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageable);
    }

    public UserAccount fromDTO(UserAccountDTO objDto) {
        return new UserAccount(objDto.getEmail(), objDto.getUsername(), null);
    }

    public UserAccount fromDTO(UserAccountNewDTO objDto) {
        objDto.setPassword(pe.encode(objDto.getPassword()));

        UserAccount userAccount = new UserAccount(objDto);
        objDto.getRoles().forEach(profile -> userAccount.addRole(profile));
        return userAccount;
    }

    private void updateData(UserAccount newObj, UserAccount obj) {
        newObj.setUsername(obj.getUsername());
        newObj.setEmail(obj.getEmail());
    }

    public UserAccount updatePassword(UserPasswordDTO userPasswordDTO) {
        UserPrincipal userAuthenticated = getUserAuthenticated();
        UserAccount userAccount = findByEmail(userAuthenticated.getEmail());

        userAccount.setPassword(pe.encode(userPasswordDTO.getPassword()));

        return repo.save(userAccount);
    }

    public void updateUserEvents(UserAccount obj) {
        UserAccount newObj = findByEmail(obj.getEmail());
        newObj.setEvents(obj.getEvents());
        repo.save(newObj);
    }

    public static UserPrincipal getUserAuthenticated() {
        try {
            return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
