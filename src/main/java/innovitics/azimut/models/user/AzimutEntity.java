package innovitics.azimut.models.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import innovitics.azimut.models.BaseEntity;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="azimut_entities")

@NamedEntityGraph(name = "AzimutEntity.details",
attributeNodes = { @NamedAttributeNode("details")},
subgraphs = {@NamedSubgraph(name = "AzimutDataLookup.details", attributeNodes = { @NamedAttributeNode("entity"),@NamedAttributeNode("entityKey"),@NamedAttributeNode("entityValue") })})
public class AzimutEntity extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String entity;
	private Long entityType;
	@OneToMany(mappedBy="parent")
	@Fetch(FetchMode.JOIN)
	private List<AzimutDataLookup> details;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public Long getEntityType() {
		return entityType;
	}
	public void setEntityType(Long entityType) {
		this.entityType = entityType;
	}
	public List<AzimutDataLookup> getDetails() {
		return details;
	}
	public void setDetails(List<AzimutDataLookup> details) {
		this.details = details;
	}
	
	

}
