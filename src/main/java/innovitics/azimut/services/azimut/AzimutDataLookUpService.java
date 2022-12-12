package innovitics.azimut.services.azimut;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphType;
import com.cosium.spring.data.jpa.entity.graph.domain.NamedEntityGraph;

import innovitics.azimut.models.azimutdetails.AzimutDetails;
import innovitics.azimut.models.azimutdetails.AzimutDetailsLookup;
import innovitics.azimut.models.user.AzimutDataLookup;
import innovitics.azimut.models.user.AzimutEntity;
import innovitics.azimut.repositories.azimut.AzimutDataLookupDynamicRepository;
import innovitics.azimut.repositories.azimut.AzimutDetailsDynamicRepository;
import innovitics.azimut.repositories.azimut.AzimutEntityDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.AzimutEntitySepcification;
import innovitics.azimut.utilities.dbutilities.specifications.child.AzmiutDataLookupSpecification;
import innovitics.azimut.utilities.dbutilities.specifications.childparent.AzimutDataLookupParentSpecification;
@Service
public class AzimutDataLookUpService extends AbstractService<AzimutDataLookup, String>
{
	@Autowired AzimutDataLookupParentSpecification azimutDataLookupParentSpecification;
	@Autowired AzimutDataLookupDynamicRepository azimutDataLookupDynamicRepository;
	
	@Autowired AzimutEntitySepcification azimutEntitySepcification;
	@Autowired AzimutEntityDynamicRepository azimutEntityDynamicRepository;
	
	@Autowired AzimutDetailsDynamicRepository azimutDetailsDynamicRepository;
	
	private final static String GET_DIRECTIONS="getDirections";
	private final static String CONTACT_US="contactUs";
	private final static String WORKING_DAYS="workingDays";
	private final static String WORKING_HOURS="workingHours";
	private final static String LONGITUTE="longt";
	private final static String LATITUDE="lat";
	private final static String EMAIL="email";
	private final static String PHONE_NUMBER="phoneNumber";
	private final static String WHATSAPP_NUMBER="whatsappNumber";
	private final static String WHATSAPP_LINK="whatsappLink";

	
	
	public AzimutDetails getAzimutDetails()
	{
		 AzimutDetails azimutDetails=new  AzimutDetails();
		List<AzimutDetailsLookup> azimutDetailsLookups=this.azimutDetailsDynamicRepository.findAll();
		
		if(azimutDetailsLookups!=null&&!azimutDetailsLookups.isEmpty())
		{
			for(AzimutDetailsLookup azimutDetailsLookup:azimutDetailsLookups)
			{
				String azimutDetailsLookupKey=azimutDetailsLookup.getKeyString();
				String azimutDetailsLookupValue=azimutDetailsLookup.getValueString();

				if(StringUtility.stringsMatch(GET_DIRECTIONS, azimutDetailsLookupKey))
				azimutDetails.setGetDirections(azimutDetailsLookupValue);
				if(StringUtility.stringsMatch(CONTACT_US, azimutDetailsLookupKey))
				azimutDetails.setContactUs(azimutDetailsLookupValue);
				if(StringUtility.stringsMatch(WORKING_DAYS, azimutDetailsLookupKey))
				azimutDetails.setWorkingDays(azimutDetailsLookupValue);
				if(StringUtility.stringsMatch(WORKING_HOURS, azimutDetailsLookupKey))
				azimutDetails.setWorkingHours(azimutDetailsLookupValue);
				if(StringUtility.stringsMatch(LONGITUTE, azimutDetailsLookupKey))
				azimutDetails.setLongt(azimutDetailsLookupValue);
				if(StringUtility.stringsMatch(LATITUDE, azimutDetailsLookupKey))
				azimutDetails.setLat(azimutDetailsLookupValue);
				if(StringUtility.stringsMatch(EMAIL, azimutDetailsLookupKey))
				azimutDetails.setEmail(azimutDetailsLookupValue);
				if(StringUtility.stringsMatch(PHONE_NUMBER, azimutDetailsLookupKey))
				azimutDetails.setPhoneNumber(azimutDetailsLookupValue);
				if(StringUtility.stringsMatch(WHATSAPP_NUMBER, azimutDetailsLookupKey))
				azimutDetails.setWhatsappNumber(azimutDetailsLookupValue);
				if(StringUtility.stringsMatch(WHATSAPP_LINK, azimutDetailsLookupKey))
				azimutDetails.setWhatsappLink(azimutDetailsLookupValue);
			}
		}	
		return azimutDetails;
	}
	
}
