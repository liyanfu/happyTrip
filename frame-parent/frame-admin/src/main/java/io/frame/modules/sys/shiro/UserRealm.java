
package io.frame.modules.sys.shiro;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

import io.frame.common.enums.Constant;
import io.frame.dao.entity.SysMenu;
import io.frame.dao.entity.SysMenuExample;
import io.frame.dao.entity.SysRoleMenu;
import io.frame.dao.entity.SysRoleMenuExample;
import io.frame.dao.entity.SysUser;
import io.frame.dao.entity.SysUserExample;
import io.frame.dao.entity.SysUserRole;
import io.frame.dao.entity.SysUserRoleExample;
import io.frame.dao.mapper.SysMenuMapper;
import io.frame.dao.mapper.SysRoleMenuMapper;
import io.frame.dao.mapper.SysUserMapper;
import io.frame.dao.mapper.SysUserRoleMapper;

/**
 * 认证
 * 
 * @author fury
 *
 */
@Component
public class UserRealm extends AuthorizingRealm {
	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	/**
	 * 授权(验证权限时调用)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUser user = (SysUser) principals.getPrimaryPrincipal();
		Long userId = user.getUserId();

		List<String> permsList = Lists.newArrayList();

		// 系统管理员，拥有最高权限
		if (user.getSuperFlag() == Constant.SUPER_ADMIN) {
			List<SysMenu> menuList = sysMenuMapper.selectByExample(null);
			for (SysMenu menu : menuList) {
				permsList.add(menu.getPerms());
			}
		} else {

			// 查找出用户角色信息
			SysUserRoleExample userRoleExample = new SysUserRoleExample();
			userRoleExample.or().andUserIdEqualTo(userId);
			List<SysUserRole> userRoles = sysUserRoleMapper.selectByExample(userRoleExample);
			List<Long> roleIds = Lists.newArrayList();
			if (!CollectionUtils.isEmpty(userRoles)) {
				// 查找角色对应的菜单权限Id
				for (SysUserRole role : userRoles) {
					roleIds.add(role.getRoleId());
				}
			}

			SysRoleMenuExample roleMenuExample = new SysRoleMenuExample();
			roleMenuExample.or().andRoleIdInIgnoreNull(roleIds);
			List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectByExample(roleMenuExample);
			List<Long> menuIds = Lists.newArrayList();
			if (!CollectionUtils.isEmpty(roleMenus)) {
				// 查找角色拥有的菜单权限
				for (SysRoleMenu roleMenu : roleMenus) {
					menuIds.add(roleMenu.getMenuId());
				}
			}

			SysMenuExample sysMenuExample = new SysMenuExample();
			sysMenuExample.or().andMenuIdInIgnoreNull(menuIds);
			List<SysMenu> sysMenus = sysMenuMapper.selectByExample(sysMenuExample);
			if (!CollectionUtils.isEmpty(sysMenus)) {
				for (SysMenu menu : sysMenus) {
					permsList.add(menu.getPerms());
				}
			}

		}

		// 用户权限列表
		Set<String> permsSet = new HashSet<>();
		for (String perms : permsList) {
			if (StringUtils.isBlank(perms)) {
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		// 查询用户信息
		SysUserExample example = new SysUserExample();
		example.or().andUserNameEqualTo(token.getUsername());
		SysUser user = sysUserMapper.selectOneByExample(example);

		// 账号不存在
		if (user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}

		// 账号锁定
		if (user.getStatus() == 0) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getUserPass(), ByteSource.Util.bytes(user.getSalt()), getName());
		return info;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
		shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
		shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
		super.setCredentialsMatcher(shaCredentialsMatcher);
	}
}
