package br.ufpb.dcx.apps4society.qtarolando.api;

import br.ufpb.dcx.apps4society.qtarolando.api.model.Evento;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class QTaRolandoAPIApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

	@Order(1)
	@Test
	@DisplayName("Deve cadastrar um evento")
	void createEvent() throws Exception {
		Evento evento = construirEventoPorJson();

		mockMvc.perform(MockMvcRequestBuilders
						.post("/api/eventos")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(evento))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Order(2)
	@Test
	@DisplayName("Deve listar todos os eventos cadastrados no sistema")
	void getAllEvents() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/eventos"))
						.andExpect(status().isOk())
						.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
	}

	@Order(3)
	@Test
	@DisplayName("Deve encontrar um evento pelo id")
	void getEventById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/eventos/{id}",1))
						.andExpect(status().isOk())
						.andExpect(MockMvcResultMatchers.jsonPath("id").value(1));
	}

	@Order(4)
	@Test
	@DisplayName("Atualizando um evento")
	void updateEvent() throws Exception {
//		Evento evento = construirEventoPorJson();
//
//		mockMvc.perform(MockMvcRequestBuilders
//						.put("/api/eventos/{id}")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(evento))
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk());
	}

	@Order(5)
	@Test
	@DisplayName("Deletando um evento")
	void deleteEvent() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/api/eventos/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	public Evento construirEventoPorJson() throws IOException {
		String url = "src/test/java/br/ufpb/dcx/apps4society/qtarolando/api/json/evento.json";
		Evento evento =  objectMapper.readValue(new File(url), Evento.class);
		return evento;
	}

}
