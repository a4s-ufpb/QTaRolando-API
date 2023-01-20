package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

//    @GetMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<List<UserAccountDTO>> findAll() {
//        List<UserAccount> list = service.findAll();
//        List<UserAccountDTO> listDto = list.stream().map(obj -> new UserAccountDTO(obj)).collect(Collectors.toList());
//        return ResponseEntity.ok().body(listDto);
//    }

    @GetMapping(value = "/page")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Pesquisa todos os usuarios cadastrados",
            description = "Retorna todos os usuários cadastrados no sistema",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "Operação feita com sucesso"),
            @ApiResponse (responseCode = "403", description = "Quando o usuário não tem a role de ADMIN ou não está logado")
    })
    public ResponseEntity<Page<UserAccountDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "24") Integer pageSize){

        Page<UserAccount> list = service.findPage(page, pageSize);
        Page<UserAccountDTO> listDto = list.map(obj -> new UserAccountDTO(obj));
        return ResponseEntity.ok().body(listDto);
    }

    //org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Unable to access lob stream
    // problema no banco Postgres quando pesquisa um user que tem um event associado a ele que não acontece no H2
    @GetMapping(value = "/{id}")
    @Operation(summary = "Pesquisa pelo id do usuário",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "401", description = "Caso não esteja logado"),
            @ApiResponse(responseCode = "403", description = "Caso passe um id que não seja o seu"),
            @ApiResponse(responseCode = "500", description = "Caso o UUID não seja valido"),
                })
    public ResponseEntity<UserAccount> findById(@PathVariable String id) {
        UserAccount obj = service.find(UUID.fromString(id));
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/email")
    @Operation(summary = "Pesquisa pelo email do usuário",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "403", description = "Caso não esteja logado ou passe um email diferente do seu")
    })
    public ResponseEntity<UserAccount> findByEmail(@RequestParam(value = "value") String email) {
        UserAccount obj = service.findByEmail(email);
        return ResponseEntity.ok().body(obj);
    }

    //TODO: consertar erro
    //NonUniqueResultException: query did not return a unique result: 2
    @GetMapping(value = "/username")
    @Operation(summary = "Pesquisa pelo nome do usuário",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "403", description = "Caso não esteja logado ou passe um username diferente do seu")
    })
    public ResponseEntity<UserAccount> findByUserName(@RequestParam(value = "value") String userName) {
        UserAccount obj = service.findByUsername(userName);
        return ResponseEntity.ok().body(obj);
    }

    //TODO:
    //O user pode mudar as roles de outros users?
    //Está mudando a role mesmo sem ter um user logado
    @PostMapping("/role")
    @Operation(summary = "Muda o papel que o usuário tem",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "500", description = "Caso passe um id que a role não esteja cadastrada")
    })
    public UserAccount role(@RequestBody CreateUserRoleDTO createUserRoleDTO) {
        return createRoleUserService.execute(createUserRoleDTO);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Altera os dados de um usuário",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "400", description = "Caso não passe corretamente os dados"),
            @ApiResponse(responseCode = "403", description = "Caso tente mudar os dados que não seja do usuário logado")
    })
    public ResponseEntity<Void> update(@Valid @RequestBody UserAccountDTO objDto, @PathVariable String id) {
        UserAccount obj = service.fromDTO(objDto);
        obj.setId(UUID.fromString(id));
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/password")
    @Operation(summary = "Altera a senha do usuário",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "400", description = "Caso a senha tenha menos de 8 caracteres"),
            @ApiResponse(responseCode = "404", description = "Caso o usuário não esteja logado")
    })
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserPasswordDTO userPasswordDTO) {
        service.updatePassword(userPasswordDTO);
        return ResponseEntity.noContent().build();
    }

    //TODO:
    //posso deletar qualquer user?
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Deleta um usuário do sistema",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "401", description = "Caso não esteja logado ou não tenha a role de ADMIN"),
            @ApiResponse(responseCode = "500", description = "Caso não encontre o id passado")
    })
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}
