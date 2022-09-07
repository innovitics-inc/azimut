package innovitics.azimut.utilities.mapping;

import org.springframework.stereotype.Component;

import innovitics.azimut.businessmodels.funds.BusinessFundPrice;
import innovitics.azimut.models.azimutdetails.FundPrice;
import innovitics.azimut.utilities.datautilities.StringUtility;
@Component
public class FundPriceMapper extends Mapper<FundPrice,BusinessFundPrice >{

	@Override
	protected FundPrice convertBusinessUnitToBasicUnit(BusinessFundPrice businessFundPrice, boolean save) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BusinessFundPrice convertBasicUnitToBusinessUnit(FundPrice fundPrice) {
		BusinessFundPrice businessFundPrice=new BusinessFundPrice();
		
		if(fundPrice.getFundId()!=null)
		businessFundPrice.setFundId(fundPrice.getFundId());
		if(fundPrice.getNav()!=null)
			
		{
			businessFundPrice.setNav(StringUtility.isStringPopulated(fundPrice.getNav())?Double.valueOf(fundPrice.getNav()):null);
			businessFundPrice.setTradePrice(StringUtility.isStringPopulated(fundPrice.getNav())?Double.valueOf(fundPrice.getNav()):null);
		}
		
		if(fundPrice.getLatestDate()!=null)
		businessFundPrice.setPriceDate(fundPrice.getLatestDate());
		if(fundPrice.getTeacomputerId()!=null)
		businessFundPrice.setTeacomputerId(fundPrice.getTeacomputerId());
		if(fundPrice.getLogo()!=null)
		businessFundPrice.setLogo(this.configProperties.getAzimutFundImagesUrl()+"/"+fundPrice.getLogo());
		
		
		return businessFundPrice;
	}

	@Override
	protected BusinessFundPrice convertBasicUnitToBusinessUnit(FundPrice fundPrice, String language) {
		// TODO Auto-generated method stub
		return null;
	}

}
