package innovitics.azimut.businessservices;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.businessmodels.valify.BusinessValify;
import innovitics.azimut.businessutilities.ValifyUtility;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.user.UserImage;
import innovitics.azimut.rest.mappers.ValifyAccessTokenMapper;
import innovitics.azimut.rest.mappers.ValifyFacialImageMapper;
import innovitics.azimut.rest.mappers.ValifyIdMapper;
import innovitics.azimut.services.kyc.UserImageService;
import innovitics.azimut.utilities.crosslayerenums.UserImageType;
import innovitics.azimut.utilities.crosslayerenums.UserStep;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.datautilities.UserUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.mapping.kyc.ValifyMapper;
import innovitics.azimut.validations.validators.valify.ValifyFacialImages;
import innovitics.azimut.validations.validators.valify.ValifyIdImages;
@Service
public class BusinessValifyService extends AbstractBusinessService <BusinessValify>{
	@Autowired  ValifyAccessTokenMapper valifyAccessTokenMapper;
	@Autowired ValifyFacialImageMapper valifyFacialImageMapper;
	@Autowired ValifyIdMapper valifyIdMapper;
	@Autowired ValifyIdImages valifyIdImages;
	@Autowired ValifyFacialImages valifyFacialImages;
	@Autowired UserImageService userImageService;
	@Autowired ValifyMapper valifyMapper;
	@Autowired ValifyUtility valifyUtility;
	@Autowired UserUtility userUtility;
	@Autowired ListUtility<UserImage> userImageListUtility; 
	@Autowired BusinessUserService businessUserService;
	public BusinessValify valifyFacial (BusinessUser  businessUser,BusinessValify RequestBusinessValify,MultipartFile straightFace,MultipartFile smilingFace,MultipartFile leftSide,MultipartFile rightSide,Integer userStep,String language) throws BusinessException,IntegrationException, IOException
	{		
		//this.validate(businessValify, valifyFacialImages,BusinessValify.class.getName());
		List<UserImage> userImages=new ArrayList<UserImage>();
		
		BusinessValify businessValify=new BusinessValify();
		BusinessValify businessValifyResponse=new BusinessValify();
		this.userUtility.removeImagesFromBlobAndDb(businessUser, false);
		businessValify.setToken(valifyUtility.getToken(0L));
		try {
		if(straightFace!=null&&smilingFace!=null)
		{
			this.logger.info("Valify straight and smiling face images");
			businessValify.setStraightFace(valifyUtility.encodeImageToBase64(straightFace));
			UserImage straightImage=this.userUtility.createUserImageRecord(businessUser, straightFace, UserImageType.STRAIGHT);
			userImages.add(straightImage);
			businessValifyResponse.setStraightFace(straightImage.getPrivateUrl());
			
			
			
			businessValify.setSmilingFace(valifyUtility.encodeImageToBase64(smilingFace));
			UserImage smilingImage=this.userUtility.createUserImageRecord(businessUser, smilingFace, UserImageType.SMILING);
			userImages.add(smilingImage);
			businessValifyResponse.setSmilingFace(smilingImage.getPrivateUrl());
			
			businessValify.setUserStep(UserStep.STRAIGHT_AND_SMILE.getStepId());

		}
		
		if(leftSide!=null&&rightSide!=null)
		{
			this.logger.info("Valify left and right face images");
			businessValify.setLeftSide(valifyUtility.encodeImageToBase64(leftSide));
			UserImage leftImage=this.userUtility.createUserImageRecord(businessUser, leftSide, UserImageType.LEFT);
			userImages.add(leftImage);
			businessValifyResponse.setLeftSide(leftImage.getPrivateUrl());
			
			businessValify.setRightSide(valifyUtility.encodeImageToBase64(rightSide));
			UserImage rightImage=this.userUtility.createUserImageRecord(businessUser, rightSide, UserImageType.RIGHT);
			userImages.add(rightImage);
			businessValifyResponse.setRightSide(rightImage.getPrivateUrl());
			
			businessValify.setUserStep(UserStep.LEFT_AND_RIGHT.getStepId());
		}
		}
		catch(Exception exception)
		{
			this.handleBusinessException(exception, ErrorCode.USER_NOT_UPDATED);
		}
		businessValify.setLanguage(language);
		
		
		this.logger.info("Request"+businessValify.toString());
		
		
		try 
		{
		  businessValifyResponse=this.valifyFacialImageMapper.consumeRestService(this.determineFacialType(businessValify), null);
		  updateUserDetailsAndSaveUserImages(businessUser,businessValify,businessValifyResponse,userImages);
		}
		catch (Exception exception) 
		{
			this.handleValifyExceptions(exception);
		}
		this.logger.info("First Page ID::::"+businessUser.getFirstPageId());
		businessValifyResponse.setFirstPageId(businessUser.getFirstPageId());
		return businessValifyResponse;
	}

	
	public 	BusinessValify valifyId (BusinessUser  businessUser,BusinessValify RequestBusinessValify,MultipartFile frontImage,MultipartFile backImage,MultipartFile passportImage,Integer userStep,String language,String documentType) throws BusinessException,IntegrationException, IOException
	{	
		//this.validate(businessValify, valifyIdImages,BusinessValify.class.getName());
		List<UserImage> userImages=new ArrayList<UserImage>();
		BusinessValify businessValify=new BusinessValify();
		BusinessValify businessValifyResponse=new BusinessValify();

		this.userUtility.removeImagesFromBlobAndDb(businessUser, true);		
		businessValify.setToken(valifyUtility.getToken(0L));
		
		try 
		{
			if(frontImage!=null&&backImage!=null)
			{
			
			this.logger.info("Valify national ID documents");	
			businessValify.setFrontImage(valifyUtility.encodeImageToBase64(frontImage));
			UserImage frontUserImage=this.userUtility.createUserImageRecord(businessUser, frontImage, UserImageType.FRONT_IMAGE);
			userImages.add(frontUserImage);
			businessValifyResponse.setFrontImage(frontUserImage.getPrivateUrl());

			businessValify.setBackImage(valifyUtility.encodeImageToBase64(backImage));
			UserImage backUserImage=this.userUtility.createUserImageRecord(businessUser, backImage, UserImageType.BACK_IMAGE);
			userImages.add(backUserImage);
			businessValifyResponse.setBackImage(backUserImage.getPrivateUrl());
			}
			
		if(passportImage!=null)
			{
			this.logger.info("Valify passport document");
			businessValify.setPassportImage(valifyUtility.encodeImageToBase64(passportImage));
			UserImage passportUserImage=this.userUtility.createUserImageRecord(businessUser, passportImage, UserImageType.PASSPORT_IMAGE);
			userImages.add(passportUserImage);
			businessValifyResponse.setPassportImage(passportUserImage.getPrivateUrl());
			}
		}
		catch(Exception exception)
		{
			this.handleBusinessException(exception, ErrorCode.USER_NOT_UPDATED);
		}
		businessValify.setDocumentType(documentType);
		businessValify.setLanguage(language);
		businessValify.setUserStep(UserStep.ID_IMAGES.getStepId());
		
		
		try 
		{
			businessValifyResponse=this.valifyIdMapper.consumeRestService(businessValify, null);
			updateUserDetailsAndSaveUserImages(businessUser,businessValify,businessValifyResponse,userImages);
			
		}
		catch (Exception exception) 
		{
			this.handleValifyExceptions(exception);
		}
		this.logger.info("First Page ID::::"+businessUser.getFirstPageId());
		businessValifyResponse.setFirstPageId(businessUser.getFirstPageId());
		return businessValifyResponse;
	}
	
	
	
