package innovitics.azimut.businessservices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.trading.BaseAzimutTrading;
import innovitics.azimut.businessmodels.user.AuthenticationRequest;
import innovitics.azimut.businessmodels.user.AzimutAccount;
import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.businessmodels.user.BusinessFlow;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.user.User;
import innovitics.azimut.models.user.UserImage;
import innovitics.azimut.models.user.UserLocation;
import innovitics.azimut.models.user.UserType;
import innovitics.azimut.pdfgenerator.PdfGenerateService;
import innovitics.azimut.rest.mappers.CheckAccountMapper;
import innovitics.azimut.services.kyc.KYCPageService;
import innovitics.azimut.services.kyc.UserAnswerSubmissionService;
import innovitics.azimut.services.kyc.UserImageService;
import innovitics.azimut.services.teacomputer.TeaComputerService;
import innovitics.azimut.services.user.UserService;
import innovitics.azimut.utilities.businessutilities.AzimutDataLookupUtility;
import innovitics.azimut.utilities.businessutilities.ChangePhoneNumberRequestUtility;
import innovitics.azimut.utilities.businessutilities.EmailUtility;
import innovitics.azimut.utilities.businessutilities.PhoneNumberBlockageUtility;
import innovitics.azimut.utilities.businessutilities.UserUtility;
import innovitics.azimut.utilities.crosslayerenums.AnswerType;
import innovitics.azimut.utilities.crosslayerenums.UserStep;
import innovitics.azimut.utilities.datautilities.BooleanUtility;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.fileutilities.BlobData;
import innovitics.azimut.utilities.logging.MyLogger;
import innovitics.azimut.utilities.mapping.UserMapper;
import innovitics.azimut.validations.validators.azimutclient.SaveUserLocation;
import innovitics.azimut.validations.validators.user.AddBusinessUserValidator;
import innovitics.azimut.validations.validators.user.ChangePhoneNumber;
import innovitics.azimut.validations.validators.user.ChangeUserPassword;
import innovitics.azimut.validations.validators.user.EditUserProfile;
import innovitics.azimut.validations.validators.user.FindUserByUserPhone;
import innovitics.azimut.validations.validators.user.ForgottenUserPassword;
@Service
public class BusinessUserService extends AbstractBusinessService<BusinessUser> {
	@Autowired UserService userService;
	@Autowired EditUserProfile editUserProfile;
	@Autowired ChangeUserPassword changeUserPassword;
	@Autowired ForgottenUserPassword forgottenUserPassword;
	@Autowired AddBusinessUserValidator addBusinessUserValidator;
	@Autowired FindUserByUserPhone findUserByUserPhone;
	@Autowired ChangePhoneNumber changePhoneNumber;
	@Autowired ChangePhoneNumberRequestUtility changePhoneNumberRequestUtility;
	@Autowired UserImageService userImageService;
	@Autowired AzimutDataLookupUtility azimutDataLookupUtility;
	@Autowired PdfGenerateService pdfGenerateService;
	@Autowired UserAnswerSubmissionService userAnswerSubmissionService;
	@Autowired KYCPageService kycPageService;
	@Autowired ListUtility<AzimutAccount> azimutAccountListUtility;
	@Autowired SaveUserLocation saveUserLocation;
	@Autowired PhoneNumberBlockageUtility phoneNumberBlockageUtility;
	@Autowired EmailUtility emailUtility;
	
	
	public static final String PROFILE_PICTURE_PARAMETER="profilePicture";
	public static final String SIGNED_PDF_PARAMETER="signedPdf";
	
	
	public void editUser(BusinessUser businessUser) throws BusinessException
	{
		User user=new User();
		try {
			user=userMapper.convertBusinessUnitToBasicUnit(businessUser,false);
			userService.save(user);
		}
		catch (Exception exception) 
		{
			throw this.handleBusinessException(exception,ErrorCode.USER_NOT_UPDATED);
		}
		
	}
	

	public BusinessUser saveUser(BusinessUser businessUser,boolean checkBlockage) throws BusinessException
	{
		businessUser.concatinate();
		return (BusinessUser) this.phoneNumberBlockageUtility.checkUserBlockage
				(
				this.configProperties.getBlockageNumberOfTrials(),
				this.configProperties.getBlockageDurationInMinutes(),businessUser.getUserPhone(),
				this,"saveUser",
				new Object[]{businessUser},
				new Class<?>[]{BusinessUser.class},
				ErrorCode.OPERATION_FAILURE
				);
		
	}
	
