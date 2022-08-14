package innovitics.azimut.services.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.user.Gender;
import innovitics.azimut.repositories.user.GenderDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.datautilities.StringUtility;
@Service
public class GenderService extends AbstractService<Gender, String> {

	@Autowired GenderDynamicRepository genderDynamicRepository;

	public Long determineGender(String genderType)
	{
		List<Gender> genders=this.genderDynamicRepository.findAll();
		
		for(Gender gender:genders)
		{
			List<String> possibilities= StringUtility.splitStringUsingCharacter(genderType,",");
			for(String possibility:possibilities)
				if(StringUtility.stringsMatch(possibility.toLowerCase(), genderType.toLowerCase()))
				{
					
					return gender.getId();
				}
		}
		
		return 1L;
	}
	
}
