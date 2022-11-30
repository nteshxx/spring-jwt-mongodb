package com.auth.jwtserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
public class AuthControllerTests {

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
	public void shouldNotSignup() {
		try {
			mockMvc.perform(post("/api/auth/signup")
					.content("{\n\"username\": \"nitesh\",\n\"email\": \"nteshxx@gmail.com\",\n\"password\": \"password\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.error").value("Username Is Already Taken"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(3)
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
	
	@Test
	@Order(4)
	public void shouldNotGetRefreshToken() {
		try {
			mockMvc.perform(post("/api/auth/refresh-token")
					.content("{\n\"refreshToken\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MzU0ZTFhYjQwZDE3NjMwYjRmNWM1MzMiLCJ0b2tlbklkIjoiNjM1NGU0YTA0MGQxNzYzMGI0ZjVjNTM5IiwiaXNzIjoiTXlBcHAiLCJleHAiOjE2NjkwOTk5MzYsImlhdCI6MTY2NjUwNzkzNn0.dKeX0hn61gAYgC1iv29EACspdDfCCBzmPo3F8SMtHyUARNbkTKCC15WQ4BDoB_23R5QizZuNBsSb80AWgASF9w\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
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
	public void shouldNotGetAccessToken() {
		try {
			mockMvc.perform(post("/api/auth/access-token")
					.content("{\n\"refreshToken\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MzU0ZTFhYjQwZDE3NjMwYjRmNWM1MzMiLCJ0b2tlbklkIjoiNjM1NGU0YTA0MGQxNzYzMGI0ZjVjNTM5IiwiaXNzIjoiTXlBcHAiLCJleHAiOjE2NjkwOTk5MzYsImlhdCI6MTY2NjUwNzkzNn0.dKeX0hn61gAYgC1iv29EACspdDfCCBzmPo3F8SMtHyUARNbkTKCC15WQ4BDoB_23R5QizZuNBsSb80AWgASF9w\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("Invalid Token"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(6)
	public void shouldNotLogout() {
		try {
			mockMvc.perform(post("/api/auth/logout")
					.content("{\n\"refreshToken\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MzU0ZTFhYjQwZDE3NjMwYjRmNWM1MzMiLCJ0b2tlbklkIjoiNjM1NGU0YTA0MGQxNzYzMGI0ZjVjNTM5IiwiaXNzIjoiTXlBcHAiLCJleHAiOjE2NjkwOTk5MzYsImlhdCI6MTY2NjUwNzkzNn0.dKeX0hn61gAYgC1iv29EACspdDfCCBzmPo3F8SMtHyUARNbkTKCC15WQ4BDoB_23R5QizZuNBsSb80AWgASF9w\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("Invalid Token"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(7)
	public void shouldNotLogoutAll() {
		try {
			mockMvc.perform(post("/api/auth/logout-all")
					.content("{\n\"refreshToken\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MzU0ZTFhYjQwZDE3NjMwYjRmNWM1MzMiLCJ0b2tlbklkIjoiNjM1NGU0YTA0MGQxNzYzMGI0ZjVjNTM5IiwiaXNzIjoiTXlBcHAiLCJleHAiOjE2NjkwOTk5MzYsImlhdCI6MTY2NjUwNzkzNn0.dKeX0hn61gAYgC1iv29EACspdDfCCBzmPo3F8SMtHyUARNbkTKCC15WQ4BDoB_23R5QizZuNBsSb80AWgASF9w\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("Invalid Token"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected String validRefreshToken = "";
	
	@Test
	@Order(8)
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
	@Order(9)
	public void shouldGetRefreshToken() {
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
			
			mockMvc.perform(post("/api/auth/refresh-token")
					.content("{\n\"refreshToken\": \"" + validRefreshToken + "\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Successfully Generated New Refresh Token"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(10)
	public void shouldGetAccessToken() {
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
			
			mockMvc.perform(post("/api/auth/access-token")
					.content("{\n\"refreshToken\": \"" + validRefreshToken + "\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Successfully Generated New Access Token"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(11)
	public void shouldLogout() {
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
			
			mockMvc.perform(post("/api/auth/logout")
					.content("{\n\"refreshToken\": \"" + validRefreshToken + "\"\n}")
					.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Successfully Logged Out"))
				.andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(12)
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
	@Order(13)
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
