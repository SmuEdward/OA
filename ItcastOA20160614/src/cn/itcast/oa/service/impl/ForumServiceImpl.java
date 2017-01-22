package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.service.ForumService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class ForumServiceImpl extends DaoSupportImpl<Forum> implements
		ForumService {

	/**
	 * 为了实现上下移动 
	 * 1. 让Forum的position值唯一 
	 * 2. 让Forum按position的升序排列
	 * 3. 交换position的值完成上下移动！！
	 */
	/*
	 * 1. 重写save()方法完成position值得唯一
	 */
	@Override
	public void save(Forum forum) {

		super.save(forum);
		// 当用户新建一个forum时，完成对position的赋值
		forum.setPosition(forum.getId().intValue());
	}

	/*
	 * 2. 重写findAll()方法，完成排序
	 */

	@Override
	public List<Forum> findAll() {

		return getSession().createQuery("From Forum f ORDER BY f.position ASC")
				.list();
	}

	/*
	 * 3. 交换position的值完成上下移动
	 */

	public void moveUp(Long id) {
		// 得得该forum和上一个forum
		Forum forum = getById(id);
		Forum other = (Forum) getSession()
				.createQuery(
						"From Forum f where f.position<? ORDER BY f.position DESC")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)//
				.setMaxResults(1)//
				.uniqueResult();//
		
		if(other==null){
			return;
		}
		// 交换position的值
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);

		// 更新到数据库中（可以不写，因为现在对象是持久化状态！！）
		getSession().update(forum);
		getSession().update(other);

	}

	public void moveDown(Long id) {
		// 得得该forum和上一个forum
		Forum forum = getById(id);
		Forum other = (Forum) getSession()
				.createQuery(
						"From Forum f where f.position>? ORDER BY f.position ASC")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)//
				.setMaxResults(1)//
				.uniqueResult();//
		if(other==null){
			return;
		}
		// 交换position的值
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);

		// 更新到数据库中（可以不写，因为现在对象是持久化状态！！）
		getSession().update(forum);
		getSession().update(other);

	}

}
