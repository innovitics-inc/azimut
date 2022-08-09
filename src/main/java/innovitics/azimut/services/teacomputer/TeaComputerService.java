package innovitics.azimut.services.teacomputer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.models.teacomputers.City;
import innovitics.azimut.models.teacomputers.Country;
import innovitics.azimut.models.teacomputers.Nationality;
import innovitics.azimut.repositories.teacomputers.CityDynamicRepository;
import innovitics.azimut.repositories.teacomputers.CountryDynamicRepository;
import innovitics.azimut.repositories.teacomputers.NationalityDynamicRepository;
import innovitics.azimut.services.AbstractService;
import innovitics.azimut.utilities.dbutilities.SearchCriteria;
import innovitics.azimut.utilities.dbutilities.SearchOperation;
import innovitics.azimut.utilities.dbutilities.specifications.child.CityChildSpecification;

@Service
public class TeaComputerService extends AbstractService<Country, String> {

	@Autowired CountryDynamicRepository countryDynamicRepository;
	@Autowired CityDynamicRepository cityDynamicRepository;
	@Autowired NationalityDynamicRepository nationalityDynamicRepository;
	@Autowired CityChildSpecification cityChildSpecification;
	
	
	
	public List<Country> saveAllCountries (List<Country> countries)
	{
		return this.countryDynamicRepository.saveAll(countries);
	}
	

	public List<City> saveAllCities (List<City> cities)
	{
		return this.cityDynamicRepository.saveAll(cities);
	}
	

	public List<Nationality> saveAllNationalities (List<Nationality> nationalities)
	{
		return this.nationalityDynamicRepository.saveAll(nationalities);
	}
	
	public List<Country> getAllCountries()
	{
		return this.countryDynamicRepository.findAll();
	}
	public List<City> getAllCities()
	{
		return  this.cityDynamicRepository.findAll();
	}
	
	public List<City> getAllCitiesByCountryId(Long countryId)
	{
		List<SearchCriteria> searchCriteriaList=new ArrayList<SearchCriteria>();
		searchCriteriaList.add(new SearchCriteria("countryId", countryId,SearchOperation.EQUAL, null));
		return this.cityDynamicRepository.findAll(cityChildSpecification.findByCriteria(searchCriteriaList));
	}
	public List<Nationality> getAllNationalities()
	{
		return this.nationalityDynamicRepository.findAll();
	}
}
