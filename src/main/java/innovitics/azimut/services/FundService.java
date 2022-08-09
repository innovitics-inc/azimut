package innovitics.azimut.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.Fund;
import innovitics.azimut.repositories.fund.FundRepository;
@Service
public class FundService extends AbstractService<Fund,Long>{

	@Autowired FundRepository fundRepository;
	public List<Fund> getAllFunds()
	{
		return this.fundRepository.findAll();
	}
	
	public List<Fund> getFundsWithoutNavs()
	{
		return this.fundRepository.getFundsWithoutNavs();
	}
	
}
