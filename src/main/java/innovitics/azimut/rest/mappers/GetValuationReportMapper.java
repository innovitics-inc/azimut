package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.teacomputers.GetValuationReportApiConsumer;
import innovitics.azimut.rest.entities.teacomputers.GetValuationReportInput;
import innovitics.azimut.rest.entities.teacomputers.GetValuationReportOutput;
import innovitics.azimut.rest.models.teacomputers.GetValuationReportResponse;
@Component
public class GetValuationReportMapper extends RestMapper<GetValuationReportInput, GetValuationReportOutput, GetValuationReportResponse, BusinessAzimutClient> {
	@Autowired GetValuationReportApiConsumer getValuationReportApiConsumer;
	
	@Override
	BusinessAzimutClient consumeRestService(BusinessAzimutClient businessAzimutClient, String params) throws IntegrationException, HttpClientErrorException, Exception {
		return this.createBusinessEntityFromOutput(this.getValuationReportApiConsumer.invoke(this.createInput(businessAzimutClient), GetValuationReportResponse.class, params));
	}

	@Override
	List<BusinessAzimutClient> consumeListRestService(BusinessAzimutClient businessAzimutClient, String params) throws IntegrationException, HttpClientErrorException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	GetValuationReportInput createInput(BusinessAzimutClient businessAzimutClient) {
		GetValuationReportInput input=new GetValuationReportInput();
		input.setIdNumber(businessAzimutClient.getAzId());		
		input.setIdTypeId(businessAzimutClient.getAzIdType());
		input.setLocale(businessAzimutClient.getLanguage());
		return  input;
	}

	@Override
	BusinessAzimutClient createBusinessEntityFromOutput(GetValuationReportOutput getValuationReportOutput) {
		BusinessAzimutClient businessAzimutClient=new BusinessAzimutClient();
		if (getValuationReportOutput!=null) 
		{
			businessAzimutClient.setDocumentURL(getValuationReportOutput.getFilePath());
		}
		return businessAzimutClient;
	}

	@Override
	protected List<BusinessAzimutClient> createListBusinessEntityFromOutput(GetValuationReportOutput getValuationReportOutput) {
		// TODO Auto-generated method stub
		return null;
	}

}
