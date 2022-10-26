package innovitics.azimut.utilities.businessutilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.BusinessTransaction;
@Component
public class CashTransactionSortCompare implements Comparator<BusinessTransaction>{
	
	//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	@Override
	public int compare(BusinessTransaction o1, BusinessTransaction o2) {			
			try {
				return simpleDateFormat.parse(o1.getTrxDate()).compareTo(simpleDateFormat.parse(o2.getTrxDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
			
	}

}
