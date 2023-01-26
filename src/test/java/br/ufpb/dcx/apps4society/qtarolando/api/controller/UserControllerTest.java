package br.ufpb.dcx.apps4society.qtarolando.api.controller;

import br.ufpb.dcx.apps4society.qtarolando.api.dto.CreateUserRoleDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserPasswordDTO;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Role;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.service.CreateRoleUserService;
import br.ufpb.dcx.apps4society.qtarolando.api.service.UserAccountService;
import br.ufpb.dcx.apps4society.qtarolando.api.util.EventCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserAccountService userAccountServiceMock;

    @Mock
    CreateRoleUserService createRoleUserServiceMock;

    private static final String UUIDstring = "93871b30-53aa-42fd-881e-5cd189787f29";

    @BeforeEach
    void setUp(){
        List<Event> events = new ArrayList<>();
        events.add(EventCreator.defaultEvent());

        Role role = new Role(1, "ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        UserAccount user = new UserAccount("test@test.com", "test", "12345678", events, roles);
        user.setId(UUID.fromString(UUIDstring));

        List<UserAccount> users = new ArrayList<>();
        users.add(user);
        PageImpl<UserAccount> userPage = new PageImpl<>(users);

        BDDMockito.when(userAccountServiceMock.findPage(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
                        .thenReturn(userPage);

        BDDMockito.when(userAccountServiceMock.find(ArgumentMatchers.any()))
                .thenReturn(user);

        BDDMockito.when(userAccountServiceMock.findByEmail(ArgumentMatchers.anyString()))
                .thenReturn(user);

        BDDMockito.when(userAccountServiceMock.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(user);

        BDDMockito.when(createRoleUserServiceMock.execute(ArgumentMatchers.any(CreateUserRoleDTO.class)))
                .thenReturn(user);

        BDDMockito.when(userAccountServiceMock.update(ArgumentMatchers.any(UserAccount.class)))
                .thenReturn(user);

        BDDMockito.when(userAccountServiceMock.fromDTO(ArgumentMatchers.any(UserAccountDTO.class)))
                .thenReturn(user);

        BDDMockito.when(userAccountServiceMock.updatePassword(ArgumentMatchers.any(UserPasswordDTO.class)))
                .thenReturn(user);

        BDDMockito.doNothing().when(userAccountServiceMock).delete(user.getId());
    }

    @Test
    void findPage_ReturnsListOfUserInsidePage(){
        int page = 0;
        int pageSize = 24;

        ResponseEntity<Page<UserAccountDTO>> userPage = userController.findPage(page, pageSize);

        Assertions.assertThat(userPage.getBody())
                .isNotNull();

        Assertions.assertThat(userPage.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(userPage.getBody().toList())
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void findById_returnsStatusOK_whenSuccessful(){
        UUID expectedUUID = UUID.fromString(UUIDstring);

        ResponseEntity<UserAccount> user = userController.findById(UUIDstring);

        Assertions.assertThat(user.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(user.getBody().getId())
                .isNotNull()
                .isEqualTo(expectedUUID);
    }

    @Test
    void findByEmail_returnsStatusOK_whenSuccessful(){
        String expectedEmail = "test@test.com";

        ResponseEntity<UserAccount> user = userController.findByEmail(expectedEmail);

        Assertions.assertThat(user.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(user.getBody().getEmail())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expectedEmail);
    }

    @Test
    void findByUsername_returnsStatusOK_whenSuccessful(){
        String expectedUsername = "test";

        ResponseEntity<UserAccount> user = userController.findByEmail(expectedUsername);

        Assertions.assertThat(user.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        Assertions.assertThat(user.getBody().getUsername())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expectedUsername);
    }

    @Test
    void role_returnsStatusOK_whenSuccessful(){
        UUID expectedUUID = UUID.fromString(UUIDstring);

        List<Integer> idsRoles = Arrays.asList(1);

        CreateUserRoleDTO createUserRoleDTO = new CreateUserRoleDTO(expectedUUID, idsRoles);

        UserAccount user = userController.role(createUserRoleDTO);

        Assertions.assertThat(user)
                .isNotNull();

        Assertions.assertThat(user.getId())
                .isNotNull()
                .isEqualTo(expectedUUID);

        Assertions.assertThat(user.getRoles())
                .hasSize(1)
                .isNotNull();
    }

    @Test
    void update_doesNotThrowAnyException(){
        String expectedId = UUIDstring;

        List<Event> events = new ArrayList<>();
        Set<Role> roles = new HashSet<>();

        UserAccount userAccount = new UserAccount("test@test.com", "test", "12345678", events, roles);
        userAccount.setId(UUID.fromString(expectedId));
        UserAccountDTO userAccountDTO = new UserAccountDTO(userAccount);

        Assertions.assertThatCode(() -> userController.update(userAccountDTO, expectedId))
                .doesNotThrowAnyException();

    }

    @Test
    void updatePassword_doesNotThrowAnyException(){
        String newPassword = "87654321";

        UserPasswordDTO userPasswordDTO = new UserPasswordDTO(newPassword);

        Assertions.assertThatCode(() -> userController.updatePassword(userPasswordDTO))
                .doesNotThrowAnyException();

    }

    @Test
    void delete_doesNotThrowAnyException(){

        Assertions.assertThatCode(() -> userController.delete(UUIDstring))
                .doesNotThrowAnyException();

    }
}
