/**
 * 
 */
package com.tcs.demo.recipe.test;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tcs.demo.recipe.RecipeApplication;
import com.tcs.demo.recipe.bean.Recipe;
import com.tcs.demo.recipe.bean.Recipe.PeopleGroup;
import com.tcs.demo.recipe.service.RecipeService;
/**
 * @author Dhiraj
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RecipeApplication.class)
@SpringBootTest()
public class RecipeApiTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@MockBean
	RecipeService recipeServie;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	@Test
	public void getRecipe() throws Exception{
		Recipe recipe = new Recipe(1L, "Caponata Pasta", "olive oil,onion,tomatoes,small capers,raisins,rigatoni", "Heat the oil in a large pan and cook the onion for 8-10 mins until starting to caramelise (or for longer if you have time – the sweeter the better). Add the garlic for the final 2 mins of cooking time.Tip in the mixed veg, tomatoes, capers and raisins. Season well and simmer, uncovered, for 10 mins, or until you have a rich sauce.Meanwhile, boil the kettle. Pour the kettleful of water into a large pan with a little salt and bring back to the boil. Add the pasta and cook until tender with a little bite, then drain, reserving some of the pasta water. Tip the pasta into the sauce, adding a splash of pasta water if it needs loosening. Scatter with the basil leaves and parmesan, if you like, and serve straight from the pan.", "image/caponata-pasta.jpg", true,PeopleGroup.FIVETOTEN,
				1L, LocalDateTime.now(), null, null,1);

		given(recipeServie.getRecipe(1L)).willReturn(recipe);

		mockMvc.perform(get("kitchenworld/api/recipes/1").with(user("admin").password("password")))
		.andExpect(status().isOk()).andExpect(content().json("{\"rcpId\":1,\"rcpName\":\"Caponata Pasta\",\"rcpIngredientDescription\":\"olive oil,onion,tomatoes,small capers,raisins,rigatoni\",\"rcpCookingInstruction\":\"Heat the oil in a large pan and cook the onion for 8-10 mins until starting to caramelise (or for longer if you have time – the sweeter the better). Add the garlic for the final 2 mins of cooking time.Tip in the mixed veg, tomatoes, capers and raisins. Season well and simmer, uncovered, for 10 mins, or until you have a rich sauce.Meanwhile, boil the kettle. Pour the kettleful of water into a large pan with a little salt and bring back to the boil. Add the pasta and cook until tender with a little bite, then drain, reserving some of the pasta water. Tip the pasta into the sauce, adding a splash of pasta water if it needs loosening. Scatter with the basil leaves and parmesan, if you like, and serve straight from the pan.\",\"rcpImagePath\":\"image/caponata-pasta.jpg\",\"rcpIsVegetarian\":true,\"rcpSuitableFor\":\"FIVETOTEN\",\"rcpCreatedBy\":1,\"rcpCreatedAt\":\"09-09-2019 00:19\",\"rcpUpdatedBy\":null,\"rcpUpdatedAt\":\"09-09-2019 00:19\",\"rcpRowState\":1}"));
	}
}
