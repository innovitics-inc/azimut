package innovitics.azimut.businessservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.kyc.BusinessKYCPage;
import innovitics.azimut.businessmodels.kyc.BusinessQuestion;
import innovitics.azimut.businessmodels.kyc.BusinessSubmittedAnswer;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.kyc.KYCPage;
import innovitics.azimut.models.kyc.Review;
import innovitics.azimut.models.user.UserType;
import innovitics.azimut.services.kyc.KYCPageService;
import innovitics.azimut.utilities.businessutilities.ReviewUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.logging.MyLogger;
import innovitics.azimut.utilities.mapping.kyc.KYCPageMapper;

@Service
public class BusinessKYCPageService extends AbstractBusinessService<BusinessKYCPage> {

	@Autowired  KYCPageService kycPageService;	
	@Autowired KYCPageMapper kycPageMapper;
	@Autowired ListUtility<BusinessQuestion> childListUtility;
	@Autowired ListUtility<BusinessSubmittedAnswer> grandChildListUtility;
	@Autowired ReviewUtility reviewUtility; 
	
	public BusinessKYCPage getKycPagebyId(BusinessUser businessUser,Long id,Boolean draw,String language) throws BusinessException
	{

		this.validation.validateUserKYCCompletion(businessUser);
		
		try 
			{
				/*KYCPage kycPage=this.kycPageService.getById(id);
				kycPage.setAppUserId(businessUser.getId());
				kycPage.setDraw(draw);
				BusinessKYCPage businessKYCPage=this.kycPageMapper.convertBasicUnitToBusinessUnit(kycPage,language,true);
				businessKYCPage.setVerificationPercentage(businessUser.getVerificationPercentage());
				return this.generateUrls(businessKYCPage);*/
				return this.getPageWithDetails(businessUser, id, draw, language);
		
			}
		catch (Exception exception) 
			{
				throw this.handleBusinessException(exception,ErrorCode.PAGE_NOT_FOUND);
			}
	}
	public BusinessKYCPage getPageWithDetails(BusinessUser businessUser,Long id,Boolean draw,String language) throws BusinessException
	{
		try 
		{
			KYCPage kycPage=this.kycPageService.getById(id);
			kycPage.setAppUserId(businessUser.getId());
			kycPage.setDraw(draw);
			BusinessKYCPage businessKYCPage=this.kycPageMapper.convertBasicUnitToBusinessUnit(kycPage,language,true);
			businessKYCPage.setVerificationPercentage(businessUser.getVerificationPercentage());
			return this.generateUrls(businessKYCPage);
	
		}
		catch (Exception exception) 
		{
			throw this.handleBusinessException(exception,ErrorCode.PAGE_NOT_FOUND);
		}
	}
	public List<BusinessKYCPage> getUserKycPages(BusinessUser tokenizedBusinessUser,Boolean draw,String language) throws BusinessException
	{
		MyLogger.info("enter::::");
		List<BusinessKYCPage> businessKYCPages=new ArrayList<BusinessKYCPage>();
		List<BusinessQuestion> businessQuestions=new LinkedList<BusinessQuestion>();
		List<BusinessQuestion> businessSubQuestions=new LinkedList<BusinessQuestion>();
		List<BusinessSubmittedAnswer> businessSubmittedAnswers=new ArrayList<BusinessSubmittedAnswer>();	
			  try 
			  {
					List<KYCPage> kycPages=this.kycPageService.getByUserType(tokenizedBusinessUser.getIdType());				
					for(KYCPage kycPage:kycPages)
					{
						BusinessKYCPage businessKYCPage=this.kycPageMapper.convertBasicUnitToBusinessUnit(kycPage,language,false);	
						businessQuestions.addAll(businessKYCPage.getQuestions());
						if(childListUtility.isListPopulated(businessQuestions))
						{
							MyLogger.info("enter2::::");
							for(BusinessQuestion businessQuestion:businessQuestions)
							{
								if(childListUtility.isListPopulated(businessQuestion.getSubQuestions()))
								{
									businessSubQuestions.addAll(businessQuestion.getSubQuestions());
								}
							}
						}
						businessKYCPages.add(businessKYCPage);
					}
					businessSubmittedAnswers=this.kycPageMapper.answerMapper.populateUserAnswersForAllPages(tokenizedBusinessUser.getId());
					kycPageMapper.matchAndAssignForList(businessSubmittedAnswers,businessQuestions,businessSubQuestions);
					return this.generateUrlsForList(businessKYCPages);			
				}
				catch (Exception exception) 
				{
					throw this.handleBusinessException(exception,ErrorCode.PAGE_NOT_FOUND);
				}
	}
	
