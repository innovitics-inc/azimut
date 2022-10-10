package innovitics.azimut.businessservices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import innovitics.azimut.businessmodels.funds.BusinessFundPrice;
import innovitics.azimut.exceptions.BusinessException;
import innovitics.azimut.exceptions.IntegrationException;
import innovitics.azimut.models.Fund;
import innovitics.azimut.models.Nav;
import innovitics.azimut.rest.mappers.GetFundPricesMapper;
import innovitics.azimut.services.FundService;
import innovitics.azimut.services.NavService;
import innovitics.azimut.utilities.datautilities.DateUtility;
import innovitics.azimut.utilities.datautilities.ListUtility;
import innovitics.azimut.utilities.datautilities.StringUtility;
import innovitics.azimut.utilities.exceptionhandling.ErrorCode;
@Service
public class BusinessFundsService extends AbstractBusinessService<BusinessFundPrice> {
	@Autowired GetFundPricesMapper getFundPricesMapper;
	@Autowired FundService fundService;
	@Autowired NavService navService;	
	@Autowired ListUtility<Nav> navListUtility;
	@Autowired ListUtility<Fund> fundListUtility;
	
	
	
	public List<BusinessFundPrice> updateFundPrices(/*List<BusinessFundPrice> inputBusinessFundPrices*/) throws IntegrationException, BusinessException {
		List<BusinessFundPrice> businessFundPrices = new ArrayList<BusinessFundPrice>();
		List<Fund> fundsWithoutNavs=new ArrayList<Fund>();
		List<Nav> insertedNavs=new ArrayList<Nav>();
		try 
		{
			businessFundPrices = this.getFundPricesMapper.wrapBaseBusinessEntity(true, this.prepareBusinessFundPriceInput(), null).getDataList();
			fundsWithoutNavs=this.fundService.getFundsWithoutNavs();
		
			for(Fund fund:fundsWithoutNavs) 
			{
				this.logger.info("Fund without Nav :::"+fund.toString());
			}			
			insertedNavs=this.checkTeaComputerNavAvailabilityForFundAndUpdatePrices(this.navService.getByJoinedTeacomputerIds(), /*businessFundPrices3*/businessFundPrices,fundsWithoutNavs);
			this.navService.batchInsert(insertedNavs);
		} 
		catch (Exception  exception) 
		{
			if (exception instanceof IntegrationException)
				throw this.exceptionHandler.handleIntegrationExceptionAsBusinessException((IntegrationException) exception, ErrorCode.FAILED_TO_INTEGRATE);
			else
				throw this.handleBusinessException((Exception) exception, ErrorCode.OPERATION_NOT_PERFORMED);
		}			
		return this.convertNavListToBusinessFundPricesList(insertedNavs);
	}

	private BusinessFundPrice prepareBusinessFundPriceInput()
	{
		BusinessFundPrice searchBusinessFundPrice = new BusinessFundPrice();
		searchBusinessFundPrice.setSearchFromDate(DateUtility.getCurrentDayMonthYear());
		searchBusinessFundPrice.setSearchToDate(DateUtility.getCurrentDayMonthYear());
		return searchBusinessFundPrice;
	}
	
