package innovitics.azimut.businessservices;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.kyc.BusinessKYCPage;
import innovitics.azimut.businessmodels.kyc.BusinessUserAnswerSubmission;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.kyc.UserAnswer;
import innovitics.azimut.services.kyc.KYCPageService;
import innovitics.azimut.services.kyc.UserAnswerSubmissionService;
import innovitics.azimut.utilities.crosslayerenums.AnswerType;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.fileutilities.BlobData;
import innovitics.azimut.utilities.kycutilities.AnswerTypeUtility;
import innovitics.azimut.utilities.mapping.kyc.UserAnswerMapper;
import innovitics.azimut.utilities.mapping.kyc.UserAnswersIntermediary;
@Service
public class BusinessUserAnswerSubmissionService extends AbstractBusinessService<BusinessUserAnswerSubmission>{

	@Autowired UserAnswerSubmissionService userAnswerSubmissionService;
	@Autowired UserAnswerMapper userAnswerMapper;
	@Autowired ListUtility<UserAnswer> userAnswerListUtility;
	@Autowired AnswerTypeUtility  answerTypeUtility;
	@Autowired BusinessUserService businessUserService;
	@Autowired KYCPageService kycPageService;

	public BusinessKYCPage submitAnswers(BusinessUser businessUser,BusinessUserAnswerSubmission businessUserAnswerSubmission) throws BusinessException
	{
		
		BusinessKYCPage businessKYCPage=new BusinessKYCPage();
		this.validation.checkUserAnswersValidity(businessUserAnswerSubmission);
		try 
		{
				this.userAnswerSubmissionService.deleteOldUserAnswers(businessUserAnswerSubmission.getPageId(),businessUser.getId());
				this.processAndSaveAnswers(businessUser.getId(),businessUserAnswerSubmission);
				businessUser.setLastSolvedPageId(businessUserAnswerSubmission.getPageId());
				businessUser.setNextPageId(businessUserAnswerSubmission.getNextPageId());
				businessUser=this.userUtility.isOldUserStepGreaterThanNewUserStep(businessUser, businessUserAnswerSubmission.getUserStep());
				businessKYCPage.setVerificationPercentage(this.updateUserProgress(businessUser, this.findPageWeight(businessUserAnswerSubmission.getPageId())));	
				this.businessUserService.editUser(businessUser);
		} 
		catch (Exception exception) 
		{			
			this.logger.info("Could not submit the user answers.");	
			this.handleBusinessException(exception, ErrorCode.ANSWER_SUBMISSION_FAILED);
		}
		
		return businessKYCPage;
	}
	
	
	
	public void processAndSaveAnswers(Long userId,BusinessUserAnswerSubmission businessUserAnswerSubmission) throws BusinessException
	{
		
		boolean relatedAnswersExist=false;
		List<UserAnswersIntermediary> userAnswersIntermediaries=userAnswerMapper.convertBusinessUserAnswerSubmissionToUserAnswerList(userId,businessUserAnswerSubmission);
		LinkedList<UserAnswer> userAnswers=new LinkedList<>();
		LinkedList<UserAnswer> relatedAnswers=new LinkedList<>();
		
		for(UserAnswersIntermediary userAnswersIntermediary: userAnswersIntermediaries)
		{
			this.logger.info("User Answer Int:::"+ userAnswersIntermediary.toString());
			this.copyAnswerIfDocument(userAnswersIntermediary.getParentAnswer());
			userAnswers.add(userAnswersIntermediary.getParentAnswer());
			
			if(userAnswerListUtility.isListPopulated(userAnswersIntermediary.getRelatedAnswers()))
			{
				relatedAnswersExist=true;
				relatedAnswers.addAll(userAnswersIntermediary.getRelatedAnswers());
			}
			
		}
		this.userAnswerSubmissionService.submitAnswers(userAnswers);
		
		if(relatedAnswersExist)
		{
			for(UserAnswer userAnswer:relatedAnswers)
			{
				this.copyAnswerIfDocument(userAnswer);
			}			
			this.userAnswerSubmissionService.submitAnswers(relatedAnswers);
		}
		
	}
	
