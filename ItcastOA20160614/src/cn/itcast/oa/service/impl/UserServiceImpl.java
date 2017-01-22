package cn.itcast.oa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.UserService;
import cn.itcast.oa.util.MD5PW;

@Service
@Transactional
public class UserServiceImpl extends DaoSupportImpl<User> implements
		UserService {

	/**
	 * 根据用户名和密码来查询用户是否存在！！
	 * 
	 */
	public User findByLoginNameAndPassword(String loginName, String password) {
		String MD5Password = MD5PW.MD5(password);
		return (User) getSession()
				.createQuery(
						"FROM User u where u.loginName =? AND u.password=?")
				.setParameter(0, loginName).setParameter(1, MD5Password)
				.uniqueResult();
	}

}
