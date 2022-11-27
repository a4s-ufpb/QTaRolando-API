package br.ufpb.dcx.apps4society.qtarolando.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
