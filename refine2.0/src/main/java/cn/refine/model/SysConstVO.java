package cn.refine.model;

import java.util.Date;

public class SysConstVO {
	
	private Integer fid;
	
	private String codetype;
	
	private String codekey;
	
	private String codevalue;
	
	private Date lasttime;
	
	private Integer orderby;
	
	private String remark;
	
	private String state;

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getCodetype() {
		return codetype;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	public String getCodekey() {
		return codekey;
	}

	public void setCodekey(String codekey) {
		this.codekey = codekey;
	}

	public String getCodevalue() {
		return codevalue;
	}

	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}

	public Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
