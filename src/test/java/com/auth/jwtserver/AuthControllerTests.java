package com.auth.jwtserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTests {

	@Autowired
	MockMvc mockMvc;
	
//	@Test
//	@Order(1)
//	public void shouldSignup() {
//		try {
//			mockMvc.perform(post("/api/auth/signup")
//					.content("{\n\"username\": \"nitesh\",\n\"email\": \"nteshxx@gmail.com\",\n\"password\": \"password\"\n}")
//					.contentType(MediaType.APPLICATION_JSON))
//				.andDo(print())
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.message").value("Successfully Signed Up"))
//				.andReturn();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	@Order(2)
	public void shouldNotSignup() {
		try {
			mockMvc.perform(post("/api/auth/signup")
					.content("{\n\"username\": \"nitesh\",\n\"email\": \"nteshxx@gmail.com\",\n\"password\": \"password\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.error").value("Username Is Already Taken"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(3)
	public void shouldLogin() {
		try {
			mockMvc.perform(post("/api/auth/login")
					.content("{\n\"username\": \"nitesh\",\n\"password\": \"password\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Successfully Logged In"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(4)
	public void shouldNotLogin() {
		try {
			mockMvc.perform(post("/api/auth/login")
					.content("{\n\"username\": \"nitesh\",\n\"password\": \"wrongpassword\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("Incorrect Username Or Password"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
