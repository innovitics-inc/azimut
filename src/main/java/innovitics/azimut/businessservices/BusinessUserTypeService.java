package innovitics.azimut.businessservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import innovitics.azimut.businessmodels.kyc.BusinessKYCPage;
import innovitics.azimut.businessmodels.kyc.BusinessUserType;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.models.kyc.KYCPage;
import innovitics.azimut.models.user.UserType;
import innovitics.azimut.services.kyc.KYCPageService;
import innovitics.azimut.services.kyc.UserTypeService;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.mapping.kyc.KYCPageMapper;
@Service
public class BusinessUserTypeService extends AbstractBusinessService<BusinessUserType> {
	
	@Autowired KYCPageMapper kycPageMapper;
	@Autowired UserTypeService userTypeService;
	@Autowired KYCPageService kycPageService;
	@Autowired ListUtility<KYCPage> listUtility;
	
	public List<BusinessKYCPage> getAllPagesByUserTypeId(Long id) throws BusinessException
	{
		List<BusinessKYCPage> businessKYCPages = new ArrayList<BusinessKYCPage>();
		try {
			/*
			UserType userType = this.userTypeService.getUserTypeById(id);
			if (userType != null && this.listUtility.isListPopulated(userType.getPages())) {
				businessKYCPages = this.kycPageMapper.convertBasicListToBusinessList(userType.getPages());
			}
			*/
		} catch (Exception exception) {
			throw this.handleBusinessException(exception, ErrorCode.PAGE_NOT_FOUND);
		}
		return businessKYCPages;
	}
	
	
	
	

}
