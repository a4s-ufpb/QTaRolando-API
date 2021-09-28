package br.ufpb.dcx.apps4society.qtarolando.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class QTaRolandoAPIApplication {

	public static void main(String[] args) {

		SpringApplication.run(QTaRolandoAPIApplication.class, args);

		// Criptografar senha
		System.out.println(new BCryptPasswordEncoder().encode("Senha"));
	}

	@GetMapping("/")
	@ResponseBody
	String index() {
		return "Welcome to QTaRolando-API! | VERSION: v0.0.1-SNAPSHOT";
	}
}
