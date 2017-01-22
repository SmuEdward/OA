package cn.itcast.oa.util;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.service.PrivilegeService;

public class InitListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {

	}

	public void contextInitialized(ServletContextEvent sce) {
		// 因为监听器是通过反射实例化的，所以在这里向使用privilegeService对象则必须自己从Spring容器中取出
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		PrivilegeService privilegeService = (PrivilegeService) ac.getBean("privilegeServiceImpl");
		
		//数据准备：顶级权限的数据
		List<Privilege> topPrivilegeList = privilegeService.findTopList();
		sce.getServletContext().setAttribute("topPrivilegeList", topPrivilegeList);
		System.out.println("==========顶级权限的数据准备完成=============");
		
		//数据准备：所有权限的数据
		Collection<Privilege> allPrivilegeList = privilegeService.findAllPrivilegeList();
		sce.getServletContext().setAttribute("allPrivilegeList", allPrivilegeList);
		System.out.println("==========所有权限的数据准备完成=============");
	}

}
