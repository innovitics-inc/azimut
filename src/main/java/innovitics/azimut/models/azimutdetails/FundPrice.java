package innovitics.azimut.models.azimutdetails;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import innovitics.azimut.models.BaseEntity;

@Entity
@Table(name = "funds_info")
@Immutable
public class FundPrice extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1403699721290674093L;
    @Id
	@Column(name="fund_id")
	private Long fundId;
	@Column(name="logo")
	private String logo;
	@Column(name="nav")
	private String nav;
	@Column(name="latest_date")
	private String latestDate;
	@Column(name="teacomputer_id")
	private Long teacomputerId;
	
	public Long getFundId() {
		return fundId;
	}
	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getNav() {
		return nav;
	}
	public void setNav(String nav) {
		this.nav = nav;
	}
	public String getLatestDate() {
		return latestDate;
	}
	public void setLatestDate(String latestDate) {
		this.latestDate = latestDate;
	}
	public Long getTeacomputerId() {
		return teacomputerId;
	}
	public void setTeacomputerId(Long teacomputerId) {
		this.teacomputerId = teacomputerId;
	}
	@Override
	public String toString() {
		return "FundPrice [fundId=" + fundId + ", logo=" + logo + ", nav=" + nav + ", latestDate=" + latestDate
				+ ", teacomputerId=" + teacomputerId + "]";
	}
	
	
	
}
