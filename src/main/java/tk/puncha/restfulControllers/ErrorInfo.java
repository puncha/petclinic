package tk.puncha.restfulControllers;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

public class ErrorInfo implements Serializable {

  @JsonView
  private String message;

  public ErrorInfo(String message) {
    this.message = message;
  }
}