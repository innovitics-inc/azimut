package innovitics.azimut.rest.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.BusinessPayment;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.apis.paytabs.QueryPaymentApiConsumer;
import innovitics.azimut.rest.entities.paytabs.QueryPaymentInput;
import innovitics.azimut.rest.entities.paytabs.QueryPaymentOutput;
import innovitics.azimut.rest.models.paytabs.PaytabsQueryRequest;
import innovitics.azimut.rest.models.paytabs.PaytabsQueryResponse;

@Component
public class PaytabsQueryPaymentMapper extends RestMapper<QueryPaymentInput, QueryPaymentOutput, PaytabsQueryRequest, PaytabsQueryResponse, BusinessPayment>{

	@Autowired QueryPaymentApiConsumer queryPaymentApiConsumer;
	@Override
	BusinessPayment consumeRestService(BusinessPayment baseBusinessEntity, String params)throws IntegrationException, HttpClientErrorException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	List<BusinessPayment> consumeListRestService(BusinessPayment businessPayment, String params)throws IntegrationException, HttpClientErrorException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	QueryPaymentInput createInput(BusinessPayment businessPayment) {
		QueryPaymentInput queryPaymentInput=new QueryPaymentInput();
		queryPaymentInput.setTransactionReference(businessPayment.getReferenceTransactionId());
		return queryPaymentInput;
	}

	@Override
	BusinessPayment createBusinessEntityFromOutput(QueryPaymentOutput queryPaymentOutput) {
		BusinessPayment businessPayment=new BusinessPayment();
		businessPayment.setAmount(queryPaymentOutput.getCartAmount());
		businessPayment.setTransactionStatus(queryPaymentOutput.getResponseStatus());
		return businessPayment;
	}

	@Override
	protected List<BusinessPayment> createListBusinessEntityFromOutput(QueryPaymentOutput BaseOutput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setConsumer(BusinessPayment businessPayment) {
		this.consumer=queryPaymentApiConsumer;
		
	}

}
