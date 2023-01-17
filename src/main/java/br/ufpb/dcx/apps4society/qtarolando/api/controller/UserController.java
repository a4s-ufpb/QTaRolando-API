package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.CreateUserRoleDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserPasswordDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.service.CreateRoleUserService;
import br.ufpb.dcx.apps4society.qtarolando.api.service.UserAccountService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "api/users")
@Log4j2
public class UserController {

    @Autowired
    private UserAccountService service;

    @Autowired
    CreateRoleUserService createRoleUserService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserAccountDTO>> findAll() {
        List<UserAccount> list = service.findAll();
        List<UserAccountDTO> listDto = list.stream().map(obj -> new UserAccountDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    //TODO: melhorar a paginacao
    @GetMapping(value = "/page")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Page<UserAccountDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<UserAccount> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<UserAccountDTO> listDto = list.map(obj -> new UserAccountDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserAccount> findById(@PathVariable String id) {
        UserAccount obj = service.find(UUID.fromString(id));
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/email")
    public ResponseEntity<UserAccount> findByEmail(@RequestParam(value = "value") String email) {
        UserAccount obj = service.findByEmail(email);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/userName")
    public ResponseEntity<UserAccount> findByUserName(@RequestParam(value = "value") String userName) {
        UserAccount obj = service.findByUsername(userName);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("/role")
    public UserAccount role(@RequestBody CreateUserRoleDTO createUserRoleDTO) {
        return createRoleUserService.execute(createUserRoleDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody UserAccountDTO objDto, @PathVariable String id) {
        UserAccount obj = service.fromDTO(objDto);
        obj.setId(UUID.fromString(id));
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserPasswordDTO userPasswordDTO) {
        service.updatePassword(userPasswordDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}
