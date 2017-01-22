package cn.itcast.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.ModelDrivenBaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.util.DepartmentUtil;
import cn.itcast.oa.util.MD5PW;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class UserAction extends ModelDrivenBaseAction<User> {

	private Long departmentId;

	private Long[] roleIds;

	/** 列表 */
	public String list() throws Exception {
		List<User> userList = userService.findAll();
		ActionContext.getContext().put("userList", userList);
		return "list";
	}

	/** 删除 */
	public String delete() throws Exception {
		userService.delete(model.getId());
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		/*
		 * 显示部门信息
		 */
		// 得到顶级部门的列表
		List<Department> topList = departmentService.findTopList();
		// 根据顶级部门得到树形列表（通过改变部门的名称从而得到树形结构，并将其放入List中）
		List<Department> departmentList = DepartmentUtil.getTreeList(topList);
		// 放入map中！！
		ActionContext.getContext().put("departmentList", departmentList);
		/*
		 * 显示岗位信息
		 */
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);

		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {
		// 因为model的存在，但页面传来的是部门的ID和岗位的ID，所以要设置未封装的属性
		/*
		 * 添加用户所属的部门 1.得到传来的departmentId 2.设置所属部门
		 */
		
		Department department = departmentService.getById(departmentId);
		model.setDepartment(department);
	
		/*
		 * 设置所属岗位 
		 * 1.得到选中的多个岗位roleIds 
		 * 2.设置岗位集合SET
		 */
		List<Role> roleList = roleService.getByIds(roleIds);
		model.setRoles(new HashSet<Role>(roleList));
		/*
		 * 设置初始密码
		 */
		String MD5Password = MD5PW.MD5("1234");
		model.setPassword(MD5Password);

		userService.save(model);
		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		// 和添加一样，其中部门和岗位的信息都要提前准备！！
		/*
		 * 显示部门信息
		 */
		// 得到顶级部门的列表
		List<Department> topList = departmentService.findTopList();
		// 根据顶级部门得到树形列表（通过改变部门的名称从而得到树形结构，并将其放入List中）
		List<Department> departmentList = DepartmentUtil.getTreeList(topList);
		// 放入map中！！
		ActionContext.getContext().put("departmentList", departmentList);
		/*
		 * 显示岗位信息
		 */
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		

		/*
		 * 准备回显数据
		 *  1.根据传过来的用户ID得到用户信息
		 *  2.如果其部门信息部位空，则将部门ID封装到departmentId中，准备回显
		 *  3.如果用户岗位的信息不为空，则根据岗位的id列表封装到roleIds中，准备回显
		 */
		User user = userService.getById(model.getId());
		if (user.getDepartment() != null) {
			departmentId = user.getDepartment().getId();
		}
		// user.getRoles()返回的是一个set集合遍历集合，将其值赋给roleIds数组
		if (user.getRoles() != null) {
			roleIds = new Long[user.getRoles().size()]; // 设置roleIds的长度！！
			int index = 0;
			for (Role role : user.getRoles()) {
				roleIds[index++] = role.getId();
			}
		}
		
		/*
		 * 回显其他信息
		 */
		
		ActionContext.getContext().getValueStack().push(user);
		
		return "saveUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		/*
		 * 1.从数据库中取出原对象
		 */
		User user = userService.getById(model.getId());
		/*
		 * 设置要修改的属性
		 */
		//设置所属的部门
		user.setDepartment(departmentService.getById(departmentId));
		//设置关联的岗位
		user.setRoles(new HashSet<Role>(roleService.getByIds(roleIds)));
		user.setDescription(model.getDescription());
		user.setEmail(model.getEmail());
		user.setGender(model.getGender());
		user.setLoginName(model.getLoginName());
		user.setName(model.getName());
		//user.setPassword(model.getPassword());
		user.setPhoneNumber(model.getPhoneNumber());
		
		/*
		 * 更新数据库对象
		 */
		userService.update(user);
		
		return "toList";
	}
	
	/** 设置初始化密码 为1234*/
	public String initPassword() throws Exception {
		User user = userService.getById(model.getId());
		String MD5Password = MD5PW.MD5("1234");
		user.setPassword(MD5Password);
		userService.update(user);
		return "toList";
	}
	
	/** 登陆界面*/
	public String loginUI() throws Exception {
		
		return "loginUI";
	}
	
	/** 登陆*/
	public String login() throws Exception {
		
		User user  = userService.findByLoginNameAndPassword(model.getLoginName(),model.getPassword());
		if(user==null){
			addFieldError("login", "用户名或者密码错误");
			return "loginUI";
		}else{
			//将用户放入session中以便获取该用户有关的信息！！
			ActionContext.getContext().getSession().put("user", user);
			return "toIndex";
		}
	}
	
	/** 注销*/
	public String logout() throws Exception {
		ActionContext.getContext().getSession().remove("user");
		return "logout";
	}


	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

}
