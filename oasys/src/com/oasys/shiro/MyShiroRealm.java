package com.oasys.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.oasys.model.Users;
import com.oasys.util.Collections;
import com.oasys.util.Constants;

public class MyShiroRealm extends AuthorizingRealm {
	// 用于获取用户信息及用户权限信息的业务接口
	private SessionFactory hibernateSessionFactory;

	public SessionFactory getSessionFactory() {
		return hibernateSessionFactory;
	}

	public void setHibernateSessionFactory(
			SessionFactory hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}

	@SuppressWarnings("unused")
	private Session getCurrentSession() {
		return hibernateSessionFactory.getCurrentSession();
	}

	@SuppressWarnings("rawtypes")
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// String username = (String)
		// principals.fromRealm(getName()).iterator().next();
		ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName())
				.iterator().next();
		String username = shiroUser.getAccount();
		if (username != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 查询用户授权信息
			// info.addRole("admin");
			String sql = null;
			// 超级管理员默认拥有所有操作权限
			if (Constants.SYSTEM_ADMINISTRATOR.equals(username)) {
				sql = "SELECT p.PERMISSION_ID,p.MYID FROM QQMS.T_PERMISSION AS p\n"
						+ "where p.STATUS='A' and p.TYPE='O' and p.ISUSED='Y' and p.SYSTEM_TYPE='2'";
			} else {
				sql = "SELECT DISTINCT rp.PERMISSION_ID,p.MYID FROM\n"
						+ "QQMS.T_ROLE_AND_PERMISSION AS rp\n"
						+ "INNER JOIN QQMS.T_ROLE AS r ON rp.ROLE_ID = r.ROLE_ID\n"
						+ "INNER JOIN QQMS.T_USER_AND_ROLE AS ur ON rp.ROLE_ID = ur.ROLE_ID\n"
						+ "INNER JOIN QQMS.T_USERS AS u ON u.USER_ID = ur.USER_ID\n"
						+ "INNER JOIN QQMS.T_PERMISSION AS p ON rp.PERMISSION_ID = p.PERMISSION_ID  and p.SYSTEM_TYPE='2'\n"
						+ "WHERE rp.STATUS='A' and r.STATUS='A' and ur.STATUS='A' and u.STATUS='A' and p.STATUS='A' and p.TYPE='O' and p.ISUSED='Y'\n"
						+ "and u.NAME ='" + username + "'";
			}
			List perList = this.getSessionFactory().getCurrentSession()
					.createSQLQuery(sql).list();
			if (perList != null && perList.size() != 0) {
				for (Object object : perList) {
					Object[] obj = (Object[]) object;
					info.addStringPermission(obj[1].toString());
				}
				return info;
			}
		}
		return null;
	}

	// 获取认证信息
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
		// 通过表单接收的用户名
		String username = token.getUsername();
		// if (username != null && !"".equals(username) &&
		// doCaptchaValidate(token))
		if (username != null && !"".equals(username)) {
			SessionFactory s = this.getSessionFactory();
			String hql = "from Users t where t.status='A' and t.account=:account";
			Users users = (Users) s.getCurrentSession().createQuery(hql)
					.setParameter("account", username).uniqueResult();
			if (users != null) {
				Subject subject = SecurityUtils.getSubject();
				List<String> roleCodes = findRoleCodesByUserId(users
						.getUserId());
				subject.getSession().setAttribute(
						Constants.SHIRO_USER,
						new ShiroUser(users.getUserId(), users.getAccount(),
								users.getName(), roleCodes));
				return new SimpleAuthenticationInfo(
						new ShiroUser(users.getUserId(), users.getAccount(),
								users.getName()), users.getPassword(),
						getName());
			}
		}
		return null;
	}

	/**
	 * 更新用户授权信息缓存.
	 */

	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */

	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	// 验证码校验
	protected boolean doCaptchaValidate(CaptchaUsernamePasswordToken token) {
		String captcha = (String) ((ServletRequestAttributes)RequestContextHolder
				.getRequestAttributes())
				.getRequest()
				.getSession()
				.getAttribute(
						com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
			throw new IncorrectCaptchaException("验证码错误！");
		}
		return true;
	}

	/**
	 * 获取用户的角色的codes
	 * 
	 * @param userId
	 * @return
	 */
	private List<String> findRoleCodesByUserId(Integer userId) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append("	tr.ROLE_CODE ");
		sb.append("FROM ");
		sb.append("	QQMS.t_users tu ");
		sb.append("LEFT JOIN QQMS.t_user_and_role tur ON tu.USER_ID = tur.USER_ID ");
		sb.append("LEFT JOIN QQMS.t_role tr ON tr.ROLE_ID = tur.ROLE_ID ");
		sb.append("WHERE ");
		sb.append("	tu.USER_ID = '" + userId + "' ");
		List list = getCurrentSession().createSQLQuery(sb.toString()).list();
		List<String> roleCodes = new ArrayList<String>();
		if (Collections.listIsNotEmpty(list)) {
			for (Object o : list) {
				roleCodes.add((String) o);
			}
		}
		return roleCodes;
	}
}
