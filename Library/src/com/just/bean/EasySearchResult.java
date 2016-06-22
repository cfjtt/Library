package com.just.bean;

import java.util.Date;

/**
 * 简单搜索图书结果bean
 * 
 * @author Administrator
 * 
 */
public class EasySearchResult {

	private Integer id;
	private String bookName; // 图书名字
	private String bookId; // 索引号
	private Integer bookTypeId;// 图书/期刊
	private String autor;// 作者
	private String publishHouse; // 出版社
	private String publishTime; // 出版时间
	private Integer totalNum; // 图书总量
	private Integer availNum; // 可用总量(判断是否是可借状态)
	private Integer place;
	private String placeName;
	private Integer star; // 总体星星的数目
	private Integer evaluationNum; // 总共评价的人数
	private Date addTime;
	private String bookTypeName;
	private String bookClassId;
	private String description;
	private Integer price;
	private Integer order; // 预约数量
	private Integer borrowTimes; // 借阅数量
	private Date startTime;
	private Date endTime;
	private String realName;
	private Double money;
	private Integer operate;
	private String loadId;
	private Integer saveTime;
	private String phone;
	private String idCard;

	public EasySearchResult() {
		super();
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Integer saveTime) {
		this.saveTime = saveTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getBorrowTimes() {
		return borrowTimes;
	}

	public void setBorrowTimes(Integer borrowTimes) {
		this.borrowTimes = borrowTimes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
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

	public Integer getPlace() {
		return place;
	}

	public void setPlace(Integer place) {
		this.place = place;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getEvaluationNum() {
		return evaluationNum;
	}

	public void setEvaluationNum(Integer evaluationNum) {
		this.evaluationNum = evaluationNum;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	public String getBookClassId() {
		return bookClassId;
	}

	public void setBookClassId(String bookClassId) {
		this.bookClassId = bookClassId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getOperate() {
		return operate;
	}

	public void setOperate(Integer operate) {
		this.operate = operate;
	}

	public String getLoadId() {
		return loadId;
	}

	public void setLoadId(String loadId) {
		this.loadId = loadId;
	}

	@Override
	public String toString() {
		return "EasySearchResult [id=" + id + ", bookName=" + bookName
				+ ", bookId=" + bookId + ", bookTypeId=" + bookTypeId
				+ ", autor=" + autor + ", publishHouse=" + publishHouse
				+ ", publishTime=" + publishTime + ", totalNum=" + totalNum
				+ ", availNum=" + availNum + ", place=" + place
				+ ", placeName=" + placeName + ", star=" + star
				+ ", evaluationNum=" + evaluationNum + ", addTime=" + addTime
				+ ", bookTypeName=" + bookTypeName + ", bookClassId="
				+ bookClassId + ", description=" + description + ", price="
				+ price + ", order=" + order + ", borrowTimes=" + borrowTimes
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", realName=" + realName + ", money=" + money + ", operate="
				+ operate + ", loadId=" + loadId + ", saveTime=" + saveTime
				+ "]";
	}

}
