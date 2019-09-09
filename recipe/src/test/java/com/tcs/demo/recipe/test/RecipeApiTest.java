/**
 * 
 */
package com.tcs.demo.recipe.test;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.tcs.demo.recipe.RecipeApplication;
import com.tcs.demo.recipe.bean.Recipe;
import com.tcs.demo.recipe.bean.Recipe.PeopleGroup;
import com.tcs.demo.recipe.repository.RecipeRespository;
import com.tcs.demo.recipe.service.RecipeService;
import com.tcs.demo.recipe.service.RecipeServiceImpl;
/**
 * @author Dhiraj
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RecipeApplication.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RecipeApiTest {


	@LocalServerPort
	private int port;

	@Mock
	RecipeRespository recipeRepository;

	@InjectMocks
	RecipeService recipeServie = new RecipeServiceImpl();

	@Autowired
	private TestRestTemplate restTemplate;


	@Test
	public void testForRecipeName() throws Exception{
		Recipe recipe = new Recipe();
		recipe.setRcpName("Caponata Pasta");
	
		String base64Creds = Base64.getEncoder() 
				.encodeToString(("admin:password") .getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<Recipe> response = restTemplate
				.exchange(new URL("http://localhost:" + port + "/kitchenworld/api/recipes/1").toString(), HttpMethod.GET, request, Recipe.class);

		assertEquals(recipe.getRcpName(), response.getBody().getRcpName());
	}
	
	@Test
	public void testForRecipeSize() throws Exception{
		String base64Creds = Base64.getEncoder() 
				.encodeToString(("admin:password") .getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate
				.exchange(new URL("http://localhost:" + port + "/kitchenworld/api/recipes/size").toString(), HttpMethod.GET, request, String.class);


		assertEquals("7", response.getBody());
	}
}
