package innovitics.azimut.utilities.businessutilities;

import java.util.List;

import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.models.kyc.Review;

@Component
public class ReviewUtility {

	
	public List<Review> getReviews()
	{
		return null;
	}
	
	public void addReviews(BaseBusinessEntity baseBusinessEntity)
	{
	
		/*for(Review review:baseBusinessEntity.getReviews())
		{
			review.setActionMaker(adminUser.getId());
			review.setPageId(baseBusinessEntity.getPageId());
			review.setPageOrder(baseBusinessEntity.getPageOrder());
			review.setUserId(baseBusinessEntity.getUserId());
		}*/
		
	}
}
