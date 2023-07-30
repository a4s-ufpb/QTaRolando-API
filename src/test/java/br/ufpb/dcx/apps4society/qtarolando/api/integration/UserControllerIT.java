//package br.ufpb.dcx.apps4society.qtarolando.api.integration;
//
//import br.ufpb.dcx.apps4society.qtarolando.api.dto.*;
//import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
//import br.ufpb.dcx.apps4society.qtarolando.api.repository.UserAccountRepository;
//import br.ufpb.dcx.apps4society.qtarolando.api.service.CreateRoleUserService;
//import br.ufpb.dcx.apps4society.qtarolando.api.service.UserAccountService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@AutoConfigureTestDatabase
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class UserControllerIT {
//
//    @Autowired
//    private TestRestTemplate userTestRestTemplate;
//
//    @Autowired
//    private UserAccountService userAccountService;
//
//    @Autowired
//    private CreateRoleUserService createRoleUserService;
//
//    @Autowired
//    private UserAccountRepository userRepository;
//
//    private static final String BASE_URL = "/api/users";
//
//    @BeforeEach
//    void setUp(){
//        userRepository.deleteAll();
//    }
//
//    @Test
//    void findByEmail_returnsStatusOK_whenSuccessful(){
//        String passwordWithoutCryptography = "12345678";
//        UserAccountNewDTO user = new UserAccountNewDTO("test@gmail.com", "test", passwordWithoutCryptography);
//        UserAccount savedUser = userAccountService.insert(user);
//
//        String expectedEmail = savedUser.getEmail();
//
//        CredentialsDTO credentials = new CredentialsDTO(savedUser.getEmail(), passwordWithoutCryptography);
//
//        ResponseEntity<UserInfoResponse> responseLogin = userTestRestTemplate.postForEntity(
//                "/api/auth/login", credentials, UserInfoResponse.class);
//
//        ResponseEntity<UserAccount> response = userTestRestTemplate.getForEntity(
//                BASE_URL+ "/email?value=test@gmail.com", UserAccount.class);
//
//        Assertions.assertThat(responseLogin.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//
//    @Test
//    void findByUserName_returnsStatusOK_whenSuccessful(){
//
//        ResponseEntity<UserAccount> response = userTestRestTemplate.getForEntity(
//                BASE_URL+ "/userName?value=test", UserAccount.class);
//
////        ResponseEntity<UserAccount> responseee = userTestRestTemplate.exchange(
////                "/api/users/email?value=test@gmail.com", HttpMethod.GET, null,
////                ResponseEntity<UserAccount>());
//
//
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//
//    @Test
//    void role_returns(){
//        UserAccountNewDTO user = new UserAccountNewDTO("test@gmail.com", "test", "12345678");
//        UserAccount savedUser = userAccountService.insert(user);
//        List<Integer> idsRoles = new ArrayList<>();
//        idsRoles.add(1);
//
//        CreateUserRoleDTO createUserRoleDTO = new CreateUserRoleDTO(savedUser.getId(), idsRoles);
//
//        UserAccount response = userTestRestTemplate.postForObject(
//                BASE_URL+ "/role", createUserRoleDTO, UserAccount.class);
//
//        Assertions.assertThat(response.getEmail()).isEqualTo(savedUser.getEmail());
//    }
//
//    @Test
//    void pass(){
//        UserAccountNewDTO user = new UserAccountNewDTO("test@gmail.com", "test", "12345678");
//        UserAccount savedUser = userAccountService.insert(user);
//        List<Integer> idsRoles = new ArrayList<>();
//        idsRoles.add(1);
//        idsRoles.add(2);
//
//        UserPasswordDTO userPasswordDTO = new UserPasswordDTO("87654321");
//        ResponseEntity<Void> response = userTestRestTemplate.patchForObject(
//                BASE_URL+ "/password", userPasswordDTO,null );
//
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//    }
//
//
//}
