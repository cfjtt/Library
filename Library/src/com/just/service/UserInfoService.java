package com.just.service;

import java.util.List;
import java.util.Map;

import com.just.bean.EasySearchResult;
import com.just.bean.UserInfo;

public interface UserInfoService {
	/**
	 * 登录
	 */
	public UserInfo findByLoadId(Map<String,Object> map);

	/**
	 * 注册
	 */
	public int addUsers(UserInfo user);

	/**
	 * 查找所有用户
	 */
	public List<UserInfo> findAllUser(Map<String, Object> map);
	
	/**
	 * 删除用户
	 */
	public int  deleteUserInfo(Integer id);
	/**
	 * 用户借书证是否存在
	 */
	public int findLoadIdIsExit(String loadId);
	
	/**
	 * 修改用户
	 */
	public int modifyUserInfo(Map<String,Object> map);
	/**
	 * 根据Id查找用户的信息
	 */
	public UserInfo findUserInfoByLoadId(String loadId);
	
	/**
	 * 更新imgPath
	 */
	public int updateImage(Map<String,Object> map);
	/**
	 * 修改密码
	 */
	public int updateUserMsg(UserInfo info);
	/**
	 * 修改状态
	 */
	public int updateIsAvail(Map<String,Object> map);
	/**
	 * 查找用户的违规信息
	 */
	public List<EasySearchResult> findAllIllegal(String loadId);
}
