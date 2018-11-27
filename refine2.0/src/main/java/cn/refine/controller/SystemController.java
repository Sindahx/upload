package cn.refine.controller;

import cn.refine.common.util.MD5Util;
import cn.refine.common.util.ResponseCommObject;
import cn.refine.mapper.SystemMapper;
import cn.refine.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/system")
public class SystemController {

	private Logger logger = Logger.getLogger(SystemController.class);
	public List<SystemAuth> auths;
	public Role role1;
	@Autowired
	private SystemMapper systemMapper;
	public Integer getOrgCode(HttpSession session){
		User userInfo = (User) session.getAttribute("userInfo");
		return userInfo.getOrgCode();
	}
	@RequestMapping("/findUserList")
	@ResponseBody
	public Map<String, Object> findUserList(HttpSession session,User user, PageVO pageVO) {
		pageVO.setPageIndex(1);
		pageVO.setPageSize(10);
		List<User> list = null;
		User userInfo = (User) session.getAttribute("userInfo");
		user.setOrgCode(userInfo.getOrgCode());
		list = systemMapper.findUserList(user, pageVO);
		return ResponseCommObject.createListToMap(list,1);
	}

	@RequestMapping("/findLogList")
	@ResponseBody
	public Map<String, Object> findLogList( PageVO pageVO) {
		pageVO.setPageIndex(1);
		pageVO.setPageSize(10);
		List<LogVO> list = null;
		list = systemMapper.findLogList(pageVO);
		return ResponseCommObject.createListToMap(list,1);
	}
	@RequestMapping("/findRoleList")
	@ResponseBody
	public Map<String, Object> findRoleList(Role role, PageVO pageVO) {
		pageVO.setPageIndex(1);
		pageVO.setPageSize(10);
		List<Role> list = null;
		list = systemMapper.findRoleList(role, pageVO);
		return ResponseCommObject.createListToMap(list,1);
	}
	@RequestMapping("/findOrgList")
	@ResponseBody
	public Map<String, Object> findOrgList(HttpSession session,Organization organization, PageVO pageVO) {
		pageVO.setPageIndex(1);
		pageVO.setPageSize(10);
		List<Role> list = null;
		User userInfo = (User) session.getAttribute("userInfo");
		organization.setOrgCode(String.valueOf(userInfo.getOrgCode()==0?"":userInfo.getOrgCode()));
		list = systemMapper.findOrgList(organization, pageVO);
		return ResponseCommObject.createListToMap(list,1);
	}

	@RequestMapping("/saveOrUpdateRole")
	@ResponseBody
	public Map<String, Object> addRole(Role role) {
		if(role.getId()!=null){
			systemMapper.updRole(role);
			SystemRoleAuth roleAuth= new SystemRoleAuth();
			roleAuth.setRoleId(role.getId());
			if (role.getAuths()!= null &&  !"".equals(role.getAuths())) {
				systemMapper.deleteRoleAuthByRoleId(roleAuth);
				if (role.getAuths().contains(",")) {
					String[] auths = role.getAuths().split(",");
					for (int i = 0; i < auths.length; i++) {
						if ( !"".equals(auths[i]) && auths[i] != null) {
							roleAuth.setAuthId(Integer.valueOf(auths[i].trim()));
							systemMapper.addRoleAuth(roleAuth);
						}
					}
				} else {
					roleAuth.setAuthId(Integer.valueOf(role.getAuths().trim()));
					systemMapper.addRoleAuth(roleAuth);
				}
			}else{
				systemMapper.deleteRoleAuthByRoleId(roleAuth);
			}
		}else{
			systemMapper.addRole(role);
			// 3 添加用户角色关系
			if (role.getAuths() != null &&  !"".equals(role.getAuths())) {
				if (role.getAuths().contains(",")) {
					String[] auths = role.getAuths().split(",");
					for (int i = 0; i < auths.length; i++) {
						if ( !"".equals(auths[i]) && auths[i] != null) {
							SystemRoleAuth roleAuth= new SystemRoleAuth();
							roleAuth.setAuthId(Integer.valueOf(auths[i].trim()));
							roleAuth.setRoleId(role.getId());
							systemMapper.addRoleAuth(roleAuth);
						}
					}
				} else {
					SystemRoleAuth roleAuth= new SystemRoleAuth();
					roleAuth.setAuthId(Integer.valueOf(role.getAuths().trim()));
					roleAuth.setRoleId(role.getId());
					systemMapper.addRoleAuth(roleAuth);
				}
			}
		}
		return ResponseCommObject.success();
	}

