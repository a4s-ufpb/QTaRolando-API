//package br.ufpb.dcx.apps4society.qtarolando.api.service;
//
//import br.ufpb.dcx.apps4society.qtarolando.api.dto.UserAccountNewDTO;
//import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
//import br.ufpb.dcx.apps4society.qtarolando.api.model.Role;
//import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
//import br.ufpb.dcx.apps4society.qtarolando.api.repository.RoleRepository;
//import br.ufpb.dcx.apps4society.qtarolando.api.repository.UserAccountRepository;
//import br.ufpb.dcx.apps4society.qtarolando.api.security.UserPrincipal;
//import br.ufpb.dcx.apps4society.qtarolando.api.util.EventCreator;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.BDDMockito;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static br.ufpb.dcx.apps4society.qtarolando.api.service.UserAccountService.getUserAuthenticated;
//
//@ExtendWith(SpringExtension.class)
//public class UserAccountServiceTest {
//
//    @Mock
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Mock
//    private UserAccountRepository userRepositoryMock;
//
//    @Mock
//    private RoleRepository roleRepository;
//
//    @InjectMocks
//    private UserAccountService userAccountService;
//
//    @BeforeEach
//    void setUp(){
//        List<Event> events = new ArrayList<>();
//        events.add(EventCreator.defaultEvent());
//        Role role = new Role(1, "ADMIN");
//        Set<Role> roles = new HashSet<>();
//        roles.add(role);
//        UserAccount user = new UserAccount("test@test.com", "test", "12345678", events, roles);
//
//        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(UserAccount.class)))
//                .thenReturn(user);
//
//        BDDMockito.when(userRepositoryMock.findByEmail(ArgumentMatchers.anyString()))
//                .thenReturn(user);
//
//    }
//
//    @Test
//    void insertUser(){
//        UserAccountNewDTO user = new UserAccountNewDTO("test@test.com", "test", "12345678");
//        UserAccount savedUser = userAccountService.insert(user);
//
//        Assertions.assertThat(savedUser)
//                .isNotNull();
//
//        Assertions.assertThat(savedUser.getEmail())
//                .isNotNull()
//                .isEqualTo(user.getEmail());
//
//        Assertions.assertThat(savedUser.getUsername())
//                .isNotNull()
//                .isEqualTo(user.getUsername());
//    }
//
//    @Test
//    void findByEmail(){
//        String expectedEmail = "test@test.com";
//        UserPrincipal user = getUserAuthenticated();
//
//        UserAccount userFound = userAccountService.findByEmail(expectedEmail);
//
//        Assertions.assertThat(userFound.getEmail())
//                .isNotNull()
//                .isEqualTo(expectedEmail);
//    }
//}
