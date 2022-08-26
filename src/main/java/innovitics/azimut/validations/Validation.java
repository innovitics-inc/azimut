package innovitics.azimut.validations;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessmodels.kyc.BusinessRelatedAnswer;
import innovitics.azimut.businessmodels.kyc.BusinessSubmittedAnswer;
import innovitics.azimut.businessmodels.kyc.BusinessUserAnswerSubmission;
import innovitics.azimut.businessmodels.kyc.BusinessUserSubmittedAnswer;
import innovitics.azimut.businessmodels.user.AuthenticationRequest;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.configproperties.ConfigProperties;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.ChangePhoneNumberRequest;
import innovitics.azimut.models.user.User;
import innovitics.azimut.security.PasswordValidation;
import innovitics.azimut.services.kyc.KYCPageService;
import innovitics.azimut.services.kyc.QuestionService;
import innovitics.azimut.services.user.UserService;
import innovitics.azimut.utilities.crosslayerenums.AnswerType;
import innovitics.azimut.utilities.datautilities.ArrayUtility;
import innovitics.azimut.utilities.datautilities.BooleanUtility;
import innovitics.azimut.utilities.datautilities.ChangePhoneNumberRequestUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.fileutilities.FileUtility;
import innovitics.azimut.utilities.kycutilities.AnswerTypeUtility;

@Component
public class Validation<T extends BaseBusinessEntity> {

protected static final Logger logger = LoggerFactory.getLogger(Validation.class);

@Autowired PasswordValidation passwordValidation;
@Autowired UserService userService;
@Autowired ChangePhoneNumberRequestUtility changePhoneNumberRequestUtility;
@Autowired AnswerTypeUtility answerTypeUtility;
@Autowired ArrayUtility arrayUtility;
@Autowired QuestionService questionService;
@Autowired FileUtility fileUtility;
@Autowired ConfigProperties configProperties;

	public void validate(T data,Validator validator,String objectName) throws BusinessException
	{
		BeanPropertyBindingResult result = new BeanPropertyBindingResult(data, objectName);
		
		validator.validate(data,result);
		
		if(result.hasErrors())
		{

			String field=""; 
			String value="";
			  
			  if(result.hasErrors()) 
			  { 
				  if(result!=null&&result.getFieldError()!=null)
				  field=result.getFieldError().getField();
				  if(result!=null&&result.getFieldError()!=null&&result.getFieldError().getRejectedValue()!=null)
				  value=result.getFieldError().getRejectedValue().toString(); 
			  }
			  
			  
			  this.logger.info("Invalid value " +value + " for field "+ field);
			  
			  throw  this.populateInavlidFieldValueBusinessException(value, field);
			 
		}
	}
	
	public void validate(Object data,Validator validator,String objectName) throws BusinessException
	{
		BeanPropertyBindingResult result = new BeanPropertyBindingResult(data, objectName);
		
		validator.validate(data,result);
		
		if(result.hasErrors())
		{

			String field=""; 
			String value="";
			  
			  if(result.hasErrors()) 
			  { 
				  if(result!=null&&result.getFieldError()!=null)
				  field=result.getFieldError().getField();
				  if(result!=null&&result.getFieldError()!=null&&result.getFieldError().getRejectedValue()!=null)
				  value=result.getFieldError().getRejectedValue().toString(); 
			  }
			  
			  
			  this.logger.info("Invalid value " +value + " for field "+ field);
			  
			  BusinessException businessException=new BusinessException(ErrorCode.INVALID_FIELD_VALUE);
			  
			  businessException.setErrorMessage("Invalid value " +value + " for field "+ field);
			  
			  throw  businessException;
			 
		}
	}
	

