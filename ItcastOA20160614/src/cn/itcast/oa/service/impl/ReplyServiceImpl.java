package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.ReplyService;

@Service
@Transactional
public class ReplyServiceImpl extends DaoSupportImpl<Reply> implements ReplyService {
	
	/**
	 * 得到回复列表
	 */
	@SuppressWarnings("unchecked")
	public List<Reply> findByTopic(Topic topic) {
		
		return getSession().createQuery("FROM Reply r WHERE r.topic=?")
				.setParameter(0, topic)
				.list();
	}

	/**
	 * 重写save方法
	 */
	
	@Override
	public void save(Reply reply) {
		
		/*
		 * 特殊属性
		 * 发表一个新回复对topic的影响
		 * 
		 */
		Topic topic = reply.getTopic();
		topic.setReplyCount(topic.getReplyCount()+1);
		topic.setLastReply(reply);
		topic.setLastUpdateTime(reply.getPostTime());
		getSession().update(topic);
		
		/*
		 * 发表一个新回复对forum的影响
		 */
		Forum forum = topic.getForum();
		forum.setArticleCount(forum.getArticleCount()+1);
		getSession().update(forum);
		
		getSession().save(reply);
	}

	public PageBean getPageBeanByTopic(int currentPage, int pageSize, Topic topic) {
		
		/*
		 * 获取每页显示的信息
		 */
		List recordList = getSession().createQuery("FROM Reply r WHERE r.topic=?")//
				.setParameter(0, topic)//
				.setFirstResult((currentPage-1)*pageSize)  //注意这是记录的索引（从0开始）
				.setMaxResults(pageSize)//
				.list();
		
		
		/*
		 * 获取记录数
		 */
		Long recordCount = (Long)getSession().createQuery("SELECT count(*) FROM Reply r WHERE r.topic=?")
				.setParameter(0, topic)
				.uniqueResult(); 
		
		return new PageBean(pageSize, currentPage, recordList, recordCount.intValue());
	}
	

}
