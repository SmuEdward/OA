package cn.itcast.oa.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.ApplicationService;
import cn.itcast.oa.service.ApplicationTemplateService;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.service.ForumService;
import cn.itcast.oa.service.PrivilegeService;
import cn.itcast.oa.service.ProcessDefinitionService;
import cn.itcast.oa.service.ReplyService;
import cn.itcast.oa.service.RoleService;
import cn.itcast.oa.service.TopicService;
import cn.itcast.oa.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	// =============Service实例声明=============
	@Resource
	protected RoleService roleService;

	@Resource
	protected DepartmentService departmentService;

	@Resource
	protected UserService userService;
	@Resource
	protected PrivilegeService privilegeService;

	@Resource
	protected ForumService forumService;

	@Resource
	protected TopicService topicService;

	@Resource
	protected ReplyService replyService;

	@Resource
	protected ProcessDefinitionService processDefinitionService;

	@Resource
	protected ApplicationTemplateService applicationTemplateService;

	@Resource
	protected ApplicationService applicationService;

	// 页码默认为第1页
	protected int currentPage = 1;


	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	// 每页默认显示10条记录
	protected int pageSize = 10;
	

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取当前用户
	 * 
	 * @return
	 */
	protected User getCurrentUser() {
		return (User) ActionContext.getContext().getSession().get("user");
	}

	/**
	 * 保存上传的文件
	 * 
	 * @param upload
	 *            上传的临时文件（保存在服务器端）
	 * @return
	 */
	protected String saveUploadFile(File upload) {

		// 得到根路径
		String root = ServletActionContext.getServletContext().getRealPath(
				"/WEB-INF/upload_files");
		// 根据日期创建文件夹
		SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd");
		String subpath = sdf.format(new Date());
		// 如果文件夹不存在，则创建文件夹！
		File dir = new File(root + subpath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 拼接路径
		String uploadFilePath = root + subpath + "/"
				+ UUID.randomUUID().toString();
		// 将临时文件移动到指定路径下
		upload.renameTo(new File(uploadFilePath));
		return uploadFilePath;
	}

}
