package br.ufpb.dcx.apps4society.qtarolando.api.service;

import br.ufpb.dcx.apps4society.qtarolando.api.security.UserAccountSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static UserAccountSS authenticated() {
        try {
            return (UserAccountSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            return null;
        }
    }
}
