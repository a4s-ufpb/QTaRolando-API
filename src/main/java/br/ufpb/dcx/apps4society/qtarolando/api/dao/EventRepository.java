package br.ufpb.dcx.apps4society.qtarolando.api.dao;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
	
}
