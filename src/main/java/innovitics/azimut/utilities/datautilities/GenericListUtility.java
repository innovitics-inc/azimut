package innovitics.azimut.utilities.datautilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class GenericListUtility {

	public static boolean isListPopulated(List<Object> dataList)
	{
		return (dataList!=null&&!dataList.isEmpty());
	}
	
	public static boolean isSetPopulated(Set<Object> dataList)
	{
		return (dataList!=null&&!dataList.isEmpty());
	}
	
	
	public static boolean isListEmptyOrNull(List<Object> dataList)
	{
		return (dataList==null||dataList.isEmpty());
	}
	
	public static boolean isPaginatedListPopulated(PaginatedEntity<Object> paginatedList)
	{
		return paginatedList!=null&&isListPopulated(paginatedList.getDataList());
	}
	
	public static boolean isPaginatedListEmpty(PaginatedEntity<Object> paginatedList)
	{
		return paginatedList==null||isListEmptyOrNull(paginatedList.getDataList());
	}
	
	List<Object> convertSetToList(Set<Object> set)
	{
		List<Object> dataList=new ArrayList<Object>();
		  for (Object t : set)
	            dataList.add(t);
	        return dataList;
	}
}
