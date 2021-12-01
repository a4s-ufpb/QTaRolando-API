package br.ufpb.dcx.apps4society.qtarolando.api;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Event;
import br.ufpb.dcx.apps4society.qtarolando.api.model.UserAccount;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.Profile;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.EventRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@SpringBootApplication
public class QTaRolandoAPIApplication implements CommandLineRunner {
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	EventRepository eventRepository;

	public static void main(String[] args) {

		SpringApplication.run(QTaRolandoAPIApplication.class, args);

	}

	@GetMapping("/")
	@ResponseBody
	String index() {
		return "Welcome to QTaRolando-API! | VERSION: v0.0.1-SNAPSHOT";
	}

	@Override
	public void run(String... args) throws Exception {

		Event event1 = new Event(null,"Novo evento",1,"um novo evento", LocalDateTime.parse("2021-12-03T06:00:00"),LocalDateTime.parse("2021-12-02T23:59:00"),"","","","","","");
		Event event2 = new Event(null,"Novo evento",5,"um novo evento", LocalDateTime.parse("2021-12-09T15:00:00"),LocalDateTime.parse("2021-12-10T23:59:00"),"","","","","","");
		eventRepository.save(event1);
		eventRepository.save(event2);


		UserAccount user1 = new UserAccount("admin@gmail.com","admin",bCryptPasswordEncoder.encode("123"));
		UserAccount user2 = new UserAccount("manager@gmail.com","manager",bCryptPasswordEncoder.encode("321"));
		user1.addProfile(Profile.ADMIN);
		user2.addProfile(Profile.MANAGER);


		user1.getEvents().add(event1);
		user2.getEvents().add(event2);

		if(userAccountRepository.findByEmail(user1.getEmail()) == null){
			userAccountRepository.save(user1);
		}

		if(userAccountRepository.findByEmail(user2.getEmail()) == null){
			userAccountRepository.save(user2);
		}
	}
}
