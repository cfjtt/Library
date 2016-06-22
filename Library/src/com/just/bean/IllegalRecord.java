package com.just.bean;

import java.util.Date;

/**
 * 违规记录
 * 
 * @author Administrator
 * 
 */
public class IllegalRecord {
	private Integer id;
	private String userId;
	private Date startTime;
	private Date endTime;
	private Integer type;
	private Integer operate;
	private String bookId;

	public IllegalRecord() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOperate() {
		return operate;
	}

	public void setOperate(Integer operate) {
		this.operate = operate;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	@Override
	public String toString() {
		return "IllegalRecord [id=" + id + ", userId=" + userId
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", type=" + type + ", operate=" + operate + ", bookId="
				+ bookId + "]";
	}
	
}
