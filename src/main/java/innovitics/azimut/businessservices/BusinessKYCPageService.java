package innovitics.azimut.businessservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.kyc.BusinessKYCPage;
import innovitics.azimut.businessmodels.kyc.BusinessQuestion;
import innovitics.azimut.businessmodels.kyc.BusinessSubmittedAnswer;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.kyc.KYCPage;
import innovitics.azimut.models.user.UserType;
import innovitics.azimut.services.kyc.KYCPageService;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.mapping.kyc.KYCPageMapper;

@Service
public class BusinessKYCPageService extends AbstractBusinessService<BusinessKYCPage> {

	@Autowired  KYCPageService kycPageService;	
	@Autowired KYCPageMapper kycPageMapper;
	@Autowired ListUtility<BusinessQuestion> childListUtility;
	@Autowired ListUtility<BusinessSubmittedAnswer> grandChildListUtility;
	
	public BusinessKYCPage getKycPagebyId(Long userId,Long id,Boolean draw) throws BusinessException
	{

		try 
			{
				KYCPage kycPage=this.kycPageService.getById(id);
				kycPage.setAppUserId(userId);
				kycPage.setDraw(draw);
				
				return this.generateUrls(this.kycPageMapper.convertBasicUnitToBusinessUnit(kycPage));
		
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
			this.logger.info("Generating the Document URL");
			businessSubmittedAnswer.setDocumentURL
			(this.blobFileUtility.generateFileRetrievalUrl(this.configProperties.getBlobKYCDocuments(), businessSubmittedAnswer.getDocumentName(), businessSubmittedAnswer.getDocumentSubDirectory(), true,15L));	
		}
		return businessSubmittedAnswer;
	}
	
	
}