	public BusinessUser saveUser(BusinessUser businessUser) throws BusinessException
	{
		businessUser.concatinate();
		this.validation.validateNewPhoneNumberAvailability(businessUser);
		this.validate(businessUser,addBusinessUserValidator,BusinessUser.class.getName());
		User user=new User();
		BusinessUser savedBusinessUser=new BusinessUser();
		try {
			if (businessUser.getAzimutAccount()!=null)
			{
				AzimutAccount businessAzimutAccount=businessUser.getAzimutAccount();
				long countryId=businessAzimutAccount.getCountryId();
				long cityId=businessAzimutAccount.getCityId();
				
				businessAzimutAccount.setNationalityId(countryId);				
				businessAzimutAccount.setCountryId(countryId);
				businessAzimutAccount.setCityId(cityId);			
				businessAzimutAccount.setOccupation(StringUtility.OCCUPATION);
				businessAzimutAccount.setClientAML(StringUtility.CLIENT_AML);
			}
			
			user=userMapper.convertBusinessUnitToBasicUnit(businessUser,true);
			user.setCreatedAt(DateUtility.getCurrentDate());
			userService.save(user);
			savedBusinessUser=userMapper.convertBasicUnitToBusinessUnit(user);
			savedBusinessUser.setIsVerified(false);
		}
		catch (Exception exception) 
		{
			throw this.handleBusinessException(exception,ErrorCode.USER_NOT_SAVED);
		}
		return savedBusinessUser;
		
	}
	
	
	public BusinessUser uploadSignedPdf(Long id,String newCountryPhoneCode,String newPhoneNumber,MultipartFile file, BusinessUser tokenizedBusinessUser) throws IOException, BusinessException
	{
		User user =new User();
		BusinessUser updatedbusinessUser=new BusinessUser();
		BusinessUser newBusinessUser=new BusinessUser();
		newBusinessUser.setUserPhone(newCountryPhoneCode+newPhoneNumber);
		newBusinessUser.setFile(file);
		this.validation.validateUser(id==null?null:Long.valueOf(id), tokenizedBusinessUser);
		this.validation.validateFilePresence(newBusinessUser);
		this.validation.validateFileSize(file, Long.valueOf(this.configProperties.getPhoneNumberMaximumSizeInBytes()));
		this.validation.validate(newBusinessUser, changePhoneNumber, BusinessUser.class.getName());
		this.validation.validateNewPhoneNumberAvailability(newBusinessUser);
		this.validation.checkForOpenChangeRequests(tokenizedBusinessUser);
		try 
		{
			user=userMapper.convertBusinessUnitToBasicUnit(tokenizedBusinessUser, false);
			this.changePhoneNumberRequestUtility.addNewChangePhoneNumberRequest(user,newBusinessUser.getUserPhone());
			user=this.storeFileBlobNameAndGenerateTokenInBusinessUser(tokenizedBusinessUser, user, file,this.configProperties.getBlobSignedPdfPath(),SIGNED_PDF_PARAMETER,true);
			
			updatedbusinessUser=userMapper.convertBasicUnitToBusinessUnit(user);			
		}
		catch(Exception exception)
		{
			throw this.handleBusinessException(exception,ErrorCode.USER_NOT_UPDATED);
		}
		return updatedbusinessUser;
		
	}
	
		
	public BusinessUser editUserProfile(Long id,MultipartFile file,String nickName,String emailAddress,BusinessUser tokenizedBusinessUser) throws BusinessException, IOException
	{

		this.validation.validateUser(id, tokenizedBusinessUser);
		if(StringUtility.isStringPopulated(nickName))
			tokenizedBusinessUser.setNickName(nickName);
		
		if(StringUtility.isStringPopulated(emailAddress))
			tokenizedBusinessUser.setEmailAddress(emailAddress);

		this.validate(tokenizedBusinessUser,editUserProfile,BusinessUser.class.getName());	
		
		if (this.fileUtility.isFilePopulated(file)) 
		{
			this.validation.validateFileSize(file, Long.valueOf(this.configProperties.getProfilePictureMaximumSizeInBytes()));
			try {
				tokenizedBusinessUser = this.uploadProfilePicture(file, tokenizedBusinessUser);

			} catch (Exception exception) {
				throw this.handleBusinessException(exception, ErrorCode.USER_NOT_UPDATED);
			}
		}
		else 
		{
			this.userService.update(this.userMapper.convertBusinessUnitToBasicUnit(tokenizedBusinessUser, false));
			if(tokenizedBusinessUser!=null&&tokenizedBusinessUser.getProfilePicture()!=null&&tokenizedBusinessUser.getPicturePath()!=null)
			{
				tokenizedBusinessUser.setProfilePicture(
				this.blobFileUtility.generateFileRetrievalUrl(this.configProperties.getBlobProfilePicturePath(), 
						tokenizedBusinessUser.getProfilePicture(), 
						tokenizedBusinessUser.getPicturePath(), false));
			}
		}
		return tokenizedBusinessUser;
	
	}
	
