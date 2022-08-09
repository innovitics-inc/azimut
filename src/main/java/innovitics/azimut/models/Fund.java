package innovitics.azimut.models;

import java.util.Date;

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
@Table(name="funds")
public class Fund extends BaseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id;
	private String name;
	private String files;
	private String objective;	
	private String RIC;
	private String bbg;
	private String subscription;	
	private String redemption;
	private Date date;
	private String logo;
	private String entitiesForm;
	private int order;
	private String filesAr;	
	private String slug;	
	private String pricingFreq;
	private Long teacomputerId; 
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getRIC() {
		return RIC;
	}

	public void setRIC(String rIC) {
		RIC = rIC;
	}

	public String getBbg() {
		return bbg;
	}

	public void setBbg(String bbg) {
		this.bbg = bbg;
	}

	public String getSubscription() {
		return subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	public String getRedemption() {
		return redemption;
	}

	public void setRedemption(String redemption) {
		this.redemption = redemption;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getEntitiesForm() {
		return entitiesForm;
	}

	public void setEntitiesForm(String entitiesForm) {
		this.entitiesForm = entitiesForm;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getFilesAr() {
		return filesAr;
	}

	public void setFilesAr(String filesAr) {
		this.filesAr = filesAr;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getPricingFreq() {
		return pricingFreq;
	}

	public void setPricingFreq(String pricingFreq) {
		this.pricingFreq = pricingFreq;
	}
	public Long getTeacomputerId() {
		return teacomputerId;
	}
	public void setTeacomputerId(Long teacomputerId) {
		this.teacomputerId = teacomputerId;
	}
	@Override
	public String toString() {
		return "Fund [id=" + id + ", name=" + name + ", files=" + files + ", objective=" + objective + ", RIC=" + RIC
				+ ", bbg=" + bbg + ", subscription=" + subscription + ", redemption=" + redemption + ", date=" + date
				+ ", logo=" + logo + ", entitiesForm=" + entitiesForm + ", order=" + order + ", filesAr=" + filesAr
				+ ", slug=" + slug + ", pricingFreq=" + pricingFreq + ", teacomputerId=" + teacomputerId + "]";
	}


	
}
