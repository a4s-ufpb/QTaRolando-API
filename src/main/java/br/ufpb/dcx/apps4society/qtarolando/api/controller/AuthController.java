package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountNewDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserInfoResponse;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
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
  public ResponseEntity<UserInfoResponse> login() {
    UserPrincipal user = UserAccountService.getUserAuthenticated();

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
  public ResponseEntity<Void> signUp(@Valid @RequestBody UserAccountNewDTO objDto) {
    UserAccount obj = service.insert(objDto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(obj.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
  }
}