	public List<BusinessKYCPage> getAllPagesByUserTypeId(BusinessUser businessUser) throws BusinessException
	{
		List<BusinessKYCPage> businessKYCPages = new ArrayList<BusinessKYCPage>();
		try {
			/*
			 for(KYCPage kycPage:this.kycPageService.getByUserType(businessUser.getUserIdType())) 
			 {
			  kycPage.setAppUserId(businessUser.getId());
			  businessKYCPages.add(this.kycPageMapper.convertBasicUnitToBusinessUnit(kycPage)); 
			 }
			 */
			}		 
		catch (Exception exception) {
			throw this.handleBusinessException(exception, ErrorCode.PAGE_NOT_FOUND);
		}
		return businessKYCPages;
	}
	
	
	public List<BusinessKYCPage> generateUrlsForList(List<BusinessKYCPage> inputBusinessKYCPages) throws IOException
	{
		MyLogger.info("enter5::::");
		List<BusinessKYCPage> businessKYCPages=new ArrayList<BusinessKYCPage>();
		for(BusinessKYCPage businessKYCPage:inputBusinessKYCPages)
		{
			businessKYCPages.add(this.generateUrls(businessKYCPage));
		}
		return businessKYCPages;
	}
	
	public BusinessKYCPage generateUrls(BusinessKYCPage businessKYCPage) throws IOException
	{
		if(businessKYCPage!=null&&this.childListUtility.isListPopulated(businessKYCPage.getQuestions()))
		{
			for(BusinessQuestion businessQuestion:businessKYCPage.getQuestions())
			{
				if(businessQuestion!=null&&grandChildListUtility.isListPopulated(businessQuestion.getUserAnswers()))
				  {	
					for(BusinessSubmittedAnswer businessSubmittedAnswer:businessQuestion.getUserAnswers())
					{
						businessSubmittedAnswer=this.generateTheURL(businessSubmittedAnswer);
						
						if(businessSubmittedAnswer!=null&&this.grandChildListUtility.isListPopulated(businessSubmittedAnswer.getRelatedUserAnswers()))
						{
							for(BusinessSubmittedAnswer relatedBusinessSubmittedAnswer:businessSubmittedAnswer.getRelatedUserAnswers())
							{
								relatedBusinessSubmittedAnswer=this.generateTheURL(relatedBusinessSubmittedAnswer);
							}
						}
					}
				}			
		    }
		}
		return businessKYCPage;
	}
	
	BusinessSubmittedAnswer generateTheURL(BusinessSubmittedAnswer businessSubmittedAnswer) throws IOException
	{
		if(businessSubmittedAnswer!=null
				&&StringUtility.isStringPopulated(businessSubmittedAnswer.getDocumentName())&&StringUtility.isStringPopulated(businessSubmittedAnswer.getDocumentSubDirectory()))
		{
			MyLogger.info("Generating the Document URL");
			businessSubmittedAnswer.setDocumentURL
			(
			  //this.blobFileUtility.generateFileRetrievalUrl(this.configProperties.getBlobKYCDocuments(), businessSubmittedAnswer.getDocumentName(), businessSubmittedAnswer.getDocumentSubDirectory(), true,15L)
				this.storageService.generateFileRetrievalUrl(this.configProperties.getBlobKYCDocuments(), businessSubmittedAnswer.getDocumentName(), businessSubmittedAnswer.getDocumentSubDirectory(), true,15L)
			);	
		}
		return businessSubmittedAnswer;
	}
	
	
	public int adjustProgress(BusinessKYCPage businessKYCPage,BusinessUser businessUser)
	{
		int progress=0;
		
		if(businessUser!=null&&businessUser.getVerificationPercentage()!=null)
		{
			progress=businessUser.getVerificationPercentage().intValue();
		}
		if(businessKYCPage!=null&&businessKYCPage.getVerificationPercentage()!=null)
		{
			progress=progress+businessKYCPage.getVerificationPercentage().intValue();
		}
		
		return progress;
	}
	
	
	BusinessKYCPage getReviewedPage(BusinessUser businessUser,BusinessKYCPage page,Boolean draw,String language)
	{
		if(true&&page!=null&&this.childListUtility.isListPopulated(page.getQuestions()))
		{
			List<Review> reviews=new ArrayList<Review>();
			this.reviewUtility.getReviews();
		}
			
		return page;
	}
}
