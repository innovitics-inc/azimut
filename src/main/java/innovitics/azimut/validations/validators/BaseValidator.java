package innovitics.azimut.validations.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.utilities.datautilities.ArrayUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
@Component
public abstract class BaseValidator implements Validator {

	protected static final Logger logger = LoggerFactory.getLogger(BaseValidator.class);
	@Autowired protected ConfigProperties configProperties;
	@Autowired protected ArrayUtility arrayUtility;

}