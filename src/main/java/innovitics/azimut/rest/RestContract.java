package innovitics.azimut.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.businessmodels.WrapperBusinessEntity;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.entities.BaseInput;
import innovitics.azimut.rest.entities.BaseOutput;
import innovitics.azimut.rest.mappers.AddAccountMapper;
import innovitics.azimut.rest.mappers.AddClientBankAccountMapper;
import innovitics.azimut.rest.mappers.CheckAccountMapper;
import innovitics.azimut.rest.mappers.GetClientBalanceMapper;
import innovitics.azimut.rest.mappers.GetClientBankAccountsMapper;
import innovitics.azimut.rest.mappers.GetClientFundsMapper;
import innovitics.azimut.rest.mappers.GetCompanyBankAccountMapper;
import innovitics.azimut.rest.mappers.GetEportfolioMapper;
import innovitics.azimut.rest.mappers.GetFundPricesMapper;
import innovitics.azimut.rest.mappers.GetFundTransactionsMapper;
import innovitics.azimut.rest.mappers.GetReportMapper;
import innovitics.azimut.rest.mappers.GetTransactionsMapper;
import innovitics.azimut.rest.mappers.HoldClientBankAccountMapper;
import innovitics.azimut.rest.mappers.InjectWithdrawMapper;
import innovitics.azimut.rest.mappers.PaytabsInitiatePaymentMapper;
import innovitics.azimut.rest.mappers.PlaceOrderMapper;
import innovitics.azimut.rest.mappers.RestMapper;
import innovitics.azimut.rest.mappers.ValifyAccessTokenMapper;
import innovitics.azimut.rest.mappers.ValifyFacialImageMapper;
import innovitics.azimut.rest.mappers.ValifyIdMapper;
import innovitics.azimut.rest.mappers.ValifyPassportIdMapper;
import innovitics.azimut.utilities.mapping.FundPriceMapper;

@Component
public class RestContract<I, O, REQ, RES, B extends BaseBusinessEntity> 
{
	@Autowired public  GetClientBalanceMapper getClientBalanceMapper;
	@Autowired public GetTransactionsMapper getTransactionsMapper;
	@Autowired public GetClientBankAccountsMapper getClientBankAccountsMapper;
	@Autowired public AddClientBankAccountMapper addClientBankAccountMapper;
	@Autowired public HoldClientBankAccountMapper holdClientBankAccountMapper;
	@Autowired public CheckAccountMapper checkAccountMapper;
	@Autowired public AddAccountMapper addAccountMapper;
	@Autowired public GetClientFundsMapper getClientFundsMapper;
	@Autowired public GetFundPricesMapper getFundPricesMapper;
	@Autowired public FundPriceMapper fundPriceMapper;
	@Autowired public GetFundTransactionsMapper getFundTransactionsMapper;
	@Autowired public GetEportfolioMapper getEportfolioMapper;
	@Autowired public GetReportMapper getReportMapper;
	@Autowired public GetCompanyBankAccountMapper getCompanyBankAccountMapper;
	@Autowired public PlaceOrderMapper placeOrderMapper;
	@Autowired public InjectWithdrawMapper injectWithdrawMapper;
	@Autowired public ValifyAccessTokenMapper valifyAccessTokenMapper;
	@Autowired public ValifyFacialImageMapper valifyFacialImageMapper;
	@Autowired public ValifyIdMapper valifyIdMapper;
	@Autowired public ValifyPassportIdMapper valifyPassportIdMapper;
	@Autowired public PaytabsInitiatePaymentMapper paytabsInitiatePaymentMapper;


	public B getData(RestMapper<BaseInput, BaseOutput, REQ, RES, B> restMapper,B baseBusinessEntity,String param) throws IntegrationException
	{
		return restMapper.wrapAdvancedBaseBusinessEntity(false, baseBusinessEntity, param,null).getData();
	}	
	public List <B> getDataList(RestMapper<BaseInput, BaseOutput, REQ, RES, B> restMapper,B baseBusinessEntity,String param) throws IntegrationException
	{
		return restMapper.wrapAdvancedBaseBusinessEntity(true, baseBusinessEntity, param,null).getDataList();
	}
	
	public List<B> getDataList(RestMapper<BaseInput, BaseOutput, REQ, RES, B> restMapper,B baseBusinessEntity,boolean mulptipleInvocations,List<String>params) throws IntegrationException
	{
		List<B> data=new ArrayList<B>();
		for(String param:params)
		{
			WrapperBusinessEntity<B> wrapperBusinessEntity=restMapper.wrapAdvancedBaseBusinessEntity(true, baseBusinessEntity, param,null);
			if(wrapperBusinessEntity!=null)
			data.addAll(wrapperBusinessEntity.getDataList());
		}
		return data;
	}
	
	public void loopConsumption(RestMapper<BaseInput, BaseOutput, REQ, RES, B> restMapper,List<B> baseBusinessEntities) throws IntegrationException
	{
		for(B baseBusinessEntity:baseBusinessEntities)
		{
			restMapper.wrapAdvancedBaseBusinessEntity(false,baseBusinessEntity, null,null);
		}
	}
	
}
