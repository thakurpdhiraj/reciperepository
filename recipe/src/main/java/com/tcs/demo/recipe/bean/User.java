/**
 * 
 */
package com.tcs.demo.recipe.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Bean class for users
 * 
 * @table kitchen.users
 * @author Dhiraj
 *
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "usrid", updatable = false, nullable = false)
  private Long usrId;

  @NotNull
  @NotEmpty
  @Column(name = "usrname", nullable = false)
  private String usrName;

  @NotNull
  @NotEmpty
  @Column(name = "usrloginid", unique = true, nullable = false)
  private String usrLoginId;

  @NotNull
  @NotEmpty
  @Column(name = "usrpassword", nullable = false)
  private String usrPassword;

  @Column(name = "usrisadmin")
  private Boolean usrIsAdmin;

  @Column(name = "usrrowstate")
  private Integer usrRowState;

}
