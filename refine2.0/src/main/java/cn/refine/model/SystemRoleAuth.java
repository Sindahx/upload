package cn.refine.model;


/** 
*   *角色菜单权限)
* @author 朱湘鄂
*   2013-2-20 下午05:46:13
*  
*/
public class SystemRoleAuth {
	
	//菜单ID
	private Integer authId;
	
	//角色ID
	private Integer roleId;
	
	
	//不属于表中的字段
	private String authName;
	
	private String authPath;

	
	public Integer getAuthId() {
		return authId;
	}

	public void setAuthId(Integer authId) {
		this.authId = authId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getAuthPath() {
		return authPath;
	}

	public void setAuthPath(String authPath) {
		this.authPath = authPath;
	}

	
}
