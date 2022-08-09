package innovitics.azimut.utilities.mapping;

import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.BusinessRole;
import innovitics.azimut.models.Role;
@Component
public class RoleMapper extends ParentMapper<Role, BusinessRole>{

	@Override
	protected Role convertBusinessUnitToBasicUnit(BusinessRole businessRole, boolean save) {
		Role role=new Role();
		
		if(businessRole!=null)
		{
			role.setDisplayName(businessRole.getDisplayName());
			role.setId(businessRole.getId());
			role.setName(businessRole.getName());
		}
		return role;
	}

	@Override
	protected BusinessRole convertBasicUnitToBusinessUnit(Role role) {
		BusinessRole businessRole=new BusinessRole();
		
		if(role!=null)
		{
			businessRole.setDisplayName(role.getDisplayName());
			businessRole.setId(role.getId());
			businessRole.setName(role.getName());
		}
		return businessRole;
	}

}
