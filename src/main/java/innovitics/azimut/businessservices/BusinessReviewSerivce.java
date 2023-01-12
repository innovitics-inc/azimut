package innovitics.azimut.businessservices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessmodels.admin.BusinessAdminUser;
import innovitics.azimut.businessmodels.kyc.BusinessReview;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.kyc.Review;
import innovitics.azimut.utilities.businessutilities.ReviewUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;

@Service
public class BusinessReviewSerivce extends AbstractBusinessService<BusinessReview>{

	@Autowired BusinessKYCPageService businessKYCPageService;
	@Autowired ListUtility<BusinessReview> reviewListUtility;
	@Autowired ReviewUtility reviewUtility;
	
	public BusinessReview submitReviews(BusinessAdminUser businessAdminUser,BaseBusinessEntity baseBusinessEntity,String language) throws BusinessException
	{
		if(reviewListUtility.isListPopulated(baseBusinessEntity.getReviews()))
		{
			reviewUtility.addReviews(businessAdminUser,baseBusinessEntity,language);
		}
		this.userService.flagTheUserAsReviewed(baseBusinessEntity.getAppUserId());
		return new BusinessReview();
	}
	
	
	public BusinessReview getReviewPages(BusinessAdminUser businessAdminUser,BaseBusinessEntity baseBusinessEntity,String language) throws BusinessException
	{
		BusinessReview businessReview= new  BusinessReview();
		List<Long> pageIds=new ArrayList<Long>();
		for(Review review:this.reviewUtility.getReviews(baseBusinessEntity))
		{
			pageIds.add(review.getPageId());
		}
		
		businessReview.setPageIds(pageIds);
		
		return businessReview;
	}
}
