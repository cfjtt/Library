package com.just.bean;

import java.util.Date;

/**
 * 评价
 * 
 * @author Administrator
 * 
 */
public class Evaluation {
	private Integer id;
	private String userId;
	private String bookId;
	private String description;
	private Integer star;
	private Integer support;
	private Integer against;
	private Date time;

	public Evaluation() {
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

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getSupport() {
		return support;
	}

	public void setSupport(Integer support) {
		this.support = support;
	}

	public Integer getAgainst() {
		return against;
	}

	public void setAgainst(Integer against) {
		this.against = against;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Evaluation [id=" + id + ", userId=" + userId + ", bookId="
				+ bookId + ", description=" + description + ", star=" + star
				+ ", support=" + support + ", against=" + against + "]";
	}

}
