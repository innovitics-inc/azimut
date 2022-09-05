package innovitics.azimut.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.Fund;
import innovitics.azimut.models.azimutdetails.FundPrice;
import innovitics.azimut.repositories.fund.FundPriceDynamicRepository;
import innovitics.azimut.repositories.fund.FundRepository;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.FundPriceChildSpecification;
@Service
public class FundService extends AbstractService<Fund,Long>{

	@Autowired FundRepository fundRepository;
	
	@Autowired FundPriceDynamicRepository fundPriceDynamicRepository;
	
	@Autowired FundPriceChildSpecification fundPriceChildSpecification;
	
	public List<Fund> getAllFunds()
	{
		return this.fundRepository.findAll();
	}
	
	public List<Fund> getFundsWithoutNavs()
	{
		return this.fundRepository.getFundsWithoutNavs();
	}

	public List<FundPrice> getAllFundPrices()
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();

		Long[] teacomputerIds=new Long[] {110l,111l,113l};
		
		searchCriteriaList.add(new SearchCriteria("teacomputerId", this.arrayUtility.generateObjectListFromObjectArray(teacomputerIds),SearchOperation.IN, null));
		
		return this.fundPriceDynamicRepository.findAll(this.fundPriceChildSpecification.findByCriteria(searchCriteriaList));
	}
	
	public List<FundPrice> getAllRelevantFundPrices(Long [] teacomputerFundIds)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();

		//Long[] teacomputerIds=new Long[] {110l,111l,113l};
		
		searchCriteriaList.add(new SearchCriteria("teacomputerId", this.arrayUtility.generateObjectListFromObjectArray(teacomputerFundIds),SearchOperation.IN, null));
		
		return this.fundPriceDynamicRepository.findAll(this.fundPriceChildSpecification.findByCriteria(searchCriteriaList));
	}
}


