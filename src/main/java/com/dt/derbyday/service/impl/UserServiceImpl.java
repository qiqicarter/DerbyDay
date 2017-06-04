package com.dt.derbyday.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dt.derbyday.mapper.UserInfoMapper;
import com.dt.derbyday.model.UserInfo;
import com.dt.derbyday.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserInfoMapper mapper;

	public int addUser(UserInfo user) {
		return mapper.insert(user);

	}

	public UserInfo queryUserByOpenId(String openId) {
		return mapper.selectByOpen(openId);
	}

}
