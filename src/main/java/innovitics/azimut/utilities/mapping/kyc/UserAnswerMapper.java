package innovitics.azimut.utilities.mapping.kyc;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.kyc.BusinessRelatedAnswer;
import innovitics.azimut.businessmodels.kyc.BusinessSubmittedAnswer;
import innovitics.azimut.businessmodels.kyc.BusinessUserAnswerSubmission;
import innovitics.azimut.businessmodels.kyc.BusinessUserSubmittedAnswer;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.kyc.Answer;
import innovitics.azimut.models.kyc.Question;
import innovitics.azimut.models.kyc.UserAnswer;
import innovitics.azimut.models.user.User;
import innovitics.azimut.utilities.datautilities.ArrayUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;
import innovitics.azimut.utilities.kycutilities.AnswerTypeUtility;
import innovitics.azimut.utilities.mapping.Mapper;
@Component
public class UserAnswerMapper extends Mapper<UserAnswer, BusinessSubmittedAnswer>{
@Autowired BlobFileUtility blobFileUtility;
@Autowired ArrayUtility arrayUtility;
@Autowired AnswerTypeUtility answerTypeUtility;


public List<UserAnswersIntermediary> convertBusinessUserAnswerSubmissionToUserAnswerList(Long userId,BusinessUserAnswerSubmission businUserAnswerSubmission) throws BusinessException
{		
	LinkedList<UserAnswersIntermediary> userAnswers=new LinkedList<UserAnswersIntermediary>();
	if(businUserAnswerSubmission!=null)
	{
		if(arrayUtility.isArrayPopulated(businUserAnswerSubmission.getUserAnswers()))
		{
			for(BusinessUserSubmittedAnswer businessUserSubmittedAnswer:businUserAnswerSubmission.getUserAnswers())
			{
				userAnswers.addAll(this.convertBusinessUserSubmittedAnswerToUserAnswerList(userId,businUserAnswerSubmission.getPageId(),businessUserSubmittedAnswer));
			}
		}
	}
	return userAnswers;
}



	public List<UserAnswersIntermediary> convertBusinessUserSubmittedAnswerToUserAnswerList(Long userId,Long pageId,BusinessUserSubmittedAnswer businessUserSubmittedAnswer) throws BusinessException
	{		
		LinkedList<UserAnswersIntermediary> userAnswers=new LinkedList<UserAnswersIntermediary>();
		if(businessUserSubmittedAnswer!=null)
		{
			User user=new User();
			user.setId(userId);
			Question question=new Question();
			question.setId(businessUserSubmittedAnswer.getQuestionId());
			if(this.arrayUtility.isArrayPopulated(businessUserSubmittedAnswer.getAnswers()))
			{
				for(BusinessSubmittedAnswer businessSubmittedAnswer:businessUserSubmittedAnswer.getAnswers())
				{

					userAnswers.add(this.convertBusinessAnswerToAnswer(businessSubmittedAnswer,question,user,pageId));
				}
			}
		}
		return userAnswers;
	}
	
	private UserAnswersIntermediary convertBusinessAnswerToAnswer(BusinessSubmittedAnswer  businessSubmittedAnswer,Question question,User user,Long pageId) throws BusinessException
	{
	
		if (businessSubmittedAnswer != null) 
		{
			UserAnswer userAnswer = new UserAnswer();
			userAnswer.setUserId(user.getId());
			/*
			userAnswer.setQuestion(question);
			userAnswer.setAnswerId(businessSubmittedAnswer.getAnswerId());
			*/
			userAnswer.setQuestionId(question.getId());
			Answer answer=new Answer();
			answer.setId(businessSubmittedAnswer.getAnswerId());
			userAnswer.setAnswerId(answer.getId());
			userAnswer.setAnswerValue(businessSubmittedAnswer.getAnswerValue());
			userAnswer.setCountryPhoneCode(businessSubmittedAnswer.getCountryPhoneCode());
			userAnswer.setAnswerType(businessSubmittedAnswer.getAnswerType());
			userAnswer.setDocumentUrl(businessSubmittedAnswer.getDocumentURL());
			userAnswer.setDocumentName(businessSubmittedAnswer.getDocumentName());
			userAnswer.setDocumentSubDirectory(businessSubmittedAnswer.getDocumentSubDirectory());
			userAnswer.setCountryCode(businessSubmittedAnswer.getCountryCode());
			userAnswer.setPageId(pageId);
			userAnswer.setCreatedAt(new Date());
			if(businessSubmittedAnswer.getDocumentSize()!=null)
			userAnswer.setDocumentSize(businessSubmittedAnswer.getDocumentSize().toString());
			
			
			return new UserAnswersIntermediary(userAnswer, this.populateRelatedAnswers(businessSubmittedAnswer,user,pageId));
		}

		return null;
	}
	
