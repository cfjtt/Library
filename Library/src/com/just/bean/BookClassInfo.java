package com.just.bean;

/**
 * 图书类型(期刊，图书)
 * 
 * @author Administrator
 * 
 */
public class BookClassInfo {
	private Integer id;
	private String bookTypeName;

	public BookClassInfo() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

}
