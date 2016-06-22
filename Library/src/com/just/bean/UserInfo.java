package com.just.bean;

/**
 * 用户信息
 * 
 * @author Administrator
 * 
 */
public class UserInfo {
	private Integer id;
	private String loadId; // 登录id
	private String userName; // 用户名
	private String realName; // 真实名字
	private Integer root; // 权限
	private String password; // 密码
	private String phone; // 电话号码
	private String idCard; // 身份证
	private Integer isMiss; // 是否遗失
	private String email;
	private String imgPath;
	private Integer isAvail;
	public UserInfo() {
		super();
	}

	
	public Integer getIsAvail() {
		return isAvail;
	}


	public void setIsAvail(Integer isAvail) {
		this.isAvail = isAvail;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoadId() {
		return loadId;
	}

	public void setLoadId(String loadId) {
		this.loadId = loadId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getRoot() {
		return root;
	}

	public void setRoot(Integer root) {
		this.root = root;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getIsMiss() {
		return isMiss;
	}

	public void setIsMiss(Integer isMiss) {
		this.isMiss = isMiss;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Override
	public String toString() {
		return "Userinfo [id=" + id + ", loadId=" + loadId + ", userName="
				+ userName + ", realName=" + realName + ", root=" + root
				+ ", password=" + password + ", phone=" + phone + ", idCard="
				+ idCard + ", isMiss=" + isMiss + "]";
	}

}
