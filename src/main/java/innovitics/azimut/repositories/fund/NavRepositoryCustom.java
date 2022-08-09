package innovitics.azimut.repositories.fund;

import java.util.List;

import innovitics.azimut.models.Nav;

public interface NavRepositoryCustom {

	List<Nav> getByJoinedTeacomputerIds();
	
}
