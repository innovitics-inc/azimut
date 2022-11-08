package innovitics.azimut.businessutilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.BusinessTransaction;
import innovitics.azimut.businessmodels.funds.BusinessClientFund;
import innovitics.azimut.businessmodels.funds.BusinessFundPrice;
import innovitics.azimut.businessmodels.funds.BusinessFundTransaction;
import innovitics.azimut.businessmodels.user.BusinessAzimutClient;
import innovitics.azimut.businessmodels.user.BusinessClientCashBalance;
import innovitics.azimut.businessmodels.user.BusinessUser;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.rest.mappers.RestManager;
import innovitics.azimut.services.FundService;
import innovitics.azimut.utilities.ParentUtility;
import innovitics.azimut.utilities.businessutilities.CashTransactionSortCompare;
import innovitics.azimut.utilities.businessutilities.FundTransactionSortCompare;
import innovitics.azimut.utilities.businessutilities.Sorting;
import innovitics.azimut.utilities.crosslayerenums.CurrencyType;
import innovitics.azimut.utilities.datautilities.ArrayUtility;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.NumberUtility;
import innovitics.azimut.utilities.datautilities.PaginatedEntity;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
import innovitics.azimut.utilities.mapping.FundMapper;
import innovitics.azimut.utilities.mapping.FundPriceMapper;
import innovitics.azimut.validations.Validation;
import innovitics.azimut.validations.validators.azimutclient.GetBalanceAndTransactions;
@Component
public class AzimutClientDetailsUtility extends ParentUtility 
{
	@Autowired Validation<?> validation;
	@Autowired ListUtility<BusinessTransaction> businessTransactionListUtility;
	@Autowired CashTransactionSortCompare cashTransactionSortCompare;
	@Autowired GetBalanceAndTransactions getBalanceAndTransactions;	
	@Autowired ListUtility<BusinessClientFund> clientFundListUtility ;
	@Autowired ListUtility<BusinessClientCashBalance> clientCashBalanceListUtility ;
	@Autowired FundService fundService;
	@Autowired FundPriceMapper fundPriceMapper;
	@Autowired FundMapper fundMapper;
	@Autowired ListUtility<BusinessFundPrice> fundPricesListUtility;
	@Autowired FundTransactionSortCompare fundTransactionSortCompare;


	
		  
}
