package com.just.bean;

/**
 * 图书存放位置
 * 
 * @author Administrator
 * 
 */
public class savePlace {
	private Integer id;
	private String placeName;

	public savePlace() {
		super();
	}

	public savePlace(Integer id, String placeName) {
		super();
		this.id = id;
		this.placeName = placeName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	@Override
	public String toString() {
		return "savePlace [id=" + id + ", placeName=" + placeName + "]";
	}

}
