package innovitics.azimut.utilities.mapping.kyc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.kyc.BusinessKYCPage;
import innovitics.azimut.businessmodels.kyc.BusinessQuestion;
import innovitics.azimut.businessmodels.kyc.BusinessSubmittedAnswer;
import innovitics.azimut.businessutilities.KYCUtility;
import innovitics.azimut.models.kyc.KYCPage;
import innovitics.azimut.models.kyc.Question;
import innovitics.azimut.utilities.datautilities.BooleanUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.mapping.GrandParentMapper;
@Component
public class KYCPageMapper extends GrandParentMapper<KYCPage, BusinessKYCPage>{

	
	@Autowired protected QuestionMapper questionMapper;
	@Autowired protected AnswerMapper answerMapper;
	@Autowired protected ListUtility<Question> baseListUtility;
	@Autowired protected ListUtility<BusinessQuestion> businessListUtility;
	@Autowired protected ListUtility<BusinessSubmittedAnswer> businessSubmittedAnswerListUtility;
	@Autowired protected KYCUtility kycUtility;

	@Override
	public KYCPage convertBusinessUnitToBasicUnit(BusinessKYCPage businessKYCPage, boolean save) {
		
		KYCPage kycPage=new KYCPage();
		
		kycPage.setId(businessKYCPage.getId());
		kycPage.setPageOrder(businessKYCPage.getPageOrder());
		kycPage.setNoOfQuestions(businessKYCPage.getNoOfQuestions());
		kycPage.setTitle(businessKYCPage.getTitle());
		kycPage.setPageDetails(businessKYCPage.getPageDetails());
		kycPage.setPageDisclaimer(businessKYCPage.getPageDisclaimer());		
		return kycPage;
		
	}

	@Override
	public BusinessKYCPage convertBasicUnitToBusinessUnit(KYCPage kycPage) {
		BusinessKYCPage  businessKYCPage=new BusinessKYCPage();
		
		businessKYCPage.setId(kycPage.getId());
		businessKYCPage.setPageOrder(kycPage.getPageOrder());
		businessKYCPage.setNoOfQuestions(kycPage.getNoOfQuestions());
		businessKYCPage.setTitle(kycPage.getTitle());
		businessKYCPage.setPageDetails(kycPage.getPageDetails());
		businessKYCPage.setPageDisclaimer(kycPage.getPageDisclaimer());
		
		businessKYCPage.setPreviousId(kycPage.getPreviousPageId());
		businessKYCPage.setNextId(kycPage.getNextPageId());
		
		if(kycPage!=null&&kycPage.getPreviousPageId()==null)
		{
			businessKYCPage.setPreviousId(kycPage.getId());
		}
		if(kycPage!=null&&kycPage.getNextPageId()==null)
		{
			businessKYCPage.setNextId(kycPage.getId());
		}
		if(kycPage.getWeight()!=null)
		{
			businessKYCPage.setVerificationPercentage(kycPage.getWeight());
		}
		
		
		if(this.baseListUtility.isSetPopulated(kycPage.getQuestions()))
		{

			LinkedList<BusinessQuestion> businessQuestions=new LinkedList<BusinessQuestion>();
			
			LinkedList<BusinessQuestion> businessSubQuestions=new LinkedList<BusinessQuestion>();
			
			for(Question question:kycPage.getQuestions())
			{
				BusinessQuestion businessQuestion=new BusinessQuestion();
				question.setAppUserId(kycPage.getAppUserId());
				question.setPageId(kycPage.getId());
				businessQuestion=this.questionMapper.convertBasicUnitToBusinessUnit(question);
				businessQuestions.add(businessQuestion);
				if(this.businessListUtility.isListPopulated(businessQuestion.getSubQuestions()))
				{
					businessSubQuestions.addAll(businessQuestion.getSubQuestions());
				}
				
			}
			
			this.matchAndAssign(businessKYCPage,businessQuestions,businessSubQuestions,kycPage.getId(), kycPage.getAppUserId());
			
			if(kycPage!=null&&BooleanUtility.isTrue(kycPage.getDraw()))
			
				businessKYCPage.setQuestions(this.kycUtility.populateTheObjectsWidth(businessQuestions));
			
			else
			
				businessKYCPage.setQuestions(businessQuestions);
				
			
			
		}
		
	
		return businessKYCPage;
	}
	
