package com.just.bean;

import java.util.Date;

/**
 * 图书信息了类
 * 
 * @author Administrator
 * 
 */
public class BookInfo {
	private Integer id;
	private String bookId; // 书号
	private String bookName;// 图书名字
	private String bookClassId;// 图书分类
	private Integer bookTypeId; // 图书类型(图书，期刊)
	private String autor; // 读者
	private String publishHouse; // 出版社
	private String publishTime; // 出版时间
	private Double price; // 价格
	private Integer borrowTimes; // 借阅次数
	private Integer totalNum; // 图书总量
	private Integer availNum; // 可用数量
	private String imgPath;// 图片路径
	private String description; // 图书简介
	private Integer saveTime;// 收藏次数
	private Integer place; // 存放地点
	private Date addTime;// 添加时间

	public BookInfo() {
		super();
	}

	public BookInfo(Integer id, String bookId, String bookName,
			String bookClassId, Integer bookTypeId, String autor,
			String publishHouse, String publishTime, Double price,
			Integer borrowTimes, Integer totalNum, Integer availNum,
			String imgPath, String description, Integer saveTime, Integer place,
			Date addTime) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.bookName = bookName;
		this.bookClassId = bookClassId;
		this.bookTypeId = bookTypeId;
		this.autor = autor;
		this.publishHouse = publishHouse;
		this.publishTime = publishTime;
		this.price = price;
		this.borrowTimes = borrowTimes;
		this.totalNum = totalNum;
		this.availNum = availNum;
		this.imgPath = imgPath;
		this.description = description;
		this.saveTime = saveTime;
		this.place = place;
		this.addTime = addTime;
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

	public void setBookClassId(String bookClassId) {
		this.bookClassId = bookClassId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Integer getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(Integer bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getPublishHouse() {
		return publishHouse;
	}

	public void setPublishHouse(String publishHouse) {
		this.publishHouse = publishHouse;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getBorrowTimes() {
		return borrowTimes;
	}

	public void setBorrowTimes(Integer borrowTimes) {
		this.borrowTimes = borrowTimes;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getAvailNum() {
		return availNum;
	}

	public void setAvailNum(Integer availNum) {
		this.availNum = availNum;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Integer saveTime) {
		this.saveTime = saveTime;
	}

	public Integer getPlace() {
		return place;
	}

	public void setPlace(Integer place) {
		this.place = place;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getBookClassId() {
		return bookClassId;
	}

	@Override
	public String toString() {
		return "BookInfo [id=" + id + ", bookId=" + bookId + ", bookName="
				+ bookName + ", bookClassId=" + bookClassId + ", bookTypeId="
				+ bookTypeId + ", autor=" + autor + ", publishHouse="
				+ publishHouse + ", publishTime=" + publishTime + ", price="
				+ price + ", borrowTimes=" + borrowTimes + ", totalNum="
				+ totalNum + ", availNum=" + availNum + ", imgPath=" + imgPath
				+ ", description=" + description + ", saveTime=" + saveTime
				+ ", place=" + place + "]";
	}

}
