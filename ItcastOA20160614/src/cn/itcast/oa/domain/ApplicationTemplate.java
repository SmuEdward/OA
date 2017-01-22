package cn.itcast.oa.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 申请模板
 * @author Administrator
 *
 */
@Entity
@Table(name="itcast_applicationTemplate")
public class ApplicationTemplate {
	
	private Long id;
	private String name;
	private String processDefinitionKey;
	private String path;
	private Set<Application> applications = new HashSet<Application>();
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	
	@OneToMany(fetch=FetchType.EAGER)
	public Set<Application> getApplications() {
		return applications;
	}
	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}
	
	
}
