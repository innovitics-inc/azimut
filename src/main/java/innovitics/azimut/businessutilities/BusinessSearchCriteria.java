package innovitics.azimut.businessutilities;

import java.util.Arrays;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import innovitics.azimut.utilities.datautilities.StringUtility;


public class BusinessSearchCriteria  {

	
	private Integer pageSize;
	private Integer pageNumber;
	private Boolean asc;
	private String sortingParam;
	private SearchFilter[] searchesAndFilters;
	
	
	
	
/*	public String generateConditionsString()
	{
		StringBuffer conditions=new StringBuffer();
		conditions.append(StringUtility.addLeadingAndTrailingSpaces("where 1=1"));
		if(this.getSearchesAndFilters()!=null&&this.getSearchesAndFilters().length>0)
		{
			  for(int i=0;i<this.getSearchesAndFilters().length;i++) 
			  {
			  
				  conditions.append(StringUtility.addLeadingAndTrailingSpaces("and"));
				  conditions.append(StringUtility.addLeadingAndTrailingSpaces(this.
				  getSearchesAndFilters()[i].generateCondition())); 
			  
			  }
			
			
		}
		
		
		return conditions.toString();
	}
*/	
	
	public PageRequest generatePaginationCriteria()
	{
		if(StringUtility.isStringPopulated(this.getSortingParam()))
		{
			if(this.getAsc()!=null)
			{
				if (this.getAsc()) 
				{
					Sort sort = Sort.by(this.getSortingParam()).ascending();
					return PageRequest.of(this.getPageNumber(), this.getPageSize(), sort);
				} 
				else 
				{
					Sort sort = Sort.by(this.getSortingParam()).descending();
					return PageRequest.of(this.getPageNumber(), this.getPageSize(), sort);
				}
			}
		}
		return PageRequest.of(this.getPageNumber(), this.getPageSize());	
	}


	public Integer getPageSize() {
		return pageSize;
	}




	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}




	public Integer getPageNumber() {
		return pageNumber;
	}




	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}




	public Boolean getAsc() {
		return asc;
	}




	public void setAsc(Boolean asc) {
		this.asc = asc;
	}



	public String getSortingParam() {
		return sortingParam;
	}




	public void setSortingParam(String sortingParam) {
		this.sortingParam = sortingParam;
	}


	public SearchFilter[] getSearchesAndFilters() {
		return searchesAndFilters;
	}


	public void setSearchesAndFilters(SearchFilter[] searchesAndFilters) {
		this.searchesAndFilters = searchesAndFilters;
	}





	
}
