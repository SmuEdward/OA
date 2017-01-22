package cn.itcast.oa.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.opensymphony.xwork2.ActionContext;

/**
 * 用户
 * @author tyg
 * 
 */
@Entity
@Table(name="itcast_user")
public class User implements java.io.Serializable{
	private Long id;
	private Department department;
	private Set<Role> roles = new HashSet<Role>();
	/*private Set<Topic> topics = new HashSet<Topic>();
	private Set<Reply> replies = new HashSet<Reply>();
	*/

	private String loginName; // 登录名
	private String password; // 密码
	private String name; // 真实姓名
	private String gender; // 性别
	private String phoneNumber; // 电话号码
	private String email; // 电子邮件
	private String description; // 说明
	
	
	/**
	 * 判断本用户是否有指定名称的权限
	 * @return
	 */
	@Transient
	public boolean hasPrivilegeByName(String name){
		//判断是否为管理员
		if(isAdmin()){
			return true;
		}
		
		//判断普通用户
		for(Role role:roles){
			for(Privilege pri:role.getPrivileges()){
				if(name.equals(pri.getName())){
					return true;
				}
			}
		}
		return false;
		
	}
	
	@Transient
	public boolean isAdmin() {
		return "admin".equals(loginName);
	}

	/**
	 * 判断本用户是否有指定名称的权限
	 * @return
	 */
	@Transient
	public boolean hasPrivilegeByUrl(String privUrl){
		//判断是否为管理员
		if(isAdmin()){
			return true;
		}
		
		//3. 去除URL后面的参数
    	int pos = privUrl.indexOf("?");
    	if(pos!=-1){
    	privUrl = privUrl.substring(0, pos);
    	}
    	//4. 如果链接后面还含有UI，去除UI；
    	if(privUrl.endsWith("UI")){
    		privUrl = privUrl.substring(0,privUrl.length()-2);
    	}
    	
    	/**
    	 * 如果用户已经登录，但是一些功能（action）比如首页的访问，注销并没有纳入到数据库中作为权限的管理
    	 * 所以需要排除这些功能
    	 * 
    	 */
    	Collection<Privilege> allPrivilegeList = (Collection<Privilege>) ActionContext.getContext().getApplication().get("allPrivilegeList");
    	//如果数据库中的权限列表中URL不包含首页的访问，注销等功能，则不需要判断，直接返回true
    	if(!allPrivilegeList.contains(privUrl)){
    		return true;
    	}else{
    		
    		//判断普通用户
    		for(Role role:roles){
    			for(Privilege pri:role.getPrivileges()){
    				if(privUrl.equals(pri.getUrl())){
    					return true;
    				}
    			}
    		}
    		return false;
    	}
		
	}
	
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	@ManyToOne
	public Department getDepartment() {
		return department;
	}

	@ManyToMany(fetch=FetchType.EAGER)
	public Set<Role> getRoles() {
		return roles;
	}
	
	public String getDescription() {
		return description;
	}

	public String getEmail() {
		return email;
	}

	public String getGender() {
		return gender;
	}
	

	public String getLoginName() {
		return loginName;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	

	public void setDepartment(Department department) {
		this.department = department;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
