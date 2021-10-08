package com.jumia.country;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.jumia.controller.CountryController;

@SpringBootTest
@AutoConfigureMockMvc
class SqlliteApplicationTests {    
    
	@Autowired
	private CountryController controller;
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Autowired
	private MockMvc mockMvc;

	@Test
	 void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(post("/v1/private/api/filter").content("{}").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("{\"data\":[")));
	}
	
	@Test
	 void categorizedDefaultContentType() throws Exception {
		this.mockMvc.perform(post("/v1/private/api/categorized").content("{}").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
}
