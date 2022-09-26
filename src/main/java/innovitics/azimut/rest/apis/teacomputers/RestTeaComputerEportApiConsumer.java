package innovitics.azimut.rest.apis.teacomputers;

import innovitics.azimut.rest.AbstractBaseRestConsumer;

public abstract class RestTeaComputerEportApiConsumer <TeaComputerRequest, TeaComputerResponse, TeaComputerInput, TeaComputerOutput> 
extends RestTeaComputerApiConsumer<TeaComputerRequest, TeaComputerResponse, TeaComputerInput, TeaComputerOutput>{

	protected void populateCredentials(innovitics.azimut.rest.models.teacomputers.TeaComputerRequest request)
	{
		request.setUserName(this.configProperties.getTeaComputersEportfolioUsername());
		
	}
	
	@Override
	public String generateURL(String params)
	{
		return this.configProperties.getTeaComputersEportUrl();
		
	}
}
