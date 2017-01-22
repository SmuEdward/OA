package cn.itcast.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends BaseAction{
	
	//根据流程定义的key来删除所有版本的流程
	private String key;
	
	//上传的临时文件(临时文件)
	private File upload;
	
	//传过来的查看流程图的ID;
	private String id;
	
	//下载的流
	private InputStream inputStream;
	
	/**
	 * 流程列表
	 */
	public String list() throws Exception {
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		return "list";
	}
	
	/**
	 * 删除流程定义
	 */
	public String delete() throws Exception {
		//get方法的请求一般位于请求头中
		//key = new String(key.getBytes("ISO8859-1"),"utf-8");
		
		//自己解码一次
		key = URLDecoder.decode(key,"utf-8");
		processDefinitionService.deleteByKey(key);
		return "toList";
	}
	
	/**
	 * 添加流程定义页面
	 */
	public String addUI() throws Exception {
		
		return "saveUI";
	}
	
	/**
	 * 添加流程定义（zip）
	 */
	public String add() throws Exception {
		if(upload!=null){
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(upload));
			processDefinitionService.deploy(zipInputStream);
			zipInputStream.close();
		}else{
			return "saveUI";
		}
		return "toList";
	}
	
	/**
	 * 查看流程图
	 */
	public String showProcessImage() throws Exception {
		//自己解码一次
		id = URLDecoder.decode(id,"utf-8");
		
		inputStream = processDefinitionService.getProcessImageResourceAsStream(id);
		
		return "showProcessImage";
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		
		this.inputStream = inputStream;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	
}
