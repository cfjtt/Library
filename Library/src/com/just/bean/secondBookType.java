package com.just.bean;

/**
 * 二级菜单
 * 
 * @author Administrator
 * 
 */
public class secondBookType {
	private Integer id;
	private Integer bookClassId;	//一级菜单id
	private Integer secondClassId;	//二级菜单id
	private String secondClassName;	//二级菜单名字

	public secondBookType() {
		super();
	}

	public secondBookType(Integer id, Integer bookClassId,
			Integer secondClassId, String secondClassName) {
		super();
		this.id = id;
		this.bookClassId = bookClassId;
		this.secondClassId = secondClassId;
		this.secondClassName = secondClassName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBookClassId() {
		return bookClassId;
	}

	public void setBookClassId(Integer bookClassId) {
		this.bookClassId = bookClassId;
	}

	public Integer getSecondClassId() {
		return secondClassId;
	}

	public void setSecondClassId(Integer secondClassId) {
		this.secondClassId = secondClassId;
	}

	public String getSecondClassName() {
		return secondClassName;
	}

	public void setSecondClassName(String secondClassName) {
		this.secondClassName = secondClassName;
	}

	@Override
	public String toString() {
		return "secondBookType [id=" + id + ", bookClassId=" + bookClassId
				+ ", secondClassId=" + secondClassId + ", secondClassName="
				+ secondClassName + "]";
	}

}
