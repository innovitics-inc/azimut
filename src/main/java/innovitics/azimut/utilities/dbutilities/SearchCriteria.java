package innovitics.azimut.utilities.dbutilities;

import java.util.List;

public class SearchCriteria {
    private String key;
    private Object value;
    private List<Object> valueList;
    private SearchOperation operation;
    private String joiningColumn;
    
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public SearchOperation getOperation() {
		return operation;
	}
	public void setOperation(SearchOperation operation) {
		this.operation = operation;
	}
	public String getJoiningColumn() {
		return joiningColumn;
	}
	public void setJoiningColumn(String joiningColumn) {
		this.joiningColumn = joiningColumn;
	}
		
	public List<Object> getValueList() {
		return valueList;
	}
	public void setValueList(List<Object> valueList) {
		this.valueList = valueList;
	}
	
	
	
	public SearchCriteria(String key, List<Object> valueList, SearchOperation operation, String joiningColumn) {
		super();
		this.key = key;
		this.valueList = valueList;
		this.operation = operation;
		this.joiningColumn = joiningColumn;
	}
	public SearchCriteria(String key, Object value, SearchOperation operation, String joiningColumn) {
		super();
		this.key = key;
		this.value = value;
		this.operation = operation;
		this.joiningColumn = joiningColumn;
	}
	
	public SearchCriteria() {
	}
	@Override
	public String toString() {
		return "SearchCriteria [key=" + key + ", value=" + value + ", valueList=" + valueList + ", operation="
				+ operation + ", joiningColumn=" + joiningColumn + "]";
	}
    
    
    
    
}