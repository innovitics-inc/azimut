package innovitics.azimut.businessservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import innovitics.azimut.businessmodels.kyc.BusinessKYCPage;
import innovitics.azimut.businessmodels.kyc.BusinessQuestion;
import innovitics.azimut.businessmodels.kyc.BusinessSubmittedAnswer;
import innovitics.azimut.businessmodels.kyc.BusinessUserAnswerSubmission;
import innovitics.azimut.businessmodels.kyc.BusinessUserSubmittedAnswer;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.kyc.KYCPage;
import innovitics.azimut.models.kyc.Question;
import innovitics.azimut.models.kyc.UserAnswer;
import innovitics.azimut.pdfgenerator.PdfGenerateService;
import innovitics.azimut.services.kyc.KYCPageService;
import innovitics.azimut.services.kyc.UserAnswerSubmissionService;
import innovitics.azimut.utilities.crosslayerenums.AnswerType;
import innovitics.azimut.utilities.crosslayerenums.UserStep;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.fileutilities.BlobData;
import innovitics.azimut.utilities.kycutilities.AnswerTypeUtility;
import innovitics.azimut.utilities.mapping.kyc.KYCPageMapper;
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
	@Autowired PdfGenerateService pdfGenerateService;
	@Autowired ListUtility<BusinessQuestion> questionListUtility;
	@Autowired KYCPageMapper kycPageMapper;
	@Autowired ListUtility<Integer> integerListUtility;
	public BusinessKYCPage submitAnswers(BusinessUser businessUser,BusinessUserAnswerSubmission businessUserAnswerSubmission) throws BusinessException
	{
		this.validation.validateUserKYCCompletion(businessUser);
		BusinessKYCPage businessKYCPage=new BusinessKYCPage();
		this.validation.checkUserAnswersValidity(businessUserAnswerSubmission);
		try 
		{
			KYCPage  pageDetails=this.kycPageService.getById(businessUserAnswerSubmission.getPageId());
			
					if(!this.userAnswerSubmissionService.checkOldAnswerExistence(businessUserAnswerSubmission.getPageId(), businessUser.getId()))
					{
						int weight=0;
						int order=0;						
						weight=pageDetails.getWeight().intValue();
						order=pageDetails.getPageOrder().intValue();
						businessUser.setLastSolvedPageId(businessUserAnswerSubmission.getPageId());
						businessUser.setNextPageId(businessUserAnswerSubmission.getNextPageId()!=null?businessUserAnswerSubmission.getNextPageId():businessUserAnswerSubmission.getPageId());
						StringBuffer stringBuffer=new StringBuffer(businessUser!=null&&StringUtility.isStringPopulated(businessUser.getSolvedPages())?businessUser.getSolvedPages():"");
						//businessUser.setSolvedPages(stringBuffer.append(String.valueOf(order)+",").toString());
						businessUser.setSolvedPages(addSolvedPageOrder(order,stringBuffer));
						businessKYCPage.setVerificationPercentage(this.updateUserProgress(businessUser,weight));						
					}	
					else
					{
						businessKYCPage.setVerificationPercentage(businessUser.getVerificationPercentage());
					}	
					this.userAnswerSubmissionService.deleteOldUserAnswers(businessUserAnswerSubmission.getPageId(),businessUser.getId());
					this.generatePdf(this.kycPageMapper.convertBasicUnitToBusinessUnit(pageDetails, businessUserAnswerSubmission.getLanguage(), false),businessUserAnswerSubmission,businessUser);
					this.processAndSaveAnswers(businessUser.getId(),businessUserAnswerSubmission);					
					//businessUser=this.userUtility.isOldUserStepGreaterThanNewUserStep(businessUser, businessUserAnswerSubmission.getUserStep());
					businessUser.setUserStep(UserStep.KYC.getStepId());
				this.businessUserService.editUser(businessUser);
				
		} 
		catch (Exception exception) 
		{			
			this.logger.info("Could not submit the user answers.");	
			throw handleBusinessException(exception, ErrorCode.ANSWER_SUBMISSION_FAILED);
		}
		
		return businessKYCPage;
	}
	
	String addSolvedPageOrder(int order,StringBuffer stringBuffer)
	{				
		List<Integer> pageOrders = StringUtility.splitStringUsingCharacterToIntegerArray(stringBuffer.toString(), ",");
		if (integerListUtility.isListPopulated(pageOrders)&&pageOrders.contains(order)) 
		{
			return "";
		} 
		else 
		{
			return stringBuffer.append(String.valueOf(order) + ",").toString();
		}

		
	}
	
	private void generatePdf(BusinessKYCPage businessKYCPage,BusinessUserAnswerSubmission businessUserAnswerSubmission,BusinessUser businessUser) throws IOException, DocumentException, BusinessException 
	{
		this.pdfGenerateService.generatePdfFile(StringUtility.PDF_TEMPLATE, this.populateMapWithPageDetails(businessKYCPage,businessUserAnswerSubmission),(businessKYCPage!=null&&businessKYCPage.getPageOrder()!=null)?String.valueOf(businessKYCPage.getPageOrder()):null,businessUser);
	}



	private Map<String, Object> populateMapWithPageDetails(BusinessKYCPage businessKYCPage,BusinessUserAnswerSubmission businessUserAnswerSubmission) {
		this.matchAndAssign(businessKYCPage, businessUserAnswerSubmission.getUserAnswers());
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("questions", businessKYCPage.getQuestions());
		return map;
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
		
		this.validation.validateFileExtension(file,StringUtility.PDF_EXTENSION);
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
	
	KYCPage findPageDetails(Long id) throws BusinessException
	{
		try 
		{
			return this.kycPageService.findPageDetailsById(id);
		}
		catch(Exception exception)
		{
			this.exceptionHandler.getNullIfNonExistent(exception);
		}
		return null;
	}
	
	private void matchAndAssign(BusinessKYCPage businessKYCPage,BusinessUserSubmittedAnswer[] userAnswers)
	{
		
		if(arrayUtility.isArrayPopulated(userAnswers)&&this.questionListUtility.isListPopulated(businessKYCPage.getQuestions()))
		{
			this.logger.info("User Answers and Questions are populated::::::::");
			for(BusinessQuestion businessQuestion:businessKYCPage.getQuestions())
			{
				List<BusinessSubmittedAnswer> businessSubmittedAnswers=new ArrayList<BusinessSubmittedAnswer>();
				List<BusinessSubmittedAnswer> businessSubmittedSubQuestionAnswers=new ArrayList<BusinessSubmittedAnswer>();
				for(BusinessUserSubmittedAnswer businessUserSubmittedAnswer:userAnswers)
				{
					if(NumberUtility.areLongValuesMatching(businessQuestion.getId(), businessUserSubmittedAnswer.getQuestionId()))
					{
						List<BusinessSubmittedAnswer> relevantBusinessSubmittedAnswers=new ArrayList<BusinessSubmittedAnswer>();
					    Collections.addAll(relevantBusinessSubmittedAnswers, businessUserSubmittedAnswer.getAnswers());
						businessSubmittedAnswers.addAll(relevantBusinessSubmittedAnswers);
					}
				}
				
				businessQuestion.setAnswers(null);
				
				businessQuestion.setUserAnswers(businessSubmittedAnswers);
				if(this.questionListUtility.isListPopulated(businessQuestion.getSubQuestions()))
				{
					for(BusinessQuestion businessSubQuestion:businessQuestion.getSubQuestions())
					{
						for(BusinessUserSubmittedAnswer businessUserSubmittedAnswer:userAnswers)
						{
							if(NumberUtility.areLongValuesMatching(businessSubQuestion.getId(), businessUserSubmittedAnswer.getQuestionId()))
							{
								List<BusinessSubmittedAnswer> relevantBusinessSubmittedAnswers=new ArrayList<BusinessSubmittedAnswer>();
								Collections.addAll(relevantBusinessSubmittedAnswers, businessUserSubmittedAnswer.getAnswers());
								businessSubmittedSubQuestionAnswers.addAll(relevantBusinessSubmittedAnswers);
							}
						}
						businessSubQuestion.setAnswers(null);
						businessSubQuestion.setUserAnswers(businessSubmittedSubQuestionAnswers);					
					}
				}
			}
			
		}
		
	}
	
	
}
