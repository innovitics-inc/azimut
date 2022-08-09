package innovitics.azimut.rest.models.teacomputers;

import com.fasterxml.jackson.annotation.JsonProperty;

import innovitics.azimut.rest.models.BaseRestRequest;

public class TeaComputerRequest extends BaseRestRequest{
	 protected String Signature;
	 protected Long IdTypeId;
	 protected String IdNumber;
	 protected String UserName;
	 protected String Password;
	 protected String FundId;
	 

	public String getSignature() {
		return Signature;
	}



	public void setSignature(String signature) {
		Signature = signature;
	}



	public Long getIdTypeId() {
		return IdTypeId;
	}



	public void setIdTypeId(Long idTypeId) {
		IdTypeId = idTypeId;
	}



	public String getIdNumber() {
		return IdNumber;
	}



	public void setIdNumber(String idNumber) {
		IdNumber = idNumber;
	}



	public String getUserName() {
		return UserName;
	}



	public void setUserName(String userName) {
		UserName = userName;
	}



	public String getPassword() {
		return Password;
	}



	public void setPassword(String password) {
		Password = password;
	}

	public String getFundId() {
		return FundId;
	}



	public void setFundId(String fundId) {
		FundId = fundId;
	}



	@Override
	public String toString() {
		return "TeaComputerRequest [Signature=" + Signature + ", IdTypeId=" + IdTypeId + ", IdNumber=" + IdNumber
				+ ", UserName=" + UserName + ", Password=" + Password + "]";
	}
	
	
	
}
