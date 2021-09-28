package br.ufpb.dcx.apps4society.qtarolando.api.dao;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {

    Usuario findByEmail(String Email);
}
