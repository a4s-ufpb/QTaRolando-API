package br.ufpb.dcx.apps4society.qtarolando.api.repository;

import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {

    @Transactional(readOnly = true)
    UserAccount findByEmail(String email);

    @Transactional(readOnly = true)
    Optional<UserAccount> findById(UUID id);

    @Transactional(readOnly = true)
    Page<UserAccount> findAll(Pageable pageable);

    @Transactional(readOnly = true)
    UserAccount findByUsername(String username);

    @Query("SELECT u from UserAccount u JOIN FETCH u.roles where u.email = :email ")
    UserAccount findByEmailFetchRoles(@Param("email") String email);
}