	private List<Nav> checkTeaComputerNavAvailabilityForFundAndUpdatePrices(List<Nav> availableNavs,List<BusinessFundPrice> businessFundPrices,List<Fund> fundsWithoutNavs)
	{
		List<Nav> insertedNavs = new ArrayList<Nav>();
		if(listUtility.isListPopulated(businessFundPrices)&&navListUtility.isListPopulated(availableNavs))	
		{
		 for (Nav nav : availableNavs)
		  {
			 for (BusinessFundPrice businessFundPrice : businessFundPrices) {
				this.logger.info("Comparison:::");
				this.logger.info("Business Fund Price:::"+businessFundPrice);
				this.logger.info("Available Nav:::"+nav);
				
				if (nav != null && businessFundPrice != null && nav.getTeacomputerId() != null
						&& businessFundPrice.getFundId() != null
						&& nav.getTeacomputerId().longValue() == businessFundPrice.getFundId().longValue()) {
					if (nav.getNav() != null && businessFundPrice.getNav() != null
							&& nav.getNav().doubleValue() != businessFundPrice.getNav().doubleValue()) {
						this.logger.info("Inserting due to different price.");
						insertedNavs.add(this.generateNavFromNavAndBusinessFund(nav.getFundId(), businessFundPrice));
					}

					else if (nav.getNav() != null && businessFundPrice.getNav() != null
							&& nav.getNav().doubleValue() == businessFundPrice.getNav().doubleValue()) {
						if (nav.getDate() != null && StringUtility.isStringPopulated(businessFundPrice.getPriceDate())
								&& 
								/*DateUtility.areDatesDifferent(nav.getDate(),
										DateUtility.changeStringDateToDate(DateUtility.changeStringDateFormat(businessFundPrice.getPriceDate(),new SimpleDateFormat("dd-MM-yyyy"),new SimpleDateFormat("yyyy-MM-dd"))))*/
							StringUtility.stringsDontMatch(nav.getDate().toString().substring(0,10), DateUtility.changeStringDateFormat(businessFundPrice.getPriceDate(),new SimpleDateFormat("dd-MM-yyyy"),new SimpleDateFormat("yyyy-MM-dd"))))	
							{
							this.logger.info("Inserting due to same price on a different date.");
							insertedNavs.add(this.generateNavFromNavAndBusinessFund(nav.getFundId(), businessFundPrice));
							}

						}
					}
				}
			}
		}
		
		if(fundListUtility.isListPopulated(fundsWithoutNavs)) 
		{
			for (Fund fund:fundsWithoutNavs)
			{
				for(BusinessFundPrice businessFundPrice : businessFundPrices)
				{
					if (fund != null && businessFundPrice != null && fund.getTeacomputerId() != null
						&& businessFundPrice.getFundId() != null
						&& fund.getTeacomputerId().longValue() == businessFundPrice.getFundId().longValue())
					{
						insertedNavs.add(this.generateNavFromNavAndBusinessFund(fund.getId(), businessFundPrice));
					}
				}
			}
		}

		return insertedNavs;
	}

	private Nav generateNavFromNavAndBusinessFund(Long fundId,BusinessFundPrice businessFundPrice)
	{
		
		this.logger.info("Fund ID:::"+fundId);
		this.logger.info("businessFundPrice:::"+businessFundPrice.toString());
		
		Nav insertedNav = new Nav();
		insertedNav.setFundId(fundId.longValue());
		insertedNav.setTeacomputerId(businessFundPrice.getFundId().longValue());
		insertedNav.setDate(DateUtility.changeStringDateToDate(businessFundPrice.getPriceDate()));
		insertedNav.setCreatedAt(new Date());
		insertedNav.setUpdatedAt(new Date());
		insertedNav.setNav(businessFundPrice.getNav());
		this.logger.info("Inserted Nav:::"+insertedNav.toString());
		
		return insertedNav;
	
	}
	private List<BusinessFundPrice> convertNavListToBusinessFundPricesList(List<Nav> navs)
	{
		List<BusinessFundPrice> responseBusinessFundPrices = new ArrayList<BusinessFundPrice>();
		if(navListUtility.isListPopulated(navs))
		{
			for (Nav nav : navs) 
			{
				BusinessFundPrice responseBusinessFundPrice = new BusinessFundPrice();
				responseBusinessFundPrice.setNav(nav.getNav());
				responseBusinessFundPrice.setFundId(nav.getFundId());
				responseBusinessFundPrice.setPriceDate(nav.getCreatedAt().toString());
				responseBusinessFundPrices.add(responseBusinessFundPrice);
			}
		}
		return responseBusinessFundPrices;
	}
	
}