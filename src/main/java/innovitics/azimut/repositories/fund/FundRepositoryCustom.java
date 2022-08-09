package innovitics.azimut.repositories.fund;

import java.util.List;

import innovitics.azimut.models.Fund;

public interface FundRepositoryCustom {

	
	List<Fund> getFundsByTeacomputerId(Long teacomputerIds);
	
	List<Fund> getFundsWithoutNavs();
	
}
