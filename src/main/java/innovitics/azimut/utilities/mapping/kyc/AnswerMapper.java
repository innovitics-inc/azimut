package innovitics.azimut.utilities.mapping.kyc;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.kyc.BusinessAnswer;
import innovitics.azimut.businessmodels.kyc.BusinessRelatedAnswer;
import innovitics.azimut.businessmodels.kyc.BusinessSubmittedAnswer;
import innovitics.azimut.models.kyc.Answer;
import innovitics.azimut.models.kyc.UserAnswer;
import innovitics.azimut.services.kyc.UserAnswerSubmissionService;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;
import innovitics.azimut.utilities.logging.MyLogger;
import innovitics.azimut.utilities.mapping.Mapper;
@Component
public class AnswerMapper extends Mapper<Answer, BusinessAnswer> {
	@Autowired protected ListUtility<UserAnswer> userAnswerListUtility;
	@Autowired protected UserAnswerMapper userAnswerMapper;
	@Autowired protected UserAnswerSubmissionService  userAnswerSubmissionService;
	@Autowired protected ExceptionHandler exceptionHandler;
	@Override
	public Answer convertBusinessUnitToBasicUnit(BusinessAnswer businessAnswer, boolean save) {
		Answer answer=new Answer();

		answer.setId(businessAnswer.getId());
		answer.setAnswerOrder(businessAnswer.getAnswerOrder());
		answer.setIsRelatedAnswerMandatory(businessAnswer.getIsRelatedAnswerMandatory());
		answer.setAnswerPlaceHolder(businessAnswer.getAnswerPlaceHolder());
		answer.setAnswerOption(businessAnswer.getAnswerOption());
		
		return answer;
	}

	@Override
	protected BusinessAnswer convertBasicUnitToBusinessUnit(Answer answer, String language) 
	{
		BusinessAnswer businessAnswer =this.convertAnswerToBusinessAnswer(answer,null,null,language);
		if(this.baseListUtility.isSetPopulated(answer.getRelatedAnswers()))
		{
			LinkedList<BusinessAnswer> businessRelatedAnswers=new LinkedList<BusinessAnswer>(); 
			for(Answer relatedAnswer:answer.getRelatedAnswers())
			{
				relatedAnswer.setAppUserId(answer.getAppUserId());
				businessRelatedAnswers.add(this.convertAnswerToBusinessAnswer(relatedAnswer,null,null,language));				
			}
			businessAnswer.setRelatedAnswers(businessRelatedAnswers);
		}
		MyLogger.info("The answers were retrieved successfully::::::");
		//this.populateUserAnswers(parentAnswers, childAnswers,answer.getPageId(),answer.getAppUserId());
		
		//businessAnswer.setBusinessSubmittedAnswers(parentAnswers);
		MyLogger.info("Business Answer, business submitted answer list::::"+businessAnswer.getBusinessSubmittedAnswers());		
		return businessAnswer;
	}

	
	
	
	@Override
	public BusinessAnswer convertBasicUnitToBusinessUnit(Answer answer) {

		BusinessAnswer businessAnswer =this.convertAnswerToBusinessAnswer(answer,null,null,null);
		if(this.baseListUtility.isSetPopulated(answer.getRelatedAnswers()))
		{
			LinkedList<BusinessAnswer> businessRelatedAnswers=new LinkedList<BusinessAnswer>(); 
			for(Answer relatedAnswer:answer.getRelatedAnswers())
			{
				relatedAnswer.setAppUserId(answer.getAppUserId());
				businessRelatedAnswers.add(this.convertAnswerToBusinessAnswer(relatedAnswer,null,null,null));				
			}
			businessAnswer.setRelatedAnswers(businessRelatedAnswers);
		}
		MyLogger.info("The answers were retrieved successfully::::::");
		//this.populateUserAnswers(parentAnswers, childAnswers,answer.getPageId(),answer.getAppUserId());
		
		//businessAnswer.setBusinessSubmittedAnswers(parentAnswers);
		MyLogger.info("Business Answer, business submitted answer list::::"+businessAnswer.getBusinessSubmittedAnswers());		
		return businessAnswer;
	}
	
	private BusinessAnswer convertAnswerToBusinessAnswer(Answer answer,List<BusinessSubmittedAnswer> parentAnswers,List<BusinessSubmittedAnswer> childAnswers,String language)
	{	
		BusinessAnswer businessAnswer =new BusinessAnswer();
		businessAnswer.setId(answer.getId());
		businessAnswer.setAnswerOrder(answer.getAnswerOrder());
		businessAnswer.setAnswerType(answer.getAnswerType());
		businessAnswer.setIsRelatedAnswerMandatory(answer.getIsRelatedAnswerMandatory());
		businessAnswer.setPdFieldName(answer.getPdfField());
		businessAnswer.setAnswerOption(answer.getAnswerOption());
		businessAnswer.setCreatedAt(answer.getCreatedAt());
		businessAnswer.setUpdatedAt(answer.getUpdatedAt());
		businessAnswer.setDeletedAt(answer.getDeletedAt());
		businessAnswer.setIsAnswerMandatory(answer.getIsAnswerMandatory());
		
		if(StringUtility.stringsMatch(language,StringUtility.ENGLISH)||!StringUtility.isStringPopulated(language))
		{
			businessAnswer.setRelatedQuestionText(answer.getRelatedQuestionText());
			businessAnswer.setAnswerPlaceHolder(answer.getAnswerPlaceHolder());
		}
		else
		{
			businessAnswer.setRelatedQuestionText(answer.getRelatedQuestionTextAr());
			businessAnswer.setAnswerPlaceHolder(answer.getAnswerPlaceHolderAr());
		}
		return businessAnswer;
	}


