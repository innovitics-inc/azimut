package innovitics.azimut.utilities.datautilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import innovitics.azimut.utilities.ParentUtility;

@Component
public class ArrayUtility extends ParentUtility{

	public boolean isArrayPopulated(Object[] array) 
	{
		return (array!=null&&array.length>0);
	}

	public List<Object> generateObjectListFromObjectArray(Object[] array)
	{
		List<Object> list=new ArrayList<Object>();
		
		if(this.isArrayPopulated(array))
		{
			for (int i=0;i<array.length;i++)
			{
				list.add(array[i]);
			}
		}
		return list;
	}
	
	List<Object> convertSetToList(Set<Object> set)
	{
		List<Object> dataList=new ArrayList<Object>();
		  for (Object object : set)
	            dataList.add(object);
	        return dataList;
	}
	
}