	public BusinessUser uploadProfilePicture(MultipartFile file, BusinessUser businessUser) throws IOException, BusinessException
	{
		User user =new User();
		BusinessUser updatedbusinessUser=new BusinessUser();
		try 
		{
			user=this.storeFileBlobNameAndGenerateTokenInBusinessUser(businessUser, user, file,this.configProperties.getBlobProfilePicturePath(),PROFILE_PICTURE_PARAMETER,false);
			updatedbusinessUser=userMapper.convertBasicUnitToBusinessUnit(user);			
		}
		catch(Exception exception)
		{
			throw this.handleBusinessException(exception,ErrorCode.USER_NOT_UPDATED);
		}
		return updatedbusinessUser;
		
	}
	
	public BusinessUser editUserPassword(BusinessUser businessUser,BusinessUser tokenizedBusinessUser,boolean checkBlockage) throws BusinessException
	{
		return (BusinessUser) this.userBlockageUtility.checkUserBlockage
		(this.configProperties.getBlockageNumberOfTrials(),
		 this.configProperties.getBlockageDurationInMinutes(), 
		 tokenizedBusinessUser, 
		 userMapper,this, "editUserPassword",
		 new Object[]{businessUser,tokenizedBusinessUser},
		 new Class<?>[]{BusinessUser.class,BusinessUser.class},
		 ErrorCode.OPERATION_FAILURE);
	}
	
	public BusinessUser editUserPassword(BusinessUser businessUser,BusinessUser tokenizedBusinessUser) throws BusinessException
	{
		this.validate(businessUser,changeUserPassword,BusinessUser.class.getName());
		this.validation.validateUser(businessUser.getId(), tokenizedBusinessUser);
		businessUser.setCountryPhoneCode(tokenizedBusinessUser.getCountryPhoneCode());
		businessUser.setPhoneNumber(tokenizedBusinessUser.getPhoneNumber());
		BusinessUser oldUser = this.getByUserPhoneAndPassword(businessUser);
		BusinessUser updatedBusinessUser=new BusinessUser();
		try 
		{
			oldUser.setPassword(businessUser.getNewPassword());
			updatedBusinessUser=this.returnUpdatedEntity(oldUser,false);
			
			if(this.checkForDifferencesInTheBusinessUser(businessUser, tokenizedBusinessUser))
			{
				this.updateUserAtTeaComputers(updatedBusinessUser);
			}			
		}
		catch(Exception exception)
		{
			throw this.handleBusinessException(exception,ErrorCode.USER_NOT_UPDATED);
		}
		return updatedBusinessUser;
	
	}

	public BusinessUser forgotUserPassword(BusinessUser businessUser) throws BusinessException
	{
		
		BusinessUser oldUser = this.getByUserPhone(businessUser.getCountryPhoneCode()+businessUser.getPhoneNumber());
		this.validate(businessUser,forgottenUserPassword,BusinessUser.class.getName());
		BusinessUser updatedBusinessUser=new BusinessUser();
		
		try 
		{
			oldUser.setPassword(businessUser.getNewPassword());
			updatedBusinessUser=this.returnUpdatedEntity(oldUser,false);				
		}
		catch(Exception exception)
		{
			throw this.handleBusinessException(exception,ErrorCode.USER_NOT_UPDATED);
		}

		return updatedBusinessUser;
	
	}
	
	public BusinessUser forgotUserPassword(AuthenticationRequest authenticationRequest,boolean checkBlockage) throws BusinessException
	{
		return (BusinessUser) this.phoneNumberBlockageUtility.checkUserBlockage
				(
				this.configProperties.getBlockageNumberOfTrials(),
				this.configProperties.getBlockageDurationInMinutes(),authenticationRequest.getCountryPhoneCode()+authenticationRequest.getPhoneNumber(),
				this,"forgotUserPassword",
				new Object[]{authenticationRequest},
				new Class<?>[]{AuthenticationRequest.class},
				ErrorCode.OPERATION_FAILURE
				);
	}
	
	public BusinessUser forgotUserPassword(AuthenticationRequest authenticationRequest) throws BusinessException
	{
		
		BusinessUser oldUser = this.getByUserPhone(authenticationRequest.getCountryPhoneCode()+authenticationRequest.getPhoneNumber());
		if(authenticationRequest!=null)
		oldUser.setNewPassword(authenticationRequest.getNewPassword());
		
		this.validate(oldUser,forgottenUserPassword,BusinessUser.class.getName());
		BusinessUser updatedBusinessUser=new BusinessUser();
		
		try 
		{
			oldUser.setPassword(authenticationRequest.getNewPassword());
			updatedBusinessUser=this.returnUpdatedEntity(oldUser,true);
			this.getMultipleTcAccounts(updatedBusinessUser);
			
		}
		catch(Exception exception)
		{
			throw this.handleBusinessException(exception,ErrorCode.USER_NOT_UPDATED);
		}

		
		return updatedBusinessUser;
	
	}
	
