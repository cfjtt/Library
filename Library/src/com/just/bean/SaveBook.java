package com.just.bean;

import java.util.Date;
/**
 * 收藏书籍
 * @author Administrator
 *
 */
public class SaveBook {
	private Integer id;
	private String bookId;
	private String userId;
	private Date saveTime;

	public SaveBook() {
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

	public Date getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

}
