package innovitics.azimut.utilities.businessutilities;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.user.AzimutAccount;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.valify.BusinessValify;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.user.User;
import innovitics.azimut.models.user.UserBlockage;
import innovitics.azimut.models.user.UserDevice;
import innovitics.azimut.models.user.UserImage;
import innovitics.azimut.models.user.UserInteraction;
import innovitics.azimut.models.user.UserLocation;
import innovitics.azimut.models.user.UserType;
import innovitics.azimut.security.AES;
import innovitics.azimut.services.kyc.UserImageService;
import innovitics.azimut.services.kyc.UserTypeService;
import innovitics.azimut.services.user.GenderService;
import innovitics.azimut.services.user.UserBlockageService;
import innovitics.azimut.services.user.UserDeviceService;
import innovitics.azimut.services.user.UserInteractionService;
import innovitics.azimut.services.user.UserLocationService;
import innovitics.azimut.services.user.UserService;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.crosslayerenums.UserImageType;
import innovitics.azimut.utilities.crosslayerenums.UserStep;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;
import innovitics.azimut.utilities.fileutilities.BlobData;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;
import innovitics.azimut.utilities.fileutilities.SecureStorageService;
import innovitics.azimut.utilities.logging.MyLogger;
import innovitics.azimut.utilities.mapping.UserMapper;
@Component
public class UserUtility extends ParentUtility{
	@Autowired UserDeviceService userDeviceService;
	@Autowired UserService userService;	
	@Autowired BlobFileUtility blobFileUtility;
	@Autowired SecureStorageService storageService;
	@Autowired UserImageService userImageService;
	@Autowired protected ListUtility<UserImage> listUtility;
	@Autowired protected ListUtility<UserDevice> userDeviceListUtility;
	@Autowired protected ListUtility<UserLocation> userLocationListUtility;

	@Autowired GenderService genderService;
	@Autowired AES aes;
	@Autowired UserLocationService userLocationService;
	@Autowired UserTypeService userTypeService;
	@Autowired UserInteractionService userInteractionService;
	public void upsertDeviceIdAudit(User user,String deviceId,UserDevice updatedUserDevice)
	{
			if(updatedUserDevice!=null)
			{
				
				updatedUserDevice.setUpdatedAt(new Date());
				updatedUserDevice.setUser(user);
				this.userDeviceService.updateUserDevice(updatedUserDevice);
			}
			else
			{
				UserDevice userDevice=new UserDevice();
				userDevice.setCreatedAt(new Date());
				userDevice.setUpdatedAt(new Date());
				userDevice.setDeviceId(deviceId);
				userDevice.setUser(user);
				userDevice.setUserPhone(user.getUserPhone());
				userDevice.setUserId(user.getUserId());
				this.userDeviceService.updateUserDevice(userDevice);
			}
			
	}
	
	public void allowUserToLogin(User user,String deviceId) throws BusinessException
	{	
		if(!StringUtility.isStringPopulated(deviceId))
		{	
			MyLogger.info("deviceId empty");
			deviceId=aes.encrypt(user.getId().toString()+StringUtility.WEB_DEVICE);
			MyLogger.info("deviceId:::::"+deviceId);
		}
		
		List<UserDevice> userDevices=this.userDeviceService.getUserDevicesWithUserId(user.getId());
		
		
		
		if(userDeviceListUtility.isListPopulated(userDevices))
		{
			
			List<String> deviceIds=new ArrayList<String>();

			Timestamp current=new Timestamp(System.currentTimeMillis());		
		    Calendar cal = Calendar.getInstance();
		    cal.setTimeInMillis(current.getTime());	    
			int tokenExpiryMinutes=Integer.valueOf(this.configProperties.getJwTokenDurationInMinutes());
		    cal.add(Calendar.MINUTE, -tokenExpiryMinutes);
		    Timestamp currentMinusTokenExpiry = new Timestamp(cal.getTime().getTime());
		    
		    for(UserDevice userDevice:userDevices)
		    {
		    	if(this.getMinutesBefore(this.configProperties.getJwTokenDurationInMinutes()).before(userDevice.getUpdatedAt()))
		    	{
		    		throw new BusinessException(ErrorCode.MULTIPLE_LOGINS);
		    	}
		    	if(userDevice!=null&&StringUtility.isStringPopulated(userDevice.getDeviceId())&&StringUtility.stringsMatch(userDevice.getDeviceId(), deviceId))
		    	{
		    		MyLogger.info("UserDevice:::"+userDevice.toString());
		    		this.upsertDeviceIdAudit(user, deviceId,userDevice);
		    	}
		    	deviceIds.add(userDevice.getDeviceId());
		    }
		    if(!deviceIds.contains(deviceId))
		    {
		    	this.upsertDeviceIdAudit(user, deviceId,null);
		    }
		}
		else
		{
			this.upsertDeviceIdAudit(user, deviceId,null);
		}
		
	}	


