package com.just.bean;

/**
 * 分类查找结果bean
 * 
 * @author Administrator
 * 
 */
public class SelectAllBookResult {
	private Integer firstBookClassId;// 第一级分类id
	private String firstBookClassName;// 第一级分类名字
	private Integer secondBookClassId; // 第二级分类Id
	private String secondBookClassName; // 第二级分类名字
	private Integer thirdBookClassId;// 第三级分类Id
	private String thirdBookClassName;// 第三级分类名字

	public SelectAllBookResult() {
		super();
	}

	public SelectAllBookResult(Integer firstBookClassId,
			String firstBookClassName, Integer secondBookClassId,
			String secondBookClassName, Integer thirdBookClassId,
			String thirdBookClassName) {
		super();
		this.firstBookClassId = firstBookClassId;
		this.firstBookClassName = firstBookClassName;
		this.secondBookClassId = secondBookClassId;
		this.secondBookClassName = secondBookClassName;
		this.thirdBookClassId = thirdBookClassId;
		this.thirdBookClassName = thirdBookClassName;
	}

	public Integer getFirstBookClassId() {
		return firstBookClassId;
	}

	public void setFirstBookClassId(Integer firstBookClassId) {
		this.firstBookClassId = firstBookClassId;
	}

	public String getFirstBookClassName() {
		return firstBookClassName;
	}

	public void setFirstBookClassName(String firstBookClassName) {
		this.firstBookClassName = firstBookClassName;
	}

	public Integer getSecondBookClassId() {
		return secondBookClassId;
	}

	public void setSecondBookClassId(Integer secondBookClassId) {
		this.secondBookClassId = secondBookClassId;
	}

	public String getSecondBookClassName() {
		return secondBookClassName;
	}

	public void setSecondBookClassName(String secondBookClassName) {
		this.secondBookClassName = secondBookClassName;
	}

	public Integer getThirdBookClassId() {
		return thirdBookClassId;
	}

	public void setThirdBookClassId(Integer thirdBookClassId) {
		this.thirdBookClassId = thirdBookClassId;
	}

	public String getThirdBookClassName() {
		return thirdBookClassName;
	}

	public void setThirdBookClassName(String thirdBookClassName) {
		this.thirdBookClassName = thirdBookClassName;
	}

	@Override
	public String toString() {
		return "SelectAllBookResult [firstBookClassId=" + firstBookClassId
				+ ", firstBookClassName=" + firstBookClassName
				+ ", secondBookClassId=" + secondBookClassId
				+ ", secondBookClassName=" + secondBookClassName
				+ ", thirdBookClassId=" + thirdBookClassId
				+ ", thirdBookClassName=" + thirdBookClassName + "]";
	}

}
