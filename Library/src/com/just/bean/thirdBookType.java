package com.just.bean;

/**
 * 三级菜单
 * 
 * @author Administrator
 * 
 */
public class thirdBookType {
	private Integer id;
	private Integer bookClassId;
	private Integer secondClassId;
	private Integer thirdClassId;
	private String thirdClassName;

	public thirdBookType() {
		super();
	}

	public thirdBookType(Integer id, Integer bookClassId,
			Integer secondClassId, Integer thirdClassId, String thirdClassName) {
		super();
		this.id = id;
		this.bookClassId = bookClassId;
		this.secondClassId = secondClassId;
		this.thirdClassId = thirdClassId;
		this.thirdClassName = thirdClassName;
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

	public Integer getThirdClassId() {
		return thirdClassId;
	}

	public void setThirdClassId(Integer thirdClassId) {
		this.thirdClassId = thirdClassId;
	}

	public String getThirdClassName() {
		return thirdClassName;
	}

	public void setThirdClassName(String thirdClassName) {
		this.thirdClassName = thirdClassName;
	}

	@Override
	public String toString() {
		return "thirdBookType [id=" + id + ", bookClassId=" + bookClassId
				+ ", secondClassId=" + secondClassId + ", thirdClassId="
				+ thirdClassId + ", thirdClassName=" + thirdClassName + "]";
	}

}
