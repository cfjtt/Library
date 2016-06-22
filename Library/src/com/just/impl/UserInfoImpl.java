package com.just.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.just.bean.EasySearchResult;
import com.just.bean.UserInfo;
import com.just.dao.UserMapperDao;
import com.just.service.UserInfoService;

@Service("UserInfoService")
public class UserInfoImpl implements UserInfoService {
	@Resource
	private UserMapperDao userMapperDao;

	public UserInfo findByLoadId(Map<String, Object> map) {

		UserInfo userinfo = userMapperDao.findByLoadId(map);
		return userinfo;
	}

	@Transactional
	public int addUsers(UserInfo user) {
		return userMapperDao.addUsers(user);
	}

	public List<UserInfo> findAllUser(Map<String, Object> map) {

		return userMapperDao.findAllUser(map);
	}

	@Transactional
	public int deleteUserInfo(Integer id) {

		return userMapperDao.deleteUserInfo(id);
	}

	/**
	 * 判断借书证是否存在
	 */
	@Override
	public int findLoadIdIsExit(String loadId) {
		return userMapperDao.findLoadIdIsExit(loadId);
	}

	@Transactional
	public int modifyUserInfo(Map<String, Object> map) {
		return userMapperDao.modifyUserInfo(map);
	}

	public UserInfo findUserInfoByLoadId(String loadId) {
		return userMapperDao.findUserInfoByLoadId(loadId);
	}

	@Transactional
	public int updateImage(Map<String, Object> map) {
		
		return userMapperDao.updateImage(map);
	}

	@Transactional
	public int updateUserMsg(UserInfo userinfo) {
		
		return userMapperDao.updateUserMsg(userinfo);
	}

	@Transactional
	public int updateIsAvail(Map<String, Object> map) {
		return userMapperDao.updateIsAvail(map);
	}

	@Override
	public List<EasySearchResult> findAllIllegal(String loadId) {
		
		return userMapperDao.findAllIllegal(loadId);
	}

}
