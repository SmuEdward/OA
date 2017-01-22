package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.service.ForumService;
import cn.itcast.oa.service.PrivilegeService;
import cn.itcast.oa.service.ReplyService;
import cn.itcast.oa.service.RoleService;
import cn.itcast.oa.service.TopicService;
import cn.itcast.oa.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ModelDrivenBaseAction<T> extends BaseAction implements ModelDriven<T> {

	// =============抽取ModelDriven层===========

	protected T model;

	public ModelDrivenBaseAction() {
		
		try {
			// 使用反射技术得到T的真实类型
			ParameterizedType pt = (ParameterizedType) this.getClass()
					.getGenericSuperclass(); // 获取当前new的对象的 泛型的父类 类型
			Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类型
			model = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public T getModel() {
		return model;
	}

	
}
