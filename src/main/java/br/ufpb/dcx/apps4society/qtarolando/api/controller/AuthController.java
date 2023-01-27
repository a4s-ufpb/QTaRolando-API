package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.CredentialsDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountNewDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserInfoResponse;
import br.ufpb.dcx.apps4society.qtarolando.api.security.UserPrincipal;
import br.ufpb.dcx.apps4society.qtarolando.api.security.jwt.JWTUtils;
import br.ufpb.dcx.apps4society.qtarolando.api.service.UserAccountService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/auth")
public class AuthController {

  @Autowired
  private UserAccountService service;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  private JWTUtils jwtUtils;

  @PostMapping(value = "/login")
  @Operation(summary = "Login é usado para o usuário entrar no sistema",
          description = "Feito isso ele poderá realizar operações que precisam de alguma autenticação",
          tags = {"auth"})
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Operação feita com sucesso"),
          @ApiResponse(responseCode = "400", description = "Quando o email ou a senha estão incorretos")
  })
  public ResponseEntity<UserInfoResponse> login(@Valid @RequestBody CredentialsDTO credentials) {
    Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user);

    List<String> roles = user.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(new UserInfoResponse(user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    roles));
  }

  @PostMapping(value = "/signup")
  @Operation(summary = "Sign Up é usado para o novo usuário se cadastrar",
          description = "O novo usuário não pode ter o mesmo email que um usuário já cadastrado",
          tags = {"auth"})
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Operação feita com sucesso"),
          @ApiResponse(responseCode = "400", description = "Quando um email já está cadastrado ou uma senha não contem 8 caracteres")
  })
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Void> signUp(@Valid @RequestBody UserAccountNewDTO objDto) {
    service.insert(objDto);
    return  new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/signout")
  @Operation(summary = "Sign out é usado para o usuário se deslogar do sistema",
          tags = {"auth"})
  @ApiResponse(responseCode = "200", description = "Operação feita com sucesso")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
  }
}
