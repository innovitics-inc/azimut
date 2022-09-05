package innovitics.azimut.utilities.datautilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import innovitics.azimut.utilities.ParentUtility;
@Component
public class ListUtility<T> extends ParentUtility{
	

	public boolean isListPopulated(List<T> dataList)
	{
		return (dataList!=null&&!dataList.isEmpty()&&dataList.size()>0);
	}
	
	public boolean isSetPopulated(Set<T> dataList)
	{
		return (dataList!=null&&!dataList.isEmpty());
	}
	
	
	public boolean isListEmptyOrNull(List<T> dataList)
	{
		return (dataList==null||dataList.isEmpty());
	}
	
	public boolean isPaginatedListPopulated(PaginatedEntity<T> paginatedList)
	{
		return paginatedList!=null&&this.isListPopulated(paginatedList.getDataList());
	}
	
	public boolean isPaginatedListEmpty(PaginatedEntity<T> paginatedList)
	{
		return paginatedList==null||this.isListEmptyOrNull(paginatedList.getDataList());
	}
	
	List<T> convertSetToList(Set<T> set)
	{
		List<T> dataList=new ArrayList<T>();
		  for (T t : set)
	            dataList.add(t);
	        return dataList;
	}
}
