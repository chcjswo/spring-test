package me.mocadev.springtest.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SecurityConfigTest {

	@Autowired
	private MockMvc mockMvc;

	@DisplayName("authentication test")
	@Test
	void authentication() throws Exception {
		// when
		ResultActions result = mockMvc.perform(get("/api/s/score"));
		String response = result.andReturn().getResponse().getContentAsString();
		System.out.println("response = " + response);

		// then
		result.andExpect(status().isUnauthorized());
	}

	@DisplayName("authorization test")
	@Test
	void authorization() throws Exception {
		// when
		ResultActions result = mockMvc.perform(get("/api/admin/score"));
		String response = result.andReturn().getResponse().getContentAsString();
		System.out.println("response = " + response);

		// then
		result.andExpect(status().isUnauthorized());
	}
}
