package innovitics.azimut.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.Nav;
import innovitics.azimut.repositories.fund.NavRepository;
@Service
public class NavService extends  AbstractService<Nav, String>{
	@Autowired NavRepository navRepository;

	public List<Nav> batchInsert(List <Nav> navs)
	{
		return this.navRepository.saveAll(navs);
	}
	
	public List<Nav> getByJoinedTeacomputerIds()
	{
		return this.navRepository.getByJoinedTeacomputerIds();
	}
	
}
