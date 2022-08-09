package innovitics.azimut.utilities.datautilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.models.ChangePhoneNumberRequest;
import innovitics.azimut.models.user.User;
import innovitics.azimut.services.user.ChangePhoneNumberRequestService;
import innovitics.azimut.services.user.UserService;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.exceptionhandling.ExceptionHandler;

@Component
public class ChangePhoneNumberRequestUtility {

	@Autowired ChangePhoneNumberRequestService changePhoneNumberRequestService;
	@Autowired ExceptionHandler  exceptionHandler;
	@Autowired ListUtility<ChangePhoneNumberRequest> listUtility;
	@Autowired UserService userService;

	public boolean checkIfTheCurrentRequestHolderHasAnyOpenChangePhoneNumberRequests(String oldPhoneNumber)
	{
		List <ChangePhoneNumberRequest> changePhoneNumberRequests=new  ArrayList<ChangePhoneNumberRequest>();

		try 
		{
			changePhoneNumberRequests=this.changePhoneNumberRequestService.getChangePhoneNumberRequestsByOldPhoneNumber(oldPhoneNumber);
		}
		
		catch(Exception exception)
		{
			return false;
		}
		
		if(listUtility.isListPopulated(changePhoneNumberRequests))
		{
			return true;
		}
		else
			return false;
	}
	
	
	public void  addNewChangePhoneNumberRequest(User user,String newPhoneNumber)
	{
		if (StringUtility.isStringPopulated(newPhoneNumber)) 
		{
			ChangePhoneNumberRequest changePhoneNumberRequest = new ChangePhoneNumberRequest();
			changePhoneNumberRequest.setCreatedAt(new Date());
			changePhoneNumberRequest.setUpdatedAt(new Date());
			changePhoneNumberRequest.setAppUserId(user.getId());
			changePhoneNumberRequest.setOldPhoneNumber(user.getUserPhone());
			changePhoneNumberRequest.setNewPhoneNumber(newPhoneNumber);
			changePhoneNumberRequest.setUserId(user.getUserId());
			changePhoneNumberRequest.setIsApproved(false);
			try {
				this.changePhoneNumberRequestService.addChangePhoneNumberRequest(changePhoneNumberRequest);
			} catch (Exception exception) {
				this.exceptionHandler.handleAsBusinessException(exception, ErrorCode.OPERATION_NOT_PERFORMED);
			}

		}
	}
	
}
