package com.dt.derbyday.service;

import com.dt.derbyday.model.UserInfo;

public interface UserService {
	int addUser(UserInfo user);
	UserInfo queryUserByOpenId(String openId);
}
