package com.leaihealth.cloud.vo.response;

import java.util.Set;

/**
 * Created by Administrator on 2018/7/31/031.
 */
public class ResponseUserInfoVo {
    private Long id;
    private String userName;
    private Long deptId;
    private Integer orgId;
    private Integer userType;
    private String ids;
    private String roleName;
    private String loginName;
    private String email;
    private String telephone;
    private String orgName;
    private String type;
    private Integer status;
    private String note;
    private String createTime;
    private String deptName;
    private String pinyin;
    private String pinyinHeader;
    private String pinyinStr;
    private Set<Long> roles;
    private Set<Integer> roleDepts;
    private Integer sex;
    private Long remoteCount;

    public Set<Long> getRoles() {
        return roles;
    }

    public void setRoles(Set<Long> roles) {
        this.roles = roles;
    }

    public Set<Integer> getRoleDepts() {
        return roleDepts;
    }

    public void setRoleDepts(Set<Integer> roleDepts) {
        this.roleDepts = roleDepts;
    }

    public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getPinyinHeader() {
		return pinyinHeader;
	}

	public void setPinyinHeader(String pinyinHeader) {
		this.pinyinHeader = pinyinHeader;
	}

	public String getPinyinStr() {
		return pinyinStr;
	}

	public void setPinyinStr(String pinyinStr) {
		this.pinyinStr = pinyinStr;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getRemoteCount() {
        return remoteCount;
    }

    public void setRemoteCount(Long remoteCount) {
        this.remoteCount = remoteCount;
    }
}
