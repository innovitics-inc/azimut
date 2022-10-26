package innovitics.azimut.utilities.exceptionhandling;

public enum ErrorCode {
	NO_MATCHED_CLIENT_NUMBER_EXIST(79,"",""),
	INVALID_CLIENT(185,"",""),
	CONNECTION_TIMEOUT(994,"Could not perform the operation, please try again later.",""),
	OPERATION_FAILURE(995,"Operation failed.",""),
	PAYMENT_FAILURE(996,"Operation Could not be performed, please check with the vendor.",""),
	INVALID_SIGNATURE(997,"Invalid signature, corrupted response.",""),
	INTEGRATION_TEST_FAILED(998,"Integration Test Failed.",""),
	FAILED_TO_INTEGRATE(999,"Operation Could not be performed.",""),
	OPERATION_NOT_PERFORMED(1000,"Operation Could not be performed.",""),
	NO_DATA_FOUND(1001,"No data found.",""),
	NO_USER_ACCOUNT_FOUND(1002,"User Account not found.",""),
	MULTIPLE_LOGINS(1003,"Multiple logins detected.",""),
	USER_BLOCKED(1004,"User Blocked.","المستخدم محظور"),
	INVALID_EXTENSION(2101,"Could not upload the file.",""),
	COPY_FAILURE(2102,"Could not upload the file.",""),
	UPLOAD_FAILURE(2103,"Could not upload the file.",""),
	FILE_TOO_BIG(2104,"File size too big.",""),
	REQUESTS_FOUND(2105,"Another request was found for this user.",""),
	MISSING_FILE(2106,"File missing.",""),
	UNAUTHORIZED_USER(2107,"Unauthorized user.","غير مصرح لهذا المستخدم"),
	WEAK_PASSWORD(2108,"The password is weak, it must be at least 8 characters long, consist of at least 1 capital letter 1 small letter and 1 number.",""),
	PASSWORDS_NOT_MATHCING(2109,"The two passwords are not matching.",""),
	TOO_MANY_USERS(2110,"More than one user was found having this phone.",""),
	NO_USERS_FOUND(2111, "No users were found.",""),
	USER_NOT_FOUND(2112, "Invalid Username or password.",""),
	USER_NOT_SAVED(2113, "User was not saved.",""),
	USER_NOT_UPDATED(2114, "User was not updated.",""),
	USER_EXISTS(2115, "User already exists.",""),
	INCORRECT_PASSWORD(2116, "Incorrect password.",""),
	BAD_CREDENTIALS(2117, "Bad credentials.",""),
	FAILED_TO_VALIDATE_TOKEN(2118,"Session expired.","لا يمكن الدخول الأن"),
	INVALID_FIELD_VALUE(2119,"Invalid value",""),
	IMAGES_NOT_SIMIILAR(2120,"The images are different, please retake them.",""),
	IMAGES_NOT_ClEAR(2121,"The images are not clear.",""),
	USE_MOBILE(2122,"Please use the mobile app to take the pictures for a better experience.",""),
	TOO_MANY_USER_LOCATIONS(2123,"More than one user was found having this phone.",""),
	USER_LOCATION_NOT_SAVED(2124,"Could not save the user location.",""),
	INVALID_USER_STEP(2125,"Invalid userStep",""),
	USER_ID_NOT_MATCHING(2126, "The User Id does not match the extracted client data.",""),
	PAGES_NOT_FOUND(31111,"No pages are found for this user type.",""),
	PAGE_NOT_FOUND(31112,"Page not found.",""),
	INVALID_ANSWER_TYPE(31113,"Invalid answer type.",""),
	ANSWER_SUBMISSION_FAILED(31114,"Could not submit the answers.",""),
	CONTRACT_DOWNLOAD_FAILED(31115,"Could not download the contract.",""),
	PDF_GENERATION_FAILED(31117,"Could not download the contract.",""),
	KYC_INCOMPLETE(31118,"Please Complete the KYC Form.",""),
	KYC_SUBMITTED(31119,"KYC Form submitted.",""),
	;
	
	private final int code;
	private final String message;
	private final String messageAr;
	

	  private ErrorCode(int code, String message,String messageAr) {
	    this.code = code;
	    this.message = message;
	    this.messageAr=messageAr;
	  }

	  public String getMessage() {
	     return message;
	  }

	  public int getCode() {
	     return code;
	  }
	  public String getMessageAr() {
		return messageAr;
	}

	@Override
	  public String toString() {
	    return code + ": " + message;
	  }
	  
	  
}
