package com.tcs.demo.recipe.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for modelmapper of Recipe
 *
 * @author Dhiraj Thakur
 */
@Data
@ApiModel
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "The database generated recipe ID")
  private Long rcpId;

  @ApiModelProperty(value = "The recipe name", required = true)
  @NotNull
  private String rcpName;

  @ApiModelProperty(value = "The recipe ingredient description")
  private String rcpIngredientDescription;

  @ApiModelProperty(value = "The recipe cooking instruction")
  private String rcpCookingInstruction;

  @ApiModelProperty(value = "The recipe image path")
  private String rcpImagePath;

  @ApiModelProperty(value = "Is the recipe vegetarian")
  private Boolean rcpIsVegetarian;

  @ApiModelProperty(value = "The database generated product ID",
      allowableValues = "LESSTHANFIVE,FIVETOTEN,MORETHANTEN")
  private Recipe.PeopleGroup rcpSuitableFor; // PeopleGroupAttributeConverter implemented with
  // autoApply=true

  @ApiModelProperty(value = "The user who created the recipe")
  private Long rcpCreatedBy;

  @ApiModelProperty(value = "Time at which recipe was created", readOnly = true)
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  private LocalDateTime rcpCreatedAt;

  @ApiModelProperty(value = "The user who updated the recipe")
  private Long rcpUpdatedBy;

  @ApiModelProperty(value = "Time at which recipe was updated", readOnly = true)
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  private LocalDateTime rcpUpdatedAt;

  @ApiModelProperty(value = "If -1 consider  this  recipe deleted", allowableValues = "1,-1")
  private Integer rcpRowState;

}
