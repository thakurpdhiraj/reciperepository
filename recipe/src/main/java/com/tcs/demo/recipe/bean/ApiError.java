/**
 * 
 */
package com.tcs.demo.recipe.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generalized Error class to represent API error
 * 
 * @author Dhiraj
 *
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ApiError implements Serializable {

  private static final long serialVersionUID = 1L;

  private HttpStatus status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private Date timestamp;

  private String message;

  private List<String> debugMessage;

  public ApiError(HttpStatus status, String message, List<String> ex) {
    this();
    this.status = status;
    this.message = message;
    this.debugMessage = ex;
  }

}
