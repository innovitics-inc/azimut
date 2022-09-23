package innovitics.azimut.utilities.businessutilities;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.funds.BusinessFundTransaction;
@Component
public class FundTransactionSortCompare implements Comparator<BusinessFundTransaction>{

	@Override
	public int compare(BusinessFundTransaction o1, BusinessFundTransaction o2) {
		   return o1.getOrderDate().compareTo(o2.getOrderDate());  
		}
	

}
