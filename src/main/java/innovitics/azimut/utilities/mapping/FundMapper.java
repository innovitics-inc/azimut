package innovitics.azimut.utilities.mapping;

import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.funds.BusinessFundPrice;
import innovitics.azimut.models.Fund;
@Component
public class FundMapper extends Mapper<Fund,BusinessFundPrice >{

	@Override
	protected Fund convertBusinessUnitToBasicUnit(BusinessFundPrice t, boolean save) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BusinessFundPrice convertBasicUnitToBusinessUnit(Fund fund) {
		BusinessFundPrice businessFundPrice=new BusinessFundPrice();
		
		if(fund.getId()!=null)
		businessFundPrice.setFundId(fund.getId());		
		if(fund.getTeacomputerId()!=null)
		businessFundPrice.setTeacomputerId(fund.getTeacomputerId());
		if(fund.getLogo()!=null)
		businessFundPrice.setLogo(this.configProperties.getAzimutFundImagesUrl()+"/"+fund.getLogo());
		
		
		return businessFundPrice;
	}

	@Override
	protected BusinessFundPrice convertBasicUnitToBusinessUnit(Fund fund, String language) {
		// TODO Auto-generated method stub
		return null;
	}

}
