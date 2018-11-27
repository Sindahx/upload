package cn.refine.model;


import java.io.Serializable;

/** 
*   *权限表)
* @author 朱湘鄂
*   2013-2-17 下午08:21:55
*  
*/
public class SystemAuth  implements Serializable {

	//序列号
	private Integer id;
	
	//权限名称
	private String authName;
	
	//权限路径
	private String path;
	
	// 权限的状态,0为禁用 1.启用
	private Integer status;
	
	//0为不需要记录,1需要记录日志
	private Integer needlog;
	
	private Integer type;
	
	private Integer pid;
	
	private Integer levels;//菜单层级

	private Integer show;//是否展示

	private String menuType;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getNeedlog() {
		return needlog;
	}

	public void setNeedlog(Integer needlog) {
		this.needlog = needlog;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

	public Integer getShow() {
		return show;
	}

	public void setShow(Integer show) {
		this.show = show;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
}
