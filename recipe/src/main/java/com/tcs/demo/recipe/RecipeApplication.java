package com.tcs.demo.recipe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.tcs.demo.recipe.bean.Recipe;
import com.tcs.demo.recipe.bean.Recipe.PeopleGroup;
import com.tcs.demo.recipe.bean.User;
import com.tcs.demo.recipe.service.RecipeService;
import com.tcs.demo.recipe.service.UserService;



@SpringBootApplication
public class RecipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeApplication.class, args);
	}

	/**
	 * Dummy default data loader class on application 
	 * @author Dhiraj
	 *
	 */
	@Component
	public class DataLoader {

		private UserService userService;

		private RecipeService recipeService;

		@Autowired
		public DataLoader(UserService userService,RecipeService recipeService) {
			this.userService=userService;
			this.recipeService = recipeService;
			loadDefaults();
		}
		//loading with default values for testing and because this is a demo project
		private void loadDefaults() {
			try {
				if(userService.getUserById(1L)==null) {
					userService.addNewUser(new User(null, "foo","admin","password",true,1));
					userService.addNewUser(new User(null, "fpp","user","password",false,1));
				}
				//TODO: read from a file			
				if(recipeService.getAllRecipes().size()==0) {
					ArrayList<Recipe> recipeList = new ArrayList<>(Arrays.asList(
							new Recipe(null, "Caponata Pasta", "olive oil,onion,tomatoes,small capers,raisins,rigatoni", "Heat the oil in a large pan and cook the onion for 8-10 mins until starting to caramelise (or for longer if you have time – the sweeter the better). Add the garlic for the final 2 mins of cooking time.Tip in the mixed veg, tomatoes, capers and raisins. Season well and simmer, uncovered, for 10 mins, or until you have a rich sauce.Meanwhile, boil the kettle. Pour the kettleful of water into a large pan with a little salt and bring back to the boil. Add the pasta and cook until tender with a little bite, then drain, reserving some of the pasta water. Tip the pasta into the sauce, adding a splash of pasta water if it needs loosening. Scatter with the basil leaves and parmesan, if you like, and serve straight from the pan.", "image/caponata-pasta.jpg", true,PeopleGroup.FIVETOTEN,
									1L, LocalDateTime.now(), null, null,1),
							new Recipe(null, "Coconut & squash dhansak", "vegetable oil,onions,tomatoes,coconut milk,lentils,baby spinach", "Heat the oil in a large pan. Put the squash in a bowl with a splash of water. Cover with cling film and microwave on High for 10 mins or until tender. Meanwhile, add the onions to the hot oil and cook for a few mins until soft. Add the curry paste, tomatoes and coconut milk, and simmer for 10 mins until thickened to a rich sauce.Warm the naan breads in a low oven or in the toaster. Drain any liquid from the squash, then add to the sauce with the lentils, spinach and some seasoning. Simmer for a further 2-3 mins to wilt the spinach, then stir in the coconut yogurt. Serve with the warm naan and a dollop of extra yogurt.", "image/coconut-squash-dhansak.jpg", true,PeopleGroup.LESSTHANFIVE,
									1L, LocalDateTime.now(), null, null,1),
							new Recipe(null, "Vegetarian fajitas", "black beans,flour tortillas,guacamole,soured cream,pepper,onion,chilli powder,paprika,lime", "To make the fajita mix, take two or three strips from each colour of pepper and finely chop them. Set aside. Heat the oil in a frying pan and fry the remaining pepper strips and the onion until soft and starting to brown at the edges. Cool slightly and mix in the chopped raw peppers. Add the garlic and cook for 1 min, then add the spices and stir. Cook for a couple of mins more until the spices become aromatic, then add half the lime juice and season. Transfer to a dish, leaving any juices behind, and keep warm.Tip the black beans into the same pan, then add the remaining lime juice and plenty of seasoning. Stir the beans around the pan to warm them through and help them absorb any flavours of the fajita mix, then stir through the coriander.Serve", "image/veggie-fajitas.jpg", true,PeopleGroup.LESSTHANFIVE,
									1L, LocalDateTime.now(), null, null,1),
							new Recipe(null, "Indian chickpeas with poached eggs", "rapeseed oil,cloves,pepper,chilli,onions,coriander,turmeric,tomatoes,chikpeas,salt,eggs", "Heat the oil in a non-stick sauté pan, add the garlic, pepper, chilli and the whites from the spring onions, and fry for 5 mins over a medium-high heat. Meanwhile, put a large pan of water on to boil.Add the spices, tomatoes, most of the coriander and the chickpeas to the sauté pan and cook for 1-2 mins more. Stir in the bouillon powder and enough liquid from the chickpeas to moisten everything, and leave to simmer gently.Once the water is at a rolling boil, crack in your eggs and poach for 2 mins, then remove with a slotted spoon. Stir the spring onion tops into the chickpeas, then very lightly crush a few of the chickpeas with a fork or potato masher. Spoon the chickpea mixture onto plates, scatter with the reserved coriander and top with the eggs. Serve with an extra sprinkle of cumin, if you like.", "image/indian-chickpeas-with-poached-eggs.jpg", false,PeopleGroup.MORETHANTEN,
									1L, LocalDateTime.now(), null, null,1),
							new Recipe(null, "Sweetcorn & courgette fritters", "sweetcorn,courgette,paprika,raisin flour,eggs,sweet chilli sauce,lime,vegetable oil,", "Mix the sweetcorn, spring onions, courgette, paprika, flour, beaten egg, milk and some seasoning in a large bowl and set aside. Put a large pan of water on to boil. In a bowl, mix the chilli sauce with the lime juice and set aside. Heat the oil in a large, non-stick pan and spoon in four burger-sized mounds of the fritter mixture, spaced apart (you may need to do this in two batches). When brown on the underside, turn over and cook for 3 mins more until goldenMeanwhile, poach the eggs in the simmering water for 2-3 mins until cooked and the yolks are runny. Remove with a slotted spoon. Serve the fritters topped with a poached egg, mixed leaves and a drizzle of the chilli dressing.", "image/sweetcorn-courgette-fritters.jpg", true,PeopleGroup.MORETHANTEN,
									1L, LocalDateTime.now(), null, null,1),
							new Recipe(null, "Pea & leek open lasagne", "rapeseed oil,leeks,peas,kale,creme fraiche,lemon,lasgne sheets", "Put a frying pan over a medium heat. Pour in the oil and add the leeks, garlic and a pinch of salt. Cook, stirring occasionally, until collapsed and soft. Meanwhile, bring a pan of water to the boil.Tip the peas and kale into the pan with the leeks and add a splash of water. Cook until the kale has wilted and peas are defrosted. Turn down the heat to low. Stir in the mustard, crème fraîche and ¾ of the lemon zest. Add enough water to make a sauce. Give everything a good mix and season to taste.Drop the lasagne into the water and cook following pack instructions, then drain well. Put one lasagne sheet on each plate, top with half the leek and pea mix then layer up the second lasagne sheet and the remaining greens. Scatter over the remaining lemon zest and add a good grinding of black pepper.", "image/pea-leek-open-lasagne.jpg", true,PeopleGroup.FIVETOTEN,
									1L, LocalDateTime.now(), null, null,1),
							new Recipe(null, "Enchilada pie", "vegetable oil,peppers,red onion,mixed beans,fajita spice mix,tomatoes,coriander,low-fat soured cream,cheddar", "Heat the oil in a pan. Fry the peppers and onion until soft, about 10 mins. Add the beans, fajita spice mix, chopped tomatoes and some seasoning. Bubble for 5 mins to reduce the tomatoes a little, then stir in most of the coriander. Heat the grill and warm the tortillas in the microwave for 30 seconds.Spread a quarter of the pepper and onion mixture over the base of an ovenproof dish (a round one, if possible) or frying pan. Top with some of the soured cream and a warm tortilla, then repeat the layers three more times, finishing off with a final layer of soured cream. Sprinkle over the cheese and grill for 5 mins, or until golden and bubbling. Scatter with the remaining coriander before serving.","image/enchilada-pie.jpg", true,PeopleGroup.MORETHANTEN,
									1L, LocalDateTime.now(), null, null,1)));
					
					recipeList.forEach(r -> recipeService.addRecipe(r));
				}
			}catch(Exception e) {
				//do nothing. Creating default users + recipes. Will give duplicate entry exception when spring.jpa.hibernate.ddl-auto=update
			}
		}
	}

}
