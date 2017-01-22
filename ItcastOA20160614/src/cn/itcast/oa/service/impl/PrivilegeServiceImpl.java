package cn.itcast.oa.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.service.PrivilegeService;

@Service
@Transactional
public class PrivilegeServiceImpl extends DaoSupportImpl<Privilege> implements PrivilegeService{

	public List<Privilege> findTopList() {
	
		return getSession().createQuery("From Privilege p where p.parent IS NULL").list();
	}

	public Collection<Privilege> findAllPrivilegeList() {
		
		return getSession().createQuery("SELECT DISTINCT p.url FROM Privilege p where p.url IS NOT NUll").list();
	}

}
