package cn.itcast.oa.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.service.ProcessDefinitionService;

@Service
@Transactional
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

	@Resource
	private ProcessEngine processEngine;

	/**
	 * 查询所有最新版本的流程定义
	 */
	public List<ProcessDefinition> findAllLatestVersions() {
		List<ProcessDefinition> all = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.orderAsc(ProcessDefinitionQuery.PROPERTY_VERSION)
				.list();
		Map<String,ProcessDefinition> map = new HashMap<String,ProcessDefinition>();
		
		for(ProcessDefinition p: all){
			map.put(p.getKey(), p);
		}
		
		/*List<ProcessDefinition> result = (List<ProcessDefinition>) map.values();*/
		
		return new ArrayList<ProcessDefinition>(map.values());
	}

	/**
	 * 根据key来删除所有版本的流程定义
	 */
	public void deleteByKey(String key) {
		//查询出所有key为key的流程定义
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(key)
				.list();
		
		for(ProcessDefinition pd :list){
			//注意：deleteDeploymentCascade()参数为DeploymentId;
			processEngine.getRepositoryService().deleteDeploymentCascade(pd.getDeploymentId());
		}

	}

	/**
	 * 根据上传的zip文件俩部署流程定义
	 */
	public void deploy(ZipInputStream zipInputStream) {

		processEngine.getRepositoryService()//
				.createDeployment()//
				.addResourcesFromZipInputStream(zipInputStream)//
				.deploy();
	}

	/**
	 * 根据流程定义的ID来返回流程图片的流
	 * 
	 * @return
	 * 
	 */
	public InputStream getProcessImageResourceAsStream(String processDefinitionId) {
		// 获取指定部署对象中的所有资源的名称
	/*	Set<String> names = processEngine.getRepositoryService().getResourceNames(processDefinitionId);
		System.out.println("所有的资源名称：");
		String imageName=null;
		for (String name : names) {
			  if(name.endsWith(".png")){
				  imageName = name;
			  }
			}*/
		
		/**
		 * 根据processDefinitionId查询返回processDefinition
		 */
		ProcessDefinition pd= processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId)
				.uniqueResult();
		
		//pd.getImageResourceName(); 返回图片资源名
		return  processEngine.getRepositoryService()
				.getResourceAsStream(pd.getDeploymentId(), pd.getImageResourceName());
		
		
		
	}
}
