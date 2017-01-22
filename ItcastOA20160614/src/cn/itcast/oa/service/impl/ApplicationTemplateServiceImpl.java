package cn.itcast.oa.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.ApplicationTemplate;
import cn.itcast.oa.service.ApplicationTemplateService;

@Service
@Transactional
public class ApplicationTemplateServiceImpl extends DaoSupportImpl<ApplicationTemplate> implements
		ApplicationTemplateService {

	@Override
	public void delete(Long id) {
		//删除数据库中的记录
		ApplicationTemplate applicationTemplate = getById(id);
		getSession().delete(applicationTemplate);
		
		//输出文件夹中的文件
		File file =new File(applicationTemplate.getPath());
		if(file.exists()){
			file.delete();
		}
		
	}
	
	
	
}
