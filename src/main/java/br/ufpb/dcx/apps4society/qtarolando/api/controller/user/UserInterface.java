package br.ufpb.dcx.apps4society.qtarolando.api.controller.user;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.CreateUserRoleDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserPasswordDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface UserInterface {

    @Operation(summary = "Pesquisa todos os usuarios cadastrados",
            description = "Retorna todos os usuários cadastrados no sistema",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "401", description = "Quando o usuário não está logado"),
            @ApiResponse(responseCode = "403", description = "Quando o usuário não tem a role de ADMIN")
    })
    public ResponseEntity<Page<UserAccountDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "24") Integer pageSize);


    @Operation(summary = "Pesquisa pelo id do usuário",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "401", description = "Caso não esteja logado"),
            @ApiResponse(responseCode = "403", description = "Caso passe um id que não seja o seu")
    })
    public ResponseEntity<UserAccount> findById(@PathVariable String id);


    @Operation(summary = "Pesquisa pelo email do usuário",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "401", description = "Caso não esteja logado"),
            @ApiResponse(responseCode = "403", description = "Caso passe um email diferente do seu")
    })
    public ResponseEntity<UserAccount> findByEmail(@RequestParam(value = "value") String email);


    @Operation(summary = "Pesquisa pelo nome do usuário",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "401", description = "Caso não esteja logado"),
            @ApiResponse(responseCode = "403", description = "Caso passe um username diferente do seu")
    })
    public ResponseEntity<UserAccount> findByUsername(@RequestParam(value = "value") String userName);


    @Operation(summary = "Muda o papel que o usuário tem",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "400", description = "Caso a UUID seja invalida"),
            @ApiResponse(responseCode = "404", description = "Caso nao encontre o UUID ou o id da role")
    })
    public UserAccount role(@RequestBody CreateUserRoleDTO createUserRoleDTO);


    @Operation(summary = "Altera os dados de um usuário",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "400", description = "Caso não passe corretamente os dados"),
            @ApiResponse(responseCode = "403", description = "Caso tente mudar os dados que não seja do usuário logado")
    })
    public ResponseEntity<Void> update(@Valid @RequestBody UserAccountDTO objDto, @PathVariable String id);


    @Operation(summary = "Altera a senha do usuário",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "400", description = "Caso a senha tenha menos de 8 caracteres"),
            @ApiResponse(responseCode = "404", description = "Caso o usuário não esteja logado")
    })
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserPasswordDTO userPasswordDTO);


    @Operation(summary = "Deleta um usuário do sistema",
            tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operação feita com sucesso"),
            @ApiResponse(responseCode = "403", description = "Caso não tenha a role de ADMIN"),
            @ApiResponse(responseCode = "500", description = "Caso não encontre o id passado")
    })
    public ResponseEntity<Void> delete(@PathVariable String id);
}
