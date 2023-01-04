package innovitics.azimut.businessservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessmodels.kyc.BusinessReview;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.kyc.Review;
import innovitics.azimut.utilities.businessutilities.ReviewUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;

@Service
public class BusinessReviewSerivce extends AbstractBusinessService<BusinessReview>{

	@Autowired BusinessKYCPageService businessKYCPageService;
	@Autowired ListUtility<Review> reviewListUtility;
	@Autowired ReviewUtility reviewUtility;
	
	public BusinessReview submitReviews(BusinessUser adminUser,BaseBusinessEntity baseBusinessEntity,String language) throws BusinessException
	{
		if(reviewListUtility.isListPopulated(baseBusinessEntity.getReviews()))
		{
			reviewUtility.addReviews(baseBusinessEntity);
		}
		return new BusinessReview();
	}
}
