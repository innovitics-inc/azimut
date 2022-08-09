package innovitics.azimut.businessmodels.user;

import java.util.List;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class BusinessEntityValues extends BaseBusinessEntity{

	private Long entityId;
	private List<BusinessAzimutEntityDetail> entityDetails;
	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	public List<BusinessAzimutEntityDetail> getEntityDetails() {
		return entityDetails;
	}
	public void setEntityDetails(List<BusinessAzimutEntityDetail> entityDetails) {
		this.entityDetails = entityDetails;
	}
	
}
