package com.leaihealth.cloud.entity.idem;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * *用户模型对象)
 *
 * @author zhuxiange 2013-1-31 下午05:10:42
 */
@SequenceGenerator(name = HzUserInfo.SEQUENCE_NAME, sequenceName = HzUserInfo.SEQUENCE_NAME, allocationSize = 1)
@Entity
@Table(name = "HZ_USER_INFO")
public class HzUserInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "seq_hz_user_info";

    public HzUserInfo() {

    }

    public HzUserInfo(Long id) {
        this.id = id;
    }

    /**
     * 序列号)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Type(type = "pg-uuid")
    @Column
    private String uuid;

    // 用户姓名
    @Column
    private String userName;

    // 用户密码
    @Column
    private String password;

    // 登录名称
    @Column(name = "login_name")
    private String loginName;

    // 邮件
    @Column
    private String email;

    // 用户详细信息
    @Column(name = "user_type")
    private Integer userType;   //用户类型 1超级管理员 2用户 3机构管理员 4运营用户，5专家用户
    // 默认是1，当相同帐户失效后再次开启时新加入记录，且版本号加1

    // 当type为3是，user_code存的是医生表中的id,当type=2时，存的是居民信息表中的MPPID;
    @Column(name = "user_code")
    private String userCode;
    @Column(name = "sex")
    private Integer sex;
    @Transient
    private Integer age;
    @Transient
    private String userResource;
    @Transient
    private String serviceCode;
    // 签约结束时间
    // AFFILIATED_HOSPITAL
    private Date sign_endtime;
    // AFFILIATED_HOSPITAL
    private Date sign_begintime;

    private String headPath;

    private String telephone;


    @Column
    private Integer status;  //状态；1有效,0失效,99删除

    @Column
    private String abstracts;// 简介

    @Column(name = "main_skill")
    private String mainSkill; // 擅长

    @Column(name = "resource")
    private String resource;


    @Column
    private Integer docLevel; //专家等级；999机构管理员,99用户,0主任医师,1副主任医师,2主治医师,医师,4医生
    /**
     * 身份证号码
     */
    @Column
    private String cardCode;

    @Transient
    private String bday;
    
    @Transient
    private String memberId;
    @Transient
    private String accountName;
    @Transient
    private String address;
    /**
     * 是否认证
     */
    @Column
    private Integer isvalidate;  //是否验证;1认证,0未认证

    @Transient
    private String isExpert;

    @Column
    private String token;

    @Column
    private Date createTime;

    @Column(name = "creator")
    private Long creator;

    @Transient
    private Set<String> authorities = new HashSet<>();
    @Column
    private BigDecimal fee;

    @Transient
    private String sessionToken;

    @Transient
    private List<String> authoritys;

    @Transient
    private String siteId;

    @Transient
    private String idNumber;
/*
    @ManyToOne
    @JoinColumn(name = "org_code")
    private AffiliatedHospital org;

    @ManyToOne
    @JoinColumn(name = "dept_code")
    private OrgDeptCoupling dept;


    @ManyToMany(mappedBy = "userGroup", fetch = FetchType.EAGER)
    private Set<HzMdtUserGroup> mdtUserGroups = new HashSet<>();*/

    


	public String getBday() {
		return bday;
	}

	public void setBday(String bday) {
		this.bday = bday;
	}
    

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<String> getAuthoritys() {
        return authoritys;
    }

    public void setAuthoritys(List<String> authoritys) {
        this.authoritys = authoritys;
    }

//    public int getDbControl() {
//        return this.dbControl;
//    }
//
//    public void setDbControl(final int dbControl) {
//        this.dbControl = dbControl;
//    }
//
//    public int getWebServicecControl() {
//        return this.webServicecControl;
//    }
//
//    public void setWebServicecControl(final int webServicecControl) {
//        this.webServicecControl = webServicecControl;
//    }
//
//    public int getActiveMq() {
//        return this.activeMq;
//    }
//
//    public void setActiveMq(final int activeMq) {
//        this.activeMq = activeMq;
//    }

    public Date getSign_endtime() {
        return this.sign_endtime;
    }

    public void setSign_endtime(final Date sign_endtime) {
        this.sign_endtime = sign_endtime;
    }

    public Date getSign_begintime() {
        return this.sign_begintime;
    }

    public void setSign_begintime(final Date sign_begintime) {
        this.sign_begintime = sign_begintime;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    public String getHeadPath() {
        return this.headPath;
    }

    public void setHeadPath(final String headPath) {
        this.headPath = headPath;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(final String loginName) {
        this.loginName = loginName;
    }

    public Integer getUserType() {
        return this.userType;
    }

    public void setUserType(final Integer userType) {
        this.userType = userType;
    }

    public String getUserCode() {
        return this.userCode;
    }

    public void setUserCode(final String userCode) {
        this.userCode = userCode;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getIsExpert() {
        return this.isExpert;
    }

    public void setIsExpert(final String isExpert) {
        this.isExpert = isExpert;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(final Integer detail_status) {
        this.status = detail_status;
    }

    public String getCardCode() {
        return this.cardCode;
    }

    public void setCardCode(final String cardCode) {
        this.cardCode = cardCode;
    }

    public Integer getIsvalidate() {
        return this.isvalidate;
    }

    public void setIsvalidate(final Integer isvalidate) {
        this.isvalidate = isvalidate;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public Integer getDocLevel() {
        return docLevel;
    }

    public void setDocLevel(Integer docLevel) {
        this.docLevel = docLevel;
    }

    public String getAbstracts() {
        return this.abstracts;
    }

    public void setAbstracts(final String abstracts) {
        this.abstracts = abstracts;
    }

    public String getMainSkill() {
        return this.mainSkill;
    }

    public void setMainSkill(final String mainSkill) {
        this.mainSkill = mainSkill;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    //    public String getOrgLevel() {
//        return this.orgLevel;
//    }
//
//    public void setOrgLevel(final String orgLevel) {
//        this.orgLevel = orgLevel;
//    }

    /**
     * Returns the session token.
     *
     * @return the session token
     */
    public String getSessionToken() {
        return this.sessionToken;
    }

    /**
     * Sets the session token.
     *
     * @param theSessionToken the session token to set
     */
    public void setSessionToken(final String theSessionToken) {
        this.sessionToken = theSessionToken;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserResource() {
        return userResource;
    }

    public void setUserResource(String userResource) {
        this.userResource = userResource;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
