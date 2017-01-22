package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.TopicService;


@Service
@Transactional
@SuppressWarnings("unchecked")
public class TopicServiceImpl extends DaoSupportImpl<Topic> implements TopicService {
	
	
	/**
	 * 
	 * 按forum来查询
	 * 1. 并且按照最后更新的时间来排序
	 * 2. 根据帖子的类型排序
	 * 3. 置顶帖要一直在最上层
	 * 4. 精华帖不一定要在第二层
	 */

	public List<Topic> findByForum(Forum forum) {
		
		return getSession().createQuery//
				("FROM Topic t where t.forum = ? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC,t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.list();//
	}

	
	
	/**
	 * 
	 * 		                  发表新主题
     *  Forum	 topicCount	                 加1
	 *           articleCount	       加1
	 *           lastTopic	          更新为当前的新主题
     *  Topic	 replyCount	      0，默认值
	 *           lastReply	     Null，默认值
	 *           lastUpdateTime	   主题的发表时间
	 */
	
	@Override
	public void save(Topic topic) {
		/*
		 * 设置主题相关属性
		 */
		topic.setLastReply(null);
		topic.setLastUpdateTime(topic.getPostTime());
		topic.setType(Topic.TYPE_NORMAL);
		topic.setReplyCount(0);
		getSession().save(topic);
		
		/*
		 * 设置论坛相关的属性
		 */
		
		Forum forum = topic.getForum();
		forum.setTopicCount(forum.getTopicCount()+1);
		forum.setArticleCount(forum.getArticleCount()+1);
		forum.setLastTopic(topic);
		//更新forum
		getSession().update(forum);
		
	}



	public PageBean getPageBeanByForum(int currentPage, int pageSize,
			Forum forum) {
		/*
		 * 获取每页显示的信息
		 */
		List recordList = getSession().createQuery("FROM Topic t where t.forum = ? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC,t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.setFirstResult((currentPage-1)*pageSize)  //注意这是记录的索引（从0开始）
				.setMaxResults(pageSize)//
				.list();
		
		
		/*
		 * 获取记录数
		 */
		Long recordCount = (Long)getSession().createQuery("SELECT count(*) FROM Topic t where t.forum = ?")
				.setParameter(0, forum)
				.uniqueResult(); 
		
		return new PageBean(pageSize, currentPage, recordList, recordCount.intValue());
	}

}
