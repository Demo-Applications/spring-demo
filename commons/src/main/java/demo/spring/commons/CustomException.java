package demo.spring.commons;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A custom exception class which provides additional methods for storing and retrieving Error
 * related Context
 *
 * <p>Use the {@link #getRootCause()} method to get the name of the product.
 *
 * <h2>Example Usage</h2>
 *
 * <p>Here's an example of how to throw this exception
 *
 * <pre>
 * {
 * 	&#64;code
 * 	Product product = new Product("Shoes", 29.99);
 * 	product.setDescription("A stylish and comfortable pair of shoes.");
 * }
 * </pre>
 */
public class CustomException extends RuntimeException {

  private static final long serialVersionUID = 2116665941260296261L;

  private static final String CUSTOM_EXCEPTION_FIELD_STRING = "Error Data";

  private List<String> errorContextList = new LinkedList<>();

  public CustomException(String message) {
    super(message);
    this.errorContextList = new LinkedList<>();
  }

  public CustomException(String message, Throwable cause) {
    super(message, cause);
    this.errorContextList = new LinkedList<>();
  }

  /** Construct a <code>CustomException</code> with the cause. */
  public CustomException(Throwable cause) {
    super(cause);
    this.errorContextList = new LinkedList<>();
  }

  /** Adds additional error context messages to the exception */
  public void addErrorContext(String... values) {
    addErrorContext(Arrays.asList(values));
  }

  /** Adds additional error context messages to the exception */
  public void addErrorContext(List<String> values) {
    this.errorContextList.addAll(values);
  }

  /** Returns the list of error messages */
  public List<String> getErrorContexts() {
    return this.errorContextList;
  }

  /** Return the detail message with nested exception cause. */
  public String getDetailedCause() {
    if (getCause() != null) {
      StringBuilder sb = new StringBuilder();
      sb.append(toString()).append("\n");
      // SONAR : java:S6201
      if (getCause() instanceof CustomException customException) {
        sb.append(customException.getDetailedCause());
      } else {
        sb.append(getCause());
      }
      return sb.toString();
    } else {
      return super.toString();
    }
  }

  public String getErrorContext() {
    StringBuilder sb = new StringBuilder();
    sb.append(CUSTOM_EXCEPTION_FIELD_STRING);
    errorContextList.forEach(
        errorContext -> {
          sb.append(errorContext);
          sb.append("\n");
        });
    return sb.toString();
  }

  @Override
  public String toString() {
    String errorMessage = String.format("CustomException thrown for %s %n", this.getMessage());
    String additonalContext = "Addional context" + getErrorContext();
    return errorMessage + additonalContext;
  }
}
