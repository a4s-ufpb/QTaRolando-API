package br.ufpb.dcx.apps4society.qtarolando.api.dao;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer>{
	
}
