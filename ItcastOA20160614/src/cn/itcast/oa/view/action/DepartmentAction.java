package cn.itcast.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.ModelDrivenBaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.util.DepartmentUtil;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DepartmentAction extends ModelDrivenBaseAction<Department>{

	
	private Long parentId;


	/** 列表 */
	public String list() throws Exception {
		List<Department> departmentList = null;
		
		if(parentId==null||parentId==-1){
			//如果没有上级部门，则只显示上级部门
			departmentList = departmentService.findTopList();
		}else{
			//如果上级部门ID不为空则显示他的全部子部门！！
			departmentList = departmentService.findChildren(parentId);
			Department parent = departmentService.getById(parentId);
			ActionContext.getContext().put("parent", parent);
		}
		ActionContext.getContext().put("departmentList", departmentList);
		return "list";
	}

	/** 删除 */
	public String delete() throws Exception {
		departmentService.delete(model.getId());
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		// 得到顶级部门的列表
		List<Department> topList = departmentService.findTopList();
		//根据顶级部门得到树形列表（通过改变部门的名称从而得到树形结构，并将其放入List中）
		List<Department> departmentList = DepartmentUtil.getTreeList(topList);
		//放入map中！！
		ActionContext.getContext().put("departmentList", departmentList);
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {
		// Department department = new Department();
		// department.setName(name);
		// department.setDescription(description)

		// 保存
		if (parentId == -1) {
			departmentService.save(model);
		} else {
			Department parent = departmentService.getById(parentId);
			// 根据树设置上级部门！！
			model.setParent(parent);
			departmentService.save(model);
		}
		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		// 将部门信息存放到map中，然后在select标签中显示
		List<Department> departmentList = departmentService.findAll();
		ActionContext.getContext().put("departmentList", departmentList);
		// 准备回显的数据
		Department department = departmentService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(department);
		
		//显示上级部门名称
		if(department.getParent()!=null){
			parentId=department.getParent().getId();
		}
		return "saveUI";
	}
	/** 修改 */
	public String edit() throws Exception {
		// 1，从数据库取出原对象
		Department department = departmentService.getById(model.getId());

		// 2，设置要修改的属性
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		//修改上级部门！！
		department.setParent(departmentService.getById(parentId));
		// 3，更新到数据库中
		departmentService.update(department);

		return "toList";
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
