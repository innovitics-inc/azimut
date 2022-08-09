package innovitics.azimut.businessmodels.kyc;

import java.util.ArrayList;
import java.util.List;

import innovitics.azimut.businessmodels.BaseBusinessEntity;

public class BusinessUserType extends BaseBusinessEntity{

	private Long id;
	private String idType;
	private int noOfKYCPages;
	private int valifyImageCount;
	private List<BusinessKYCPage> pages= new ArrayList<BusinessKYCPage>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public int getNoOfKYCPages() {
		return noOfKYCPages;
	}
	public void setNoOfKYCPages(int noOfKYCPages) {
		this.noOfKYCPages = noOfKYCPages;
	}
	public int getValifyImageCount() {
		return valifyImageCount;
	}
	public void setValifyImageCount(int valifyImageCount) {
		this.valifyImageCount = valifyImageCount;
	}
	public List<BusinessKYCPage> getPages() {
		return pages;
	}
	public void setPages(List<BusinessKYCPage> pages) {
		this.pages = pages;
	}
	
	
	
	
	
}
