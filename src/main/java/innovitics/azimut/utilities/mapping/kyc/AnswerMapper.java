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
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;
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
	public BusinessAnswer convertBasicUnitToBusinessUnit(Answer answer) {
		
		List<BusinessSubmittedAnswer> parentAnswers=new LinkedList<BusinessSubmittedAnswer>();
		List<BusinessSubmittedAnswer> childAnswers=new LinkedList<BusinessSubmittedAnswer>();


		BusinessAnswer businessAnswer =this.convertAnswerToBusinessAnswer(answer,null,null);
		if(this.baseListUtility.isSetPopulated(answer.getRelatedAnswers()))
		{
			LinkedList<BusinessAnswer> businessRelatedAnswers=new LinkedList<BusinessAnswer>(); 
			for(Answer relatedAnswer:answer.getRelatedAnswers())
			{
				relatedAnswer.setAppUserId(answer.getAppUserId());
				businessRelatedAnswers.add(this.convertAnswerToBusinessAnswer(relatedAnswer,null,null));				
			}
			businessAnswer.setRelatedAnswers(businessRelatedAnswers);
		}
		this.logger.info("The answers were retrieved successfully::::::");
		//this.populateUserAnswers(parentAnswers, childAnswers,answer.getPageId(),answer.getAppUserId());
		
		//businessAnswer.setBusinessSubmittedAnswers(parentAnswers);
		this.logger.info("Business Answer, business submitted answer list::::"+businessAnswer.getBusinessSubmittedAnswers());		
		return businessAnswer;
	}
	
	private BusinessAnswer convertAnswerToBusinessAnswer(Answer answer,List<BusinessSubmittedAnswer> parentAnswers,List<BusinessSubmittedAnswer> childAnswers)
	{	
		BusinessAnswer businessAnswer =new BusinessAnswer();
		businessAnswer.setId(answer.getId());
		businessAnswer.setAnswerOrder(answer.getAnswerOrder());
		businessAnswer.setAnswerType(answer.getAnswerType());
		businessAnswer.setIsRelatedAnswerMandatory(answer.getIsRelatedAnswerMandatory());
		businessAnswer.setAnswerPlaceHolder(answer.getAnswerPlaceHolder());
		businessAnswer.setAnswerOption(answer.getAnswerOption());
		businessAnswer.setCreatedAt(answer.getCreatedAt());
		businessAnswer.setUpdatedAt(answer.getUpdatedAt());
		businessAnswer.setDeletedAt(answer.getDeletedAt());
		businessAnswer.setRelatedQuestionText(answer.getRelatedQuestionText());
		businessAnswer.setIsAnswerMandatory(answer.getIsAnswerMandatory());		
		//this.populateUserAnswers(answer, parentAnswers, childAnswers);
		/*if(this.userAnswerListUtility.isSetPopulated(answer.getUserAnswers()))
		{		
			   this.logger.info("User answers Found:::::"+answer.getUserAnswers());
				
				List<Long> parentIds=new LinkedList<Long>();
				List<Long> childIds = new LinkedList<Long>();
					for(UserAnswer userAnswer:answer.getUserAnswers())
					{
						if(this.longUtility.areLongValuesMatching(answer.getAppUserId(), userAnswer.getUserId()))
							
						{
								BusinessSubmittedAnswer parentBusinessSubmittedAnswer=new BusinessSubmittedAnswer();
								BusinessSubmittedAnswer businessSubmittedAnswer=new BusinessSubmittedAnswer();
		
								if(answer.getParentAnswer()!=null)
								 {
									businessSubmittedAnswer=this.userAnswerMapper.convertAnswerToBusinessAnswer(userAnswer);
									businessSubmittedAnswer.setParentAnswerId(answer.getParentAnswer().getId());
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
					
			}*/
		
	
		return businessAnswer;
	}


	void matchAndAssign(List<BusinessSubmittedAnswer> parentAnswers,List<BusinessSubmittedAnswer> childAnswers)
	{
		for(BusinessSubmittedAnswer parent:parentAnswers)
		{
			List<BusinessSubmittedAnswer> relatedAnswers=new LinkedList<BusinessSubmittedAnswer>();
			for(BusinessSubmittedAnswer child:childAnswers)
			{
				this.logger.info("Comparing"+" "+child+" and "+parent);
				if (NumberUtility.areLongValuesMatching(parent.getAnswerId(), child.getParentAnswerId()))
				{
					relatedAnswers.add(child);
				}
			}
			parent.setRelatedUserAnswers(relatedAnswers);
		}

	}
/*
	void populateUserAnswers(Answer answer,List<BusinessSubmittedAnswer> parentAnswers,List<BusinessSubmittedAnswer> childAnswers)
	{
		if(this.userAnswerListUtility.isSetPopulated(answer.getUserAnswers()))
		{		
			   this.logger.info("User answers Found:::::"+answer.getUserAnswers());
				
				List<Long> parentIds=new LinkedList<Long>();
				List<Long> childIds = new LinkedList<Long>();
					for(UserAnswer userAnswer:answer.getUserAnswers())
					{
						if(this.longUtility.areLongValuesMatching(answer.getAppUserId(), userAnswer.getUserId()))
							
						{
								BusinessSubmittedAnswer parentBusinessSubmittedAnswer=new BusinessSubmittedAnswer();
								BusinessSubmittedAnswer businessSubmittedAnswer=new BusinessSubmittedAnswer();
		
								if(answer.getParentAnswer()!=null)
								 {
									businessSubmittedAnswer=this.userAnswerMapper.convertAnswerToBusinessAnswer(userAnswer);
									businessSubmittedAnswer.setParentAnswerId(answer.getParentAnswer().getId());
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
					
			}
		 
	}
	*/
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
			   this.logger.info("User answers Found:::::"+userAnswers.toString());
				
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
	
	private Answer convertBusinessAnswerToAnswer(BusinessAnswer businessAnswer,boolean save)
	{
		Answer answer=new Answer();
		answer.setId(businessAnswer.getId());
		answer.setAnswerOrder(businessAnswer.getAnswerOrder());
		answer.setIsRelatedAnswerMandatory(businessAnswer.getIsRelatedAnswerMandatory());
		answer.setAnswerPlaceHolder(businessAnswer.getAnswerPlaceHolder());
		answer.setAnswerOption(businessAnswer.getAnswerOption());
		answer.setCreatedAt(save?new Date():businessAnswer.getCreatedAt());
		answer.setUpdatedAt(businessAnswer.getUpdatedAt());
		answer.setDeletedAt(businessAnswer.getDeletedAt());
		return answer;

	}

	
}
