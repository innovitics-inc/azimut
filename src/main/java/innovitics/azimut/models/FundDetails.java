package innovitics.azimut.models;


import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="funds_details")
public class FundDetails extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	private	String key;
	private String value;
	
	@ManyToOne
	@JoinColumn(name="fund_id",foreignKey = @javax.persistence.ForeignKey(name="none",value = ConstraintMode.NO_CONSTRAINT))
	private Fund fund;

	
	private Boolean isDisplay;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public Fund getFund() {
		return fund;
	}


	public void setFund(Fund fund) {
		this.fund = fund;
	}


	public Boolean getIsDisplay() {
		return isDisplay;
	}


	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}


	
}
