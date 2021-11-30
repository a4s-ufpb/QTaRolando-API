package br.ufpb.dcx.apps4society.qtarolando.api.service;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountNewDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService implements UserDetailsService {
    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private UserAccountRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if (email == null || email.isEmpty()){
            throw new RuntimeException("Informe o email!");
        } else if (repo.findByEmail(email) == null){
            throw new UsernameNotFoundException("Usuário não Encontrado");
        }

        return repo.findByEmail(email);
    }

    public UserAccount fromDTO(UserAccountDTO objDto){
        return new UserAccount(objDto.getEmail(),objDto.getUserName(),null);
    }

    public UserAccount fromDTO(UserAccountNewDTO objDto){
        return new UserAccount(objDto.getEmail(),objDto.getUserName(),pe.encode(objDto.getPassword()));
    }
}
