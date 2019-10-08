package com.tcs.demo.recipe.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Bean class for Recipe
 *
 * @author Dhiraj
 */
@Entity
@Table(name = "recipes")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Recipe implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rcpid", updatable = false, nullable = false)
  private Long rcpId;

  @NotNull
  @Column(name = "rcpname")
  private String rcpName;

  @Lob
  @Column(name = "rcpingredientdescription")
  private String rcpIngredientDescription;

  @Lob
  @Column(name = "rcpcookinginstruction")
  private String rcpCookingInstruction;

  @Column(name = "rcpimagepath")
  private String rcpImagePath;

  @Column(name = "rcpisvegetarian")
  private Boolean rcpIsVegetarian;

  @Column(name = "rcpsuitablefor")
  private PeopleGroup rcpSuitableFor; // PeopleGroupAttributeConverter implemented with
  // autoApply=true

  @Column(name = "rcpcreatedby", updatable = false)
  private Long rcpCreatedBy;

  @Column(name = "rcpcreatedat", updatable = false)
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  @CreationTimestamp
  private LocalDateTime rcpCreatedAt; // LocalDateTimeAttributeConverter implemented with autoApply
  // = true

  @Column(name = "rcpupdatedby")
  private Long rcpUpdatedBy;

  @Column(name = "rcpupdatedat")
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
  @UpdateTimestamp
  private LocalDateTime rcpUpdatedAt;

  @Column(name = "rcprowstate")
  private Integer rcpRowState;

  /** Defines the no of people the recipe is suitable for. */
  public enum PeopleGroup {
    LESSTHANFIVE(0),
    FIVETOTEN(1),
    MORETHANTEN(2);

    private int groupId;

    PeopleGroup(int groupId) {
      this.groupId = groupId;
    }

    /** @return the ageGroupId */
    public int getGroupId() {
      return groupId;
    }

    public String getGroupDescription() {
      switch (this) {
        case LESSTHANFIVE:
          return "< 5";
        case FIVETOTEN:
          return "5 - 10";
        case MORETHANTEN:
          return "> 10";
        default:
          return "-";
      }
    }
  }
}
