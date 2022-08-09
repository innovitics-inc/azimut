package innovitics.azimut.controllers;

import java.util.List;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import innovitics.azimut.utilities.datautilities.PaginatedEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseGenericResponse<T> {
	protected Integer status;
	protected String message;
	

	@JsonInclude(value = Include.NON_EMPTY)
	Map<String,List<T>> result = new HashMap<String,List<T>>();
	
	@JsonInclude(value = Include.NON_EMPTY)
	Map<String,PaginatedEntity<T>> pages = new HashMap<String,PaginatedEntity<T>>();
	
	@JsonInclude(value = Include.NON_EMPTY)
	Map<String,T> object = new HashMap<String,T>();
	
	
	@JsonInclude(value = Include.NON_EMPTY)
	Object response ;
		
	@JsonInclude(value = Include.NON_EMPTY)
	Integer currentPage;
	@JsonInclude(value = Include.NON_EMPTY)
	Long numberOfItems;
	@JsonInclude(value = Include.NON_EMPTY)
	Integer numberOfPages;
	@JsonInclude(value = Include.NON_EMPTY)
	Integer pageSize;
	@JsonInclude(value = Include.NON_EMPTY)
	Boolean hasPrevious;
	@JsonInclude(value = Include.NON_EMPTY)
	Boolean hasNext;
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


	public Map<String, List<T>> getResult() {
		return result;
	}
	public void setResult(Map<String, List<T>> result) {
		this.result = result;
	}
	public Map<String, T> getObject() {
		return object;
	}
	public void setObject(Map<String, T> object) {
		this.object = object;
	}
	public Map<String, PaginatedEntity<T>> getPages() {
		return pages;
	}
	public void setPages(Map<String, PaginatedEntity<T>> pages) {
		this.pages = pages;
	}

	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
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
	@Override
	public String toString() {
		return "BaseGenericResponse [status=" + status + ", message=" + message + ", result=" + result + ", pages=" + pages + ", object=" + object + "]";
	}




}
