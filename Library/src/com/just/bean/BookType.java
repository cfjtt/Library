package com.just.bean;

/**
 * 图书类型(一级菜单)
 * 
 * @author Administrator
 * 
 */
public class BookType {
	private Integer id;
	private String bookClassId; // 图书类别ID
	private String bookClass; // 图书类型类别

	public BookType() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookClassId() {
		return bookClassId;
	}

	public void setBookClassId(String bookClassId) {
		this.bookClassId = bookClassId;
	}

	public String getBookClass() {
		return bookClass;
	}

	public void setBookClass(String bookClass) {
		this.bookClass = bookClass;
	}

	@Override
	public String toString() {
		return "BookType [id=" + id + ", bookClassId=" + bookClassId
				+ ",  bookClass=" + bookClass + "]";
	}

}
