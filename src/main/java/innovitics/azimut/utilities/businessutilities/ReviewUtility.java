package innovitics.azimut.utilities.businessutilities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.models.admin.AdminUser;
import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessmodels.admin.BusinessAdminUser;
import innovitics.azimut.businessmodels.kyc.BusinessReview;
import innovitics.azimut.models.kyc.Review;
import innovitics.azimut.services.kyc.ReviewService;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;

@Component
public class ReviewUtility {

	@Autowired ReviewService reviewService;	
	@Autowired ListUtility<Review> reviewListUtility;

	
	public List<Review> getReviews()
	{
		return null;
	}
	
	public void addReviews(BusinessAdminUser businessAdminUser,BaseBusinessEntity baseBusinessEntity,String language)
	{
	
		List<Review> reviews=new ArrayList<Review>();
		for(BusinessReview businessReview:baseBusinessEntity.getReviews())
		{
			Review review=new Review();
			review.setActionMaker(businessAdminUser.getId());
			review.setPageId(baseBusinessEntity.getPageId());
			review.setPageOrder(baseBusinessEntity.getPageOrder());
			review.setUserId(baseBusinessEntity.getAppUserId());
			reviews.add(this.convertBusinessReviewToReview(businessReview, language));
		}
		
		reviewService.submitReviews(reviews);
	}
	
	public List<Review> getReviews(BaseBusinessEntity baseBusinessEntity)
	{
		return reviewService.getReviewsByUserId(baseBusinessEntity.getAppUserId(), baseBusinessEntity.getPageId());
	}
	
	
	public boolean isListPopulated(List<Review> reviews)
	{
		return this.reviewListUtility.isListPopulated(reviews);
	}
	
	public BusinessReview convertReviewToBusinessReview(Review review,String language)
	{
		BusinessReview businessReview=new BusinessReview();
		if(review!=null)
		{
		   businessReview.setId(review.getId());
		   businessReview.setQuestionId(review.getQuestionId());
		   if(StringUtility.stringsMatch(language, StringUtility.ARABIC))
		   {
			   businessReview.setReason(review.getCommentAr());
		   }
		   else
		   {
			   businessReview.setReason(review.getComment());
		   }
		   businessReview.setPageId(review.getPageId());
		   businessReview.setAppUserId(review.getAppUserId());
		   businessReview.setActionMaker(review.getActionMaker());
		   businessReview.setStatus(review.getResult());
		}
		return businessReview;
		
	}
	public Review convertBusinessReviewToReview(BusinessReview businessReview,String language)
	{
		Review review=new Review();
		if(review!=null)
		{
			review.setQuestionId(businessReview.getQuestionId());
		   if(StringUtility.stringsMatch(language, StringUtility.ARABIC))
		   {
			   review.setCommentAr(businessReview.getReason());
		   }
		   else
		   {
			   review.setComment(businessReview.getReason());
		   }
		   review.setPageId(businessReview.getPageId());
		   review.setAppUserId(businessReview.getAppUserId());
		   review.setActionMaker(businessReview.getActionMaker());
		   review.setResult(businessReview.getStatus());
		}
		return review;
		
	}
}