	void matchAndAssign(BusinessKYCPage businessKYCPage,List <BusinessQuestion> businessQuestions,List <BusinessQuestion> businessSubQuestions,Long pageId,Long userId)
	{
		List<BusinessSubmittedAnswer> businessSubmittedAnswers=new ArrayList<BusinessSubmittedAnswer>();
		
		businessSubmittedAnswers=this.answerMapper.populateUserAnswers(pageId, userId);				
		
		if(this.businessSubmittedAnswerListUtility.isListPopulated(businessSubmittedAnswers))
	{
		businessKYCPage.setIsAnswered(true);	
		for(BusinessQuestion businessQuestion:businessQuestions)
		{
			List<BusinessSubmittedAnswer> questionRelatedBusinessSubmittedAnswers=new ArrayList<BusinessSubmittedAnswer>();
			for(BusinessSubmittedAnswer businessSubmittedAnswer:businessSubmittedAnswers)
			{
				if(NumberUtility.areLongValuesMatching(businessQuestion.getId(),businessSubmittedAnswer.getQuestionId()))
				{
						questionRelatedBusinessSubmittedAnswers.add(businessSubmittedAnswer);
				}
			 }
			businessQuestion.setUserAnswers(questionRelatedBusinessSubmittedAnswers);
			
					
		}
		
		if (this.businessListUtility.isListPopulated(businessSubQuestions)) 
		{
			for (BusinessQuestion businessSubQuestion : businessSubQuestions) 
			{
				List<BusinessSubmittedAnswer> subQuestionRelatedBusinessSubmittedAnswers = new ArrayList<BusinessSubmittedAnswer>();
				for (BusinessSubmittedAnswer businessSubmittedAnswer : businessSubmittedAnswers) 
				{
					if (NumberUtility.areLongValuesMatching(businessSubQuestion.getId(),businessSubmittedAnswer.getQuestionId())) 
					{
						subQuestionRelatedBusinessSubmittedAnswers.add(businessSubmittedAnswer);
					}
				}
				businessSubQuestion.setUserAnswers(subQuestionRelatedBusinessSubmittedAnswers);	
			}
			
		}
		
	}
		else
			businessKYCPage.setIsAnswered(false);
		
	}

	@Override
	public BusinessKYCPage convertBasicUnitToBusinessUnit(KYCPage kycPage, String language) 
	{
		BusinessKYCPage  businessKYCPage=new BusinessKYCPage();
		
		businessKYCPage.setId(kycPage.getId());
		businessKYCPage.setPageOrder(kycPage.getPageOrder());
		businessKYCPage.setNoOfQuestions(kycPage.getNoOfQuestions());
		
		if(StringUtility.stringsMatch(language,StringUtility.ENGLISH)||!StringUtility.isStringPopulated(language))
		{
			businessKYCPage.setTitle(kycPage.getTitle());
			businessKYCPage.setPageDetails(kycPage.getPageDetails());
			businessKYCPage.setPageDisclaimer(kycPage.getPageDisclaimer());
		}
		else 
		{
			businessKYCPage.setTitle(kycPage.getTitleAr());
			businessKYCPage.setPageDetails(kycPage.getPageDetailsAr());
			businessKYCPage.setPageDisclaimer(kycPage.getPageDisclaimerAr());
		}
		
		
		businessKYCPage.setPreviousId(kycPage.getPreviousPageId());
		businessKYCPage.setNextId(kycPage.getNextPageId());
		
		if(kycPage!=null&&kycPage.getPreviousPageId()==null)
		{
			businessKYCPage.setPreviousId(kycPage.getId());
		}
		if(kycPage!=null&&kycPage.getNextPageId()==null)
		{
			businessKYCPage.setNextId(kycPage.getId());
		}
		if(kycPage.getWeight()!=null)
		{
			businessKYCPage.setVerificationPercentage(kycPage.getWeight());
		}
		
		
		if(this.baseListUtility.isSetPopulated(kycPage.getQuestions()))
		{

			LinkedList<BusinessQuestion> businessQuestions=new LinkedList<BusinessQuestion>();
			
			LinkedList<BusinessQuestion> businessSubQuestions=new LinkedList<BusinessQuestion>();
			
			for(Question question:kycPage.getQuestions())
			{
				BusinessQuestion businessQuestion=new BusinessQuestion();
				question.setAppUserId(kycPage.getAppUserId());
				question.setPageId(kycPage.getId());
				businessQuestion=this.questionMapper.convertBasicUnitToBusinessUnit(question,language);
				businessQuestions.add(businessQuestion);
				if(this.businessListUtility.isListPopulated(businessQuestion.getSubQuestions()))
				{
					businessSubQuestions.addAll(businessQuestion.getSubQuestions());
				}
				
			}
			
			this.matchAndAssign(businessKYCPage,businessQuestions,businessSubQuestions,kycPage.getId(), kycPage.getAppUserId());
			
			if(kycPage!=null&&BooleanUtility.isTrue(kycPage.getDraw()))
			
				businessKYCPage.setQuestions(this.kycUtility.populateTheObjectsWidth(businessQuestions));	
			else
				businessKYCPage.setQuestions(businessQuestions);
			}
		return businessKYCPage;
		}	
	}
