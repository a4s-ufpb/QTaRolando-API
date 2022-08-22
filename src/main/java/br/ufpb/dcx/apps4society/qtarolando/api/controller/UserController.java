package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.CreateUserRoleDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountNewDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserPasswordDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.security.JWTUtil;
import br.ufpb.dcx.apps4society.qtarolando.api.security.UserPrincipal;
import br.ufpb.dcx.apps4society.qtarolando.api.service.CreateRoleUserService;
import br.ufpb.dcx.apps4society.qtarolando.api.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    private UserAccountService service;

    @Autowired
    CreateRoleUserService createRoleUserService;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserAccountDTO>> findAll() {
        List<UserAccount> list = service.findAll();
        List<UserAccountDTO> listDto = list.stream().map(obj -> new UserAccountDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

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

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody UserAccountNewDTO objDto) {
        UserAccount obj = service.fromDTO(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
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

    @PostMapping(value = "/token")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserPrincipal user = UserAccountService.getUserAuthenticated();
        String token = jwtUtil.generateToken(user.getEmail());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }
}
