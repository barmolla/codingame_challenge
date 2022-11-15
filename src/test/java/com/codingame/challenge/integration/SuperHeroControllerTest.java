package com.codingame.challenge.integration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import com.codingame.challenge.repository.SuperHeroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles({ "test" })
@SqlGroup({
	@Sql(value = "classpath:reset/truncate.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "classpath:init/superhero-data.sql", executionPhase = BEFORE_TEST_METHOD)
})
public class SuperHeroControllerTest {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Autowired
	private SuperHeroRepository superHeroRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldRetrieveSpiderman() throws Exception {
		final var payloadAuth = "{\"username\":\"brian\",\"password\":\"password\"}";

		this.mockMvc
			.perform(
				post("/api/v1/authenticate")
					.contentType(APPLICATION_JSON)
	                .content(payloadAuth)
			)
			.andDo(authResult -> {
				final var content = authResult.getResponse().getContentAsString();
				final Map<String, String> map = OBJECT_MAPPER.readValue(content, Map.class);
				final var accessToken = map.get("token");
				final var uuidExpected = "2d46131e-6453-11ed-81ce-0242ac120002";
				final var nameExpected = "Spiderman";

				final var getResult = this.mockMvc
			        .perform(
		        		get("/api/v1/super-hero/{uuid}", uuidExpected)
		        			.header("Authorization", "Bearer " + accessToken)
		        			.header("Content-Type", APPLICATION_JSON)
	        		)
			        .andExpect(status().isOk())
			        .andExpect(content().contentType(APPLICATION_JSON))
			        .andExpect(content().json("{'uuid':'2d46131e-6453-11ed-81ce-0242ac120002','name':'Spiderman'}"));
			});
	}

	@Test
	void shouldCreateHero() throws Exception {
		final var payloadAuth = "{\"username\":\"brian\",\"password\":\"password\"}";

		this.mockMvc
			.perform(
				post("/api/v1/authenticate")
					.contentType(APPLICATION_JSON)
	                .content(payloadAuth)
			)
			.andDo(authResult -> {
				final var payloadCreate = "{\"uuid\":\"2d46131e-6453-11ed-81ce-0242ac120003\",\"name\":\"Superman\"}";
				final var content = authResult.getResponse().getContentAsString();
				final Map<String, String> map = OBJECT_MAPPER.readValue(content, Map.class);
				final var accessToken = map.get("token");

				this.mockMvc
					.perform(
						post("/api/v1/super-hero")
							.contentType(APPLICATION_JSON)
		        			.header("Authorization", "Bearer " + accessToken)
			                .content(payloadCreate)
					)
			        .andExpect(status().isCreated())
			        .andExpect(content().contentType(APPLICATION_JSON))
			        .andExpect(content().json(payloadCreate));

				assertEquals(2, superHeroRepository.findAll().size());
			});
	}
}