	@RequestMapping("/updateUserInfo")
	@ResponseBody
	public Map<String, Object> updateUserInfo(User user) {
		if(user.getId()!=null){
//			user.setLoginPwd(MD5Util.getMD5ofStr(user.getLoginPwd()));
			systemMapper.updateUserInfo(user);
		}
		return ResponseCommObject.success();
	}

	/**
	 * (用户修改密码)
	 *
	 * @throws
	 */
	@RequestMapping("/updPwd")
	@ResponseBody
	public Map<String, Object> updPwd(User user,String newPwd,String oldPwd){
//		SystemUser user = (SystemUser) session.getAttribute("userInfo");
		String msg = "";
		if (user != null) {
			if (oldPwd != null) {
				user.setLoginPwd(oldPwd);
//				Map<String, Object> map = new HashMap<>();
				user.setLoginPwd(MD5Util.getMD5ofStr(oldPwd));
				User users = systemMapper.getUserInfo(user);
				if (users != null) {
					user.setLoginPwd(MD5Util.getMD5ofStr(newPwd));
					systemMapper.resetPwd(user);
					// 重置用户session信息
//					session.setAttribute("userInfo", users);
//					map.put("message", "修改密码成功");
//					map.put("flag", "1");
					return ResponseCommObject.success();
				} else {
					msg= ("旧密码输入错误，请重新输入");
					return ResponseCommObject.fail(msg);
				}
			}
		} else {
			msg= ("用户登录时间超时，请重新登录");
			return ResponseCommObject.fail(msg);
		}
//		ResponseCommObject.createListToMap();
		return null;
	}

	@RequestMapping("/saveOrUpdUser")
	@ResponseBody
	public Map<String, Object> saveOrUpdUser(User user) {
		if(user.getId()!=null){
			user.setLoginPwd(MD5Util.getMD5ofStr(user.getLoginPwd()));
			systemMapper.updUser(user);
			UserRole userRole = new UserRole();
			userRole.setUserId(user.getId());
			if (user.getRoles() != null &&  !"".equals(user.getRoles())) {
				systemMapper.deleteUserRoleByUserId(userRole);
				if (user.getRoles().contains(",")) {
					String[] role = user.getRoles().split(",");
					for (int i = 0; i < role.length; i++) {
						if ( !"".equals(role[i]) && role[i] != null) {
							userRole.setRoleId(Integer.valueOf(role[i].trim()));
							systemMapper.addUserRole(userRole);
						}
					}
				} else {
					userRole.setRoleId(Integer.valueOf(user.getRoles().trim()));
					systemMapper.addUserRole(userRole);
				}
			}else{
				systemMapper.deleteUserRoleByUserId(userRole);
			}
		}else {
			user.setLoginPwd(MD5Util.getMD5ofStr(user.getLoginPwd()));
			systemMapper.addUser(user);
			// 3 添加用户角色关系
			if (user.getRoles() != null &&  !"".equals(user.getRoles())) {
				if (user.getRoles().contains(",")) {
					String[] role = user.getRoles().split(",");
					for (int i = 0; i < role.length; i++) {
						if ( !"".equals(role[i]) && role[i] != null) {
							UserRole userRole = new UserRole();
							userRole.setRoleId(Integer.valueOf(role[i].trim()));
							userRole.setUserId(user.getId());
							systemMapper.addUserRole(userRole);
						}
					}
				} else {
					UserRole userRole = new UserRole();
					userRole.setRoleId(Integer.valueOf(user.getRoles().trim()));
					userRole.setUserId(user.getId());
					systemMapper.addUserRole(userRole);
				}
			}
		}
		return ResponseCommObject.success();
	}

	@RequestMapping("/saveOrUpdOrg")
	@ResponseBody
	public Map<String, Object> addOrg(Organization organization) {
		if(organization.getId()!=null){
			systemMapper.updOrg(organization);
		}else {
			systemMapper.addOrg(organization);
		}
		return ResponseCommObject.success();
	}

	@RequestMapping("/deleteRole")
	@ResponseBody
	public Map<String, Object> deleteRole(Role role) {
		systemMapper.deleteRole(role);
		return ResponseCommObject.success();
	}

	@RequestMapping("/deleteOrg")
	@ResponseBody
	public Map<String, Object> deleteOrg(Organization organization) {
		systemMapper.deleteOrg(organization);
		return ResponseCommObject.success();
	}