	public BusinessKYCPage uploadDocumentAndOverwriteOldDocumentIfExisting(BusinessUser businessUser,Long answerId,MultipartFile file) throws BusinessException
	{		
		BusinessKYCPage businessKYCPage=new BusinessKYCPage();
		if (businessUser != null) 
		{
			UserAnswer oldUserAnswer=this.checkForPreviousAnswer(businessUser, answerId);			
			if(oldUserAnswer!=null)
			{
				try 
				{
					this.deleteDocument(oldUserAnswer.getDocumentSubDirectory(), oldUserAnswer.getDocumentName());					
				}
				catch(Exception exception)
				{
					this.handleBusinessException(exception, ErrorCode.UPLOAD_FAILURE);
				}				
			}			
			this.uploadDocument(businessUser, answerId, file);	
		}		
		return businessKYCPage;
	}
	
	
	
	public UserAnswer checkForPreviousAnswer(BusinessUser businessUser,Long answerId)
	{
		try
		{
			return this.userAnswerSubmissionService.getPreviousAnswerIfExisting(businessUser.getId(), answerId);
		}
		catch(Exception exception)
		{
			if(this.exceptionHandler.isABusinessException(exception))
			{
				return null;
			}
		}
		return null;
	}
	
	public BusinessKYCPage uploadDocument(BusinessUser businessUser,Long answerId,MultipartFile file) throws BusinessException
	{
		BusinessKYCPage businessKYCPage=new BusinessKYCPage();
		UserAnswer  userAnswer=this.checkForPreviousAnswer(businessUser, answerId);
		if(userAnswer!=null)
		{	
				this.updateAnswer(userAnswer);
		}
		try 
		{
			
			BlobData blobData=this.blobFileUtility.uploadFileToBlob(file, true, this.configProperties.getBlobKYCDocumentsTemp(),DateUtility.getCurrentDayMonthYear()+"/"+businessUser.getUserId(),true);
			businessKYCPage.setDocumentURL(blobData.getConcatinated(false));
			businessKYCPage.setDocumentSize(blobData.getFileSize());
			businessKYCPage.setDocumentName(blobData.getFileName());
			businessKYCPage.setDocumentSubDirectory(blobData.getSubDirectory());
			
		} catch (IOException exception) {
			this.logger.info("Failed to upload the document.");
			exception.printStackTrace();
			this.handleBusinessException(exception, ErrorCode.UPLOAD_FAILURE);
		}
		return businessKYCPage;
	}
	
	public void deleteDocument(String subDiretory,String fileName) throws IOException
	{	
		this.blobFileUtility.deleteFileFromBlob(this.configProperties.getBlobKYCDocuments(),subDiretory, fileName, false);
	}
	
	public void copyDocument(String subDiretory,String fileName)
	{	
		try 
		{
			this.blobFileUtility.copyBlobFile(this.configProperties.getBlobKYCDocumentsTemp(), this.configProperties.getBlobKYCDocuments(), subDiretory, fileName, false);
		}
		catch(Exception exception)
		{
			this.handleBusinessException(exception, ErrorCode.COPY_FAILURE);
		}
	}
	
	void copyAnswerIfDocument(UserAnswer userAnswer)
	{
		if(this.answerTypeUtility.isAnswerTypeMatching(userAnswer, AnswerType.DOCUMENT))
		{
			this.copyDocument(userAnswer.getDocumentSubDirectory(), userAnswer.getDocumentName());
		}
	}
	
	UserAnswer	updateAnswer(UserAnswer userAnswer)
	{
		try
		{
			this.deleteDocument(userAnswer.getDocumentSubDirectory(), userAnswer.getDocumentName());
		} 
		catch (IOException exception) 
		{
			this.handleBusinessException(exception, ErrorCode.UPLOAD_FAILURE);
		}
		
		if(userAnswer!=null)
		{
			userAnswer.setDocumentName(null);
			userAnswer.setDocumentSize(null);
			userAnswer.setDocumentSubDirectory(null);
			userAnswer.setDocumentUrl(null);
		}
		
		return 	this.userAnswerSubmissionService.updateAnswer(userAnswer);
	}
	
	Integer updateUserProgress(BusinessUser businessUser,Integer pageWeight)
	{
		if(businessUser!=null&&businessUser.getVerificationPercentage()==null)
		{
			businessUser.setVerificationPercentage(pageWeight);
		}
		else if(businessUser!=null&&businessUser.getVerificationPercentage()!=null)
		{
			int currentValue=businessUser.getVerificationPercentage();
			businessUser.setVerificationPercentage(currentValue+pageWeight.intValue());
		}
		return businessUser.getVerificationPercentage();
	}
	
	Integer findPageWeight(Long id)
	{
		try 
		{
			return this.kycPageService.findPageWeightById(id);
		}
		catch(Exception exception)
		{
			if(this.exceptionHandler.isABusinessException(exception))
			{
				return null;
			}
		}
		return null;
	}
}
