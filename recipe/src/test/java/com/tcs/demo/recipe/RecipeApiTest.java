/**
 * 
 */
package com.tcs.demo.recipe;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.tcs.demo.recipe.RecipeApplication;
import com.tcs.demo.recipe.bean.Recipe;
import com.tcs.demo.recipe.bean.Recipe.PeopleGroup;
import com.tcs.demo.recipe.builder.RecipeBuilder;
import com.tcs.demo.recipe.service.RecipeService;
/**
 * Unit test cases for Recipe Api
 * @author Dhiraj
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RecipeApplication.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RecipeApiTest {


	@LocalServerPort
	private int port;

	@Autowired
	private RecipeService recipeServie ;

	@Autowired
	private TestRestTemplate restTemplate;


	@Test
	public void testForRecipeName() throws Exception{
		
		ResponseEntity<Recipe> response = restTemplate.withBasicAuth("admin", "password")
				.getForEntity(new URL("http://localhost:" + port + "/kitchenworld/api/recipes/1").toString(), Recipe.class);

		assertEquals(recipeServie.getRecipe(1L).getRcpName(), response.getBody().getRcpName());
	}

	@Test
	public void testForRecipeNotFound() throws Exception{
		ResponseEntity<Recipe> response = restTemplate.withBasicAuth("admin", "password")
				.getForEntity(new URL("http://localhost:" + port + "/kitchenworld/api/recipes/3000").toString(), Recipe.class);

		assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
	}
	
	@Test
	public void testForRecipeSize() throws Exception{
		
		ResponseEntity<String> response = restTemplate.withBasicAuth("admin", "password")
				.getForEntity(new URL("http://localhost:" + port + "/kitchenworld/api/recipes/size").toString(),String.class);

		assertEquals(recipeServie.getAllRecipes().size(), Integer.parseInt(response.getBody()));
	}

	@Test
	public void testForAddRecipe() throws Exception {
		
		Recipe recipe = new RecipeBuilder().withName("test-recipe")
				.withCookingInstruction("test - instructions")
				.withIngredientDescription("test-ingrediens")
				.withImagePath("image/caponata-pasta.jpg")
				.withCreatedAt( LocalDateTime.now())
				.withCreatedBy(1L)
				.withSuitableFor(PeopleGroup.FIVETOTEN)
				.withIsVegetarian(true)
				.build();
		
		RequestEntity<Recipe> requestEntity = RequestEntity
				.post(new URI("http://localhost:" + port + "/kitchenworld/api/recipes/"))				
				.body(recipe);
		
		ResponseEntity<Recipe> response = restTemplate.withBasicAuth("admin", "password")
				.exchange(requestEntity, Recipe.class);
		
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
	}
	
	@Test
	public void testForUpdateRecipe() throws Exception{
		Recipe recipe = new RecipeBuilder().withId(2L)
				.withName("Updated Coconut dhansak")
				.withUpdatedBy(2L)
				.withSuitableFor(PeopleGroup.MORETHANTEN)
				.withIsVegetarian(false)
				.build();
		
		RequestEntity<Recipe> requestEntity = RequestEntity
				.put(new URI("http://localhost:" + port + "/kitchenworld/api/recipes/2"))				
				.body(recipe);
		
		ResponseEntity<Recipe> response = restTemplate.withBasicAuth("admin", "password")
			.exchange(requestEntity, Recipe.class);

		Recipe updatedRecipe = recipeServie.getRecipe(2L);
		assertEquals(updatedRecipe.getRcpName(),response.getBody().getRcpName());
		assertEquals(updatedRecipe.getRcpSuitableFor(),response.getBody().getRcpSuitableFor());
		assertEquals(updatedRecipe.getRcpUpdatedBy(),response.getBody().getRcpUpdatedBy());
		assertEquals(updatedRecipe.getRcpIsVegetarian(),response.getBody().getRcpIsVegetarian());
	}

	@Test
	public void testForDeleteRecipe() throws Exception{
		RequestEntity<Void> requestEntity = RequestEntity
				.delete(new URI("http://localhost:" + port + "/kitchenworld/api/recipes/2?editor=1")).build();
		
		ResponseEntity<Void> response = restTemplate.withBasicAuth("admin", "password")
				.exchange(requestEntity, Void.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	public void testForDeleteRecipeWithoutEditor() throws Exception{
		RequestEntity<Void> requestEntity = RequestEntity
				.delete(new URI("http://localhost:" + port + "/kitchenworld/api/recipes/2")).build();
		
		ResponseEntity<Void> response = restTemplate.withBasicAuth("admin", "password")
				.exchange(requestEntity, Void.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void testForUpdateWithoutRecipeName() throws Exception{
		Recipe recipe = new RecipeBuilder().withId(2L)
				.withUpdatedBy(2L)
				.withSuitableFor(PeopleGroup.MORETHANTEN)
				.withIsVegetarian(false)
				.build();
		
		RequestEntity<Recipe> requestEntity = RequestEntity
				.put(new URI("http://localhost:" + port + "/kitchenworld/api/recipes/2"))				
				.body(recipe);
		
		ResponseEntity<Recipe> response = restTemplate.withBasicAuth("admin", "password")
			.exchange(requestEntity, Recipe.class);
		assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
	}
	
	//authentication tests
	@Test
	public void testForGetWithoutAuthorization() throws Exception{
		ResponseEntity<Recipe> response = restTemplate
				.getForEntity(new URL("http://localhost:" + port + "/kitchenworld/api/recipes/1").toString(), Recipe.class);

		assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
	}
	
	@Test
	public void testForPostWithoutAuthorization() throws Exception{
		Recipe recipe = new RecipeBuilder().withName("test-recipe")
				.withCookingInstruction("test - instructions")
				.withIngredientDescription("test-ingrediens")
				.withImagePath("image/caponata-pasta.jpg")
				.withCreatedAt( LocalDateTime.now())
				.withCreatedBy(1L)
				.withSuitableFor(PeopleGroup.FIVETOTEN)
				.withIsVegetarian(true)
				.build();
		
		RequestEntity<Recipe> requestEntity = RequestEntity
				.post(new URI("http://localhost:" + port + "/kitchenworld/api/recipes/"))				
				.body(recipe);
		
		ResponseEntity<Recipe> response = restTemplate
				.exchange(requestEntity, Recipe.class);
		
		assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
	}
	
	@Test
	public void testForPutWithoutAuthorization() throws Exception{
		Recipe recipe = new RecipeBuilder().withId(2L)
				.withUpdatedBy(2L)
				.withSuitableFor(PeopleGroup.MORETHANTEN)
				.withIsVegetarian(false)
				.build();
		
		RequestEntity<Recipe> requestEntity = RequestEntity
				.put(new URI("http://localhost:" + port + "/kitchenworld/api/recipes/2"))				
				.body(recipe);
		
		ResponseEntity<Recipe> response = restTemplate
			.exchange(requestEntity, Recipe.class);
		assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
	}
	
	@Test
	public void testForDeleteWithoutAuthorization() throws Exception{
		RequestEntity<Void> requestEntity = RequestEntity
				.delete(new URI("http://localhost:" + port + "/kitchenworld/api/recipes/2")).build();
		
		ResponseEntity<Void> response = restTemplate
				.exchange(requestEntity, Void.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}
	//authorization tests
	@Test
	public void testForPostWithoutAdminRights() throws Exception{
		Recipe recipe = new RecipeBuilder().withName("test-recipe")
				.withCookingInstruction("test - instructions")
				.withIngredientDescription("test-ingrediens")
				.withImagePath("image/caponata-pasta.jpg")
				.withCreatedAt( LocalDateTime.now())
				.withCreatedBy(1L)
				.withSuitableFor(PeopleGroup.FIVETOTEN)
				.withIsVegetarian(true)
				.build();
		
		RequestEntity<Recipe> requestEntity = RequestEntity
				.post(new URI("http://localhost:" + port + "/kitchenworld/api/recipes/"))				
				.body(recipe);
		
		ResponseEntity<Recipe> response = restTemplate.withBasicAuth("user", "password")
				.exchange(requestEntity, Recipe.class);
		
		assertEquals(HttpStatus.FORBIDDEN,response.getStatusCode());
	}
	@Test
	public void testForDeleteWithoutAdminRights() throws Exception{
		RequestEntity<Void> requestEntity = RequestEntity
				.delete(new URI("http://localhost:" + port + "/kitchenworld/api/recipes/2")).build();
		
		ResponseEntity<Void> response = restTemplate.withBasicAuth("user", "password")
				.exchange(requestEntity, Void.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	@Test
	public void testForPutWithoutAdminRights() throws Exception{
		Recipe recipe = new RecipeBuilder().withId(2L)
				.withUpdatedBy(2L)
				.withSuitableFor(PeopleGroup.MORETHANTEN)
				.withIsVegetarian(false)
				.build();
		
		RequestEntity<Recipe> requestEntity = RequestEntity
				.put(new URI("http://localhost:" + port + "/kitchenworld/api/recipes/2"))				
				.body(recipe);
		
		ResponseEntity<Recipe> response = restTemplate.withBasicAuth("user", "password")
			.exchange(requestEntity, Recipe.class);
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	@Test
	public void testForGetWithoutAdminRights() throws Exception{
		ResponseEntity<Recipe> response = restTemplate.withBasicAuth("user", "password")
				.getForEntity(new URL("http://localhost:" + port + "/kitchenworld/api/recipes/1").toString(), Recipe.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
