package innovitics.azimut.repositories.fund;

import java.util.List;

import innovitics.azimut.models.Fund;
import innovitics.azimut.models.Nav;
import innovitics.azimut.repositories.AbstractRepository;

public class FundRepositoryImpl extends AbstractRepository<Fund>  implements FundRepositoryCustom{

	@Override
	public List<Fund> getFundsByTeacomputerId(Long teacomputerIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fund> getFundsWithoutNavs() {
		return this.generateQuery("select * from funds f  WHERE not EXISTS (SELECT * from navs2 n where n.fund_id=f.id)", Fund.class, null).getResultList();
	}

}
