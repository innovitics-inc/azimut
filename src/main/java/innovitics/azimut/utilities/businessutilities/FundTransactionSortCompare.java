package innovitics.azimut.utilities.businessutilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.funds.BusinessFundTransaction;
@Component
public class FundTransactionSortCompare implements Comparator<BusinessFundTransaction>{
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public int compare(BusinessFundTransaction o1, BusinessFundTransaction o2) {
		try {
			return simpleDateFormat.parse(o1.getOrderDate()).compareTo(simpleDateFormat.parse(o2.getOrderDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		}
	

}