	public BusinessSubmittedAnswer convertAnswerToBusinessAnswer(UserAnswer userAnswer)
	{
	
		if (userAnswer != null) 
		{
			BusinessSubmittedAnswer businessSubmittedAnswer = new BusinessSubmittedAnswer();
			businessSubmittedAnswer.setQuestionId(userAnswer.getQuestionId());
			businessSubmittedAnswer.setAnswerId(userAnswer.getAnswerId());			
			businessSubmittedAnswer.setAnswerType(userAnswer.getAnswerType());
			businessSubmittedAnswer.setAnswerValue(userAnswer.getAnswerValue());
			businessSubmittedAnswer.setDocumentURL(null);
			businessSubmittedAnswer.setDocumentName(userAnswer.getDocumentName());
			if(StringUtility.isStringPopulated(userAnswer.getDocumentSize()))
			businessSubmittedAnswer.setDocumentSize(Double.valueOf(userAnswer.getDocumentSize()));
			businessSubmittedAnswer.setDocumentSubDirectory(userAnswer.getDocumentSubDirectory());
			businessSubmittedAnswer.setCountryCode(userAnswer.getCountryCode());
			businessSubmittedAnswer.setCountryPhoneCode(userAnswer.getCountryPhoneCode());
			return businessSubmittedAnswer;
		}

		return null;
	}

	private List<UserAnswer> populateRelatedAnswers(BusinessSubmittedAnswer  businessSubmittedAnswer,User user,Long pageId) throws BusinessException
	{
		
		if (arrayUtility.isArrayPopulated(businessSubmittedAnswer.getRelatedAnswers())) 
		{	
			Answer answer=new Answer();
			answer.setId(businessSubmittedAnswer.getAnswerId());
			LinkedList<UserAnswer> relatedAnswers=new LinkedList<UserAnswer>();
			for(BusinessRelatedAnswer businessRelatedAnswer:businessSubmittedAnswer.getRelatedAnswers())
			{
				relatedAnswers.add(this.convertRelatedBusinessAnswerToAnswer(businessRelatedAnswer,answer,user,pageId));
			}
			return relatedAnswers;
		}
	
		return null;
		
	}
	
	
	
	private UserAnswer convertRelatedBusinessAnswerToAnswer(BusinessRelatedAnswer  businessRelatedAnswer,Answer parentAnswer, User user,Long pageId) throws BusinessException
	{
		if(businessRelatedAnswer!=null)
		{
		Answer answer=new Answer();
		answer.setId(businessRelatedAnswer.getRelatedAnswerId());
		UserAnswer userAnswer=new UserAnswer();
		userAnswer.setAnswerId(answer.getId());
		userAnswer.setDocumentUrl(businessRelatedAnswer.getDocumentURL());
		userAnswer.setDocumentName(businessRelatedAnswer.getDocumentName());
		userAnswer.setDocumentSubDirectory(businessRelatedAnswer.getDocumentSubDirectory());
		if(businessRelatedAnswer.getDocumentSize()!=null)
		userAnswer.setDocumentSize(businessRelatedAnswer.getDocumentSize().toString());
		userAnswer.setRelatedAnswerId(parentAnswer.getId());
		userAnswer.setAnswerType(businessRelatedAnswer.getRelatedAnswerType());
		userAnswer.setAnswerValue(businessRelatedAnswer.getRelatedAnswerValue());
		userAnswer.setCountryPhoneCode(businessRelatedAnswer.getCountryPhoneCode());
		userAnswer.setCountryCode(businessRelatedAnswer.getCountryCode());		
		userAnswer.setCreatedAt(new Date());
		userAnswer.setUserId(user.getId());
		userAnswer.setPageId(pageId);
		return userAnswer;
		}
		
		return null;
	}
	
	
	public BusinessRelatedAnswer convertRelatedAnswerToBusinessRelatedAnswer(UserAnswer userAnswer)
	{
	
		if (userAnswer != null) 
		{
			BusinessRelatedAnswer businessRelatedAnswer = new BusinessRelatedAnswer();
			businessRelatedAnswer.setRelatedAnswerId(userAnswer.getRelatedAnswerId());		
			businessRelatedAnswer.setRelatedAnswerType(userAnswer.getAnswerType());
			businessRelatedAnswer.setRelatedAnswerValue(userAnswer.getAnswerValue());
			businessRelatedAnswer.setDocumentURL(userAnswer.getDocumentUrl());
			businessRelatedAnswer.setDocumentName(userAnswer.getDocumentName());
			if(StringUtility.isStringPopulated(userAnswer.getDocumentSize()))
				businessRelatedAnswer.setDocumentSize(Double.valueOf(userAnswer.getDocumentSize()));
			
			
			return businessRelatedAnswer;
		}

		return null;
	}


	@Override
	protected UserAnswer convertBusinessUnitToBasicUnit(BusinessSubmittedAnswer t, boolean save) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected BusinessSubmittedAnswer convertBasicUnitToBusinessUnit(UserAnswer s) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
