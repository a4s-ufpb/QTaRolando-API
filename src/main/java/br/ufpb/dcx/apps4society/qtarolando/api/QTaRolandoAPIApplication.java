package br.ufpb.dcx.apps4society.qtarolando.api;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Category;
import br.ufpb.dcx.apps4society.qtarolando.api.model.Role;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.Categories;
import br.ufpb.dcx.apps4society.qtarolando.api.model.enums.Roles;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.CategoryRepository;
import br.ufpb.dcx.apps4society.qtarolando.api.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@SpringBootApplication
public class QTaRolandoAPIApplication implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	CategoryRepository categoryRepository;

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
		Role role_admin = new Role(Roles.ADMIN.getCod(), Roles.ADMIN.getDescription());
		Role role_user = new Role(Roles.USER.getCod(), Roles.USER.getDescription());

		if (!roleRepository.findByName(role_admin.getName()).isPresent()) {
			roleRepository.save(role_admin);
		}
		if (!roleRepository.findByName(role_user.getName()).isPresent()) {
			roleRepository.save(role_user);
		}

		seedCategoryTable();
	}

	private void seedCategoryTable() {
		for (Categories category : Arrays.asList(Categories.values())) {
			Category temp = new Category(category.getCod(), category.getName());

			if (!categoryRepository.findById(temp.getId()).isPresent()) {
				categoryRepository.save(temp);
			}
		}
	}
}
