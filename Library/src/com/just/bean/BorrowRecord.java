package com.just.bean;

import java.util.Date;

/**
 * 借阅记录
 * 
 * @author Administrator
 * 
 */
public class BorrowRecord {
	private Integer id;
	private String bookId;
	private String userId;
	private Date beginTime;
	private Date endTime;
	private Integer operate; // 0:预约 1:借阅为归还 2:借阅已归还 3:挂失
	private Integer isReBorrow;

	public BorrowRecord() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getOperate() {
		return operate;
	}

	public void setOperate(Integer operate) {
		this.operate = operate;
	}

	public Integer getIsReBorrow() {
		return isReBorrow;
	}

	public void setIsReBorrow(Integer isReBorrow) {
		this.isReBorrow = isReBorrow;
	}

	@Override
	public String toString() {
		return "BorrowRecord [id=" + id + ", bookId=" + bookId + ", userId="
				+ userId + ", beginTime=" + beginTime + ", endTime=" + endTime
				+ ", operate=" + operate + "]";
	}

}
