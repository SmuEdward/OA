package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;

public interface ReplyService extends DaoSupport<Reply>{

	List<Reply> findByTopic(Topic topic);

	PageBean getPageBeanByTopic(int currentPage, int pageSize, Topic topic);

}
