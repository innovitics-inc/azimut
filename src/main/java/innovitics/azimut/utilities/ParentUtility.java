package innovitics.azimut.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessservices.AbstractBusinessService;
import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.models.BaseEntity;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;

@Component
public class ParentUtility {
@Autowired protected ConfigProperties configProperties;
protected static final Logger logger = LoggerFactory.getLogger(ParentUtility.class);
	
	
}
