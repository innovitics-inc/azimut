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
	
	 public PaginatedEntity<T>  getPaginatedList(List<T> list,int currentPage, int pageSize)
	 { 
		 PaginatedEntity<T> paginatedEntity=new PaginatedEntity<>(); 
		boolean hasPrevious = (currentPage>1);
		 paginatedEntity.setCurrentPage(currentPage);
		 currentPage=currentPage-1;
		 if(this.isListPopulated(list))
			{
				paginatedEntity.setNumberOfItems(Long.valueOf(list.size()));
				int start = Math.min(list.size(), Math.abs(currentPage * pageSize));
				list.subList(0, start).clear();
				int size = list.size();
				int end = Math.min(pageSize, size);
				list.subList(end, size).clear();
				boolean hasNext = (end < size);
				paginatedEntity.setPageSize(pageSize);
				paginatedEntity.setPageList(list);
				paginatedEntity.setHasNext(hasNext);
				paginatedEntity.setHasPrevious(hasPrevious);
			}		 
		 return paginatedEntity;

	 }
}
