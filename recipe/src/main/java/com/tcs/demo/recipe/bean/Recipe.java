/**
 * 
 */
package com.tcs.demo.recipe.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * Bean class for Recipe
 * 
 * @author Dhiraj
 *
 */

@ApiModel
@Entity
@Table(name = "recipes")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Recipe implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "The database generated recipe ID")
  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rcpid", updatable = false, nullable = false)
  private Long rcpId;

  @ApiModelProperty(value = "The recipe name", required = true)
  @NotNull
  @Column(name = "rcpname")
  private String rcpName;

  @ApiModelProperty(value = "The recipe ingredient description")
  @Lob
  @Column(name = "rcpingredientdescription")
  private String rcpIngredientDescription;

  @ApiModelProperty(value = "The recipe cooking instruction")
  @Lob
  @Column(name = "rcpcookinginstruction")
  private String rcpCookingInstruction;

  @ApiModelProperty(value = "The recipe image path")
  @Column(name = "rcpimagepath")
  private String rcpImagePath;

  @ApiModelProperty(value = "Is the recipe venetarian")
  @Column(name = "rcpisvegetarian")
  private Boolean rcpIsVegetarian;

  @ApiModelProperty(value = "The database generated product ID",
      allowableValues = "LESSTHANFIVE,FIVETOTEN,MORETHANTEN")
  @Column(name = "rcpsuitablefor")
  private PeopleGroup rcpSuitableFor; // PeopleGroupAttributeConverter implemented with
                                      // autoApply=true

  @ApiModelProperty(value = "The user who created the recipe")
  @Column(name = "rcpcreatedby", updatable = false)
  private Long rcpCreatedBy;

  @ApiModelProperty(value = "Time at which recipe was created", readOnly = true)
  @Column(name = "rcpcreatedat", updatable = false)
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  @CreationTimestamp
  private LocalDateTime rcpCreatedAt; // LocalDateTimeAttributeConverter implemented with autoApply
                                      // = true

  @ApiModelProperty(value = "The user who updated the recipe")
  @Column(name = "rcpupdatedby")
  private Long rcpUpdatedBy;

  @ApiModelProperty(value = "Time at which recipe was updated", readOnly = true)
  @Column(name = "rcpupdatedat")
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  @UpdateTimestamp
  private LocalDateTime rcpUpdatedAt;

  @ApiModelProperty(value = "If -1 consided  this  recipe deleted", allowableValues = "1,-1")
  @Column(name = "rcprowstate")
  private Integer rcpRowState;


  /**
   * Defines the no of people the recipe is suitable for.
   * 
   *
   */
  public enum PeopleGroup {

    LESSTHANFIVE(0), FIVETOTEN(1), MORETHANTEN(2);

    private int groupId;

    PeopleGroup(int groupId) {
      this.groupId = groupId;
    }

    /**
     * @return the ageGroupId
     */
    public int getGroupId() {
      return groupId;
    }

  }

}
