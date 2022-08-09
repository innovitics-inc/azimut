package innovitics.azimut.utilities.mapping;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.models.user.User;
import innovitics.azimut.models.user.UserType;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.fileutilities.BlobFileUtility;
@Component
public class UserMapper extends Mapper<User, BusinessUser>{
@Autowired RoleMapper roleMapper;
@Autowired BlobFileUtility blobFileUtility;

	@Override
	public User convertBusinessUnitToBasicUnit(BusinessUser businessUser, boolean save) {

		User user =new User();
		
		if(businessUser!=null)
		{
			//this.convertBusinessDatesToBasicDates(user, businessUser, save);
			
			user.setId(businessUser.getId());
			if(businessUser.getUserPhone()!=null)
			user.setUserPhone(businessUser.getUserPhone());
			if(businessUser.getPassword()!=null)
			user.setPassword(businessUser.getPassword());
			if(businessUser.getCountryPhoneCode()!=null)
			user.setCountryPhoneCode(businessUser.getCountryPhoneCode());
			if(businessUser.getPhoneNumber()!=null)
			user.setPhoneNumber(businessUser.getPhoneNumber());
			if(businessUser.getEmailAddress()!=null)
			user.setEmailAddress(businessUser.getEmailAddress());
			if(businessUser.getNickName()!=null)
			user.setNickName(businessUser.getNickName());
			if(businessUser.getDeviceId()!=null)
			user.setDeviceId(businessUser.getDeviceId());
			if(businessUser.getProfilePicture()!=null)
			user.setProfilePicture(businessUser.getProfilePicture());
			if(businessUser.getUserId()!=null)
			user.setUserId(businessUser.getUserId());
			
			if(businessUser.getIdType()!=null)
			{
				UserType userType=new UserType();
				userType.setId(businessUser.getIdType());
				user.setUserType(userType);
			}
			
			
			if(businessUser.getSignedPdf()!=null)
			user.setSignedPdf(businessUser.getSignedPdf());
			if(businessUser.getPicturePath()!=null)
			user.setPicturePath(businessUser.getPicturePath());
			if(businessUser.getPdfPath()!=null)
			user.setPdfPath(businessUser.getPdfPath());		
			user.setUpdatedAt(DateUtility.getCurrentDate());
			businessUser.setUpdatedAt(DateUtility.getCurrentDate());
			if(businessUser.isChangeNoApproved()!=null)
			user.setChangeNoApproved(businessUser.isChangeNoApproved());
			if(businessUser.getIsVerified()!=null)
			user.setIsVerified(businessUser.getIsVerified());
			if(businessUser.getVerificationPercentage()!=null)
			user.setVerificationPercentage(businessUser.getVerificationPercentage());	
			if(businessUser.getMigrated()!=null)
			user.setMigrated(businessUser.getMigrated());
			if(businessUser.getCreatedAt()!=null)
			user.setCreatedAt(businessUser.getCreatedAt());	
			if(businessUser.getCountryCode()!=null)
			user.setCountryCode(businessUser.getCountryCode());	
			if(businessUser.getLastSolvedPageId()!=null)
			user.setLastSolvedPageId(businessUser.getLastSolvedPageId());
			if(businessUser.getNextPageId()!=null)
			user.setNextPageId(businessUser.getNextPageId());
			if(businessUser.getUserStep()!=null)
			user.setUserStep(businessUser.getUserStep());
			if(businessUser.getContractMap()!=null)
			user.setContractMap(businessUser.getContractMap());

			if(businessUser.getCountry()!=null)
			user.setCountry(businessUser.getCountry());

			if(businessUser.getCity()!=null)
			user.setCity(businessUser.getCity());

			if(businessUser.getFirstName()!=null)
			user.setFirstName(businessUser.getFirstName());

			if(businessUser.getLastName()!=null)
			user.setLastName(businessUser.getLastName());

			if(businessUser.getDateOfBirth()!=null)
			user.setDateOfBirth(businessUser.getDateOfBirth());
			
			if(businessUser.getDateOfIdExpiry()!=null)
				user.setDateOfIdExpiry(businessUser.getDateOfIdExpiry());
			
			
			if(businessUser.getIdType()!=null)
			{
				UserType userType=new UserType();
				userType.setId(businessUser.getIdType());
				user.setUserType(userType);
			}
			
			
			if(businessUser.getOtherIdType()!=null)
				user.setOtherIdType(businessUser.getOtherIdType());
			if(businessUser.getOtherUserId()!=null)
				user.setOtherUserId(businessUser.getOtherUserId());

			if(businessUser.getOtherNationality()!=null)
				user.setOtherNationality(businessUser.getOtherNationality());
			
			user.concatinate();
			
			return user;
			
			
			

		}
		
		
		this.logger.info("User::"+user.toString());
		return user;
		
		

	}

