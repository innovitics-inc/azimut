package innovitics.azimut.utilities.mapping.kyc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.kyc.BusinessAnswer;
import innovitics.azimut.businessmodels.kyc.BusinessQuestion;
import innovitics.azimut.businessmodels.kyc.BusinessSubmittedAnswer;
import innovitics.azimut.models.kyc.Answer;
import innovitics.azimut.models.kyc.Question;
import innovitics.azimut.models.kyc.UserAnswer;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.logging.MyLogger;
import innovitics.azimut.utilities.mapping.ParentMapper;
@Component
public class QuestionMapper extends ParentMapper<Question,BusinessQuestion>{
	
	@Autowired protected AnswerMapper answerMapper;
	@Autowired protected ListUtility<Answer> baseChildListUtility;
	@Autowired protected ListUtility<BusinessAnswer> businessChildListUtility;
	@Autowired protected ListUtility<BusinessSubmittedAnswer> businessSubmittedAnswerListUtility;
	@Autowired protected ListUtility<Question> baseListUtility;
	@Autowired protected ListUtility<BusinessQuestion> businessListUtility;
	
	@Override
	public Question convertBusinessUnitToBasicUnit(BusinessQuestion businessQuestion, boolean save) {
		Question question=new Question();
		question.setId(businessQuestion.getId());
		question.setQuestionText(businessQuestion.getQuestionText());
		question.setIsAnswerMandatory(businessQuestion.getIsAnswerMandatory());
		question.setAnswerType(businessQuestion.getAnswerType());
		return question;
	}

	@Override
	public BusinessQuestion convertBasicUnitToBusinessUnit(Question question) {
		BusinessQuestion businessQuestion=convertBasicQuestionToBusinessQuestion(question,null);
		
		if(this.baseListUtility.isSetPopulated(question.getSubQuestions()))
		{
			LinkedList<BusinessQuestion> businessSubQuestions=new LinkedList<BusinessQuestion>();
			for(Question subQuestion:question.getSubQuestions())
			{
				subQuestion.setAppUserId(question.getAppUserId());
				subQuestion.setPageId(question.getPageId());
				businessSubQuestions.add(this.convertBasicQuestionToBusinessQuestion(subQuestion,null));
			}
			businessQuestion.setSubQuestions(businessSubQuestions);
		}
		
		return businessQuestion;
	}
	
	@Override
	protected BusinessQuestion convertBasicUnitToBusinessUnit(Question question, String language) {
		BusinessQuestion businessQuestion=convertBasicQuestionToBusinessQuestion(question,language);
		
		if(this.baseListUtility.isSetPopulated(question.getSubQuestions()))
		{
			LinkedList<BusinessQuestion> businessSubQuestions=new LinkedList<BusinessQuestion>();
			for(Question subQuestion:question.getSubQuestions())
			{
				subQuestion.setAppUserId(question.getAppUserId());
				subQuestion.setPageId(question.getPageId());
				businessSubQuestions.add(this.convertBasicQuestionToBusinessQuestion(subQuestion,language));
			}
			businessQuestion.setSubQuestions(businessSubQuestions);
		}
		
		return businessQuestion;
	}
	
	
	private BusinessQuestion convertBasicQuestionToBusinessQuestion(Question question,String language) 
	{
		BusinessQuestion businessQuestion=new BusinessQuestion();
		businessQuestion.setId(question.getId());
		businessQuestion.setIsAnswerMandatory(question.getIsAnswerMandatory());
		businessQuestion.setAnswerType(question.getAnswerType());
		businessQuestion.setQuestionOrder(question.getQuestionOrder());
		businessQuestion.setPdfieldName(question.getPdfField());
		if(StringUtility.stringsMatch(language,StringUtility.ENGLISH)||!StringUtility.isStringPopulated(language))
		{
			businessQuestion.setQuestionText(question.getQuestionText());
			businessQuestion.setQuestionPlaceHolder(question.getQuestionPlaceHolder());
		}
		else
		{
			businessQuestion.setQuestionText(question.getQuestionTextAr());
			businessQuestion.setQuestionPlaceHolder(question.getQuestionPlaceHolderAr());
		}
		
		
		if(this.baseChildListUtility.isSetPopulated(question.getAnswers()))
		{
			LinkedList<BusinessAnswer> businessAnswers=new LinkedList<BusinessAnswer>();
			for(Answer answer:question.getAnswers())
			{
				answer.setPageId(question.getPageId());
				answer.setAppUserId(question.getAppUserId());
				BusinessAnswer businessAnswer=this.answerMapper.convertBasicUnitToBusinessUnit(answer,language);
					if(this.businessSubmittedAnswerListUtility.isListPopulated(businessAnswer.getBusinessSubmittedAnswers()))
					{
						MyLogger.info("The business answer in the Question Mapper::::");

						businessAnswer.setBusinessSubmittedAnswers(null);
					}
				businessAnswers.add(businessAnswer);
				
			}
			businessQuestion.setAnswers(businessAnswers);
			
		}
		return businessQuestion;
	
	}

	
	

}
