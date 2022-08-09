package innovitics.azimut.businessmodels.user;

import java.util.List;

import innovitics.azimut.businessmodels.BaseBusinessEntity;
import innovitics.azimut.models.teacomputers.City;
import innovitics.azimut.models.teacomputers.Country;
import innovitics.azimut.models.teacomputers.Nationality;

public class BusinessAzimutDataLookup extends BaseBusinessEntity{


	private String entityType;
	private Long entityTypeId;
	
	private List<Country> countries;
	private List<City> cities;
	private List<Nationality> nationalities;
	
	
	
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public Long getEntityTypeId() {
		return entityTypeId;
	}
	public void setEntityTypeId(Long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public List<Country> getCountries() {
		return countries;
	}
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	public List<City> getCities() {
		return cities;
	}
	public void setCities(List<City> cities) {
		this.cities = cities;
	}
	public List<Nationality> getNationalities() {
		return nationalities;
	}
	public void setNationalities(List<Nationality> nationalities) {
		this.nationalities = nationalities;
	}
	
	
	
	
	
	
}
