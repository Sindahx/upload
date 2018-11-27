package cn.refine.model;

/**
 * Created by Administrator on 2018/3/16.
 */
public class LogVO {
    private String userName;
    private String loginName;
    private String orgName;
    private String operFunc;
    private Integer operId;
    private String operTime;
    private String operIp;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOperFunc() {
        return operFunc;
    }

    public void setOperFunc(String operFunc) {
        this.operFunc = operFunc;
    }

    public Integer getOperId() {
        return operId;
    }

    public void setOperId(Integer operId) {
        this.operId = operId;
    }

    public String getOperTime() {
        return operTime;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime;
    }

    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }
}
