package innovitics.azimut.utilities.dbutilities;

import java.util.List;

import org.springframework.data.domain.PageRequest;

public class DatabaseConditions {

	List<SearchCriteria> searchCriteria;
	
	PageRequest pageRequest;

	public List<SearchCriteria> getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(List<SearchCriteria> searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public PageRequest getPageRequest() {
		return pageRequest;
	}

	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}
	
	
	
	
}