	public void validateImagesTaken(BusinessUser businessUser,MultipartFile frontImage,MultipartFile backImage,MultipartFile passportImage,Integer userStep,String language,String documentType,Boolean incrementFailure) throws BusinessException
	{
		
		if(BooleanUtility.isTrue(incrementFailure))
		{
			if(businessUser!=null&&businessUser.getFailureNumber()!=null&&Integer.valueOf(this.configProperties.getValifyTrialCount())<=businessUser.getFailureNumber().intValue())
			{
				throw new BusinessException(ErrorCode.USE_MOBILE);
			}
		}
		
		if((!StringUtility.isStringPopulated(documentType))||(StringUtility.isStringPopulated(documentType)&&StringUtility.stringsDontMatch(StringUtility.NATIONAL_ID_DOCUMENT_TYPE, documentType)&&StringUtility.stringsDontMatch(StringUtility.PASSPORT_DOCUMENT_TYPE, documentType)))
		{
			throw populateInavlidFieldValueBusinessException("", "documentType");
		}
		if(!StringUtility.isStringPopulated(language))
		{
			throw populateInavlidFieldValueBusinessException("", "language");
		}
		if(StringUtility.isStringPopulated(documentType)&&StringUtility.stringsMatch(StringUtility.NATIONAL_ID_DOCUMENT_TYPE, documentType))
		{
			if(frontImage==null)
			{
				throw populateInavlidFieldValueBusinessException("", "frontImage");
			}
			if(backImage==null)
			{
				throw populateInavlidFieldValueBusinessException("", "backImage");
			}
		}
		if(StringUtility.isStringPopulated(documentType)&&StringUtility.stringsMatch(StringUtility.PASSPORT_DOCUMENT_TYPE, documentType))
		{
			if(passportImage==null)
			{
				throw populateInavlidFieldValueBusinessException("", "passportImage");
			}
			
		}
		if(userStep==null||userStep.intValue()==0)
		{	
			throw populateInavlidFieldValueBusinessException("", "userStep");
		}

	}
	
	public void validateTwoPasswords(String password1,String password2) throws BusinessException
	{
		this.passwordValidation.validateTwoPasswords(password1, password2);
	}
		
	public void validateUser(Long id,BusinessUser businessUser) throws BusinessException
	{
		if(id==null)
		{
			throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);
		}
		
