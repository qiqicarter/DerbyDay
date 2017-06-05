package com.dt.derbyday.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dt.derbyday.dto.AddScore;
import com.dt.derbyday.dto.UserScoreDisplay;
import com.dt.derbyday.model.UserScore;

@Repository
public interface UserScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserScore record);

    int insertSelective(UserScore record);

    UserScore selectByPrimaryKey(Integer id);
    
    List<UserScoreDisplay> selectRank(String game);

    int updateByPrimaryKeySelective(UserScore record);

    int updateByPrimaryKey(UserScore record);
    
    int updateScoreBatch(AddScore	 record);
    
    int updateScoreByUserId(UserScore record);
}