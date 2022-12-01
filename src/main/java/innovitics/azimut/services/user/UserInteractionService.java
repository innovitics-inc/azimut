package innovitics.azimut.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.user.UserInteraction;
import innovitics.azimut.repositories.user.UserInteractionRepository;
import innovitics.azimut.services.AbstractService;

@Service
public class UserInteractionService extends AbstractService<UserInteraction, String> {

	@Autowired UserInteractionRepository userInteractionRepository;
	
	
	public UserInteraction addUserInteraction(UserInteraction userInteraction)
	{
		return this.userInteractionRepository.save(userInteraction);
	}
	
	
}
