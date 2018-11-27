package cn.refine.model;

import java.io.Serializable;

/**
 * Created by zl on 2015/8/27.
 */
public class Role implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 14234234324554353L;
    private Integer id;
    private String roleName;
    private String description;
    private String orgCode;
    private Integer status;
    private String auths;

    public String getAuths() {
        return auths;
    }

    public void setAuths(String auths) {
        this.auths = auths;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