	public BusinessUser getById(Long id,BusinessUser tokenizedBusinessUser,boolean validate) throws BusinessException
	{
		this.validation.validateUser(id, tokenizedBusinessUser);
		if(validate)
		{
			this.validation.validateUserKYCCompletion(tokenizedBusinessUser);
		}
		
		BusinessUser businessUser=new BusinessUser();		
		try 
		{	
			businessUser=this.convertBasicToBusinessAndPrepareURLsInBusinessUser(businessUser, this.userService.findById(id),validate?false:true);
		}
		catch(Exception exception)
		{
			throw this.handleBusinessException(exception,ErrorCode.USER_NOT_FOUND);
		}
		return businessUser;
		
	}
	
	public BusinessUser getByUserPhone(String username) throws BusinessException
	{
		BusinessUser businessUser=new BusinessUser();		
		try 
		{
			businessUser=this.convertBasicToBusinessAndPrepareURLsInBusinessUser(businessUser, this.userService.findByUserPhone(username),false);		
		}
		catch(Exception exception)
		{
			throw this.handleBusinessException(exception,ErrorCode.USER_NOT_FOUND);
		}
		return businessUser;
		
	}
	
	public BusinessUser verifyUserExistence(BusinessUser businessUser) throws BusinessException, IntegrationException
	{
		this.validate(businessUser, findUserByUserPhone, BusinessUser.class.getName());
		BusinessUser searchedForBusinessUser=new BusinessUser();
		try 
		{
			searchedForBusinessUser=this.convertBasicToBusinessAndPrepareURLsInBusinessUser(searchedForBusinessUser, this.userService.findByUserPhone(businessUser.getCountryPhoneCode()+businessUser.getPhoneNumber()), false);
			
			if(searchedForBusinessUser!=null)
			{
				
				if(BooleanUtility.isTrue(searchedForBusinessUser.getIsOld())&&!StringUtility.isStringPopulated(searchedForBusinessUser.getPassword()))
				{
					searchedForBusinessUser.setBusinessFlow(BusinessFlow.SET_PASSWORD);
				}

				if(StringUtility.isStringPopulated(searchedForBusinessUser.getPassword()))
				{
					searchedForBusinessUser.setBusinessFlow(BusinessFlow.VERIFY_PASSWORD);
				}
				
			}
			
					
		}
		catch(Exception exception)
		{
			MyLogger.info("Enter Excepton Handling");
			BusinessException businessException=this.handleBusinessException(exception,ErrorCode.USER_NOT_FOUND);			
			if(NumberUtility.areIntegerValuesMatching(businessException.getErrorCode(), ErrorCode.USER_NOT_FOUND.getCode()))
			{
				searchedForBusinessUser=this.lookForuUserAtTC(searchedForBusinessUser, businessUser);
			}
			else
			{
				throw businessException;
			}
		}
		
		return searchedForBusinessUser;
	}

	public BusinessUser getByUserPhoneAndPassword(String username,String password,String deviceId) throws BusinessException
	{
		BusinessUser businessUser=new BusinessUser();
		User user=new User();
		try 
		{
			user=this.userService.findByUserPhoneAndPassword(username,this.userUtility.encryptUserPassword(password));
			
			businessUser=this.convertBasicToBusinessAndPrepareURLsInBusinessUser(businessUser, user, true);
			
			
			if(StringUtility.isStringPopulated(deviceId)&&BooleanUtility.isFalse(businessUser.getLivenessChecked())&&businessUser.getUserStep()!=null
					&&businessUser.getUserStep().intValue()>UserStep.LEFT_AND_RIGHT.getStepId())
			{
				businessUser.setNextUserStep(UserStep.LEFT_AND_RIGHT.getStepId());
			}
			
			this.getMultipleTcAccounts(businessUser);
			
		}
		catch(Exception exception)
		{
			throw this.handleBusinessException(exception,ErrorCode.USER_NOT_FOUND);
		}
		//this.userUtility.allowUserToLogin(user, deviceId);
		return businessUser;
		
	}
	public BusinessUser getByUserPhoneAndPassword(BusinessUser insertedBusinessUser) throws BusinessException
	{
		BusinessUser businessUser=new BusinessUser();		
		try 
		{
			businessUser=userMapper.convertBasicUnitToBusinessUnit(this.userService.findByUserPhoneAndPassword(insertedBusinessUser.getCountryPhoneCode(),insertedBusinessUser.getPhoneNumber(),aes.encrypt(insertedBusinessUser.getPassword())));
		}
		catch(Exception exception)
		{
			throw this.handleBusinessException(exception,ErrorCode.USER_NOT_FOUND);
		}
		return businessUser;
		
	}

