package cn.itcast.oa.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/*
 * 
 * 权限
 */
@Entity
@Table(name="itcast_privilege")
public class Privilege implements java.io.Serializable{
	
	private Long id;
	private String name;
	private String url;
	private Set<Role> roles = new HashSet<Role>();
	
	private Privilege parent;
	private Set<Privilege> children = new HashSet<Privilege>();
	
	public Privilege() {
		
	}
	
	public Privilege(String name, String url, Privilege parent) {
		super();
		this.name = name;
		this.url = url;
		this.parent = parent;
	}

	@OneToMany(mappedBy="parent", cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	@OrderBy("id ASC")
	public Set<Privilege> getChildren() {
		return children;
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	@ManyToOne
	public Privilege getParent() {
		return parent;
	}
	@ManyToMany
	public Set<Role> getRoles() {
		return roles;
	}
	public String getUrl() {
		return url;
	}
	
	
	public void setChildren(Set<Privilege> children) {
		this.children = children;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setParent(Privilege parent) {
		this.parent = parent;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
