package innovitics.azimut.rest.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.BaseInput;
import innovitics.azimut.rest.entities.BaseOutput;
import innovitics.azimut.rest.models.BaseRestResponse;
import innovitics.azimut.utilities.mapping.FundPriceMapper;

@Component
public class RestManager {

	@Autowired public GetClientBalanceMapper getClientBalanceMapper;
	@Autowired public GetTransactionsMapper getTransactionsMapper;
	@Autowired public  GetClientBankAccountsMapper getClientBankAccountsMapper;
	@Autowired public  AddClientBankAccountMapper addClientBankAccountMapper;
	@Autowired public  HoldClientBankAccountMapper holdClientBankAccountMapper;
	@Autowired public  CheckAccountMapper checkAccountMapper;
	@Autowired public  AddAccountMapper addAccountMapper;
	@Autowired public  GetClientFundsMapper getClientFundsMapper;
	@Autowired public  GetFundPricesMapper getFundPricesMapper;
	@Autowired public  FundPriceMapper fundPriceMapper;
	@Autowired public  GetFundTransactionsMapper getFundTransactionsMapper;
	@Autowired public  GetEportfolioMapper getEportfolioMapper;
	@Autowired public  GetReportMapper getReportMapper;
	@Autowired public  GetCompanyBankAccountMapper getCompanyBankAccountMapper;
	@Autowired public PlaceOrderMapper placeOrderMapper;
	@Autowired public InjectWithdrawMapper injectWithdrawMapper;
	
	
	
	public void consume(RestMapper<BaseInput, BaseOutput, BaseRestResponse, BaseBusinessEntity> restMapper,BaseBusinessEntity baseBusinessEntity,boolean isList) throws HttpClientErrorException, IntegrationException, Exception
	{
		restMapper.wrapBaseBusinessEntity(isList, baseBusinessEntity,null);
	}
}
