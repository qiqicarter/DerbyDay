package com.dt.derbyday.mapper;

import org.springframework.stereotype.Repository;

import com.dt.derbyday.model.UserInfo;
@Repository
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);
    
    UserInfo selectByOpen(String open);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}