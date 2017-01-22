package cn.itcast.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.ModelDrivenBaseAction;
import cn.itcast.oa.domain.ApplicationTemplate;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ApplicationTemplateAction extends ModelDrivenBaseAction<ApplicationTemplate>{
	
	private File upload;
	//定义下载流
	private InputStream inputStream;
	
	/** 列表 */
	public String list() throws Exception {
		List<ApplicationTemplate> applicationTemplateList = applicationTemplateService.findAll();
		ActionContext.getContext().put("applicationTemplateList", applicationTemplateList);
 		return "list";
 		
	}

	/** 删除 */
	public String delete() throws Exception {
		applicationTemplateService.delete(model.getId());
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {
		
		String uploadFilePath = saveUploadFile(upload);
		
		model.setPath(uploadFilePath);
		applicationTemplateService.save(model);
		return "toList";
	}

	
	/** 修改页面 */
	public String editUI() throws Exception {
		//准备下拉框中的数据
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		
		//准备回显的数据
		ApplicationTemplate applicationTemplate = applicationTemplateService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(applicationTemplate);
		return "saveUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		//从数据库中获取原对象
		ApplicationTemplate applicationTemplate = applicationTemplateService.getById(model.getId());
		applicationTemplate.setName(model.getName());
		applicationTemplate.setProcessDefinitionKey(model.getProcessDefinitionKey());
		//验证是否修改了上传的文件
		if(upload!=null){
			//删除原来的文件
			new File(applicationTemplate.getPath()).delete();
			String uploadFilePath = saveUploadFile(upload);
			applicationTemplate.setPath(uploadFilePath);
		}
		applicationTemplateService.update(applicationTemplate);
		return "toList";
	}
	
	/** 下载 */
	public String download() throws Exception {
		ApplicationTemplate applicationTemplate = applicationTemplateService.getById(model.getId());
		inputStream = new FileInputStream(new File(applicationTemplate.getPath()));
		
		//准备文件名称(注意乱码问题的解决)
		String fileName = java.net.URLEncoder.encode(applicationTemplate.getName(), "utf-8");
		ActionContext.getContext().put("fileName", fileName);
		return "download";
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}
	
	
}
