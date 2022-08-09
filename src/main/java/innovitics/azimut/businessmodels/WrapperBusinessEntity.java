package innovitics.azimut.businessmodels;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import innovitics.azimut.utilities.datautilities.ListUtility;

public class WrapperBusinessEntity <B extends BaseBusinessEntity> {
	@Autowired ListUtility<B> listUtility;
	
	private B data;
	private List<B> dataList;
	
	public B getData() {
		return data;
	}
	public void setData(B data) {
		this.data = data;
	}
	public List<B> getDataList() {
		return dataList;
	}
	public void setDataList(List<B> list) {
		this.dataList = list;
	}
	
	boolean isListPopulated(List<B> dataList)
	{
		return this.listUtility.isListPopulated(dataList);
	}
	
	
}
