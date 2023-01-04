package br.ufpb.dcx.apps4society.qtarolando.api.integration;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.CredentialsDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountNewDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserInfoResponse;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.UserAccountRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.service.UserAccountService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIt {

    @Autowired
    private TestRestTemplate userTestRestTemplate;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserAccountRepository userRepository;

    private static final String BASE_URL = "/api/auth/";

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
    }

    @Test
    void login_returnsStatusOk_whenSuccessful(){
        String senhaSemCriptografia = "12345678";
        UserAccountNewDTO user = new UserAccountNewDTO("wb@gmail.com", "wellington", senhaSemCriptografia);
        UserAccount savedUser = userAccountService.insert(user);

        CredentialsDTO credentials = new CredentialsDTO(user.getEmail(), senhaSemCriptografia);

        ResponseEntity<UserInfoResponse> response = userTestRestTemplate.postForEntity(
                BASE_URL+"login", credentials, UserInfoResponse.class);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(response.getBody().getEmail())
                .isEqualTo(savedUser.getEmail());

        Assertions.assertThat(response.getBody().getUsername())
                .isEqualTo(savedUser.getUsername());

    }

    @Test
    void login_returnsStatusUNAUTHORIZED_whenUserIsntRegistered(){
        String senhaSemCriptografia = "12345678";
        String email = "wb@gmail.com";
        CredentialsDTO credentials = new CredentialsDTO(email, senhaSemCriptografia);

        ResponseEntity<UserInfoResponse> response = userTestRestTemplate.postForEntity(
                BASE_URL+"login", credentials, UserInfoResponse.class);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.UNAUTHORIZED);

        Assertions.assertThat(response.getBody().getEmail())
                .isNull();

        Assertions.assertThat(response.getBody().getUsername())
                .isNull();

    }

    @Test
    void signup_returnsStatusOk_whenSuccessful(){
        UserAccountNewDTO user = new UserAccountNewDTO("wb@gmail.com", "wellington", "12345678");

        ResponseEntity<Void> response = userTestRestTemplate.postForEntity(BASE_URL+"signup", user, null);

        List<UserAccount> users = userAccountService.findAll();

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(users).hasSize(1);

        Assertions.assertThat(users.get(0).getEmail()).isEqualTo("wb@gmail.com");
    }

    @Test
    void signup_returnsStatusBADREQUEST_whenUserEmailAlreadyBeRegistred(){
        UserAccountNewDTO user = new UserAccountNewDTO("wb@gmail.com", "wellington", "12345678");
        userAccountService.insert(user);

        ResponseEntity<Void> response = userTestRestTemplate.postForEntity(BASE_URL+"signup", user, null);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void logoutUser_returnsStatusOK_whenSuccesful(){
        UserAccountNewDTO user = new UserAccountNewDTO("wb@gmail.com", "wellington", "12345678");

//        ResponseEntity<Void> responseSignup = userTestRestTemplate.postForEntity(BASE_URL+"signup", user, null);

        ResponseEntity<?> responseLogout = userTestRestTemplate.postForEntity(BASE_URL+"signout", null, null);

//        Assertions.assertThat(responseSignup.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(responseLogout.getStatusCode()).isEqualTo(HttpStatus.OK);


    }
}