	public UserImage createUserImageRecord(BusinessUser businessUser,MultipartFile file,UserImageType userImageType) throws IOException, BusinessException
	{
		//BlobData blobData=this.uploadFile(file, businessUser);
		UserImage userImage=new UserImage();
		
		User user=new User();
		user.setId(businessUser.getId());
		userImage.setUser(user);
		
		userImage.setUserIdType(businessUser.getIdType());
		userImage.setImageType(userImageType.getTypeId());
		//userImage.setImageUrl(blobData.getUrl());
		//userImage.setPrivateUrl(blobData.getConcatinated(true));
		userImage.setImageName(file.getOriginalFilename());
		userImage.setImageSubDirectory(this.generateUserImageSubDirectory(businessUser));
		userImage.setUserPhone(businessUser.getUserPhone());	
		userImage.setCreatedAt(new Date());
		userImage.setFile(file);
		return userImage;
		
	}
	
	public BlobData uploadFile(MultipartFile file,BusinessUser businessUser) throws IOException, BusinessException
	{
		//return blobFileUtility.uploadFileToBlob(file, true, this.configProperties.getBlobKYCDocuments(),this.generateUserImageSubDirectory(businessUser),true);
		return storageService.uploadFile(file, true, this.configProperties.getBlobKYCDocuments(),this.generateUserImageSubDirectory(businessUser),true);
	}
	

	public BusinessUser isOldUserStepGreaterThanNewUserStep(BusinessUser tokenizedBusinessUser,Integer newUserStep)
	{	
		if(newUserStep!=null)
		{
			MyLogger.info("New user step::::"+newUserStep.toString());
		}
		if(tokenizedBusinessUser.getUserStep()!=null)
		{
			MyLogger.info("Old user step::::"+tokenizedBusinessUser.getUserStep().toString());
		}
		if(tokenizedBusinessUser.getUserStep()!=null)
		{
			if(!NumberUtility.isNewValueLessThanOrEqualOldValue(tokenizedBusinessUser.getUserStep(), newUserStep))
			{
				if(tokenizedBusinessUser!=null)
				{
					if(tokenizedBusinessUser.getVerificationPercentage()!=null)
					{
						int oldVerificationPercentage=tokenizedBusinessUser.getVerificationPercentage();
						tokenizedBusinessUser.setVerificationPercentage(oldVerificationPercentage+UserStep.findWeightById(newUserStep));
					}
					else
					{
						tokenizedBusinessUser.setVerificationPercentage(UserStep.findWeightById(newUserStep));
				
					}
				}
				
				tokenizedBusinessUser.setUserStep(newUserStep);
				
			}				
		}
		return tokenizedBusinessUser;
	}
	
	public void removeImagesFromBlobAndDb(BusinessUser businessUser,boolean deleteIdImages) throws IOException
	{
		List<UserImage> userImages=new ArrayList<UserImage>();
		if(deleteIdImages)
		{
			userImages=this.getUserImages(businessUser, new Integer[] {UserImageType.FRONT_IMAGE.getTypeId(),UserImageType.BACK_IMAGE.getTypeId(),UserImageType.PASSPORT_IMAGE.getTypeId()},true);
		}
		else
		{
			userImages=this.getUserImages(businessUser, new Integer[] {UserImageType.LEFT.getTypeId(),UserImageType.RIGHT.getTypeId(),UserImageType.STRAIGHT.getTypeId(),UserImageType.SMILING.getTypeId()},true);
		}
		if(this.listUtility.isListPopulated(userImages))
		{
			for(UserImage userImage:userImages)
			{
				//this.blobFileUtility.deleteFileFromBlob(this.configProperties.getBlobKYCDocuments(), userImage.getImageSubDirectory(), userImage.getImageName(), false);
				this.storageService.deleteFile(this.configProperties.getBlobKYCDocuments(), userImage.getImageSubDirectory(), userImage.getImageName(), false);
			}
		}
		
		if(deleteIdImages)
		{
			this.userImageService.softDeleteOldUserIdImages(businessUser.getId());
		}
		else
		{
			this.userImageService.softDeleteOldUserFaceImages(businessUser.getId());
		}
	}
	
	public String generateUserImageSubDirectory(BusinessUser businessUser)
	{
		return "ValifyImages/"+DateUtility.getCurrentDayMonthYear()+"/"+businessUser.getUserPhone();
	}
	
	public List<UserImage> getUserImages(BusinessUser tokenizedBusinessUser,Integer[] imageTypes,boolean showUser)
	{
		List<UserImage> userImages=new ArrayList<UserImage>();
		try 
		{
			userImages=	this.userImageService.getUserImagesByUserAndTypes(tokenizedBusinessUser.getId(), imageTypes);
			
			for(UserImage userImage:userImages)
			{
				//userImage.setImageUrl(this.blobFileUtility.generateFileRetrievalUrl(this.configProperties.getBlobKYCDocuments(), userImage.getImageName(), userImage.getImageSubDirectory(), true));
				userImage.setImageUrl(this.storageService.generateFileRetrievalUrl(this.configProperties.getBlobKYCDocuments(), userImage.getImageName(), userImage.getImageSubDirectory(), true));
				if(!showUser)
				{
					userImage.setUser(null);
				}
			}
			
			tokenizedBusinessUser.setUserImages(userImages);				
		}
		catch(Exception exception)
		{
			if(this.exceptionHandler.isABusinessException(exception))
			{
				return null;
			}
		}
		return userImages;
	}
	
