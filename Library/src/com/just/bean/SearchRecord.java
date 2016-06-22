package com.just.bean;

import java.util.Date;

/**
 * 搜索记录
 * 
 * @author Administrator
 * 
 */
public class SearchRecord {
	private Integer id;
	private Integer userId;
	private Integer bookId;
	private Date time;

	public SearchRecord() {
		super();
	}

	public SearchRecord(Integer id, Integer userId, Integer bookId, Date time) {
		super();
		this.id = id;
		this.userId = userId;
		this.bookId = bookId;
		this.time = time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "SearchRecord [id=" + id + ", userId=" + userId + ", bookId="
				+ bookId + ", time=" + time + "]";
	}

}
