package com.tcs.demo.recipe;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tcs.demo.recipe.bean.Recipe;
import com.tcs.demo.recipe.repository.RecipeRespository;
import com.tcs.demo.recipe.service.RecipeService;
import com.tcs.demo.recipe.service.RecipeServiceImpl;
import com.tcs.demo.recipe.test.RecipeBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
public class RecipeServiceTest {

	@InjectMocks
	private RecipeService recipeService = new RecipeServiceImpl() ;

	@Mock
	private RecipeRespository recipeRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAddRecipe() throws Exception{
		Recipe recipe = new RecipeBuilder().withId(1L)
				.withName("Recipe 1")
				.build();
		
		when(recipeRepository.saveAndFlush(recipe)).thenReturn(recipe);
		
		Recipe serviceRecipe = recipeService.addRecipe(recipe);
		
		assertEquals(recipe.getRcpId(), serviceRecipe.getRcpId());
		assertEquals(recipe.getRcpName(), serviceRecipe.getRcpName());
	}
	
	@Test
	public void testDeleteRecipe() throws Exception{
		Recipe recipe = new RecipeBuilder().withId(1L)
				.withName("Recipe 1")
				.withRowState(1)
				.withUpdatedBy(2L)
				.build();
		Recipe deletedRecipe = new RecipeBuilder().withId(1L)
				.withName("Recipe 1")
				.withRowState(-1)
				.withUpdatedBy(1L)
				.build();
		
		when(recipeRepository.findByRcpIdAndRcpRowState(1L, 1)).thenReturn(recipe);		
		when(recipeRepository.saveAndFlush(recipe)).thenReturn(deletedRecipe);
		
		Recipe serviceRecipe = recipeService.deleteRecipe(1L, 1L);
		assertEquals(serviceRecipe.getRcpRowState(), deletedRecipe.getRcpRowState());
		assertEquals(serviceRecipe.getRcpUpdatedBy(), deletedRecipe.getRcpUpdatedBy());
		
	}
	
	@Test
	public void testUpdateRecipe() throws Exception{
		Recipe recipe = new RecipeBuilder().withId(1L)
				.withName("Recipe 1")
				.build();
		
		when(recipeRepository.findByRcpIdAndRcpRowState(1L, 1)).thenReturn(recipe);
		when(recipeRepository.save(recipe)).thenReturn(recipe);
		
		Recipe serviceRecipe = recipeService.updateRecipe(recipe);
		
		assertEquals(recipe.getRcpId(), serviceRecipe.getRcpId());
		assertEquals(recipe.getRcpName(), serviceRecipe.getRcpName());
	}

	@Test
	public void testGetRecipeById() throws Exception {
		Recipe recipe = new RecipeBuilder().withId(1L)
				.withName("Caponata Pasta")
				.withIngredientDescription("olive oil,onion,tomatoes,small capers,raisins,rigatoni")
				.withImagePath("image/caponata-pasta.jpg")
				.build();
		
		when(recipeRepository.findByRcpIdAndRcpRowState(1L,1)).thenReturn(recipe);
		Recipe recp = recipeService.getRecipe(1L);
		assertEquals("Caponata Pasta" , recp.getRcpName());
		assertEquals("image/caponata-pasta.jpg", recp.getRcpImagePath());
		assertEquals("olive oil,onion,tomatoes,small capers,raisins,rigatoni", recp.getRcpIngredientDescription());

	}

	@Test
	public void testGetAllRecipe() throws Exception{
		Recipe recipe = new RecipeBuilder().withId(1L)
				.withName("Recipe 1")
				.build();
		Recipe recipe1 = new RecipeBuilder().withId(1L)
				.withName("Recipe 2")
				.build();
		Recipe recipe2 = new RecipeBuilder().withId(1L)
				.withName("Recipe 3")
				.build();

		List<Recipe> recipeList = Stream.of(recipe,recipe1,recipe2).collect(Collectors.toList());
		when(recipeRepository.findAllByRcpRowState(1)).thenReturn(recipeList);
		List<Recipe> serviceList = recipeService.getAllRecipes();
		assertEquals(recipeList.size(), serviceList.size());

	}
	

	@Test
	public void testGetAllRecipeByLimitAndPage() throws Exception{
		Recipe recipe = new RecipeBuilder().withId(1L)
				.withName("Recipe 1")
				.build();
		Recipe recipe1 = new RecipeBuilder().withId(1L)
				.withName("Recipe 2")
				.build();
		Recipe recipe2 = new RecipeBuilder().withId(1L)
				.withName("Recipe 3")
				.build();
		
		Pageable pageable = PageRequest.of(0, 3);

		List<Recipe> recipeList = Stream.of(recipe,recipe1,recipe2).collect(Collectors.toList());
		
		Page<Recipe> recipePage = new PageImpl<>(recipeList, pageable, 3);
		
		when(recipeRepository.findAllByRcpRowState(1,pageable)).thenReturn(recipePage);
		Page<Recipe> servicePage = recipeService.getRecipes(0,3);
		
		assertEquals(recipePage.getContent().size(), servicePage.getContent().size());

	}

}
