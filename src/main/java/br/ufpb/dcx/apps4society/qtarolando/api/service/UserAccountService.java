package br.ufpb.dcx.apps4society.qtarolando.api.service;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountNewDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.Profile;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.UserAccountRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.security.UserAccountSS;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.AuthorizationException;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.DataIntegrityException;
import br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserAccountService {
    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private UserAccountRepository repo;

    public UserAccount find(Integer id) {
        UserAccountSS user = getUserAuthenticated();
        if (user==null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
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

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionados");
        }
    }

    public List<UserAccount> findAll() {
        return (List<UserAccount>) repo.findAll();
    }

    public UserAccount findByEmail(String email) {
        UserAccountSS user = getUserAuthenticated();
        if (user == null || !user.hasRole(Profile.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }

        UserAccount obj = repo.findByEmail(email);
        if (obj == null) {
            throw new ObjectNotFoundException("Usuário não encontrado!");
        }
        return obj;
    }

    public Page<UserAccount> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        Pageable pageable = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageable);
    }

    public UserAccount fromDTO(UserAccountDTO objDto){
        return new UserAccount(objDto.getEmail(),objDto.getUserName(),null);
    }

    public UserAccount fromDTO(UserAccountNewDTO objDto){
        return new UserAccount(objDto.getEmail(),objDto.getUserName(),pe.encode(objDto.getPassword()));
    }

    private void updateData(UserAccount newObj, UserAccount obj) {
        newObj.setUserName(obj.getUserName());
        newObj.setEmail(obj.getEmail());
    }

    public static UserAccountSS getUserAuthenticated() {
        try {
            return (UserAccountSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            return null;
        }
    }
}
