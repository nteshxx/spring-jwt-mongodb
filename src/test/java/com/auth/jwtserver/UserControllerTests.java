package com.auth.jwtserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTests {

	@Autowired
	MockMvc mockMvc;
	
	@Test
	@Order(1)
	public void shouldSignup() {
		try {
			mockMvc.perform(post("/api/auth/signup")
					.content("{\n\"username\": \"nitesh\",\n\"email\": \"nteshxx@gmail.com\",\n\"password\": \"password\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.message").value("Successfully Signed Up"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(2)
	public void shouldNotGetProfile() {
		try {
			String invalidAccessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2Mzg0YmQ4ZGM4ZjkxNTI5ZWVkOTY1MTEiLCJpc3MiOiJNeUFwcCIsImV4cCI6MTY2OTc4Nzc3MiwiaWF0IjoxNjY5Nzg3NDcyfQ.JeSavorxw46X3bzhY8pmIeIhd8il-lSEb0NAnUb-m07LZDKNhPw5p2QogVLHWSVDbkzNpneoN7KSlQcq5869bQ";
			
			mockMvc.perform(get("/api/users/profile")
					.header("Authorization", "Bearer " + invalidAccessToken))
				.andDo(print())
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("Invalid Token"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(3)
	public void shouldGetProfile() {
		try {
			MvcResult result = mockMvc.perform(post("/api/auth/login")
					.content("{\n\"username\": \"nitesh\",\n\"password\": \"password\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Successfully Logged In"))
				.andReturn();
			
			String jsonString = result.getResponse().getContentAsString();
			JsonObject data = new Gson().fromJson(jsonString, JsonObject.class);
			String validAccessToken = data.get("data").getAsJsonObject().get("accessToken").getAsString();
			
			mockMvc.perform(get("/api/users/profile")
					.header("Authorization", "Bearer " + validAccessToken))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Success"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(4)
	public void shouldNotGetUserById() {
		try {
			String invalidAccessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2Mzg0YmQ4ZGM4ZjkxNTI5ZWVkOTY1MTEiLCJpc3MiOiJNeUFwcCIsImV4cCI6MTY2OTc4Nzc3MiwiaWF0IjoxNjY5Nzg3NDcyfQ.JeSavorxw46X3bzhY8pmIeIhd8il-lSEb0NAnUb-m07LZDKNhPw5p2QogVLHWSVDbkzNpneoN7KSlQcq5869bQ";
			String invalidId = "6384bd8dc8f91529eed96512277";
	
			mockMvc.perform(get("/api/users/" + invalidId)
					.header("Authorization", "Bearer " + invalidAccessToken))
				.andDo(print())
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("Invalid Token"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(5)
	public void shouldGetUserById() {
		try {
			MvcResult result = mockMvc.perform(post("/api/auth/login")
					.content("{\n\"username\": \"nitesh\",\n\"password\": \"password\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Successfully Logged In"))
				.andReturn();
			
			String jsonString = result.getResponse().getContentAsString();
			JsonObject data = new Gson().fromJson(jsonString, JsonObject.class);
			String validAccessToken = data.get("data").getAsJsonObject().get("accessToken").getAsString();
			String userId = data.get("data").getAsJsonObject().get("userId").getAsString();
			
			mockMvc.perform(get("/api/users/" + userId)
					.header("Authorization", "Bearer " + validAccessToken))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Success"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(6)
	public void shouldDenyAccessToGetUserById() {
		try {
			MvcResult result = mockMvc.perform(post("/api/auth/login")
					.content("{\n\"username\": \"nitesh\",\n\"password\": \"password\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Successfully Logged In"))
				.andReturn();
			
			String jsonString = result.getResponse().getContentAsString();
			JsonObject data = new Gson().fromJson(jsonString, JsonObject.class);
			String validAccessToken = data.get("data").getAsJsonObject().get("accessToken").getAsString();
			String someOtherUserId = data.get("data").getAsJsonObject().get("userId").getAsString() + "4567";
			
			mockMvc.perform(get("/api/users/" + someOtherUserId)
					.header("Authorization", "Bearer " + validAccessToken))
				.andDo(print())
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.error").value("You Do Not Have Permission To Access This Resouce"))
				.andExpect(jsonPath("$.message").value("Access Denied"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(7)
	public void shouldNotDeleteUserById() {
		try {
			String invalidAccessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2Mzg0YmQ4ZGM4ZjkxNTI5ZWVkOTY1MTEiLCJpc3MiOiJNeUFwcCIsImV4cCI6MTY2OTc4Nzc3MiwiaWF0IjoxNjY5Nzg3NDcyfQ.JeSavorxw46X3bzhY8pmIeIhd8il-lSEb0NAnUb-m07LZDKNhPw5p2QogVLHWSVDbkzNpneoN7KSlQcq5869bQ";
			String invalidId = "6384bd8dc8f91529eed96512277";
			
			mockMvc.perform(delete("/api/users/" + invalidId)
					.header("Authorization", "Bearer " + invalidAccessToken))
				.andDo(print())
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("Invalid Token"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(8)
	public void shouldDenyAccessToDeleteUserById() {
		try {
			MvcResult result = mockMvc.perform(post("/api/auth/login")
					.content("{\n\"username\": \"nitesh\",\n\"password\": \"password\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Successfully Logged In"))
				.andReturn();
			
			String jsonString = result.getResponse().getContentAsString();
			JsonObject data = new Gson().fromJson(jsonString, JsonObject.class);
			String validAccessToken = data.get("data").getAsJsonObject().get("accessToken").getAsString();
			String invalidUserId = data.get("data").getAsJsonObject().get("userId").getAsString() + "4567";
			
			mockMvc.perform(delete("/api/users/" + invalidUserId)
					.header("Authorization", "Bearer " + validAccessToken))
				.andDo(print())
				.andExpect(status().isForbidden())
				.andExpect(jsonPath("$.error").value("You Do Not Have Permission To Access This Resouce"))
				.andExpect(jsonPath("$.message").value("Access Denied"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(9)
	public void shouldLogoutAll() {
		try {
			MvcResult result = mockMvc.perform(post("/api/auth/login")
					.content("{\n\"username\": \"nitesh\",\n\"password\": \"password\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Successfully Logged In"))
				.andReturn();
			
			String jsonString = result.getResponse().getContentAsString();
			JsonObject data = new Gson().fromJson(jsonString, JsonObject.class);
			String validRefreshToken = data.get("data").getAsJsonObject().get("refreshToken").getAsString();
			
			mockMvc.perform(post("/api/auth/logout-all")
					.content("{\n\"refreshToken\": \"" + validRefreshToken + "\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Successfully Logged Out Of All Devices"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(10)
	public void shouldDeleteUserById() {
		try {
			MvcResult result = mockMvc.perform(post("/api/auth/login")
					.content("{\n\"username\": \"nitesh\",\n\"password\": \"password\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Successfully Logged In"))
				.andReturn();
			
			String jsonString = result.getResponse().getContentAsString();
			JsonObject data = new Gson().fromJson(jsonString, JsonObject.class);
			String validAccessToken = data.get("data").getAsJsonObject().get("accessToken").getAsString();
			String userId = data.get("data").getAsJsonObject().get("userId").getAsString();
			
			mockMvc.perform(delete("/api/users/" + userId)
					.header("Authorization", "Bearer " + validAccessToken))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Account Deleted"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
