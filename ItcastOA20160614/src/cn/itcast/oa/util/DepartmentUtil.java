package cn.itcast.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.itcast.oa.domain.Department;

public class DepartmentUtil {

	public static List<Department> getTreeList(List<Department> topList) {
		List<Department> list = new ArrayList<Department>();
		walkDepartmentTree(topList,">",list);
		return list;
	}
	
	//遍历部门树，并改变其名称，将其放入list中
	public static void walkDepartmentTree(Collection<Department> topList,String prefix,List<Department> list){
		
		for(Department top : topList){
			Department copy = new Department();
			//改变顶级部门名称
			copy.setId(top.getId());
			copy.setName(prefix+top.getName());
			//加入List中
			list.add(copy);
			//top.getChildren()返回的set类型的容器，所以是无序的
			walkDepartmentTree(top.getChildren(),"　"+prefix,list);
		}
	}
}
