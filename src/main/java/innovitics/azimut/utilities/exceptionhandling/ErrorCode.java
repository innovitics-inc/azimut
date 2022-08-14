package innovitics.azimut.utilities.exceptionhandling;

public enum ErrorCode {
	
	PAYMENT_FAILURE(996,"Operation Could not be performed, please check with the vendor."),
	INVALID_SIGNATURE(997,"Invalid signature, corrupted request."),
	INTEGRATION_TEST_FAILED(998,"Integration Test Failed."),
	FAILED_TO_INTEGRATE(999,"Operation Could not be performed."),
	OPERATION_NOT_PERFORMED(1000,"Operation Could not be performed."),
	NO_DATA_FOUND(1001,"No data found."),
	COPY_FAILURE(2102,"Could not upload the file."),
	UPLOAD_FAILURE(2103,"Could not upload the file."),
	FILE_TOO_BIG(2104,"File size too big."),
	REQUESTS_FOUND(2105,"Another request was found for this user."),
	MISSING_FILE(2106,"File missing."),
	UNAUTHORIZED_USER(2107,"Unauthorized user."),
	WEAK_PASSWORD(2108,"The password is weak, it must be at least 8 characters long, consist of at least 1 capital letter 1 small letter and 1 number."),
	PASSWORDS_NOT_MATHCING(2109,"The two passwords are not matching."),
	TOO_MANY_USERS(2110,"More than one user was found having this phone."),
	NO_USERS_FOUND(2111, "No users were found."),
	USER_NOT_FOUND(2112, "Invalid Username or password."),
	USER_NOT_SAVED(2113, "User was not saved."),
	USER_NOT_UPDATED(2114, "User was not updated."),
	USER_EXISTS(2115, "User already exists."),
	PAGES_NOT_FOUND(31111,"No pages are found for this user type."),
	PAGE_NOT_FOUND(31112,"Page not found."),
	INVALID_ANSWER_TYPE(31113,"Invalid answer type."),
	ANSWER_SUBMISSION_FAILED(31114,"Could not submit the answers."),
	INCORRECT_PASSWORD(2116, "Incorrect password."),
	BAD_CREDENTIALS(2117, "Bad credentials."),
	FAILED_TO_VALIDATE_TOKEN(2118,"Session expired."),
	INVALID_FIELD_VALUE(2119,"Invalid value"),
	IMAGES_NOT_SIMIILAR(2120,"The images are different, please retake them."),
	IMAGES_NOT_ClEAR(2121,"The images are not clear.")
	
	;
	
	private final int code;
	private final String message;
	

	  private ErrorCode(int code, String message) {
	    this.code = code;
	    this.message = message;
	  }

	  public String getMessage() {
	     return message;
	  }

	  public int getCode() {
	     return code;
	  }

	  @Override
	  public String toString() {
	    return code + ": " + message;
	  }
	  
	  
}
