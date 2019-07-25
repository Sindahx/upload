/**
 * ClassName: LoginVo.java
 *
 * Version Information:
 *
 * Date: 2018/06/06
 *
 * COPYRIGHT (C) 2018 RETair.com. All Rights Reserved.
 */
package com.leaihealth.cloud.vo;

/**
 * @author Joseph S. Kuo
 * @since 0.0.1, 2018/06/06
 */
public class LoginVo {
    //登录名称
    private String loginName;

    private String password;

    
    //用户姓名
    private String userName;


    //邮件
    private String email;



    //用户详细信息
    private String user_type;
    //默认是1，当相同帐户失效后再次开启时新加入记录，且版本号加1

    //当type为3是，user_code存的是医生表中的id,当type=2时，存的是居民信息表中的MPPID;
    private String user_code;

    public String getUserDes() {
        return userDes;
    }

    public void setUserDes(String userDes) {
        this.userDes = userDes;
    }

    private String userDes;
    private String deptCode;

    private String role_id;

    private String deptName;

    private String orgCode;

    private String orgName;

    private String  orgLevel;
    //签约结束时间
    private String sign_endtime;

    private String sign_begintime;

    private String changeCount;
    
    private String headPath;

    private String mobile;

    private String type;//角色类型

    private String loginType;//是中心登录，还是区县登录

    private String loginUrl;//返回区县的地址

    private String areaName;

    private String areaPassword;

    private String ftpAddr;

    private String ftpUsername;

    private String ftpPwd;

    private String detail_status;

    private String depts;//有权限的科室代码，全院时为空字符串

    private String hos_status;

    private String deptMgr;
    private String webServiceAddress;
    private String hospitalTel;
    private String yuser;
    private String ypassword;
    private String yorgid;
    private String sign;
    private Boolean isApply;
    private String orgType;
    private String abstracts;//简介
    private String mainSkill; //擅长
    private Integer video_controller;
    private String address;
    private String hospitalPath;
    private String postcode;
    private String emrControl;

    private String docLevel;
    
    private String KeyId;
    private String isKeyLogin = "0";//区别是密码登录还是key登录的。默认是密码登录
    private String hosStatus;
    
    

    
    public String getHosStatus() {
		return hosStatus;
	}

	public void setHosStatus(String hosStatus) {
		this.hosStatus = hosStatus;
	}

	/**
     * 身份证号码
     */
    private String idcard;

    /**
     * 是否认证
     */
    private String isvalidate;
    /**
     * Returns the login name.
     *
     * @return the login name
     */
    public String getLoginName() {
        return this.loginName;
    }

    /**
     * Sets the login name.
     *
     * @param loginName the login name
     */
    public void setLoginName(final String loginName) {
        this.loginName = loginName;
    }

    /**
     * Returns the password.
     *
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getSign_endtime() {
		return sign_endtime;
	}

	public void setSign_endtime(String sign_endtime) {
		this.sign_endtime = sign_endtime;
	}

	public String getSign_begintime() {
		return sign_begintime;
	}

	public void setSign_begintime(String sign_begintime) {
		this.sign_begintime = sign_begintime;
	}

	public String getChangeCount() {
		return changeCount;
	}

	public void setChangeCount(String changeCount) {
		this.changeCount = changeCount;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaPassword() {
		return areaPassword;
	}

	public void setAreaPassword(String areaPassword) {
		this.areaPassword = areaPassword;
	}

	public String getFtpAddr() {
		return ftpAddr;
	}

	public void setFtpAddr(String ftpAddr) {
		this.ftpAddr = ftpAddr;
	}

	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	public String getFtpPwd() {
		return ftpPwd;
	}

	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}

	public String getDetail_status() {
		return detail_status;
	}

	public void setDetail_status(String detail_status) {
		this.detail_status = detail_status;
	}

	public String getDepts() {
		return depts;
	}

	public void setDepts(String depts) {
		this.depts = depts;
	}

	public String getHos_status() {
		return hos_status;
	}

	public void setHos_status(String hos_status) {
		this.hos_status = hos_status;
	}

	public String getDeptMgr() {
		return deptMgr;
	}

	public void setDeptMgr(String deptMgr) {
		this.deptMgr = deptMgr;
	}

	public String getWebServiceAddress() {
		return webServiceAddress;
	}

	public void setWebServiceAddress(String webServiceAddress) {
		this.webServiceAddress = webServiceAddress;
	}

	public String getHospitalTel() {
		return hospitalTel;
	}

	public void setHospitalTel(String hospitalTel) {
		this.hospitalTel = hospitalTel;
	}

	public String getYuser() {
		return yuser;
	}

	public void setYuser(String yuser) {
		this.yuser = yuser;
	}

	public String getYpassword() {
		return ypassword;
	}

	public void setYpassword(String ypassword) {
		this.ypassword = ypassword;
	}

	public String getYorgid() {
		return yorgid;
	}

	public void setYorgid(String yorgid) {
		this.yorgid = yorgid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Boolean getIsApply() {
		return isApply;
	}

	public void setIsApply(Boolean isApply) {
		this.isApply = isApply;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getAbstracts() {
		return abstracts;
	}

	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}

	public String getMainSkill() {
		return mainSkill;
	}

	public void setMainSkill(String mainSkill) {
		this.mainSkill = mainSkill;
	}

	public Integer getVideo_controller() {
		return video_controller;
	}

	public void setVideo_controller(Integer video_controller) {
		this.video_controller = video_controller;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHospitalPath() {
		return hospitalPath;
	}

	public void setHospitalPath(String hospitalPath) {
		this.hospitalPath = hospitalPath;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getEmrControl() {
		return emrControl;
	}

	public void setEmrControl(String emrControl) {
		this.emrControl = emrControl;
	}

	public String getDocLevel() {
		return docLevel;
	}

	public void setDocLevel(String docLevel) {
		this.docLevel = docLevel;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getIsvalidate() {
		return isvalidate;
	}

	public void setIsvalidate(String isvalidate) {
		this.isvalidate = isvalidate;
	}

	public String getKeyId() {
		return KeyId;
	}

	public void setKeyId(String keyId) {
		KeyId = keyId;
	}

	public String getIsKeyLogin() {
		return isKeyLogin;
	}

	public void setIsKeyLogin(String isKeyLogin) {
		this.isKeyLogin = isKeyLogin;
	}
    
    
}
