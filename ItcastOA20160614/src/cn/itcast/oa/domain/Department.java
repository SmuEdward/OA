package cn.itcast.oa.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 部门
 * 
 * @author tyg
 * 
 */
@Entity
@Table(name="itcast_department")
public class Department implements java.io.Serializable{

	public Department() {

	} 

	public Department(String name) {
		super();
		this.name = name;
	}

	private Long id;
	private Set<User> users = new HashSet<User>();
	private Department parent;
	private Set<Department> children = new HashSet<Department>();

	private String name;
	private String description;
	

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	//设置级联，会同时删除子部门！！
	/*
	 * 1.JPA中的CascadeType.ALL并不等于
	 * {CascadeType.PESIST,CascadeType.REMOVE,CascadeType.MERGE,CascadeType.REFRESH}
	 * 所以在设置ALL时要注意，
	 * 如果设置了ALL，那么在save 和 update时会更行其子树
	 */
	@OneToMany(mappedBy="parent",cascade={CascadeType.REMOVE})
	@OrderBy("id ASC")
	public Set<Department> getChildren() {
		return children;
	}
	
	//@manytoone中默认使用的是Eager,如果设置成了LAZY就会出现错误！！
	//可以使用Spring的openSessionInView来保存懒加载，并不会出现异常！！
	@ManyToOne(fetch=FetchType.LAZY) 
	public Department getParent() {
		return parent;
	}
	
	@OneToMany(mappedBy = "department")
	public Set<User> getUsers() {
		return users;
	}
	
	public String getDescription() {
		return description;
	}


	public String getName() {
		return name;
	}


	public void setChildren(Set<Department> children) {
		this.children = children;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
