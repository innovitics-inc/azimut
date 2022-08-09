package innovitics.azimut.utilities.businessutilities;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.BusinessTransaction;
@Component
public class SortCompare implements Comparator<BusinessTransaction>{

	@Override
	public int compare(BusinessTransaction o1, BusinessTransaction o2) {
		   return o1.getTrxDate().compareTo(o2.getTrxDate());  
	}

}
