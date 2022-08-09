package innovitics.azimut.utilities.mapping.kyc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import innovitics.azimut.businessmodels.kyc.BusinessKYCPage;
import innovitics.azimut.businessmodels.kyc.BusinessUserType;
import innovitics.azimut.models.kyc.KYCPage;
import innovitics.azimut.models.user.UserType;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.mapping.GreatGrandParentMapper;
@Component
public class UserTypeMapper extends GreatGrandParentMapper<UserType, BusinessUserType>{

	@Autowired protected KYCPageMapper kycPageMapper;
	@Autowired protected ListUtility<KYCPage> baseListUtility;
	@Autowired protected ListUtility<BusinessKYCPage> businessListUtility;

	@Override
	protected UserType convertBusinessUnitToBasicUnit(BusinessUserType businessUserType, boolean save) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BusinessUserType convertBasicUnitToBusinessUnit(UserType userType) {
		BusinessUserType businessUserType=new BusinessUserType();
		
		businessUserType.setId(userType.getId());
		businessUserType.setIdType(userType.getIdType());
		businessUserType.setNoOfKYCPages(userType.getNoOfKYCPages());
		businessUserType.setValifyImageCount(userType.getValifyImageCount());
		businessUserType.setCreatedAt(userType.getCreatedAt());
		businessUserType.setUpdatedAt(userType.getUpdatedAt());
		businessUserType.setDeletedAt(userType.getDeletedAt());
		/*if(this.baseListUtility.isListPopulated(userType.getPages()))
		{
			List<BusinessKYCPage> businessKYCPages=new ArrayList<BusinessKYCPage>();
			for(KYCPage kycPage:userType.getPages())
			{
				businessKYCPages.add(this.kycPageMapper.convertBasicUnitToBusinessUnit(kycPage));
			}
			businessUserType.setPages(businessKYCPages);
		}
		*/
		return businessUserType;
	}

	
}
