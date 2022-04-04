package br.ufpb.dcx.apps4society.qtarolando.api.repository;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.EventLocationDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT event FROM Event event JOIN UserAccount userAccount ON event MEMBER OF userAccount.events " +
            "WHERE userAccount = :userAccount")
    Page<Event> findByUserAccount(Pageable pageable, @Param("userAccount") UserAccount userAccount);

    List<Event> findAllByTitle(String Title);

    List<Event> findAllByCategoryId(Integer CategoryId);

    @Query("SELECT event FROM Event event WHERE event.eventModalityId = :eventModalityId")
    List<Event> findAllByEventModalityId(Integer eventModalityId);

    @Query("SELECT event FROM Event event WHERE event.location = :eventlocation")
    List<Event> findAllByEventLocation(String eventlocation);

    List<Event> findAllEventsByData(String eventData);
}
