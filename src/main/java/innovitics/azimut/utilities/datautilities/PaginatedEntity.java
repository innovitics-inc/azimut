package innovitics.azimut.utilities.datautilities;

import java.util.List;
import java.util.Map;

public class PaginatedEntity<T> {

	List<T> dataList;
	List<?> genericDataList;
	Integer currentPage;
	Long numberOfItems;
	Integer numberOfPages;
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
	public List<?> getGenericDataList() {
		return genericDataList;
	}
	public void setGenericDataList(List<?> genericDataList) {
		this.genericDataList = genericDataList;
	}
	@Override
	public String toString() {
		return "PaginatedEntity [dataList=" + dataList + ", currentPage=" + currentPage + ", numberOfItems="
				+ numberOfItems + ", numberOfPages=" + numberOfPages + ", pageSize=" + pageSize + ", hasPrevious="
				+ hasPrevious + ", hasNext=" + hasNext + ", namedList=" + namedList + "]";
	}


	
	
	
}
