package br.ufpb.dcx.apps4society.qtarolando.api.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.CreateUserRoleDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Role;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.UserAccountRepository;

@Service
public class CreateRoleUserService {

  @Autowired
  UserAccountRepository userRepository;

  public UserAccount execute(CreateUserRoleDTO createUserRoleDTO) {

    Optional<UserAccount> userExists = userRepository.findById(createUserRoleDTO.getUserId());
    Set<Role> roles = new HashSet<>();

    if (!userExists.isPresent()) {
      throw new Error("User does not exists!");
    }

    roles = createUserRoleDTO.getIdsRoles().stream().map(role -> {
      return new Role(role);
    }).collect(Collectors.toSet());

    UserAccount user = userExists.get();

    user.setRoles(roles);

    userRepository.save(user);

    return user;

  }
}