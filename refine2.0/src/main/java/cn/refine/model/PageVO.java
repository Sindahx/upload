package cn.refine.model;

public class PageVO {
	
	private int pageIndex;
	
	private int pageSize;
	
	private String sortName;
	
	private String sortOrder;
	
	public int getStartIndex() {
		return (pageIndex - 1) * pageSize;
	}
	
	public int getEndIndex() {
		return pageIndex * pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

}