	void matchAndAssign(List<BusinessSubmittedAnswer> parentAnswers,List<BusinessSubmittedAnswer> childAnswers)
	{
		for(BusinessSubmittedAnswer parent:parentAnswers)
		{
			List<BusinessSubmittedAnswer> relatedAnswers=new LinkedList<BusinessSubmittedAnswer>();
			for(BusinessSubmittedAnswer child:childAnswers)
			{
				MyLogger.info("Comparing"+" "+child+" and "+parent);
				if (NumberUtility.areLongValuesMatching(parent.getAnswerId(), child.getParentAnswerId()))
				{
					relatedAnswers.add(child);
				}
			}
			parent.setRelatedUserAnswers(relatedAnswers);
		}

	}

	List<BusinessSubmittedAnswer> populateUserAnswers(Long pageId,Long userId)
	{
		List<UserAnswer> userAnswers=new ArrayList<UserAnswer>();
		List<BusinessSubmittedAnswer> parentAnswers=new LinkedList<BusinessSubmittedAnswer>();
		List<BusinessSubmittedAnswer> childAnswers=new LinkedList<BusinessSubmittedAnswer>();
		try
		{
			userAnswers=this.userAnswerSubmissionService.getUserAnswersByUserIdandPageId(pageId, userId);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			if(this.exceptionHandler.isABusinessException(exception))
			{
				return null;
			}
		}
		if(this.userAnswerListUtility.isListPopulated(userAnswers))
		{		
			   MyLogger.info("User answers Found:::::"+userAnswers.toString());
				
				List<Long> parentIds=new LinkedList<Long>();
				List<Long> childIds = new LinkedList<Long>();
					for(UserAnswer userAnswer:userAnswers)
					{
								BusinessSubmittedAnswer parentBusinessSubmittedAnswer=new BusinessSubmittedAnswer();
								BusinessSubmittedAnswer businessSubmittedAnswer=new BusinessSubmittedAnswer();
		
								if(userAnswer.getQuestionId()==null)
								 {
									businessSubmittedAnswer=this.userAnswerMapper.convertAnswerToBusinessAnswer(userAnswer);
									businessSubmittedAnswer.setParentAnswerId(userAnswer.getRelatedAnswerId());
									childAnswers.add(businessSubmittedAnswer);
									childIds.add(businessSubmittedAnswer.getAnswerId());
								 }
								else
								{
									parentBusinessSubmittedAnswer=this.userAnswerMapper.convertAnswerToBusinessAnswer(userAnswer);
									parentAnswers.add(parentBusinessSubmittedAnswer);
									parentIds.add(parentBusinessSubmittedAnswer.getAnswerId());
								}
							 
					}
					
			}
			this.matchAndAssign(parentAnswers, childAnswers);
			return parentAnswers;
	}
	
	public List<BusinessSubmittedAnswer> populateUserAnswersForAllPages(Long userId)
	{
		MyLogger.info("enter3::::");
		List<UserAnswer> userAnswers=new ArrayList<UserAnswer>();
		List<BusinessSubmittedAnswer> parentAnswers=new LinkedList<BusinessSubmittedAnswer>();
		List<BusinessSubmittedAnswer> childAnswers=new LinkedList<BusinessSubmittedAnswer>();
		try
		{
			userAnswers=this.userAnswerSubmissionService.getUserAnswersByUserId(userId);
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
			if(this.exceptionHandler.isABusinessException(exception))
			{
				return null;
			}
		}
		if(this.userAnswerListUtility.isListPopulated(userAnswers))
		{		
			   MyLogger.info("User answers Found:::::"+userAnswers.toString());
				
				List<Long> parentIds=new LinkedList<Long>();
				List<Long> childIds = new LinkedList<Long>();
					for(UserAnswer userAnswer:userAnswers)
					{
								BusinessSubmittedAnswer parentBusinessSubmittedAnswer=new BusinessSubmittedAnswer();
								BusinessSubmittedAnswer businessSubmittedAnswer=new BusinessSubmittedAnswer();
		
								if(userAnswer.getQuestionId()==null)
								 {
									businessSubmittedAnswer=this.userAnswerMapper.convertAnswerToBusinessAnswer(userAnswer);
									businessSubmittedAnswer.setParentAnswerId(userAnswer.getRelatedAnswerId());
									childAnswers.add(businessSubmittedAnswer);
									childIds.add(businessSubmittedAnswer.getAnswerId());
								 }
								else
								{
									parentBusinessSubmittedAnswer=this.userAnswerMapper.convertAnswerToBusinessAnswer(userAnswer);
									parentAnswers.add(parentBusinessSubmittedAnswer);
									parentIds.add(parentBusinessSubmittedAnswer.getAnswerId());
								}
							 
					}
					
			}
			this.matchAndAssign(parentAnswers, childAnswers);
			return parentAnswers;
	}
	
}
