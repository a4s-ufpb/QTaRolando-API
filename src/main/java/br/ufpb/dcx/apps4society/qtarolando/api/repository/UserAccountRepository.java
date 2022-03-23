package br.ufpb.dcx.apps4society.qtarolando.api.repository;

import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

    @Transactional(readOnly=true)
    UserAccount findByEmail(String Email);

    @Transactional(readOnly=true)
    Optional<UserAccount> findById(Integer id);

    @Transactional(readOnly=true)
    Page<UserAccount> findAll(Pageable pageable);

    @Transactional(readOnly=true)
    UserAccount findByUserName(String userName);
}
