package cn.itcast.oa.view.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.ModelDrivenBaseAction;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.util.QueryHelper;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class TopicAction extends ModelDrivenBaseAction<Topic>{
	
	private Long forumId;
	
	private int currentPage = 1;
	private int pageSize = 10;  //默认每页显示数量
	
	/* 单个主题页面（主题+回复列表）*/
	public String show() throws Exception {
/*		//论坛相关的信息
		Forum forum = forumService.getById(forumId);
		ActionContext.getContext().put("forum", forum);*/
		
		//主题相关的信息
		Topic topic = topicService.getById(model.getId());
		ActionContext.getContext().put("topic", topic);
		
		//回复相关的信息
		//List<Reply> replyList =  replyService.findByTopic(topic);
		//ActionContext.getContext().put("replyList", replyList);
		
		//分页相关的信息 (原始版本)
		//PageBean pageBean = replyService.getPageBeanByTopic(currentPage,pageSize,topic);
		//放在栈顶，以便获取
		//ActionContext.getContext().getValueStack().push(pageBean);
		
		//抽取service方法后的分页信息的获取
		//String hql = "FROM Reply r WHERE r.topic=?";
		//List<Object> parameters = new ArrayList<Object>();
		//parameters.add(topic);
		//PageBean pageBean = replyService.getPageBean(currentPage,pageSize,hql,parameters);
		//ActionContext.getContext().getValueStack().push(pageBean);
		

		// 准备分页信息, 最终版
		new QueryHelper(Reply.class, "r")//
				.addCondition("r.topic=?", topic)//
				.addOrderProperty("r.postTime", true)//
				.preparePageBean(replyService, currentPage, pageSize);
		return "show";
	}
	
	/* 发帖界面*/
	public String addUI() throws Exception {
		//准备数据
		Forum forum = forumService.getById(forumId);
		ActionContext.getContext().put("forum", forum);
		return "addUI";
	}
	
	/* 发帖*/
	public String add() throws Exception {
		
		//下面两个属性已经自动封装在model中了，所以不需要设置！
		//model.setTitile(model.getTitile());
		//model.setContent(model.getContent());
		
		/*
		 * 下面这些属性可以在此直接设置
		 */
		model.setAuthor(getCurrentUser()); 
		model.setForum(forumService.getById(forumId));
		//获取当前请求的IP地址
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());
		
		/*
		 * 下面这些特殊属性在service层设置
		 */
	/*	model.setLastReply(lastReply);
		model.setLastUpdateTime(lastUpdateTime);
		model.setType(type);
		model.setReplyCount(replyCount);*/
		
		
		topicService.save(model);
		
		return "toShow";  //发完贴回到单个主题页面
	}

	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
}
