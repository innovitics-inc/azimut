package innovitics.azimut.rest.entities.teacomputers;

import innovitics.azimut.rest.entities.BaseInput;

public  class TeaComputerInput extends BaseInput{

	protected String locale;
	protected String idNumber;
	protected Long idTypeId;
	protected String username;
	protected String password;
	protected Long bankId;
	protected Long fundId;

	
	
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Long getIdTypeId() {
		return idTypeId;
	}

	public void setIdTypeId(Long idTypeId) {
		this.idTypeId = idTypeId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
	
	@Override
	public String setBaseUrl()
	{
		return this.configProperties.getTeaComputersUrl()+"/";
	}

	@Override
	public String getUrl() {
		return null;
		// TODO Auto-generated method stub
		
	}
	
	
}
