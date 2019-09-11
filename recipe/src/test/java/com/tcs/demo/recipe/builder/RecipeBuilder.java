package com.tcs.demo.recipe.builder;

import java.time.LocalDateTime;

import com.tcs.demo.recipe.bean.Recipe;
import com.tcs.demo.recipe.bean.Recipe.PeopleGroup;

public class RecipeBuilder {

	 Long rcpId;

	 String rcpName;

	 String rcpIngredientDescription;

	 String rcpCookingInstruction;

	 String rcpImagePath;

	 Boolean rcpIsVegetarian;

	 PeopleGroup rcpSuitableFor; 
	 
	 Long rcpCreatedBy;

	 LocalDateTime rcpCreatedAt; 

	 Long rcpUpdatedBy;

	 LocalDateTime rcpUpdatedAt;

	 Integer rcpRowState;
	
	public RecipeBuilder() {
		
	}
	
	public RecipeBuilder withId(Long id) {
		this.rcpId = id;
		return this;
	}
	public RecipeBuilder withName(String rcpName) {
		this.rcpName = rcpName;
		return this;
	}
	public RecipeBuilder withIngredientDescription(String rcpIngredientDescription) {
		this.rcpIngredientDescription = rcpIngredientDescription;
		return this;
	}
	public RecipeBuilder withCookingInstruction(String rcpCookingInstruction) {
		this.rcpCookingInstruction = rcpCookingInstruction;
		return this;
	}
	public RecipeBuilder withImagePath(String rcpImagePath) {
		this.rcpImagePath = rcpImagePath;
		return this;
	}
	public RecipeBuilder withIsVegetarian(Boolean rcpIsVegetarian) {
		this.rcpIsVegetarian = rcpIsVegetarian;
		return this;
	}
	public RecipeBuilder withSuitableFor(PeopleGroup rcpSuitableFor) {
		this.rcpSuitableFor = rcpSuitableFor;
		return this;
	}
	public RecipeBuilder withCreatedBy(Long rcpCreatedBy) {
		this.rcpCreatedBy = rcpCreatedBy;
		return this;
	}
	public RecipeBuilder withCreatedAt(LocalDateTime rcpCreatedAt) {
		this.rcpCreatedAt = rcpCreatedAt;
		return this;
	}
	public RecipeBuilder withUpdatedBy(Long rcpUpdatedBy) {
		this.rcpUpdatedBy = rcpUpdatedBy;
		return this;
	}
	public RecipeBuilder withUpdatedAt(LocalDateTime rcpUpdatedAt) {
		this.rcpUpdatedAt = rcpUpdatedAt;
		return this;
	}
	public RecipeBuilder withRowState(Integer rcpRowState) {
		this.rcpRowState = rcpRowState;
		return this;
	}
	
	public Recipe build() {
		return new Recipe(rcpId, rcpName, rcpIngredientDescription, rcpCookingInstruction, rcpImagePath, rcpIsVegetarian, rcpSuitableFor, rcpCreatedBy, rcpCreatedAt, rcpUpdatedBy, rcpUpdatedAt, rcpRowState);
	}
}
