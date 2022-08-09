package innovitics.azimut.businessutilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SearchFilter {
	
	public final static Logger logger = LogManager.getLogger(SearchFilter.class.getName());
	private String key;
	private Object[] values;
	private String value;
	private Boolean search;
	private String operation;
	private String parentColumn;

	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public Object[] getValues() {
		return values;
	}


	public void setValues(Object[] values) {
		this.values = values;
	}


	
	
	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public Boolean getSearch() {
		return search;
	}


	public void setSearch(Boolean search) {
		this.search = search;
	}



	public String getParentColumn() {
		return parentColumn;
	}



	public void setParentColumn(String parentColumn) {
		this.parentColumn = parentColumn;
	}


	public String getOperation() {
		return operation;
	}


	public void setOperation(String operation) {
		this.operation = operation;
	}


	public SearchFilter(String key, String operation ,String value,Object[] values,
			String parentColumn,Boolean search) {
		super();
		this.key = key;
		this.values = values;
		this.value = value;
		this.search = search;
		this.operation = operation;
		this.parentColumn = parentColumn;
	}




	
	
}