		else if(id!=null&&businessUser!=null&&businessUser.getId().longValue()!=id.longValue())
		{
			throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);
		}
		
		else if(id!=null&&id.longValue()<0)
		{
			throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);
		}
		
		
	}
		
	public void validateAuthenticationCredentials(AuthenticationRequest authenticationRequest) throws BusinessException
	{
		if(authenticationRequest!=null)
		   {
			  if(!StringUtility.isStringPopulated(authenticationRequest.getCountryPhoneCode()))
			  {  
				  throw new BusinessException(ErrorCode.BAD_CREDENTIALS);
			  }
			  if(!StringUtility.isStringPopulated(authenticationRequest.getPhoneNumber()))
			  { 
				  throw new BusinessException(ErrorCode.BAD_CREDENTIALS);
			  
			  }
			  if(!StringUtility.isStringPopulated(authenticationRequest.getPassword()))
			 {
				  	throw new BusinessException(ErrorCode.BAD_CREDENTIALS);
		     }
			  
			  
		   }
	}

	public void validateFilePresence(BusinessUser businessUser) throws BusinessException
	{
		if(businessUser!=null)
		   {
			  if(businessUser.getFile()==null)
			  {  
				  throw new BusinessException(ErrorCode.MISSING_FILE);
			  }
			 
			  
		   }
	}
	public void validateFileSize(MultipartFile file,long maximumSize) throws BusinessException
	{
		if(file!=null)
		   {
			  if(file.getSize()>maximumSize)
			  {  
				  this.logger.info("File Size::::"+file.getSize());
				  BusinessException  businessException=new BusinessException(ErrorCode.FILE_TOO_BIG);
				 businessException.setErrorMessage("File size should not exceed "+this.fileUtility.getFileSizeInMegabytes(maximumSize)+ " Megabytes");
				  throw  businessException;
			  }
			 
			  
		   }
	}
	
	public void validateNewPhoneNumberAvailability(BusinessUser businessUser) throws BusinessException
	{
		User user=new User();
		if(businessUser!=null&&businessUser.getUserPhone()!=null)
		{
			try {
				user=this.userService.findByUserPhone(businessUser.getUserPhone());
			} catch (Exception exception) {
				this.logger.info("No user was found having this number");
			}
		}
		
		if(user!=null&&user.getUserPhone()!=null)
			throw  new BusinessException(ErrorCode.USER_EXISTS);
		
	}
	
	
	public void checkForOpenChangeRequests(BusinessUser businessUser) throws BusinessException
	{
		if(this.changePhoneNumberRequestUtility.checkIfTheCurrentRequestHolderHasAnyOpenChangePhoneNumberRequests(businessUser.getUserPhone()))
		{
			throw new BusinessException(ErrorCode.REQUESTS_FOUND);
		}
	}
	
	public void checkUserAnswersValidity(BusinessUserAnswerSubmission businessUserAnswerSubmission) throws BusinessException
	{
		this.logger.info("Validating the User Answers:::");
		String errorMessage="";
		if (businessUserAnswerSubmission != null) 
		{

			if (this.arrayUtility.isArrayPopulated(businessUserAnswerSubmission.getUserAnswers())) 
			{
			
				List<Long> questionIds=new ArrayList<Long>();
				for(BusinessUserSubmittedAnswer businessUserSubmittedAnswer:businessUserAnswerSubmission.getUserAnswers())
				{
					questionIds.add(businessUserSubmittedAnswer.getQuestionId());
				}
				int suppliedMandatoryQuestionCount=0;
				int mandatoryQuestionCount=0;
				List<BigInteger> mandatoryQuestionIds=new ArrayList<BigInteger>();
				try
				{
					mandatoryQuestionIds=this.questionService.getMandatoryQuestionsByPage(businessUserAnswerSubmission.getPageId());
					this.logger.info("Mandatory Question Ids::"+mandatoryQuestionIds.toString());
					
					mandatoryQuestionCount=mandatoryQuestionIds.size();
				}
				catch(Exception exception)
				{
					exception.printStackTrace();
					throw new BusinessException(ErrorCode.NO_DATA_FOUND);
				}
				
				
				for(BigInteger mandatoryQuestionId:mandatoryQuestionIds)
				{
					if(questionIds.contains(mandatoryQuestionId.longValue()))
					suppliedMandatoryQuestionCount++;
				}
				
				
				
				this.logger.info("mandatory Question Count: "+mandatoryQuestionCount);
				this.logger.info("submiited mandatory Question Count: "+suppliedMandatoryQuestionCount);
				if (suppliedMandatoryQuestionCount==mandatoryQuestionCount) 
				{
					for (BusinessUserSubmittedAnswer businessUserSubmittedAnswer : businessUserAnswerSubmission.getUserAnswers()) 
					{
						if (businessUserSubmittedAnswer != null) 
						{
							if (businessUserSubmittedAnswer.getQuestionId() != null) 
							{

								if (this.arrayUtility.isArrayPopulated(businessUserSubmittedAnswer.getAnswers())) 
								{
									errorMessage = this.singleAnswerValidityCheck(businessUserSubmittedAnswer);
									if (StringUtility.isStringPopulated(errorMessage))
										break;
								}
								else 
								{
									errorMessage = "Answers Array object is empty";
									break;
								}
							} 
							else 
							{
								errorMessage = "Question Id is empty";
								break;
							}
						}
					}
				}
				else
				{
					errorMessage="A mandatory answer is missing";
				}
			}
			
			else
			{
				errorMessage="User Answer Array object is empty";
			}
		}
		else
		{
			this.logger.info("Request Object is Null");
			errorMessage="Request is empty";
		}
	
		if(StringUtility.isStringPopulated(errorMessage))
		{
				BusinessException businessException=new BusinessException(ErrorCode.ANSWER_SUBMISSION_FAILED);
				businessException.setErrorMessage(errorMessage);
				throw businessException;
				
		}
		
	}
	
	
	
	
	
	private String singleAnswerValidityCheck(BusinessUserSubmittedAnswer businessUserSubmittedAnswer)
	{
		String errorMessage="";
		int radioAnswerCount=0;
		for(BusinessSubmittedAnswer businessSubmittedAnswer:businessUserSubmittedAnswer.getAnswers())
		{
			if(businessSubmittedAnswer!=null)
			{
				if(this.answerTypeUtility.isAnswerTypeValid(businessSubmittedAnswer.getAnswerType()))
				{
					if(StringUtility.stringsMatch(businessSubmittedAnswer.getAnswerType(), AnswerType.RADIO.getType()))
					{
						radioAnswerCount++;
						if(radioAnswerCount>1)
						{
							errorMessage="Too many answers for question id:"+businessUserSubmittedAnswer.getQuestionId();
							break;
						}
					}
					
					if(this.arrayUtility.isArrayPopulated(businessSubmittedAnswer.getRelatedAnswers()))
					{
						errorMessage=this.singleRelatedAnswerValidityCheck(businessSubmittedAnswer);
						if (StringUtility.isStringPopulated(errorMessage))
							break;						
					}

				}
				else
				{
					errorMessage="Answers Id: "+ businessSubmittedAnswer.getAnswerId()+" has an invalid type: "+businessSubmittedAnswer.getAnswerType();
					break;
				}
			}
		}
	
		return errorMessage;
	}

	private String singleRelatedAnswerValidityCheck(BusinessSubmittedAnswer businessSubmittedAnswer)
	{
		String errorMessage="";
		int radioAnswerCount=0;
		for(BusinessRelatedAnswer businessRelatedAnswer:businessSubmittedAnswer.getRelatedAnswers())
		{
			if(businessRelatedAnswer!=null)
			{
				if(this.answerTypeUtility.isAnswerTypeValid(businessRelatedAnswer.getRelatedAnswerType()))
				{
					if(StringUtility.stringsMatch(businessRelatedAnswer.getRelatedAnswerType(), AnswerType.RADIO.getType()))
					{
						radioAnswerCount++;
						if(radioAnswerCount>1)
						{
							errorMessage="Too many related answers for answer id:"+businessSubmittedAnswer.getAnswerId();
							break;
						}
					}
				}
				else
				{
					errorMessage="Related answer Id: "+ businessRelatedAnswer.getRelatedAnswerId()+" has an invalid type: "+businessRelatedAnswer.getRelatedAnswerType();
					break;
				}
			}
		}
	
		return errorMessage;
	}

	
	BusinessException populateInavlidFieldValueBusinessException(String value,String field)
	{
		 BusinessException businessException=new BusinessException(ErrorCode.INVALID_FIELD_VALUE);
		  
		  businessException.setErrorMessage("Invalid value " +value + " for field "+ field);
		  
		  return businessException;
	}

	public List<String> validateKYCFormCompletion(BusinessUser businessUser,Long pageCount) throws BusinessException 
	{
		this.logger.info("Page Count:::"+pageCount);
		List<String> solvedPageOrders=new ArrayList<String>();
		if(businessUser!=null)
		{
			if(StringUtility.isStringPopulated(businessUser.getSolvedPages()))
			{
				solvedPageOrders = Arrays.asList(businessUser.getSolvedPages().split(","));
				for(String solvedPage:solvedPageOrders)
				{
					this.logger.info("Page Order:::"+solvedPage);
				}
			}
			else
			{
				throw new BusinessException(ErrorCode.KYC_INCOMPLETE);
			}
		}
	
		else
		{
			this.logger.info("KYC Incomplete");
			throw new BusinessException(ErrorCode.OPERATION_NOT_PERFORMED);
		}
		if(pageCount!=null&&solvedPageOrders!=null&&!solvedPageOrders.isEmpty())
		{
			Long solvedPageCount=Long.valueOf(solvedPageOrders.size());
			this.logger.info("Solved Page Count:::"+solvedPageCount);
			
			if(solvedPageCount.longValue()<pageCount.longValue())
			{
				
				throw new BusinessException(ErrorCode.KYC_INCOMPLETE);
			}
			
		}
		
		return solvedPageOrders;
	}

}
