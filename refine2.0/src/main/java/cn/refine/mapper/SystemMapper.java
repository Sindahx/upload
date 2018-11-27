package cn.refine.mapper;

import cn.refine.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by zl on 2015/8/27.
 */
public interface SystemMapper {

	public List<User> findUserList(@Param("vo") User vo, @Param("pageVO") PageVO pageVO);
	public List<Role> findRoleList(@Param("vo") Role role, @Param("pageVO") PageVO pageVO);
	public List<LogVO> findLogList(PageVO pageVO);
	public List<Role> findOrgList(@Param("vo") Organization organization, @Param("pageVO") PageVO pageVO);
	public void addRole(Role role);

	public void addDept(Dept dept);
	public List<Dept> selectDeptList(Dept dept);
	List<SysDefaultVO> selectConfigList( SysDefaultVO sysDefaultVO);
	void updateConfig( SysDefaultVO sysDefaultVO);
	public void updRole(Role role);
	public void addOrg(Organization organization);
	public void updOrg(Organization organization);
	public void updUser(User user);
	public void updateUserInfo(User user);
	public void addUser(User user);
	public void addUserRole(UserRole userRole);
	public void addRoleAuth(SystemRoleAuth roleAuth);
	public void deleteUserRoleByUserId(UserRole userRole);
	public void deleteRoleAuthByRoleId(SystemRoleAuth roleAuth);
	public void deleteRole(Role role);
	public void deleteOrg(Organization organization);
	public Organization getOrgById(Organization organization);
	public User getUserById(User user);
	public Role getRoleById(Role role);
	public List<SystemAuth> getAllAuth(Map<String,Object> params);
	public List<UserRole> getUserRoleByUserId(UserRole userRole);
	public List<Map<String,Object>> getUserRoleByUserId1(UserRole userRole);
	public List<SystemRoleAuth> getRoleAuthByRoleId(SystemRoleAuth roleAuth);
	public void deleteUser(User user);
	public void deleteDept(Dept dept);
	public void resetPwd(User user);
	public void updPwd(User user);
	public void insertLog(LogVO vo);
	User getUserInfo(@Param("vo") User vo);
}
