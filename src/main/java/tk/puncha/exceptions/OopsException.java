package tk.puncha.exceptions;

public class OopsException extends RuntimeException {
  @Override
  public String getMessage() {
    return "Oops!";
  }
}
