package cn.itcast.oa.util;

import cn.itcast.oa.domain.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
/**
 * 
 * 自定义检查权限的拦截器
 * @author Administrator
 *
 */
public class CheckPrivilegeIntercepter implements Interceptor {

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		/*System.out.println("before");
		String result = actionInvocation.invoke();
		System.out.println("after");
		return result;*/
		/**
		 * 获取用户和权限
		 */
		User user = (User) ActionContext.getContext().getSession().get("user");
		//得到用户访问的URL地址，根据hasPrivilegeByUrl()判断用户是否有访问权限
		//从actionInvocation得到actionProxy,从而得到配置信息！
		String namespace = actionInvocation.getProxy().getNamespace();
		String actionName = actionInvocation.getProxy().getActionName();
		String privUrl = namespace+actionName;  
		
		
		/**
		 * 1.如果用户没有登录，则返回登录页面
		 * 2. 如果用户已经登录，则判断用户是否有权限
		 */
		if(user==null){
			/*注意：当用户正在登录或者在去登录的路上时，我们应该放心
			 *也就是说当用户访问的地址是user_login或者是user_loginUI时，
			 *我们是放行的 ！！
			 */
			if(privUrl.startsWith("/user_login")){
				actionInvocation.invoke();
			}else{
				
				return "loginUI";
			}
			
		}else{
			if(user.hasPrivilegeByUrl(privUrl)){
				//如果用户有权限，则方行
				actionInvocation.invoke();
			}else{
				return "noPrivilegeError";
			}
		}
		return null;
		
	}

	public void destroy() {
		
		
	}

	public void init() {
		
	}

}
