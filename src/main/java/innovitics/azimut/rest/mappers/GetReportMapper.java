package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.GetReportApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.GetReportInput;
import innovitics.azimut.rest.entities.teacomputers.GetReportOutput;
import innovitics.azimut.rest.models.teacomputers.GetReportResponse;
@Component
public class GetReportMapper extends RestMapper<GetReportInput, GetReportOutput, GetReportResponse, BusinessAzimutClient> {
	@Autowired GetReportApiConsumer getReportApiConsumer;
	
	@Override
	BusinessAzimutClient consumeRestService(BusinessAzimutClient businessAzimutClient, String params) throws IntegrationException, HttpClientErrorException, Exception {
		return this.createBusinessEntityFromOutput(this.getReportApiConsumer.invoke(this.createInput(businessAzimutClient), GetReportResponse.class, params));
	}

	@Override
	List<BusinessAzimutClient> consumeListRestService(BusinessAzimutClient businessAzimutClient, String params) throws IntegrationException, HttpClientErrorException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	GetReportInput createInput(BusinessAzimutClient businessAzimutClient) {
		GetReportInput input=new GetReportInput();
		input.setIdNumber(businessAzimutClient.getAzId());		
		input.setIdTypeId(businessAzimutClient.getAzIdType());
		input.setLocale(businessAzimutClient.getLanguage());
		input.setReportType(businessAzimutClient.getReportType());
		input.setFromDate(businessAzimutClient.getSearchFromDate());
		input.setToDate(businessAzimutClient.getSearchToDate());
		input.setCurrencyId(businessAzimutClient.getCurrencyId());
		return  input;
	}

	@Override
	BusinessAzimutClient createBusinessEntityFromOutput(GetReportOutput getValuationReportOutput) {
		BusinessAzimutClient businessAzimutClient=new BusinessAzimutClient();
		if (getValuationReportOutput!=null) 
		{
			businessAzimutClient.setDocumentURL(getValuationReportOutput.getFilePath());
		}
		return businessAzimutClient;
	}

	@Override
	protected List<BusinessAzimutClient> createListBusinessEntityFromOutput(GetReportOutput getValuationReportOutput) {
		// TODO Auto-generated method stub
		return null;
	}

}
