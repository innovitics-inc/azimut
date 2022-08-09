package innovitics.azimut.repositories.fund;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import innovitics.azimut.models.Nav;
import innovitics.azimut.models.user.User;
import innovitics.azimut.repositories.AbstractRepository;
import innovitics.azimut.utilities.datautilities.DateUtility;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
public class NavRepositoryImpl extends AbstractRepository<Nav>  implements NavRepositoryCustom{

	@Override
	public List<Nav> getByJoinedTeacomputerIds() {
		return (List<Nav>)this.generateQuery("select n.* from navs2 n  inner join"
				+ "(select fund_id,teacomputer_id ,max(created_at) max_date from navs2 group by fund_id,teacomputer_id)x"
				+ " on n.fund_id=x.fund_id"
				+ " and n.teacomputer_id=x.teacomputer_id"
				+ " and n.created_at = x.max_date"
				+ " order by fund_id ,n.created_at", Nav.class, null).getResultList();
	}

}
