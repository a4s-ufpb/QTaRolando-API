package br.ufpb.dcx.apps4society.qtarolando.api.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.utils.DateValidatorSimpleDateFormat;

@Component
public class EventCustomRepository {

  @Autowired
  private EntityManager em;

  public Page<Event> find(String title, Long categoryId, String modality, String dateType,
      String initialDate,
      String finalDate, Pageable pageable) {

    String queryStr = "SELECT E FROM Event AS E JOIN Category C ON C MEMBER OF E.categories";
    String condition = " WHERE ";

    if (title != null && title != "") {
      queryStr += condition + "LOWER(CONCAT(E.title, E.subtitle)) LIKE CONCAT('%',LOWER(:title),'%')";
      condition = " AND ";
    }

    if (categoryId != null) {
      queryStr += condition + "C.id = :categoryId";
      condition = " AND ";
    }

    if (modality != null && modality != "") {
      queryStr += condition + "E.modality LIKE :modality";
      condition = " AND ";
    }

    if (dateType != null) {
      switch (dateType.toUpperCase()) {
        case "HOJE":
          queryStr += condition + "cast(E.initialDate as date) = CURRENT_DATE";
          break;
        case "AMANHA":
          queryStr += condition + "cast(E.initialDate as date) = CURRENT_DATE + 1";
          break;
        case "ESTA_SEMANA":
          queryStr += condition + "extract(week from cast(E.initialDate as date)) = extract(week from CURRENT_DATE) "
              + "AND extract(year from cast(E.initialDate as date)) = extract(year from CURRENT_DATE)";
          break;
        case "FIM_SEMANA":
          queryStr += condition
              + "extract(dow from cast(E.initialDate as date)) = 0 OR extract(dow from cast(E.initialDate as date)) = 6 "
              + "AND extract(month from cast(E.initialDate as date)) = extract(month from CURRENT_DATE) "
              + "AND extract(year from cast(E.initialDate as date)) = extract(year from CURRENT_DATE)";
          break;
        case "PROX_SEMANA":
          queryStr += condition
              + "extract(week from cast(E.initialDate as date)) = (extract(week from CURRENT_DATE) + 1) "
              + "AND extract(year from cast(E.initialDate as date)) = extract(year from CURRENT_DATE)";
          break;
        case "ESTE_MES":
          queryStr += condition
              + "extract(month from cast(E.initialDate as date)) = extract(month from CURRENT_DATE)"
              + condition + "extract(year from cast(E.initialDate as date)) = extract(year from CURRENT_DATE)";
          break;
        case "ESCOLHER_INTERVALO":
          if (DateValidatorSimpleDateFormat.isValid(initialDate)
              && DateValidatorSimpleDateFormat.isValid(finalDate)) {
            queryStr += condition
                + "(cast(E.initialDate as date) BETWEEN cast(:initialDate as date) AND cast(:finalDate as date))" +
                " OR (cast(E.finalDate as date) BETWEEN cast(:initialDate as date) AND cast(:finalDate as date))" +
                " OR (cast(E.initialDate as date) <= cast(:initialDate as date) AND cast(E.finalDate as date) >= cast(:finalDate as date))";
          }
          break;
        case "ANTIGOS":
          queryStr += condition + "cast(E.finalDate as date) < CURRENT_DATE";
          break;
      }
    }

    if (dateType == null || dateType == "") {
      queryStr += condition + "cast(E.finalDate as date) >= CURRENT_DATE";
    }

    queryStr += " ORDER BY E.initialDate ASC";
    TypedQuery<Event> typedQuery = em.createQuery(queryStr, Event.class);

    if (title != null && title != "") {
      typedQuery.setParameter("title", title);
    }

    if (categoryId != null) {
      typedQuery.setParameter("categoryId", categoryId);
    }

    if (modality != null && modality != "") {
      typedQuery.setParameter("modality", modality.toUpperCase());
    }

    if (dateType != null) {
      if (dateType.toUpperCase().equals("ESCOLHER_INTERVALO")
          && DateValidatorSimpleDateFormat.isValid(initialDate)
          && DateValidatorSimpleDateFormat.isValid(finalDate)) {
        typedQuery.setParameter("initialDate", initialDate);
        typedQuery.setParameter("finalDate", finalDate);
      }
    }

    CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

    CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
    countQuery.select(criteriaBuilder.count(countQuery.from(Event.class)));
    Long count = em.createQuery(countQuery).getSingleResult();

    typedQuery.setFirstResult(pageable.getPageNumber() < 0 ? 0 : pageable.getPageNumber());
    typedQuery.setMaxResults(pageable.getPageSize() < 0 ? 24 : pageable.getPageSize());

    List<Event> events = typedQuery.getResultList();
    Page<Event> page = (Page<Event>) new PageImpl(events, pageable, count);

    return page;
  }
}
