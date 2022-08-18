package innovitics.azimut.utilities.mapping.kyc;

import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.valify.BusinessValify;
import innovitics.azimut.models.kyc.Valify;
import innovitics.azimut.utilities.mapping.Mapper;
@Component
public class ValifyMapper extends Mapper<Valify, BusinessValify> {

	@Override
	protected Valify convertBusinessUnitToBasicUnit(BusinessValify t, boolean save) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BusinessValify convertBasicUnitToBusinessUnit(Valify s) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	protected BusinessValify convertBusinessUnitToBusinessUnit(BusinessValify source, BusinessValify destination) {
		
		
		destination.setFirstName(source.getFirstName());
		destination.setFullName(source.getFullName());
		destination.setStreet(source.getStreet());
		destination.setArea(source.getArea());
		destination.setFrontNid(source.getFrontNid());
		destination.setSerialNumber(source.getSerialNumber());
		destination.setBackNid(source.getBackNid());
		destination.setReleaseDate(source.getReleaseDate());
		destination.setGender(source.getGender());
		destination.setMaritalStatus(source.getMaritalStatus());
		destination.setProfession(source.getProfession());
		destination.setReligion(source.getReligion());
		destination.setHusbandName(source.getHusbandName());
		destination.setDateOfBirth(source.getDateOfBirth());
		destination.setExpiryDate(source.getExpirationDate());
		
		destination.setName(source.getName());
		destination.setSurname(source.getSurname());
		destination.setPassportNumber(source.getPassportNumber());
		destination.setExpirationDate(source.getExpirationDate());
		destination.setDateOfBirth(source.getDateOfBirth());
		destination.setSex(source.getSex());
		destination.setNationality(source.getNationality());
		destination.setValidity(source.getValidity());
		
		
		destination.setImagesSimilar(source.getImagesSimilar());
		destination.setConfidence(source.getConfidence());
		
		return destination;
	}

	@Override
	protected BusinessValify convertBasicUnitToBusinessUnit(Valify s, String language) {
		// TODO Auto-generated method stub
		return null;
	}

}