	public 	void uploadUserImages(List<UserImage> userImages,BusinessValify businessValifyResponse,BusinessUser businessUser) throws BusinessException, IOException
	{
		if(this.listUtility.isListPopulated(userImages))
		{
			for(UserImage userImage:userImages)
			{
				userImage.setTransactionId(businessValifyResponse.getValifyTransactionId());
				this.uploadFile(userImage.getFile(), businessUser);
			}
		}
	}
	
	public String encryptUserPassword(String password)
	{
		return this.aes.encrypt(password);
	}

	public void addUserLocation(BusinessUser tokenizedBusinessUser,UserLocation userLocation) throws BusinessException
	{

		try 
		{
			this.userLocationService.softdeleteOldUserLocations(tokenizedBusinessUser.getId());
			userLocation.setUserId(tokenizedBusinessUser.getId());
			userLocation.setCreatedAt(new Date());
			this.userLocationService.addUserLocation(userLocation);
		} 
		catch (Exception exception) 
		{
			throw this.exceptionHandler.handleBusinessException(exception,ErrorCode.USER_LOCATION_NOT_SAVED);
		}
	}
	public UserLocation getUserLocation(BusinessUser tokenizedBusinessUser) throws Exception
	{
		try 
		{
			return this.userLocationService.findByUserId(tokenizedBusinessUser.getId());
		} 
		catch (Exception exception) 
		{
			this.exceptionHandler.getNullIfNonExistent(exception);
			return new UserLocation();
		}
		
	}
	
	public User saveOldUser(String countryPhoneCode,String phoneNumber,List<AzimutAccount> azimutAccounts) throws Exception
	{
		User user=new User();
		user.setCountryPhoneCode(countryPhoneCode);
		user.setPhoneNumber(phoneNumber);
		user.setIsVerified(true);
		user.setIsOld(true);
		user.setUserStep(UserStep.CHOOSE_CONTRACT_MAP.getStepId());
		user.concatinate();
		user.setCreatedAt(new Date());
		user.setUpdatedAt(new Date());	
		
		String userId="";
		Long userIdType=0L;
		
	    if(azimutAccounts.size()==1&&azimutAccounts.get(0)!=null)
	    {
	    	userId=azimutAccounts.get(0).getAzId();
	    	userIdType=azimutAccounts.get(0).getAzIdType();
	    	UserType userType=this.getUserTypeByTeacomputerId(userIdType);
	    	if(userType==null)
	    	{	    		
	    		userIdType=this.userTypeService.addUserType(userIdType).getId();
	    	}
	    	user.setUserType(userType);
			user.setUserId(userId);
	    }
		
		MyLogger.info("Old user being saved::::::::::::::::::::::"+user.toString());
		this.userService.save(user);
		return user;
	}
	
	public UserType getUserTypeByTeacomputerId(Long teacomputerId) throws Exception
	{
		try 
		{
			return this.userTypeService.getUserTypeByTeacomputerId(teacomputerId);
		}
		catch (Exception exception) 
		{
			this.exceptionHandler.getNullIfNonExistent(exception);
			return null;
		}
				
	}
	
	public UserInteraction addUserInteraction(String countryCode,String countryPhoneCode,String phoneNumber,String email,String body,Integer type,MultipartFile file) throws BusinessException
	{
		UserInteraction  userInteraction=new UserInteraction();
		if(file!=null)
		{
			try 
			{	
				String url="";
				String subDirectory=DateUtility.getCurrentDayMonthYear();
				//url=this.blobFileUtility.uploadFileToBlob(file,false,this.configProperties.getBlobUserInteractionPath(),subDirectory,true).getUrl();
				url=this.storageService.uploadFile(file,false,this.configProperties.getBlobUserInteractionPath(),subDirectory,true).getUrl();
				userInteraction.setPrivateUrl(url);
				userInteraction.setImageName(file.getOriginalFilename());
				userInteraction.setImagePath(subDirectory);
			} 	
			catch (BusinessException | IOException e) 
			{
				MyLogger.info("Failed to upload the file::::");
				e.printStackTrace();
			}
		}
		userInteraction.setCountryCode(countryCode);
		userInteraction.setCountryPhoneCode(countryPhoneCode);
		userInteraction.setPhoneNumber(phoneNumber);
		userInteraction.setEmail(email);
		userInteraction.setBody(body);
		userInteraction.setType(type);
		userInteraction.setCreatedAt(new Date());
		userInteraction.setUpdatedAt(new Date());
		try 
		{
			return this.userInteractionService.addUserInteraction(userInteraction);
		}
		catch(Exception exception)
		{
			throw this.exceptionHandler.handleBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);
		}
	}
	
	public List<UserInteraction> getUserInteractions(Integer type) throws BusinessException
	{
		try 
		{
		return this.userInteractionService.getUserInteractionsByType(type);
		}
		catch(Exception exception)
		{
			this.exceptionHandler.getNullIfNonExistent(exception);
			return new ArrayList<UserInteraction>();
		}
	}
}