	@Override
	public BusinessUser convertBasicUnitToBusinessUnit(User user) {
		BusinessUser businessUser=new BusinessUser();
		if(user!=null)
		{
			this.logger.info("User before conversion::"+user.toString());
			//this.convertBasicDatesToBusinessDates(user, businessUser);
			
			if(user.getId()!=null)
			businessUser.setId(user.getId());
			if(user.getUserPhone()!=null)
			businessUser.setUserPhone(user.getUserPhone());
			if(user.getPassword()!=null) 
			businessUser.setPassword(user.getPassword());
		 	if(user.getCountryPhoneCode()!=null)
			businessUser.setCountryPhoneCode(user.getCountryPhoneCode());
			if(user.getPhoneNumber()!=null)
			businessUser.setPhoneNumber(user.getPhoneNumber());
			if(user.getEmailAddress()!=null)
			businessUser.setEmailAddress(user.getEmailAddress());
			if(user.getNickName()!=null)
			businessUser.setNickName(user.getNickName());
			if(user.getDeviceId()!=null)
			businessUser.setDeviceId(user.getDeviceId());
			if(user.getProfilePicture()!=null)
			businessUser.setProfilePicture(user.getProfilePicture());
			if(user.getUserId()!=null)
			businessUser.setUserId(user.getUserId());
			if(user.getUserType()!=null)
			{
				businessUser.setUserIdType(user.getUserType().getIdType());
				businessUser.setIdType(user.getUserType().getId());
				businessUser.setFirstPageId(user.getUserType().getFirstPageId());
			}
			if(user.getProfilePicture()!=null)
			businessUser.setProfilePicture(user.getProfilePicture());
			if(user.getSignedPdf()!=null)
			businessUser.setSignedPdf(user.getSignedPdf());
			if(user.getPicturePath()!=null)
			businessUser.setPicturePath(user.getPicturePath());
			if(user.getPdfPath()!=null)
			businessUser.setPdfPath(user.getPdfPath());
			if(user.isChangeNoApproved()!=null)
			businessUser.setChangeNoApproved(user.isChangeNoApproved());			
			if(user.getIsVerified()!=null)
			businessUser.setIsVerified(user.getIsVerified());
			if(user.getVerificationPercentage()!=null)
			businessUser.setVerificationPercentage(user.getVerificationPercentage());				
			if(user.getMigrated()!=null)
			businessUser.setMigrated(user.getMigrated());
			if(user.getCreatedAt()!=null)
			businessUser.setCreatedAt(user.getCreatedAt());
			if(user.getUpdatedAt()!=null)
			businessUser.setUpdatedAt(user.getUpdatedAt());				
			if(user.getCountryCode()!=null)
			businessUser.setCountryCode(user.getCountryCode());
			if(user.getLastSolvedPageId()!=null)
			businessUser.setLastSolvedPageId(user.getLastSolvedPageId());
			if(user.getNextPageId()!=null)
			businessUser.setNextPageId(user.getNextPageId());
			if(user.getUserStep()!=null)
			{
				businessUser.setUserStep(user.getUserStep().intValue());
				businessUser.setNextUserStep(user.getUserStep().intValue()+1);
			}
			else if(user.getUserStep()==null)
			{
				businessUser.setUserStep(0);
				businessUser.setNextUserStep(1);
			}
			if(user.getContractMap()!=null)
			businessUser.setContractMap(user.getContractMap());

			
			
			if(user.getCountry()!=null)
				businessUser.setCountry(user.getCountry());

				if(user.getCity()!=null)
					businessUser.setCity(user.getCity());

				if(user.getFirstName()!=null)
					businessUser.setFirstName(user.getFirstName());

				if(user.getLastName()!=null)
					businessUser.setLastName(user.getLastName());

				if(user.getDateOfBirth()!=null)
					businessUser.setDateOfBirth(user.getDateOfBirth());
				
				if(user.getDateOfIdExpiry()!=null)
					businessUser.setDateOfIdExpiry(user.getDateOfIdExpiry());
				

				if(user.getOtherIdType()!=null)
					businessUser.setOtherIdType(user.getOtherIdType());
				if(user.getOtherUserId()!=null)
					businessUser.setOtherUserId(user.getOtherUserId());
			

				if(user.getOtherNationality()!=null)
					businessUser.setOtherNationality(user.getOtherNationality());


			businessUser.concatinate();

		}
		this.logger.info("Business User::"+businessUser.toString());
		return businessUser;

	}
	
	
	public BusinessUser convertNewBusinessUnitToOldBusinessUnit(BusinessUser businessUser,BusinessUser oldBusinessUser, boolean save) {

		
		
		if(businessUser!=null)
		{
			//this.convertBusinessDatesToBusinessDates(oldBusinessUser, businessUser, save);
			
			oldBusinessUser.setId(businessUser.getId());
			if(businessUser.getUserPhone()!=null)
				oldBusinessUser.setUserPhone(businessUser.getUserPhone());
			if(businessUser.getPassword()!=null)
				oldBusinessUser.setPassword(businessUser.getPassword());
			if(businessUser.getCountryPhoneCode()!=null)
				oldBusinessUser.setCountryPhoneCode(businessUser.getCountryPhoneCode());
			if(businessUser.getPhoneNumber()!=null)
				oldBusinessUser.setPhoneNumber(businessUser.getPhoneNumber());
			if(businessUser.getEmailAddress()!=null)
				oldBusinessUser.setEmailAddress(businessUser.getEmailAddress());
			if(businessUser.getNickName()!=null)
				oldBusinessUser.setNickName(businessUser.getNickName());
			if(businessUser.getDeviceId()!=null)
				oldBusinessUser.setDeviceId(businessUser.getDeviceId());
			if(businessUser.getProfilePicture()!=null)
				oldBusinessUser.setProfilePicture(businessUser.getProfilePicture());
			if(businessUser.getUserId()!=null)
				oldBusinessUser.setUserId(businessUser.getUserId());
			if(businessUser.getUserIdType()!=null)
				oldBusinessUser.setUserIdType(businessUser.getUserIdType());
			if(businessUser.getSignedPdf()!=null)
				oldBusinessUser.setSignedPdf(businessUser.getSignedPdf());	
			
			if(businessUser.isChangeNoApproved()!=null)
				oldBusinessUser.setChangeNoApproved(businessUser.isChangeNoApproved());
				
			if(businessUser.getIsVerified()!=null)
				oldBusinessUser.setIsVerified(businessUser.getIsVerified());

				
			if(businessUser.getVerificationPercentage()!=null)
				oldBusinessUser.setVerificationPercentage(businessUser.getVerificationPercentage());	
				
			if(businessUser.getMigrated()!=null)
			
				oldBusinessUser.setMigrated(businessUser.getMigrated());
			
		     if(businessUser.getCreatedAt()!=null)
				
					oldBusinessUser.setCreatedAt(businessUser.getCreatedAt());
				
		     if(businessUser.getCountryCode()!=null)
					
					oldBusinessUser.setCountryCode(businessUser.getCountryCode());
			
		     if(businessUser.getIdType()!=null)
		     {
		    	 oldBusinessUser.setIdType(businessUser.getIdType());
		     }
		     
		     if(businessUser.getUserIdType()!=null)
		     {
		    	 oldBusinessUser.setUserIdType(businessUser.getUserIdType());
		     }
		     
		     if(businessUser.getLastSolvedPageId()!=null)
		     { 
		    	 oldBusinessUser.setLastSolvedPageId(businessUser.getLastSolvedPageId());
		     }
			if(businessUser.getNextPageId()!=null)
			{	
				oldBusinessUser.setNextPageId(businessUser.getNextPageId());
			}
					if(businessUser.getUserStep()!=null)
			{		
				oldBusinessUser.setUserStep(businessUser.getUserStep());
			}
			if(businessUser.getContractMap()!=null)
			{	
				oldBusinessUser.setContractMap(businessUser.getContractMap());
			}

			if(businessUser.getCountry()!=null)
				oldBusinessUser.setCountry(businessUser.getCountry());

				if(businessUser.getCity()!=null)
					oldBusinessUser.setCity(businessUser.getCity());

				if(businessUser.getFirstName()!=null)
					oldBusinessUser.setFirstName(businessUser.getFirstName());

				if(businessUser.getLastName()!=null)
					oldBusinessUser.setLastName(businessUser.getLastName());

				if(businessUser.getDateOfBirth()!=null)
					oldBusinessUser.setDateOfBirth(businessUser.getDateOfBirth());

			

				if(businessUser.getOtherIdType()!=null)
					oldBusinessUser.setOtherIdType(businessUser.getOtherIdType());
				if(businessUser.getOtherUserId()!=null)
					oldBusinessUser.setOtherUserId(businessUser.getOtherUserId());

				if(businessUser.getOtherNationality()!=null)
					oldBusinessUser.setOtherNationality(businessUser.getOtherNationality());



		     
			businessUser.concatinate();
			oldBusinessUser.concatinate();

		}
		return oldBusinessUser;
		
	}


}