	@RequestMapping("/getOrgById")
	@ResponseBody
	public Map<String, Object> getOrgById(Organization organization) {
		organization = systemMapper.getOrgById(organization);
		Map<String,Object> map = new HashMap<>();
		map.put("data",organization);
		return map;
	}
	@RequestMapping("/getUserById")
	@ResponseBody
	public Map<String, Object> getUserById(User user) {
		user = systemMapper.getUserById(user);
		Map<String,Object> map = new HashMap<>();
		map.put("data",user);
		return map;
	}

	@RequestMapping("/getRoleById")
	@ResponseBody
	public Map<String, Object> getRoleById(Role role) {
		role = systemMapper.getRoleById(role);
		Map<String,Object> map = new HashMap<>();
		map.put("data",role);
		return map;
	}

	@RequestMapping("/getUserRoleByUserId")
	@ResponseBody
	public Map<String, Object> getUserRoleByUserId(UserRole userRole) {
		List<UserRole> userRoles = systemMapper.getUserRoleByUserId(userRole);
		Map<String,Object> map = new HashMap<>();
		map.put("data",userRoles);
		return map;
	}
	@RequestMapping("/getRoleAuthByRoleId")
	@ResponseBody
	public Map<String, Object> getRoleAuthByRoleId(SystemRoleAuth roleAuth) {
		List<SystemRoleAuth> roleAuths = systemMapper.getRoleAuthByRoleId(roleAuth);
		Map<String,Object> map = new HashMap<>();
		map.put("data",roleAuths);
		return map;
	}

	@RequestMapping("/deleteUser")
	@ResponseBody
	public Map<String, Object> deleteUser(User user) {
		systemMapper.deleteUser(user);
		return ResponseCommObject.success();
	}
	@RequestMapping("/deleteDept")
	@ResponseBody
	public Map<String, Object> deleteDept(Dept dept) {
		systemMapper.deleteDept(dept);
		return ResponseCommObject.success();
	}
	@RequestMapping("/resetPwd")
	@ResponseBody
	public Map<String, Object> resetPwd(User user) {
		user.setLoginPwd(MD5Util.getMD5ofStr("123456"));
		systemMapper.resetPwd(user);
		return ResponseCommObject.success();
	}

	@RequestMapping(value="/registerAddRole", method= RequestMethod.GET)
	@ResponseBody
	public  Map<String, Object>    registerAddRole() {
		// 列出所有的权限
//		SystemUser user = (SystemUser) session.getAttribute("userInfo");
//		if("1".equals(user.getUser_type())){
//			auths = authService.getAllAuth();
//		} else {
			Map<String,Object> params = new HashMap<>();
//			params.put("id",1);
//			params.put("show",1);
			auths = systemMapper.getAllAuth(params);
//		}
		Map<String,Object> map = new HashMap<>();
		map.put("data",auths);
		return map;
	}

	@RequestMapping("/saveOrUpdateDept")
	@ResponseBody
	public Map<String, Object> saveOrUpdateDept(Organization org) {
		Map<String,Object> map = new HashMap<>();
		if( org.getDepts() != null && org.getDepts()!="" ) {
			String[] deptIds = org.getDepts().split(";");
			int errorResult = 0;
			int result2 = 0;
			for (int i = 0; i < deptIds.length; i++) {
				String[] deptInfo = deptIds[i].split(",");
				if (deptInfo.length == 2) {
					if (StringUtils.isEmpty(deptInfo[0]) || StringUtils.isEmpty(deptInfo[1])) {
						errorResult++;
						continue;
					}
				} else {
					errorResult++;
					continue;
				}
				Dept dept = new Dept();
				dept.setDeptName(deptInfo[1]);
				dept.setDeptCode(deptInfo[0]);
				dept.setOrgId(org.getId());
				systemMapper.addDept(dept);
				map.clear();
			}
		}
		return ResponseCommObject.success();
	}

	@RequestMapping("/selectDeptList")
	@ResponseBody
	public Map<String, Object> selectDeptList(Dept dept) {
		List<Dept> depts = systemMapper.selectDeptList(dept);
		Map<String,Object> map = new HashMap<>();
		map.put("data",depts);
		return map;
	}

	@RequestMapping("/selectConfigList")
	@ResponseBody
	public Map<String, Object> selectConfigList( SysDefaultVO sysDefaultVO) {
		List<SysDefaultVO> sysDefaultVOs = systemMapper.selectConfigList(sysDefaultVO);
		Map<String,Object> map = new HashMap<>();
		map.put("data",sysDefaultVOs);
		return map;
	}

	@RequestMapping("/updateConfig")
	@ResponseBody
	public Map<String, Object> updateConfig( SysDefaultVO sysDefaultVO) {
		systemMapper.updateConfig(sysDefaultVO);
		return ResponseCommObject.success();
	}
}
