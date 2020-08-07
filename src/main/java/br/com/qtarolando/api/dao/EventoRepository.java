package br.com.qtarolando.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.qtarolando.api.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer>{
	
	
}
