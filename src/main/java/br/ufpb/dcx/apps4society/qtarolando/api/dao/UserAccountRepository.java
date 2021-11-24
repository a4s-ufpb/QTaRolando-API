package br.ufpb.dcx.apps4society.qtarolando.api.dao;

import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<UserAccount, String> {

    UserAccount findByEmail(String Email);
}
