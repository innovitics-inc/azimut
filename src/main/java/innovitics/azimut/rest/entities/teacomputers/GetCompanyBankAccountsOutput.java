package innovitics.azimut.rest.entities.teacomputers;

import java.util.List;

public class GetCompanyBankAccountsOutput extends TeaComputerOutput{

	
	List <CompanyBankAccountOutput> companyBankAccountOutputs;

	public List<CompanyBankAccountOutput> getCompanyBankAccountOutputs() {
		return companyBankAccountOutputs;
	}

	public void setCompanyBankAccountOutputs(List<CompanyBankAccountOutput> companyBankAccountOutputs) {
		this.companyBankAccountOutputs = companyBankAccountOutputs;
	}
	
	
	
}
