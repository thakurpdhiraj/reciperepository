/**
 * 
 */
package com.tcs.demo.recipe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tcs.demo.recipe.bean.Recipe;
import com.tcs.demo.recipe.repository.RecipeRespository;



/**
 * @author Dhiraj
 *
 */
@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	RecipeRespository recipeRepository;
	
	@Override
	public Recipe addRecipe(Recipe recipe) {
		recipe.setRcpCreatedAt(null);
		recipe.setRcpUpdatedAt(null);
		recipe.setRcpUpdatedBy(null);
		recipe.setRcpRowState(1);
		return recipeRepository.saveAndFlush(recipe);
	}

	@Override
	public void deleteRecipe(Long rcpId, Long editor) {
		Recipe existingRecipe = recipeRepository.findByRcpIdAndRcpRowState(rcpId, 1);
		existingRecipe.setRcpRowState(-1);
		existingRecipe.setRcpUpdatedBy(editor);
		 recipeRepository.saveAndFlush(existingRecipe);
		
	}

	
	@Override
	public Recipe updateRecipe(Recipe recipe) {
		
		Recipe existing = recipeRepository.findByRcpIdAndRcpRowState(recipe.getRcpId(), 1);
		if(recipe.getRcpName() == null || recipe.getRcpName().equals("")) {
			recipe.setRcpName(existing.getRcpName());
		}
		if(recipe.getRcpIngredientDescription()==null || recipe.getRcpIngredientDescription().equals("")) {
			recipe.setRcpIngredientDescription(existing.getRcpIngredientDescription());
		}
		if(recipe.getRcpIsVegetarian()==null) {
			recipe.setRcpIsVegetarian(existing.getRcpIsVegetarian());
		}
		
		if(recipe.getRcpSuitableFor()==null) {
			recipe.setRcpSuitableFor(existing.getRcpSuitableFor());
		}
		
		if(recipe.getRcpUpdatedBy()==null || recipe.getRcpUpdatedBy() <= 0) {
			recipe.setRcpUpdatedBy(existing.getRcpUpdatedBy());
		}
		
		if(recipe.getRcpImagePath()==null || recipe.getRcpImagePath().equals("")){
			recipe.setRcpImagePath(existing.getRcpImagePath());
		}
	
		return recipeRepository.saveAndFlush(recipe);
	}


	@Override
	public Recipe getRecipe(Long rcpId) {
		return recipeRepository.findByRcpIdAndRcpRowState(rcpId, 1);
	}


	@Override
	public List<Recipe> getAllRecipes() {
		return recipeRepository.findAllByRcpRowState(1);
	}

	@Override
	public void updateRecipeImagePath(Long  rcpId, String path) {
	}

	/* (non-Javadoc)
	 * @see com.tcs.recipe_jpa.service.RecipeService#getRecipes(int, int)
	 */
	@Override
	public Page<Recipe> getRecipes(int pageNumber,int limit) {
		Pageable pageable = PageRequest.of(pageNumber, limit);
		return recipeRepository.findAllByRcpRowState(1, pageable);
	}



}
