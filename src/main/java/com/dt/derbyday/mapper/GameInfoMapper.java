package com.dt.derbyday.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dt.derbyday.model.GameInfo;

@Repository
public interface GameInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GameInfo record);

    int insertSelective(GameInfo record);

    GameInfo selectByPrimaryKey(Integer id);
    
    GameInfo selectByGame(String game);
    
    List<GameInfo> selectAllGame();

    int updateByPrimaryKeySelective(GameInfo record);

    int updateByPrimaryKey(GameInfo record);
}