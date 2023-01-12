package innovitics.azimut.services.kyc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.kyc.Review;
import innovitics.azimut.repositories.kyc.ReviewDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.ReviewSpecification;

@Service
public class ReviewService extends AbstractService<Review, String>{
	
	@Autowired ReviewDynamicRepository reviewDynamicRepository;
	@Autowired ReviewSpecification reviewSpecification;
	
	public void submitReviews(List<Review> reviews)
	{
		this.reviewDynamicRepository.saveAll(reviews);
	}
	public List<Review> getReviewsByUserId(Long userId,Long pageId)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("userId", userId,SearchOperation.EQUAL, null));
		if(pageId!=null)
		{
			searchCriteriaList.add(new SearchCriteria("pageId", pageId,SearchOperation.EQUAL, null));
		}		
		
		return this.reviewDynamicRepository.findAll(reviewSpecification.findByCriteria(searchCriteriaList));
		
	}
	
	@SuppressWarnings("unchecked")
	public Long getIdOfThePageWithLeastOrder(Long userId)
	{
		return this.reviewDynamicRepository.getIdOfThePageWithLeastOrder(userId);
		
	}
}
