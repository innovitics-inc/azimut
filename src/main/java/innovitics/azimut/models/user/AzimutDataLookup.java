package innovitics.azimut.models.user;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import innovitics.azimut.models.BaseEntity;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="azimut_data_lookup")
@NamedEntityGraph(name = "AzimutDataLookup.details", 
attributeNodes = { @NamedAttributeNode("entity"),@NamedAttributeNode("entityKey"),@NamedAttributeNode("entityValue") })

public class AzimutDataLookup extends BaseEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long entity;
	private String entityKey;
	private String entityValue;
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="entity_id")
	private AzimutEntity parent;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEntity() {
		return entity;
	}
	public void setEntity(Long entity) {
		this.entity = entity;
	}
	public String getEntityKey() {
		return entityKey;
	}
	public void setEntityKey(String entityKey) {
		this.entityKey = entityKey;
	}
	public String getEntityValue() {
		return entityValue;
	}
	public void setEntityValue(String entityValue) {
		this.entityValue = entityValue;
	}
	
	
	public AzimutEntity getParent() {
		return parent;
	}
	public void setParent(AzimutEntity parent) {
		this.parent = parent;
	}
	

}