	BusinessValify determineFacialType(BusinessValify businessValify)
	{
		if(businessValify!=null&&this.numberUtility.areIntegerValuesMatching(businessValify.getUserStep(), UserStep.STRAIGHT_AND_SMILE.getStepId()))
		{
			this.logger.info("Valify national straight and smiling face images user step");
			businessValify.setFirstImage(businessValify.getStraightFace());
			businessValify.setSecondImage(businessValify.getSmilingFace());
		}
		
		else if(businessValify!=null&&this.numberUtility.areIntegerValuesMatching(businessValify.getUserStep(), UserStep.LEFT_AND_RIGHT.getStepId()))
		{
			this.logger.info("Valify national left and right face images user step");
			businessValify.setFirstImage(businessValify.getLeftSide());
			businessValify.setSecondImage(businessValify.getRightSide());
		}		
		return businessValify;
	}
	
	
	void  handleValifyExceptions(Exception exception) throws BusinessException
	{
		exception.printStackTrace();	
		if(exception instanceof IntegrationException)
			throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException)exception, ErrorCode.FAILED_TO_INTEGRATE);
			else		
			throw this.handleBusinessException((Exception)exception,ErrorCode.OPERATION_NOT_PERFORMED);
	}
	
	void updateUserDetailsAndSaveUserImages(BusinessUser businessUser,BusinessValify businessValify,BusinessValify businessValifyResponse,List<UserImage> userImages) throws BusinessException
	{
		try {
			businessUser=this.userUtility.isOldUserStepGreaterThanNewUserStep(businessUser,businessValify.getUserStep());
			businessUser.setFirstName(businessValifyResponse.getFirstName());
			businessUser.setLastName(businessValifyResponse.getLastName());
			businessUser.setCountry(StringUtility.isStringPopulated(businessValifyResponse.getCountry())?businessValifyResponse.getCountry():businessValifyResponse.getNationality());
			businessUser.setCity(businessValifyResponse.getCity());
			businessUser.setDateOfBirth(DateUtility.changeStringDateFormat(businessValifyResponse.getDateOfBirth(), new SimpleDateFormat("yyyy-mm-dd"), new SimpleDateFormat("dd-mm-yyyy")));
			businessUser.setDateOfIdExpiry(DateUtility.changeStringDateFormat(businessValifyResponse.getExpiryDate(), new SimpleDateFormat("yyyy-mm-dd"), new SimpleDateFormat("dd-mm-yyyy")));
			
			
			businessUserService.editUser(businessUser);
			this.userUtility.uploadUserImages(userImages,businessValifyResponse,businessUser);
			userImageService.saveImages(userImages);
		}
		catch(Exception exception)
		{
			this.logger.info("Could not update the user details");
			exception.printStackTrace();
			this.handleBusinessException(exception, ErrorCode.USER_NOT_UPDATED);
		}
	}
	
	
}
