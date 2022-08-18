package innovitics.azimut.utilities.datautilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.valify.BusinessValify;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.user.User;
import innovitics.azimut.models.user.UserDevice;
import innovitics.azimut.models.user.UserImage;
import innovitics.azimut.models.user.UserLocation;
import innovitics.azimut.security.AES;
import innovitics.azimut.services.kyc.UserImageService;
import innovitics.azimut.services.user.GenderService;
import innovitics.azimut.services.user.UserDeviceService;
import innovitics.azimut.services.user.UserLocationService;
import innovitics.azimut.services.user.UserService;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.crosslayerenums.UserImageType;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;
import innovitics.azimut.utilities.fileutilities.BlobData;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;
@Component
public class UserUtility extends ParentUtility{
	@Autowired UserDeviceService userDeviceService;
	@Autowired UserService userService;	
	@Autowired BlobFileUtility blobFileUtility;	
	@Autowired UserImageService userImageService;
	@Autowired protected ListUtility<UserImage> listUtility;
	@Autowired protected ListUtility<UserLocation> userLocationListUtility;
	@Autowired protected ExceptionHandler exceptionHandler;
	@Autowired GenderService genderService;
	@Autowired AES aes;
	@Autowired UserLocationService userLocationService;

	public void upsertDeviceIdAudit(User user,String deviceId)
	{
		if(user!=null&&StringUtility.isStringPopulated(deviceId))
		{			
				if (StringUtility.stringsDontMatch(user.getDeviceId(), deviceId)) 
				{
					UserDevice userDevice=new UserDevice();
					userDevice.setDeviceId(deviceId);
					userDevice.setCreatedAt(new Date());
					userDevice.setUser(user);
					userDevice.setUserPhone(user.getUserPhone());
					userDevice.setUpdatedAt(new Date());
					userDevice.setUserId(user.getUserId());
					user.setDeviceId(deviceId);
					
					
					this.userDeviceService.addUserDevice(userDevice);
					this.userService.update(user);
				}

			
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
		return blobFileUtility.uploadFileToBlob(file, true, this.configProperties.getBlobKYCDocuments(),this.generateUserImageSubDirectory(businessUser),true);
	}
	

	public BusinessUser isOldUserStepGreaterThanNewUserStep(BusinessUser tokenizedBusinessUser,Integer newUserStep)
	{	
		if(newUserStep!=null)
		{
			this.logger.info("New user step::::"+newUserStep.toString());
		}
		if(tokenizedBusinessUser.getUserStep()!=null)
		{
			this.logger.info("Old user step::::"+tokenizedBusinessUser.getUserStep().toString());
		}
		if(tokenizedBusinessUser.getUserStep()!=null)
		{
			if(!NumberUtility.isNewValueLessThanOrEqualOldValue(tokenizedBusinessUser.getUserStep(), newUserStep))
			{
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
				this.blobFileUtility.deleteFileFromBlob(this.configProperties.getBlobKYCDocuments(), userImage.getImageSubDirectory(), userImage.getImageName(), false);
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
				userImage.setImageUrl(this.blobFileUtility.generateFileRetrievalUrl(this.configProperties.getBlobKYCDocuments(), userImage.getImageName(), userImage.getImageSubDirectory(), true));
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
	public UserLocation getUserLocation(BusinessUser tokenizedBusinessUser) throws BusinessException
	{
		try 
		{
			return this.userLocationService.findByUserId(tokenizedBusinessUser.getId());
		} 
		catch (Exception exception) 
		{
			return (UserLocation)this.exceptionHandler.getNullIfNonExistent(exception);
		}
	}
}