	public BusinessUser getUnsignedPDF(BusinessUser businessUser,BusinessUser tokenizedBusinessUser) throws IOException, BusinessException 
	{
		this.validation.validateUser(businessUser.getId(), tokenizedBusinessUser);
		try {
			tokenizedBusinessUser.setDocumentURL(
					this.blobFileUtility.generateFileRetrievalUrl(this.configProperties.getBlobUnsignedPdfPath(),
							this.configProperties.getPhoneNumberChangeDocumentName(),
							this.configProperties.getBlobUnsignedPdfPathSubDirectory(), true));
		} catch (Exception exception) {
			throw this.handleBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);
		}
		return tokenizedBusinessUser;
	}
	
	public BusinessUser updateUserStep(BusinessUser tokenizedBusinessUser,BusinessUser businessUser) throws BusinessException
	{
		if(!(businessUser.getUserStep().intValue()>UserStep.CONTRACT_MAP.getStepId()))
		{
			this.validation.validateUserKYCCompletion(tokenizedBusinessUser);
		}
		
		BusinessUser editedUser=new BusinessUser();
		try 
		{
		
			if(NumberUtility.areIntegerValuesMatching(businessUser.getUserStep().intValue(), UserStep.STRAIGHT_AND_SMILE.getStepId()))
			{
				throw new BusinessException(ErrorCode.INVALID_USER_STEP);
			}
			
			if(NumberUtility.areIntegerValuesMatching(businessUser.getUserStep().intValue(), UserStep.LEFT_AND_RIGHT.getStepId()))
			{
				
				/*if(businessUser!=null&&StringUtility.isStringPopulated(businessUser.getUserId()))
				{
					if(StringUtility.stringsDontMatch(tokenizedBusinessUser.getUserId(), businessUser.getUserId()))
						throw new BusinessException(ErrorCode.USER_ID_NOT_MATCHING);
					
				}*/
				
				tokenizedBusinessUser.setLivenessChecked(true);
			}
		
			editedUser=this.userUtility.isOldUserStepGreaterThanNewUserStep(tokenizedBusinessUser, businessUser.getUserStep().intValue());
			if(NumberUtility.areIntegerValuesMatching(businessUser.getUserStep(), UserStep.CHOOSE_CONTRACT_MAP.getStepId()))
			{
				editedUser.setNextUserStep(UserStep.FINISHED.getStepId());
			}
			this.editUser(editedUser);
		}
		catch(Exception exception)
		{
			throw this.handleBusinessException(exception,ErrorCode.USER_NOT_UPDATED);
		}
		return editedUser;
	}
	
	public int userStepCheck(Integer oldUserStep,Integer newUserStep)
	{
		return 0;
	}
	
	public BusinessUser addUserLocation(UserLocation userLocation,BusinessUser tokenizedBusinessUser) throws BusinessException
	{
		this.validation.validate(userLocation, saveUserLocation, UserLocation.class.getName());
		try 
		{
			this.userUtility.addUserLocation(tokenizedBusinessUser, userLocation);
			
			if(userLocation.getUserStep()!=null&&NumberUtility.areIntegerValuesMatching(userLocation.getUserStep(), UserStep.CHOOSE_CONTRACT_MAP.getStepId()))
			{
				tokenizedBusinessUser.setUserStep(UserStep.CHOOSE_CONTRACT_MAP.getStepId());
				this.editUser(tokenizedBusinessUser);
			}
			
		}
		catch (Exception exception)
		{
			return (BusinessUser)this.exceptionHandler.getNullIfNonExistent(exception);
		}
		return new BusinessUser();
	}
	public BusinessUser getUserLocation(BusinessUser tokenizedBusinessUser) throws BusinessException
	{
		BusinessUser businessUser=new BusinessUser();

		try 
		{
			businessUser.setUserLocation(this.userUtility.getUserLocation(tokenizedBusinessUser));
		}
		catch (Exception exception)
		{
			return (BusinessUser)this.exceptionHandler.getNullIfNonExistent(exception);
		}
		return businessUser;
	}
	
	public BusinessUser updateUserDetails(BusinessUser tokenizedBusinessUser,BusinessUser businessUser,boolean isContractSigning) throws BusinessException
	{
		if(!isContractSigning)
		{
			this.validation.validateUserKYCCompletion(tokenizedBusinessUser);
		}
		
		try 
		{
			
			tokenizedBusinessUser=this.userUtility.isOldUserStepGreaterThanNewUserStep(tokenizedBusinessUser, businessUser.getUserStep());
			tokenizedBusinessUser.setContractMap(businessUser.getContractMap());
			tokenizedBusinessUser.setOtherIdType(businessUser.getOtherIdType());
			tokenizedBusinessUser.setOtherUserId(businessUser.getOtherUserId());	
			tokenizedBusinessUser.setOtherNationality(businessUser.getOtherNationality());
			this.editUser(tokenizedBusinessUser);
			if(BooleanUtility.isTrue(businessUser.getIsMobile()))
			{
				businessUser.setClientBankAccounts(this.azimutDataLookupUtility.getKYCClientBankAccountData(tokenizedBusinessUser));
				
			}
			businessUser.setVerificationPercentage(tokenizedBusinessUser.getVerificationPercentage());
			businessUser.setFirstPageId(tokenizedBusinessUser.getFirstPageId());
			businessUser.setNextUserStep(businessUser.getUserStep()+1);
		}
		catch(Exception exception)
		{
			throw this.handleBusinessException(exception,ErrorCode.USER_NOT_UPDATED);
		}
		return businessUser;
	}
	
	public BusinessUser getUserImages(BusinessUser tokenizedBusinessUser,BusinessUser businessUser) throws BusinessException
	{
		tokenizedBusinessUser.setUserImages(this.userUtility.getUserImages(tokenizedBusinessUser, businessUser.getImageTypes(),false));
		return tokenizedBusinessUser;
	}
	
	
	public BusinessUser returnUpdatedEntity(BusinessUser oldUser,boolean getDocuments) throws IOException
	{
		User user=this.userMapper.convertBusinessUnitToBasicUnit(oldUser, false);
		if(getDocuments)
			return this.convertBasicToBusinessAndPrepareURLsInBusinessUser(oldUser, this.userService.update(user), getDocuments);
		else
			return userMapper.convertBasicUnitToBusinessUnit(this.userService.update(user));
	}

	boolean checkForDifferencesInTheBusinessUser(BusinessUser businessUser,BusinessUser oldBusinessUser)
	{
		return (businessUser!=null&&oldBusinessUser!=null)&&
		       (
		    	StringUtility.stringsDontMatch(businessUser.getCountryPhoneCode(), oldBusinessUser.getCountryPhoneCode())||
			    StringUtility.stringsDontMatch(businessUser.getPhoneNumber(),oldBusinessUser.getPhoneNumber())
			   );
	}

	public boolean verifyUserExistenceAtTeaComputers(BusinessUser businessUser) 
	{
		return true;
	}
	
	public void updateUserAtTeaComputers(BusinessUser businessUser) 
	{
		MyLogger.info("Update User at Tea Computers");
	}
	
	public BusinessUser downloadUserContract(BusinessUser tokenizedBusinessUser) throws BusinessException
	{
		List<String> solvedPages=this.validation.validateKYCFormCompletion(tokenizedBusinessUser,this.kycPageService.countPagesByUserType(tokenizedBusinessUser.getIdType()));
		BusinessUser businessUser=new BusinessUser();
		try 
		{
		businessUser.setDocumentURL(this.pdfGenerateService.downloadContract(this.userAnswerSubmissionService.getUserAnswersByUserIdAndAnswerType(tokenizedBusinessUser.getId(), AnswerType.DOCUMENT.getType()), tokenizedBusinessUser,solvedPages));
		
		this.emailUtility.sendMailWithAttachment("alaa.sadek@innovitics.com","Contract Download","Hello!!",this.blobFileUtility.downloadStreamFromBlob(this.configProperties.getBlobKYCDocuments(),  StringUtility.CONTRACTS_SUBDIRECTORY+"/"+tokenizedBusinessUser.getUserId(), StringUtility.CONTRACT_DOCUMENT_NAME +".pdf"));
		
		//this.emailUtility.sendSimpleMessage("omar.amer@innovitics.com", "Contract Download", "Test!!");
		
		
		}
		catch (BusinessException | IOException e) 
		{
			e.printStackTrace();
			throw this.exceptionHandler.handleBusinessException(e,ErrorCode.CONTRACT_DOWNLOAD_FAILED);
		}
		return businessUser;
	}
	public BusinessAzimutClient checkAccountAtTeaComputers(BusinessAzimutClient businessAzimutClient,BusinessUser tokenizedBusinessUser) throws BusinessException,IntegrationException
	{
		BusinessAzimutClient responseBusinessAzimutClient=new BusinessAzimutClient();
		this.validation.validateUser(businessAzimutClient.getId(), tokenizedBusinessUser);
		try 
		{			
			//responseBusinessAzimutClient.setAzimutAccounts(this.restManager.checkAccountMapper.wrapBaseBusinessEntity(true, this.prepareAccountRetrievalInputs(businessAzimutClient,tokenizedBusinessUser), null).getDataList());
			responseBusinessAzimutClient.setAzimutAccounts(this.restContract.getDataList(this.restContract.checkAccountMapper, this.prepareAccountRetrievalInputs(businessAzimutClient, tokenizedBusinessUser), null));
		}
		catch(Exception exception)
		{
	
			if(exception instanceof IntegrationException)
			{
				MyLogger.info("Detecting the exception type in the checkAccountAtTeaComputers method:::");
				throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
			}
			else
			{
				throw this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
			}
		}

		return responseBusinessAzimutClient;
	}
	  AzimutAccount	prepareAccountRetrievalInputs(BusinessAzimutClient businessAzimutClient,BusinessUser searchBusinessUser)
	  {
		  AzimutAccount azimutAccount=new AzimutAccount();		  
		  if(searchBusinessUser!=null)
		  {
			  String withoutPlus=StringUtility.isStringPopulated((searchBusinessUser.getCountryPhoneCode()))?(searchBusinessUser.getCountryPhoneCode()).substring(1):null;
			  azimutAccount.setPhoneNumber(withoutPlus+searchBusinessUser.getPhoneNumber());
		  }
		  return azimutAccount;
	  }	
	 
	
	private User storeFileBlobNameAndGenerateTokenInBusinessUser(BusinessUser businessUser,User user,MultipartFile file,String containerName,String parameter,boolean generateSasToken) throws IOException, BusinessException
	{
		BlobData blobData=new BlobData();
		blobData=this.blobFileUtility.uploadFileToBlob(file, generateSasToken, containerName,null,false);
		String fileName=blobData.getFileName();
		String filePath=blobData.getSubDirectory();
		user=this.userMapper.convertBusinessUnitToBasicUnit(businessUser, false);
		MyLogger.info("Parameter::"+parameter);
		if(StringUtility.stringsMatch(PROFILE_PICTURE_PARAMETER, parameter))
		{	
			MyLogger.info("updating Profile Picture");
			user.setProfilePicture(fileName);
			user.setPicturePath(filePath);									
		}
		else if(StringUtility.stringsMatch(SIGNED_PDF_PARAMETER, parameter))
		{	
			MyLogger.info("updating signed PDF");
			user.setSignedPdf(fileName);
			user.setPdfPath(filePath);
			user.setChangeNoApproved(false);
		}		
		user= userService.update(user);
		if(user.getProfilePicture()!=null&&user.getPicturePath()!=null&&StringUtility.stringsMatch(PROFILE_PICTURE_PARAMETER, parameter))
		{
			MyLogger.info("Generating Profile Picture URL");
			user.setProfilePicture(blobData.getConcatinated(generateSasToken));
		}
		
		if(user.getSignedPdf()!=null&&user.getPdfPath()!=null&&StringUtility.stringsMatch(SIGNED_PDF_PARAMETER, parameter))
		{
			MyLogger.info("Generating Signed PDF URL");
			user.setSignedPdf(blobData.getConcatinated(generateSasToken));
		}
		
		return user;
	}
	private BusinessUser convertBasicToBusinessAndPrepareURLsInBusinessUser(BusinessUser businessUser,User user,boolean getDocuments) throws IOException
	{
		businessUser = userMapper.convertBasicUnitToBusinessUnit(user);
		if(getDocuments)
		{
			if(user.getProfilePicture()!=null&&user.getPicturePath()!=null)
			businessUser.setProfilePicture(this.blobFileUtility.generateFileRetrievalUrl(this.configProperties.getBlobProfilePicturePath(), user.getProfilePicture(),user.getPicturePath(),false));
			
			if(user.getSignedPdf()!=null&&user.getPdfPath()!=null)
			businessUser.setSignedPdf(this.blobFileUtility.generateFileRetrievalUrl(this.configProperties.getBlobSignedPdfPath(), user.getSignedPdf(),user.getPdfPath(),true));
		}
		return businessUser;
	}
	
		

	public BusinessUser hideUserPassword(BusinessUser businessUser)
	{
		if(businessUser!=null)
		{businessUser.setPassword(null);}
		return businessUser;
	}
	public BusinessUser beautifyUser(BusinessUser businessUser)
	{
		if(businessUser!=null)
		{
			businessUser.setPassword(null);
			if(businessUser.getIsVerified()==null)
			businessUser.setIsVerified(false);
			if(businessUser.isChangeNoApproved()==null)
			businessUser.setChangeNoApproved(true);
			if(businessUser.getVerificationPercentage()==null)
			businessUser.setVerificationPercentage(0);
			if(businessUser.getMigrated()==null)
			businessUser.setMigrated(false);	
		}
		
		return businessUser;
	}

	public BusinessUser hideUserDetails(BusinessUser businessUser)
	{
		BusinessUser businessUserWithHiddenDetails=new BusinessUser();
		if(businessUser!=null)
		{
			businessUserWithHiddenDetails.setPhoneNumber(businessUser.getPhoneNumber());
			businessUserWithHiddenDetails.setCountryPhoneCode(businessUser.getCountryPhoneCode());
			businessUserWithHiddenDetails.setEmailAddress(businessUser.getEmailAddress());
			businessUserWithHiddenDetails.setFlowId(businessUser.getFlowId());
			businessUserWithHiddenDetails.setBusinessFlow(businessUser.getBusinessFlow());
		}
		
		return businessUserWithHiddenDetails;
	}
	
	public BusinessUser setUserIdAndUserIdType(BusinessUser tokenizedBusinessUser,BusinessUser inputBusinessUser)
	{
		MyLogger.info("Editing the user:::");
		if(inputBusinessUser!=null)
		{
			tokenizedBusinessUser.setIdType(inputBusinessUser.getIdType());
			tokenizedBusinessUser.setUserId(inputBusinessUser.getUserId());
			
			tokenizedBusinessUser.setNickName(inputBusinessUser.getFullName());
			try 
			{
				this.editUser(tokenizedBusinessUser);
				MyLogger.info("User Editted:::");
			}
			catch(Exception exception)
			{
				this.handleBusinessException(exception, ErrorCode.USER_NOT_UPDATED);
			}
		}
		
		return tokenizedBusinessUser;
	}
	
	
	public void getMultipleTcAccounts(BusinessUser businessUser) throws BusinessException
	{
		if(businessUser!=null&&BooleanUtility.isTrue(businessUser.getIsOld()))
		{
			try 
			{
				//List<AzimutAccount> azimutAccounts=this.restManager.checkAccountMapper.wrapBaseBusinessEntity(true, this.prepareAccountRetrievalInputs(null, businessUser),null).getDataList();
				List<AzimutAccount> azimutAccounts=this.restContract.getDataList(this.restContract.checkAccountMapper, this.prepareAccountRetrievalInputs(null, businessUser), null);
				boolean isListPopulated=azimutAccountListUtility.isListPopulated(azimutAccounts);
				
				if(isListPopulated&&azimutAccounts.size()==1)
				{
					MyLogger.info("Single old account::::");
					BusinessUser inputBusinessUser=new BusinessUser();
					
					inputBusinessUser.setIdType(azimutAccounts.get(0).getAzIdType());
					inputBusinessUser.setUserId(azimutAccounts.get(0).getAzId());
					inputBusinessUser.setFullName(azimutAccounts.get(0).getFullName());	
					this.setUserIdAndUserIdType(businessUser, inputBusinessUser);
				}
				else if(isListPopulated&&azimutAccounts.size()>1)
				{	
					businessUser.setAzimutAccounts(azimutAccounts);
				}
				else
				{
					businessUser.setAzimutAccounts(null);
				}
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
				MyLogger.info("Could not retrieve the azimut bank accounts");
				if(this.exceptionHandler.checkIfIntegrationExceptinWithSpecificErrorCode(exception, ErrorCode.NO_MATCHED_CLIENT_NUMBER_EXIST))
				{
					throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, null);
				}
				else
				{
					throw this.exceptionHandler.handleException(exception);
				}
			}
		}
	
	}
	
	private BusinessUser lookForuUserAtTC(BusinessUser searchedForBusinessUser,BusinessUser businessUser) throws BusinessException
	{
		searchedForBusinessUser=new BusinessUser();			
		try 
		{
			//List<AzimutAccount> azimutAccounts=this.restManager.checkAccountMapper.wrapBaseBusinessEntity(true, this.prepareAccountRetrievalInputs(null, businessUser), null).getDataList();
			List<AzimutAccount> azimutAccounts=this.restContract.getDataList(this.restContract.checkAccountMapper, this.prepareAccountRetrievalInputs(null, businessUser), null);
			if(this.azimutAccountListUtility.isListPopulated(azimutAccounts))
			{
				searchedForBusinessUser.setBusinessFlow(BusinessFlow.SET_PASSWORD);					
				this.userUtility.saveOldUser(businessUser.getCountryPhoneCode(),businessUser.getPhoneNumber(),azimutAccounts);
			}
		
			else
			{
				searchedForBusinessUser.setBusinessFlow(BusinessFlow.GO_TO_REGISTRATION);
			}
		}
		catch(Exception teacomputerException)
		{
			teacomputerException.printStackTrace();
		
			MyLogger.info("Enter TC exception Handling");
			if(exceptionHandler.checkIfIntegrationExceptinWithSpecificErrorCode(teacomputerException, ErrorCode.NO_MATCHED_CLIENT_NUMBER_EXIST))
			{
				MyLogger.info("Go to reg");
				searchedForBusinessUser.setBusinessFlow(BusinessFlow.GO_TO_REGISTRATION);
			}
			else
			{
				MyLogger.info("Throw exception");
				throw this.exceptionHandler.handleException(teacomputerException);
			}
		}
		return searchedForBusinessUser;
	}
	
	public BusinessUser addUserInteraction(String countryCode,String countryPhoneCode,String phoneNumber,String email,String body,Integer type,MultipartFile file) throws BusinessException
	{
		this.userUtility.addUserInteraction(countryCode, countryPhoneCode, phoneNumber, email, body, type, file);
		return new BusinessUser();
	}
	public BusinessUser getUserInteractions(Integer typeId) throws BusinessException
	{
		this.userUtility.getUserInteractions(typeId);
		return new BusinessUser();
	}
}
