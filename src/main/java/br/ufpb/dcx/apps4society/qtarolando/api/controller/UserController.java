package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountNewDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="api/users")
public class UserController {

    @Autowired
    private UserAccountService service;

    @GetMapping(value="/{id}")
    public ResponseEntity<UserAccount> find(@PathVariable Integer id) {
        UserAccount obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value="/email")
    public ResponseEntity<UserAccount> find(@RequestParam(value="value") String email) {
        UserAccount obj = service.findByEmail(email);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody UserAccountNewDTO objDto) {
        UserAccount obj = service.fromDTO(objDto);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody UserAccountDTO objDto, @PathVariable Integer id) {
        UserAccount obj = service.fromDTO(objDto);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<UserAccountDTO>> findAll() {
        List<UserAccount> list = service.findAll();
        List<UserAccountDTO> listDto = list.stream().map(obj -> new UserAccountDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
}
