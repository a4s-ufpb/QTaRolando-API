package br.ufpb.dcx.apps4society.qtarolando.api.service;

import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.UserAccountRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserAccountRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount user = repo.findByEmailFetchRoles(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return UserPrincipal.create(user);
    }
}
