package innovitics.azimut.utilities.datautilities;

import java.util.List;
import java.util.Map;

public class PaginatedEntity<T> {

	List<T> dataList;
	List<?> pageList;
	Integer currentPage;
	Integer nextPage;
	Long numberOfItems;
	Integer numberOfPages;
	Integer lastPage;
	Integer pageSize;
	Boolean hasPrevious;
	Boolean hasNext;
	Map<String,List<T>> namedList;
	
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	
	public Integer getNextPage() {
		return nextPage;
	}
	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}
	public Long getNumberOfItems() {
		return numberOfItems;
	}
	public void setNumberOfItems(Long numberOfItems) {
		this.numberOfItems = numberOfItems;
	}
	public Integer getNumberOfPages() {
		return numberOfPages;
	}
	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Boolean getHasPrevious() {
		return hasPrevious;
	}
	public void setHasPrevious(Boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}
	public Boolean getHasNext() {
		return hasNext;
	}
	public void setHasNext(Boolean hasNext) {
		this.hasNext = hasNext;
	}

	public Map<String, List<T>> getNamedList() {
		return namedList;
	}
	public void setNamedList(Map<String, List<T>> namedList) {
		this.namedList = namedList;
	}
	public List<?> getPageList() {
		return pageList;
	}
	public void setPageList(List<?> pageList) {
		this.pageList = pageList;
	}
	public Integer getLastPage() {
		return lastPage;
	}
	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}
	@Override
	public String toString() {
		return "PaginatedEntity [dataList=" + dataList + ", currentPage=" + currentPage + ", numberOfItems="
				+ numberOfItems + ", numberOfPages=" + numberOfPages + ", pageSize=" + pageSize + ", hasPrevious="
				+ hasPrevious + ", hasNext=" + hasNext + ", namedList=" + namedList + "]";
	}


	
	
	
}
