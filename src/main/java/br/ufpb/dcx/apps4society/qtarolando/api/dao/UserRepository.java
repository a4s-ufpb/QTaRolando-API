package br.ufpb.dcx.apps4society.qtarolando.api.dao;

import br.ufpb.dcx.apps4society.qtarolando.api.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findByEmail(String Email);
}
